package io.zipkin.brave;

import java.util.Map;

import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;

import brave.Span;
import brave.Tracer;
import brave.propagation.CurrentTraceContext;
import brave.propagation.SamplingFlags;
import brave.propagation.TraceContext;
import brave.propagation.TraceContext.Extractor;
import brave.propagation.TraceContext.Injector;
import brave.propagation.TraceContextOrSamplingFlags;

public class DubboClientHandler extends DubboHandler {
	final Tracer tracer;
	final DubboClientParser parser;
	final CurrentTraceContext currentTraceContext;

	public static DubboClientHandler create(DubboTracing dubboTracing) {
		return new DubboClientHandler(dubboTracing);
	}

	DubboClientHandler(DubboTracing dubboTracing) {
		this.tracer = dubboTracing.tracing().tracer();
		this.parser = dubboTracing.clientParser();
		this.currentTraceContext = dubboTracing.tracing().currentTraceContext();
	}

	@Override
	public Span onServerReceive(Extractor extractor) {
		return null;
	}

	@Override
	public Span nextSpan(TraceContextOrSamplingFlags extracted) {
		TraceContext parent = currentTraceContext.get();
		if (parent == null) {
			parent = extracted.context();
		}
		if (parent != null) {
			System.out.println("new span");
			return tracer.newChild(parent);
		}
		System.out.println("new trace");
		return tracer.newTrace(SamplingFlags.NOT_SAMPLED);
	}

	@Override
	public void onServerSend(Result rpcResult, Span span) {
	}

	@Override
	public Span onClientSend(Extractor extractor, Injector injector) {
		Map<String, String> map = RpcContext.getContext().getAttachments();
		System.out.println("DubboClientHandler.onClientSend():"+map.toString());
		Span span = nextSpan(extractor.extract(map));
		injector.inject(span.context(), map);
		if (span.isNoop())
			return span;
		span.kind(Span.Kind.CLIENT);
		Tracer.SpanInScope ws = tracer.withSpanInScope(span);
		try {
			parser.request(RpcContext.getContext(), span);
		} finally {
			ws.close();
		}
		return span.start();
	}

	@Override
	public void onClientReceive(Result rpcResult, Span span) {
		if (span.isNoop())
			return;
		Tracer.SpanInScope ws = tracer.withSpanInScope(span);
		try {
			parser.response(rpcResult, span);
		} finally {
			ws.close();
			span.finish();
		}
	}

}

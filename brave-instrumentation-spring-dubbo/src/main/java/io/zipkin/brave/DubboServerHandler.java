package io.zipkin.brave;

import java.util.Map;

import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;

import brave.Span;
import brave.Tracer;
import brave.propagation.TraceContext.Extractor;
import brave.propagation.TraceContext.Injector;
import brave.propagation.TraceContextOrSamplingFlags;

public class DubboServerHandler extends DubboHandler {
	final Tracer tracer;
	final DubboServerParser parser;

	DubboServerHandler(DubboTracing dubboTracing) {
		this.tracer = dubboTracing.tracing().tracer();
		this.parser = dubboTracing.serverParser();
	}

	public static DubboServerHandler create(DubboTracing dubboTracing) {
		return new DubboServerHandler(dubboTracing);
	}

	@Override
	public Span onServerReceive(Extractor extractor) {
		Map<String, String> map = RpcContext.getContext().getAttachments();
		System.out.println("DubboServerHandler.onServerReceive():"+map.toString());
		Span span = nextSpan(extractor.extract(map));
		if (span.isNoop())
			return span;
		span.kind(Span.Kind.SERVER);
		Tracer.SpanInScope ws = tracer.withSpanInScope(span);
		try {
			parser.request(RpcContext.getContext(), span);
		} finally {
			ws.close();
		}
		return span.start();
	}

	@Override
	public Span nextSpan(TraceContextOrSamplingFlags extracted) {
		if (extracted.sampled() == null) {
			extracted = extracted.sampled(false);//RealSpan
		}
		if( extracted.context() != null) {
			System.out.println("加入trance");
			return tracer.joinSpan(extracted.context());
		}
		System.out.println("创建新的span");
		return tracer.nextSpan(extracted);
	}

	@Override
	public void onServerSend(Result rpcResult, Span span) {
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

	@Override
	public Span onClientSend(Extractor extractor, Injector injector) {
		return null;
	}

	@Override
	public void onClientReceive(Result rpcResult, Span span) {
	}
}

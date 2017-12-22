package io.zipkin.brave;

import com.alibaba.dubbo.rpc.Result;

import brave.Span;
import brave.propagation.TraceContext;
import brave.propagation.TraceContextOrSamplingFlags;

public abstract class DubboHandler {
	public abstract Span nextSpan(TraceContextOrSamplingFlags extracted);

	/** server */
	public abstract Span onServerReceive(TraceContext.Extractor extractor);

	public abstract void onServerSend(Result rpcResult, Span span);

	/** client */
	public abstract Span onClientSend(TraceContext.Extractor extractor, TraceContext.Injector injector);

	public abstract void onClientReceive(Result rpcResult, Span span);
}

package io.zipkin.brave;

import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;

import brave.SpanCustomizer;

public class DubboServerParser extends DubboParser {

	/**
	 * server send
	 */
	@Override
	public void request(RpcContext rpcContext, SpanCustomizer customizer) {
		customizer.name(spanName(rpcContext));
		String path = getRemoteAddress(rpcContext);
		if (path != null) {
			customizer.tag("consumer.address", path);
		}
	}

	/**
	 * server received
	 */
	@Override
	public void response(Result rpcResult, SpanCustomizer customizer) {
		if (!rpcResult.hasException()) {
			customizer.tag("provider.result", "true");
		} else {
			customizer.tag("provider.exception", rpcResult.getException().getMessage());
		}
	}

}

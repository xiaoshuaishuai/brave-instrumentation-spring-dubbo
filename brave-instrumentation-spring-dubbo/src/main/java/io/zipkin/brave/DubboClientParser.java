package io.zipkin.brave;

import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;

import brave.SpanCustomizer;

public class DubboClientParser extends DubboParser {
	/**
	 * 
	 * client send
	 * 
	 */
	@Override
	public void request(RpcContext rpcContext, SpanCustomizer customizer) {
		customizer.name(spanName(rpcContext));
		String path = getRemoteAddress(rpcContext);
		if (path != null) {
			customizer.tag("provider.address", path);
		}
	}

	/**
	 * client received
	 */
	@Override
	public void response(Result rpcResult, SpanCustomizer customizer) {
		if (!rpcResult.hasException()) {
			customizer.tag("consumer.result", "true");
		} else {
			customizer.tag("consumer.exception", rpcResult.getException().getMessage());
		}
	}
}

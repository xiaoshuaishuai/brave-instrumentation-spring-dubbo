package io.zipkin.brave;

import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;

import brave.SpanCustomizer;

public abstract class DubboParser {

	public abstract void request(RpcContext rpcContext, SpanCustomizer customizer);

	public abstract void response(Result rpcResult, SpanCustomizer customizer);
	/**
	 * spnaname 
	 */
	public String spanName(RpcContext rpcContext) {
		String path = rpcContext.getUrl().getPath();
		String spanName = path.substring(path.lastIndexOf(".") + 1);
		return spanName + "." + RpcContext.getContext().getMethodName();
	}
	/**
	 * ip address
	 */
	public String getRemoteAddress(RpcContext rpcContext) {
		return rpcContext.getRemoteHost();
	}

}

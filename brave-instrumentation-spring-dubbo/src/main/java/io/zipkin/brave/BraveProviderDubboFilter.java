package io.zipkin.brave;

import java.util.Map;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;

import brave.Span;
import brave.propagation.TraceContext;

@Activate(group = { Constants.PROVIDER })
public class BraveProviderDubboFilter implements Filter {

	private DubboServerHandler handler;
	private TraceContext.Extractor<Map<String, String>> extractor;

	public void setDubboTracing(DubboTracing dubboTracing) {
		handler = DubboServerHandler.create(dubboTracing);
		extractor = dubboTracing.tracing().propagation().extractor(Map::get);
	}
	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

		Map<String, String> map = RpcContext.getContext().getAttachments();
		RpcContext.getContext().setAttachments(map);
		System.out.println("BraveProviderDubboFilter.invoke():"+map.toString());
		System.out.println("BraveProviderDubboFilter onServerReceive= :"+invocation.getMethodName());
		Span span = handler.onServerReceive(extractor);
		Result rpcResult = invoker.invoke(invocation);
		handler.onServerSend(rpcResult, span);
		System.out.println("BraveProviderDubboFilter onServerSend= ");
		return rpcResult;
	}

}

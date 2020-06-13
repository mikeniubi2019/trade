package com.mike.trade.netty.channelHandler;

import com.mike.trade.pojo.servicePojo.Request;
import com.mike.trade.pojo.servicePojo.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;

public class RecieveResponseChannelHandler extends SimpleChannelInboundHandler {
    private Map<Long,Request> requestMap;
    public RecieveResponseChannelHandler(Map<Long, Request> requestMap) {
        this.requestMap = requestMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        if (o instanceof Response){
            Response response = (Response)o;
            Request request = requestMap.get(response.getResponseId());
            if (request==null) {
                return;
            }
            if (response.isSuccess()){
                requestMap.remove(request.getRequestId());
            }
            synchronized (request){
                request.notifyAll();
            }
            //TODO 接收到消息
        }
    }
}

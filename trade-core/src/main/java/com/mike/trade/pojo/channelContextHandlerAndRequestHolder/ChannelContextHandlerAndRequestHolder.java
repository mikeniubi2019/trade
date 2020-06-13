package com.mike.trade.pojo.channelContextHandlerAndRequestHolder;

import com.mike.trade.pojo.servicePojo.Request;
import io.netty.channel.ChannelHandlerContext;

public class ChannelContextHandlerAndRequestHolder {
    private ChannelHandlerContext channelHandlerContext;
    private Request request;

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }

    public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}

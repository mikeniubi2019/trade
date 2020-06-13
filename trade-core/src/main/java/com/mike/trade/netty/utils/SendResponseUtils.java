package com.mike.trade.netty.utils;

import com.mike.trade.pojo.servicePojo.Response;
import io.netty.channel.ChannelHandlerContext;

public class SendResponseUtils {
    private static Response responseTemplet = new Response();
    public static void sendResponse(long id, boolean success, ChannelHandlerContext channelHandlerContext){
        Response response = null;
        try {
            response = (Response) responseTemplet.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        response.setSuccess(success);
        response.setResponseId(id);
        channelHandlerContext.writeAndFlush(response);
    }
}

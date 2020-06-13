package com.mike.trade.netty;


import com.lmax.disruptor.RingBuffer;
import com.mike.trade.pojo.channelContextHandlerAndRequestHolder.ChannelContextHandlerAndRequestHolder;
import com.mike.trade.pojo.servicePojo.Request;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@ChannelHandler.Sharable
public class PutRequestToDisruptorHandler extends ChannelInboundHandlerAdapter {
    private RingBuffer<ChannelContextHandlerAndRequestHolder> requestHolderRingBuffer;
    //congchannel 投递进 disruptor
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Request){
            Request request = (Request)msg;
            long seq = requestHolderRingBuffer.next();
            ChannelContextHandlerAndRequestHolder channelContextHandlerAndRequestHolder = requestHolderRingBuffer.get(seq);
            channelContextHandlerAndRequestHolder.setRequest(request);
            channelContextHandlerAndRequestHolder.setChannelHandlerContext(ctx);
            requestHolderRingBuffer.publish(seq);
        }else {
            ctx.fireChannelRead(msg);
        }
    }

    public PutRequestToDisruptorHandler(RingBuffer<ChannelContextHandlerAndRequestHolder> requestHolderRingBuffer) {
        this.requestHolderRingBuffer = requestHolderRingBuffer;
    }
}

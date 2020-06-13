package com.mike.trade.netty.codec;

import com.jfireframework.fse.ByteArray;
import com.jfireframework.fse.Fse;
import com.mike.trade.pojo.servicePojo.Request;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

@ChannelHandler.Sharable
public class ClientRequestEncode extends MessageToByteEncoder<Request> {
    private ThreadLocal<Fse> requestThreadLocal = new ThreadLocal<>();
    private ThreadLocal<ByteArray> byteArrayThreadLocal = new ThreadLocal<>();
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Request request, ByteBuf byteBuf) throws Exception {
        Fse fse = requestThreadLocal.get();
        ByteArray byteArray = ByteArray.allocate();
        if (fse==null){
            fse = new Fse();
            requestThreadLocal.set(fse);
        }
        if (byteArray==null){
            byteArray = ByteArray.allocate();
            byteArrayThreadLocal.set(byteArray);
        }
        byteArray.clear();
        fse.serialize(request,byteArray);
        byte[] bytes = byteArray.toArray();
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(byteBuf);
    }
}

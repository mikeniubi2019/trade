package com.mike.trade.netty.codec;

import com.jfireframework.fse.ByteArray;
import com.jfireframework.fse.Fse;
import com.mike.trade.pojo.servicePojo.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ResponseFseEncode extends MessageToByteEncoder<Response> {

    private Fse fse = new Fse();
    private ByteArray byteArray = ByteArray.allocate();
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Response response, ByteBuf byteBuf) throws Exception {

        byteArray.clear();
        fse.serialize(response,byteArray);
        byte[] bytes = byteArray.toArray();
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }
}

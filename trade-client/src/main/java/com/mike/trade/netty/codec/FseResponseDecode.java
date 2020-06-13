package com.mike.trade.netty.codec;

import com.jfireframework.fse.ByteArray;
import com.jfireframework.fse.Fse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ReferenceCountUtil;

import java.util.List;


public class FseResponseDecode extends ByteToMessageDecoder {
    private Fse fse = new Fse();
    private ByteArray byteArray = ByteArray.allocate();

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        byteArray.clear();
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        byteArray.put(bytes);
        list.add(fse.deSerialize(byteArray));
    }

}

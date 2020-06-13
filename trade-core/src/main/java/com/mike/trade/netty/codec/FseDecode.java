package com.mike.trade.netty.codec;

import com.jfireframework.fse.ByteArray;
import com.jfireframework.fse.Fse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;


public class FseDecode extends ByteToMessageDecoder {

    private Fse fse = new Fse();
    private ByteArray byteArray = ByteArray.allocate();
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int length = byteBuf.readInt();
        if (length<1) return;
        byteArray.clear();
        byte[] by2 = new byte[length];
        for (int index=0;index<length;index++){
            by2[index]=byteBuf.readByte();
        }
        byteArray.put(by2);
        list.add(fse.deSerialize(byteArray));
    }

}

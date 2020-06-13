package com.mike.trade.synchronize.netty.handler;

import com.mike.trade.synchronize.pojo.TransferFile;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.nio.ByteBuffer;

public class ClusterDeliverHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof TransferFile){
            TransferFile transferFile = (TransferFile) msg;
            byte[] bytes = transferFile.getDatas();
            String path = transferFile.getLocationPath();
            File file = new File(path);
            if (file.exists()){
                return;
            }
            if (file.getName().endsWith(".mlog")){
                ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
                while (byteBuffer.hasRemaining()){
                    int length = byteBuffer.getInt();
                }
            }
        }
    }

}

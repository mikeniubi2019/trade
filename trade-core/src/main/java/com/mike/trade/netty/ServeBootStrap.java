package com.mike.trade.netty;

import com.lmax.disruptor.RingBuffer;
import com.mike.trade.netty.codec.FseDecode;
import com.mike.trade.netty.codec.ResponseFseEncode;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.AbstractByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

import java.util.ArrayList;
import java.util.List;


public class ServeBootStrap {
    private ServerBootstrap serverBootstrap;
    private Channel channel;
    private String address;
    private int port;
    private EventLoopGroup boss;
    private EventLoopGroup wooker;
    private List<ChannelHandler> channelHandlerList=new ArrayList<>();
    private RingBuffer ringBuffer;

    public void init(){

        this.serverBootstrap = new ServerBootstrap();

        boss = new NioEventLoopGroup();
        wooker = new NioEventLoopGroup();

        serverBootstrap.group(boss,wooker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                .option(ChannelOption.ALLOCATOR, AbstractByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .handler(new LoggingHandler())
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline channelPipeline = socketChannel.pipeline();
                        channelPipeline.addLast(new LengthFieldBasedFrameDecoder(1024*1024,0,4,0,0));
                        channelPipeline.addLast(new FseDecode());
                        channelPipeline.addLast(new ResponseFseEncode());
                        //TODO addServiceChannelHandler
                        channelPipeline.addLast(new PutRequestToDisruptorHandler(ringBuffer));
                    }
                });
    }

    public void start() throws InterruptedException {
        channel=serverBootstrap.bind(address,port).sync().channel();
    }

    public void shutdown(){
        this.channel.close();
        this.boss.shutdownGracefully();
        this.wooker.shutdownGracefully();
    }

    public ServeBootStrap(String address, int port,RingBuffer ringBuffer) {
        this.address = address;
        this.port = port;
        this.ringBuffer=ringBuffer;
    }

    public void addSirilizerHandler(ChannelHandler channelHandler){
        this.channelHandlerList.add(channelHandler);
    }
}

package com.mike.trade.synchronize.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class LogServeBootstrap {
    private int port;
    private String address;
    private EventLoopGroup boss;
    private EventLoopGroup worker;
    private Channel channel;

    public void start(){
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();
        serverBootstrap.group(boss,worker)
                        .channel(NioServerSocketChannel.class)
                        .option(ChannelOption.SO_KEEPALIVE,true)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline channelPipeline = ch.pipeline();
                            }
                        });
        try {
            channel = serverBootstrap.bind(address,port).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown(){
        if (channel.isOpen()) channel.close();
        worker.shutdownGracefully();
        boss.shutdownGracefully();
    }


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}

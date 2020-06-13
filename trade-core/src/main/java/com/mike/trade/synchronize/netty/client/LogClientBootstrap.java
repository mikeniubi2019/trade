package com.mike.trade.synchronize.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class LogClientBootstrap {
    private int port;
    private String address;
    private EventLoopGroup worder;
    private Bootstrap bootstrap;
    private Channel channel;

    public void start(){
        bootstrap = new Bootstrap();
        worder = new NioEventLoopGroup();
        bootstrap.group(worder)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline channelPipeline = ch.pipeline();
                    }
                });

        try {
            channel = bootstrap.connect(address,port).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown(){
        if (channel.isOpen()) channel.close();
        worder.shutdownGracefully();
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

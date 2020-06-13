package com.mike.trade.netty.clientBootstrap;

import com.mike.trade.netty.channelHandler.RecieveResponseChannelHandler;
import com.mike.trade.netty.codec.ClientRequestEncode;
import com.mike.trade.netty.codec.FseResponseDecode;
import com.mike.trade.pojo.servicePojo.Request;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.util.Map;

public class ClientBootstrap {
    private int port;
    private String address;
    private Bootstrap bootstrap;
    private EventLoopGroup works;
    private Channel channel;
    private Map<Long,Request> requestMap;

    public void start(){
        bootstrap = new Bootstrap();
        works = new NioEventLoopGroup();
        bootstrap.group(works)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline channelPipeline = socketChannel.pipeline();
                        channelPipeline.addLast(new LengthFieldBasedFrameDecoder(1024*1024,0,4,0,0));
                        channelPipeline.addLast(new FseResponseDecode());
                        channelPipeline.addLast(new ClientRequestEncode());
                        channelPipeline.addLast(new RecieveResponseChannelHandler(requestMap));
                    }
                });
        try {
            channel = bootstrap.connect(address,port).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        channel.close();
        works.shutdownGracefully();
    }

    public ClientBootstrap(int port, String address, Map<Long, Request> requestMap) {
        this.port = port;
        this.address = address;
        this.requestMap = requestMap;
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

package com.mike.trade.client;

import com.mike.trade.netty.clientBootstrap.ClientBootstrap;
import com.mike.trade.pojo.servicePojo.Request;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TradeClient {
    private Map<Long,Request> requestMap = new ConcurrentHashMap<>();
    private ClientBootstrap clientBootstrap;
    private Channel channel;

    public TradeClient(String address, int port) {
        ClientBootstrap clientBootstrap = new ClientBootstrap(port,address,requestMap);
        clientBootstrap.start();
        this.clientBootstrap = clientBootstrap;
        this.channel = clientBootstrap.getChannel();
    }

    public void close(){
        clientBootstrap.close();
    }

    public boolean trySend(Request request){
        if (request==null){
            return false;
        }
        requestMap.put(request.getRequestId(),request);
        int count = 0;
        while (requestMap.containsKey(request.getRequestId())&&count<3){
            channel.writeAndFlush(request);
            synchronized (request){
                try {
                    request.wait(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            count+=1;
        }
        return requestMap.remove(request.getRequestId())==null;
    }

}

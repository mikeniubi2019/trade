package com.mike.trade.service.utils;

import com.mike.trade.cache.Cache;
import com.mike.trade.cache.CacheBuilder;
import com.mike.trade.cache.LevelDBCacheBuilder;
import com.mike.trade.pojo.servicePojo.Order;
import com.mike.trade.pojo.servicePojo.Request;


import java.io.IOException;

public class TradeCacheServiceUtils {
    private static Cache cache;
    static {
        CacheBuilder cacheBuilder = new LevelDBCacheBuilder();
        try {
            cache = cacheBuilder.build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generatorCache(Order order){
        String key = new StringBuffer()
                .append(order.getTradeName())
                .append(":")
                .append(order.getTradeType())
                .append(":")
                .append(order.getRequestId())
                .toString();
        cache.putObject(key,order);
    }

    public static void removeCache(Order order){
        String key = new StringBuffer()
                .append(order.getTradeName())
                .append(":")
                .append(order.getTradeType())
                .append(":")
                .append(order.getRequestId())
                .toString();
        cache.delete(key);
    }

    public static Order getCache(Request request){
        String key = new StringBuffer()
                .append(request.getTradeName())
                .append(":")
                .append(request.getTradeType())
                .append(":")
                .append(request.getRequestId())
                .toString();
        return (Order)cache.getObject(key);
    }
    public static Cache getCache(){
        return cache;
    }
}

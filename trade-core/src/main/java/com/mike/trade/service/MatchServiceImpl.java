package com.mike.trade.service;

import com.mike.trade.pojo.servicePojo.Request;
import com.mike.trade.pojo.servicePojo.Trade;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MatchServiceImpl implements MatchService{

    private Map<Integer,Trade> tradeMap = new ConcurrentHashMap<>();

    @Override
    public boolean checkTradeOpen(int tradeName) {
        return tradeMap.containsKey(tradeName);
    }

    @Override
    public Trade getTrade(int tradeName) {
        return tradeMap.get(tradeName);
    }

    @Override
    public void Match(Request request, Trade trade) {
        trade.doMatch(request);
    }

    @Override
    public void openTrade(Trade trade) {
        tradeMap.put(trade.getTradeName(),trade);
    }

    @Override
    public void closeTrade(int tradeName) {
        tradeMap.remove(tradeName);
    }


}

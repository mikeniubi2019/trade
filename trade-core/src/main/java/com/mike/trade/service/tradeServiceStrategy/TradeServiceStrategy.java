package com.mike.trade.service.tradeServiceStrategy;

import com.mike.trade.pojo.serviceEnum.ActionType;
import com.mike.trade.pojo.serviceEnum.TradeType;
import com.mike.trade.pojo.servicePojo.Order;
import com.mike.trade.pojo.servicePojo.Request;
import com.mike.trade.pojo.servicePojo.Trade;

import java.util.concurrent.ConcurrentSkipListMap;

public interface TradeServiceStrategy {
    void doMatchService(Request request, Trade trade);
    void sendOKOrder(Order order);
    void generatorOrderAndPut2List(Request request,ConcurrentSkipListMap<Double, ConcurrentSkipListMap<String,Order>> sellPriceSkipListMap,ConcurrentSkipListMap<Double, ConcurrentSkipListMap<String,Order>> buyPriceSkipListMap);
    public TradeType getTradeType();
    public ActionType getActionType();

}

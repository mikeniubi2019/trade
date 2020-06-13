package com.mike.trade.pojo.servicePojo;

import com.mike.trade.service.tradeServiceStrategy.ITradeServiceStratrgyHolds;
import com.mike.trade.service.tradeServiceStrategy.TradeServiceStrategy;
import com.mike.trade.service.tradeServiceStrategy.TradeStrategyHolds;

import java.util.concurrent.ConcurrentSkipListMap;

public class Trade {
    //实时价格
    private double currentPrice;
    //卖单队列
    private ConcurrentSkipListMap<Double, ConcurrentSkipListMap<String,Order>> sellPriceSkipListMap = new ConcurrentSkipListMap<>();
    //买单队列
    private ConcurrentSkipListMap<Double, ConcurrentSkipListMap<String,Order>> buyPriceSkipListMap = new ConcurrentSkipListMap<>();

    private ITradeServiceStratrgyHolds tradeServiceStratrgyHolds = new TradeStrategyHolds(null);

    private int tradeName;

    public void doMatch(Request request){
        //策略模式，根据请求类型获取哪种订单类型的策略，
        TradeServiceStrategy tradeServiceStrategy = tradeServiceStratrgyHolds.getTradeServiceStrategy(request.getTradeType(),request.getActionType());
        //面向接口编程
        tradeServiceStrategy.doMatchService(request,this);
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public ConcurrentSkipListMap<Double, ConcurrentSkipListMap<String, Order>> getSellPriceSkipListMap() {
        return sellPriceSkipListMap;
    }

    public void setSellPriceSkipListMap(ConcurrentSkipListMap<Double, ConcurrentSkipListMap<String, Order>> sellPriceSkipListMap) {
        this.sellPriceSkipListMap = sellPriceSkipListMap;
    }

    public ConcurrentSkipListMap<Double, ConcurrentSkipListMap<String, Order>> getBuyPriceSkipListMap() {
        return buyPriceSkipListMap;
    }

    public void setBuyPriceSkipListMap(ConcurrentSkipListMap<Double, ConcurrentSkipListMap<String, Order>> buyPriceSkipListMap) {
        this.buyPriceSkipListMap = buyPriceSkipListMap;
    }

    public ITradeServiceStratrgyHolds getTradeServiceStratrgyHolds() {
        return tradeServiceStratrgyHolds;
    }

    public void setTradeServiceStratrgyHolds(ITradeServiceStratrgyHolds tradeServiceStratrgyHolds) {
        this.tradeServiceStratrgyHolds = tradeServiceStratrgyHolds;
    }

    public Trade(int tradeName) {
        this.tradeName = tradeName;
    }

    public int getTradeName() {
        return tradeName;
    }

    public void setTradeName(int tradeName) {
        this.tradeName = tradeName;
    }
}

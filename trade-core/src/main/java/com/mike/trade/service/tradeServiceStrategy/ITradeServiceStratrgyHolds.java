package com.mike.trade.service.tradeServiceStrategy;

public interface ITradeServiceStratrgyHolds {
    TradeServiceStrategy getTradeServiceStrategy(int tradeTypeValue, int actionTypeValue);
    public void addTradeServiceStrategy(TradeServiceStrategy tradeServiceStrategy);
}

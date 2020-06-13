package com.mike.trade.service.tradeServiceStrategy;


import java.util.List;

public class TradeStrategyHolds implements ITradeServiceStratrgyHolds{
    private TradeServiceStrategy[][] tradeServiceStrategies;

    @Override
    public TradeServiceStrategy getTradeServiceStrategy(int tradeTypeValue, int actionTypeValue) {
        //如果用map存，则每次取都要hash运算，效率相对较低.所有用数组存，
        return this.tradeServiceStrategies[actionTypeValue][tradeTypeValue];
    }

    public TradeStrategyHolds(List<TradeServiceStrategy> tradeServiceStrategyList) {
        tradeServiceStrategies = new TradeServiceStrategy[30][30];
        if (tradeServiceStrategyList==null) return;
        for (TradeServiceStrategy tradeServiceStrategy : tradeServiceStrategyList){
            tradeServiceStrategies[tradeServiceStrategy.getActionType().getValue()][tradeServiceStrategy.getTradeType().getValue()]=tradeServiceStrategy;
        }
    }

    @Override
    public void addTradeServiceStrategy(TradeServiceStrategy tradeServiceStrategy){
        tradeServiceStrategies[tradeServiceStrategy.getActionType().getValue()][tradeServiceStrategy.getTradeType().getValue()]=tradeServiceStrategy;
    }
}

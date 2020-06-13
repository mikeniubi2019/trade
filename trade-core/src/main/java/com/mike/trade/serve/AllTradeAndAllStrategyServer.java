package com.mike.trade.serve;

import com.mike.trade.pojo.serviceEnum.ActionType;
import com.mike.trade.pojo.serviceEnum.TradeName;
import com.mike.trade.pojo.serviceEnum.TradeType;
import com.mike.trade.pojo.servicePojo.Trade;
import com.mike.trade.restore.LevelDBRestore;
import com.mike.trade.service.tradeServiceStrategy.*;

import java.util.ArrayList;
import java.util.List;

public class AllTradeAndAllStrategyServer extends AbstractBootstrap{
    private List<TradeServiceStrategy> tradeServiceStrategyList;


    @Override
    public void registRestore() {
        super.setRestore(new LevelDBRestore());
    }

    @Override
    public void initTrades() {
        initAllTradeServiceStrategy();
        for (TradeName tradeName : TradeName.values()){
            Trade trade = new Trade(tradeName.getValue());
            for (TradeServiceStrategy tradeServiceStrategy : tradeServiceStrategyList){
                super.putServiceStrategyHodlers2Trade(trade,tradeServiceStrategy);
            }
            super.openTrade(trade);
        }
    }

    private void initAllTradeServiceStrategy(){
        tradeServiceStrategyList = new ArrayList<>();
        BuyMarketPriceServiceStrategy buyMarketPriceServiceStrategy = new BuyMarketPriceServiceStrategy(ActionType.MARKET,TradeType.BUY);
        BuyLimitPriceServiceStrategy buyLimitPriceServiceStrategy = new BuyLimitPriceServiceStrategy(ActionType.LIMIT,TradeType.BUY);
        SellLimitPriceTradeServiceStrategy sellLimitPriceTradeServiceStrategy = new SellLimitPriceTradeServiceStrategy(ActionType.LIMIT,TradeType.SELL);
        SellMarketPriceTradeServiceStrategy sellMarketPriceTradeServiceStrategy = new SellMarketPriceTradeServiceStrategy(ActionType.MARKET,TradeType.SELL);
        tradeServiceStrategyList.add(buyLimitPriceServiceStrategy);
        tradeServiceStrategyList.add(buyMarketPriceServiceStrategy);
        tradeServiceStrategyList.add(sellLimitPriceTradeServiceStrategy);
        tradeServiceStrategyList.add(sellMarketPriceTradeServiceStrategy);
    }

    @Override
    public void bind(String address,int port){
        super.bind(address,port);
    }

    public AllTradeAndAllStrategyServer() {
    }

    public AllTradeAndAllStrategyServer(String address,int port) {
        bind(address,port);
    }
}

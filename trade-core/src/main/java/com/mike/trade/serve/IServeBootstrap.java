package com.mike.trade.serve;

import com.mike.trade.pojo.servicePojo.Trade;
import com.mike.trade.restore.IRestore;
import com.mike.trade.service.tradeServiceStrategy.TradeServiceStrategy;

import java.util.List;

public interface IServeBootstrap {
    void openMatchService();
    void start() throws Exception;
    void openTrade(Trade trade);
    void openTrades(List<Trade> trades);
    void registTrade() throws Exception;
    void registRestore();
    void restore();
    void bind(String address,int port);
    void putServiceStrategyHodlers2Trade(Trade trade,TradeServiceStrategy tradeServiceStrategy);

    void initTrades();
}

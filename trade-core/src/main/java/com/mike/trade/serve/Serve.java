package com.mike.trade.serve;

import com.mike.trade.disruptor.DisruptorBuilder;
import com.mike.trade.disruptor.handler.ServiceWorkerHandler;
import com.mike.trade.netty.ServeBootStrap;
import com.mike.trade.netty.codec.FseDecode;
import com.mike.trade.netty.codec.ResponseFseEncode;
import com.mike.trade.pojo.serviceEnum.ActionType;
import com.mike.trade.pojo.serviceEnum.TradeName;
import com.mike.trade.pojo.serviceEnum.TradeType;
import com.mike.trade.pojo.servicePojo.Trade;
import com.mike.trade.restore.IRestore;
import com.mike.trade.restore.LevelDBRestore;

import com.mike.trade.service.MatchServiceImpl;
import com.mike.trade.service.tradeServiceStrategy.*;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.util.ArrayList;
import java.util.List;

public class Serve {
    public MatchServiceImpl matchService;
    public void start() throws Exception {
        this.start("192.168.1.6",11100);
    }
    public void start(String address,int port) throws Exception {

            matchService = new MatchServiceImpl();
            Trade trade = new Trade(TradeName.BTCEOS.getValue());

            List<TradeServiceStrategy> tradeServiceStrategies = new ArrayList<>();
            tradeServiceStrategies.add(new BuyLimitPriceServiceStrategy(ActionType.LIMIT, TradeType.BUY));
            tradeServiceStrategies.add(new BuyMarketPriceServiceStrategy(ActionType.MARKET,TradeType.BUY));
            tradeServiceStrategies.add(new SellLimitPriceTradeServiceStrategy(ActionType.LIMIT,TradeType.SELL));
            tradeServiceStrategies.add(new SellMarketPriceTradeServiceStrategy(ActionType.MARKET,TradeType.SELL));

            trade.setTradeServiceStratrgyHolds(new TradeStrategyHolds(tradeServiceStrategies));
            matchService.openTrade(trade);
            IRestore restore = new LevelDBRestore();
            restore.restore(matchService);

            DisruptorBuilder disruptorBuilder = new DisruptorBuilder();
            for (int x=0;x<Runtime.getRuntime().availableProcessors();x++){
                disruptorBuilder.addWorker(new ServiceWorkerHandler(matchService));
            }

            ServeBootStrap serveBootStrap = new ServeBootStrap(address,port,disruptorBuilder.build());
            serveBootStrap.init();
            serveBootStrap.start();

            System.out.println("启动成功");
    }

}

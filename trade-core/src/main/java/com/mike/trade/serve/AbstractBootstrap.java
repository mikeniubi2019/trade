package com.mike.trade.serve;

import com.mike.trade.disruptor.DisruptorBuilder;
import com.mike.trade.disruptor.handler.ServiceWorkerHandler;
import com.mike.trade.netty.ServeBootStrap;
import com.mike.trade.pojo.servicePojo.Trade;
import com.mike.trade.restore.IRestore;
import com.mike.trade.service.MatchServiceImpl;
import com.mike.trade.service.tradeServiceStrategy.TradeServiceStrategy;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBootstrap implements IServeBootstrap{
    private MatchServiceImpl matchService;
    private List<Trade> trades = new ArrayList<>();
    private IRestore restore;
    private DisruptorBuilder disruptorBuilder;
    private ServeBootStrap serveBootStrap;
    private String address;
    private int port;

    @Override
    public void start() throws Exception {

        openMatchService();

        initTrades();

        registTrade();

        registRestore();

        restore();

        openDisruptor();

        this.bind(address,port);

        openConnect();

    }

    private void openConnect() throws Exception {
        serveBootStrap = new ServeBootStrap(address,port,disruptorBuilder.build());
        serveBootStrap.init();
        serveBootStrap.start();
    }

    @Override
    public void openMatchService(){
        this.matchService = new MatchServiceImpl();
    }

    @Override
    public void openTrade(Trade trade){
        if (trades.stream().anyMatch(trade1 -> trade1.getTradeName()==trade.getTradeName())){
            return;
        }
        trades.add(trade);
    }

    @Override
    public void openTrades(List<Trade> trades){
        trades.forEach(this::openTrade);
    }

    @Override
    public void registTrade() throws Exception {
        if (trades.size()<1) throw new Exception("交易对不能为空");
        for (Trade trade : trades){
            matchService.openTrade(trade);
        }
    }

    @Override
    public void putServiceStrategyHodlers2Trade(Trade trade,TradeServiceStrategy tradeServiceStrategy){
        trade.getTradeServiceStratrgyHolds().addTradeServiceStrategy(tradeServiceStrategy);
    }

    public void restore(){
        if (restore!=null){
            restore.restore(matchService);
        }
    }

    private void openDisruptor(){
        disruptorBuilder = new DisruptorBuilder();
        for (int x=0;x<Runtime.getRuntime().availableProcessors();x++){
            disruptorBuilder.addWorker(new ServiceWorkerHandler(matchService));
        }
    }

    @Override
    public void bind(String address,int port){
        this.address = address==null?"localhost":address;
        this.port = port==0?7777:port;
    }

    public IRestore getRestore() {
        return restore;
    }

    public void setRestore(IRestore restore) {
        this.restore = restore;
    }


}

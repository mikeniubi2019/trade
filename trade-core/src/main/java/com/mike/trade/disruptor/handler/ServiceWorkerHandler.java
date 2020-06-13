package com.mike.trade.disruptor.handler;

import com.lmax.disruptor.WorkHandler;
import com.mike.trade.netty.utils.SendResponseUtils;
import com.mike.trade.pojo.channelContextHandlerAndRequestHolder.ChannelContextHandlerAndRequestHolder;
import com.mike.trade.pojo.servicePojo.Request;
import com.mike.trade.pojo.servicePojo.Trade;
import com.mike.trade.service.MatchService;
import com.mike.trade.service.utils.RequestValidata;


public class ServiceWorkerHandler implements WorkHandler {
    private MatchService matchService;

    @Override
    public void onEvent(Object o) throws Exception {
        if (o instanceof ChannelContextHandlerAndRequestHolder){
            ChannelContextHandlerAndRequestHolder channelContextHandlerAndRequestHolder = (ChannelContextHandlerAndRequestHolder) o;
            //TODO service
            Request request = channelContextHandlerAndRequestHolder.getRequest();
            if (!RequestValidata.validataRequest(request)||!RequestValidata.checkRequestRepeat(request)){
                SendResponseUtils.sendResponse(request.getRequestId(),false,channelContextHandlerAndRequestHolder.getChannelHandlerContext());
                return;
            }
            if (!matchService.checkTradeOpen(request.getTradeName())){
                //检测没开启这个交易对
                SendResponseUtils.sendResponse(request.getRequestId(),false,channelContextHandlerAndRequestHolder.getChannelHandlerContext());
                return;
            }
            SendResponseUtils.sendResponse(request.getRequestId(),true,channelContextHandlerAndRequestHolder.getChannelHandlerContext());

            Trade trade = matchService.getTrade(request.getTradeName());
            //核心方法
            matchService.Match(request,trade);
        }
    }
    public ServiceWorkerHandler(MatchService matchService) {
        this.matchService = matchService;
    }
}

package com.mike.trade.service;

import com.mike.trade.pojo.servicePojo.Request;
import com.mike.trade.pojo.servicePojo.Trade;

public interface MatchService {
    boolean checkTradeOpen(int tradeName);
    Trade getTrade(int tradeName);
    void Match(Request request, Trade trade);
    void openTrade(Trade trade);
    void closeTrade(int tradeName);
}

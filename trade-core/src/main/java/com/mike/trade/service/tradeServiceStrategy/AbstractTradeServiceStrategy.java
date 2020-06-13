package com.mike.trade.service.tradeServiceStrategy;

import com.mike.trade.pojo.serviceEnum.ActionType;
import com.mike.trade.pojo.serviceEnum.TradeType;
import com.mike.trade.pojo.servicePojo.Order;

import java.util.concurrent.atomic.LongAdder;

public abstract class AbstractTradeServiceStrategy implements TradeServiceStrategy{

    private final ActionType actionType;
    private final TradeType tradeType;

    @Override
    public void sendOKOrder(Order order) {
        //TODO 这里操作成功的订单
        //System.out.println("成交"+order);

    }

    public AbstractTradeServiceStrategy(ActionType actionType, TradeType tradeType) {
        this.actionType = actionType;
        this.tradeType = tradeType;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public TradeType getTradeType() {
        return tradeType;
    }


}

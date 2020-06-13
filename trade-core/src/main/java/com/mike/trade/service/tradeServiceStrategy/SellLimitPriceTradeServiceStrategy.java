package com.mike.trade.service.tradeServiceStrategy;

import com.mike.trade.pojo.serviceEnum.ActionType;
import com.mike.trade.pojo.serviceEnum.TradeType;
import com.mike.trade.pojo.servicePojo.Order;
import com.mike.trade.pojo.servicePojo.Request;
import com.mike.trade.pojo.servicePojo.Trade;
import com.mike.trade.service.utils.TradeCacheServiceUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class SellLimitPriceTradeServiceStrategy extends AbstractTradeServiceStrategy{

    public SellLimitPriceTradeServiceStrategy(ActionType actionType, TradeType tradeType) {
        super(actionType, tradeType);
    }

    @Override
    public void doMatchService(Request request, Trade trade) {
        Map.Entry heightPriceEntry = trade.getBuyPriceSkipListMap().lastEntry();

        while (heightPriceEntry!=null){
            //当前价格
            double currentPrice = (Double)heightPriceEntry.getKey();
            //判断卖单价格是否小于挂单价格，如果大于，则挂到卖单列表，并返回
            if (currentPrice<request.getPrice()){
                //生成卖单，挂到卖单列表
                generatorOrderAndPut2List(request,trade.getSellPriceSkipListMap(),trade.getBuyPriceSkipListMap());
                return;
            }

            //卖单数量
            double currentCount = request.getAcount();
            //当前价格下的所有订单列表
            ConcurrentSkipListMap heightPriceEntryValueMap = (ConcurrentSkipListMap)heightPriceEntry.getValue();
            //挂单
            Map.Entry orderEntry = heightPriceEntryValueMap.pollFirstEntry();
            while (orderEntry!=null){
                Order order = (Order)orderEntry.getValue();
                //挂单数量
                double orderAccount = order.getAcount();
                //挂单数量高于此卖单的数量
                if (orderAccount>currentCount){
                    //1，创建新的卖订单 。
                    Order newSellOrder = new Order(request.getRequestId(),order.getSellId(),System.currentTimeMillis(),order.getPrice(),request.getAcount(),true, TradeType.SELL.getValue(),request.getRequestId(),request.getTradeName());
                    //2，减去卖单数量后，把挂单重新添加回列表
                    order.setAcount(orderAccount-currentCount);
                    heightPriceEntryValueMap.put(orderEntry.getKey(),order);
                    sendOKOrder(newSellOrder);
                    TradeCacheServiceUtils.removeCache(newSellOrder);
                    //TODO 把成功的newOrder订单发送出去
                    return;
                }else {
                    //因为旧买单已经卖完，发送旧买单出去
                    order.setOrderTime(System.currentTimeMillis());
                    order.setOK(true);
                    sendOKOrder(order);
                    TradeCacheServiceUtils.removeCache(order);
                    //减去成交数量后，新买单继续搜寻下一个买单
                    currentCount-=orderAccount;
                    request.setAcount(currentCount);
                    orderEntry = heightPriceEntryValueMap.pollFirstEntry();
                }
                //设置当前trad价格，给外部使用
                trade.setCurrentPrice(order.getPrice());
            }
            //如果为空，说明当前这个价格的列表为空。则删除这个列表，并进行下一轮循环
            trade.getBuyPriceSkipListMap().remove(currentPrice);
            heightPriceEntry = trade.getBuyPriceSkipListMap().lastEntry();
        }

        //如果买单价格列表为空，则直接将卖单挂到卖单列表
        generatorOrderAndPut2List(request,trade.getSellPriceSkipListMap(),trade.getBuyPriceSkipListMap());

    }

    @Override
    public void generatorOrderAndPut2List(Request request, ConcurrentSkipListMap<Double, ConcurrentSkipListMap<String, Order>> sellPriceSkipListMap, ConcurrentSkipListMap<Double, ConcurrentSkipListMap<String, Order>> buyPriceSkipListMap) {
        Order newSellOrder = new Order(request.getRequestId(),2,System.currentTimeMillis(),request.getPrice(),request.getAcount(),false,TradeType.SELL.getValue(),request.getRequestId(),request.getTradeName());
        ConcurrentSkipListMap requestPriceConcurrentMap = sellPriceSkipListMap.computeIfAbsent(request.getPrice(),(key)->new ConcurrentSkipListMap());
        requestPriceConcurrentMap.put(""+newSellOrder.getOrderTime()+newSellOrder.getSellId(),newSellOrder);
        TradeCacheServiceUtils.generatorCache(newSellOrder);
    }




}

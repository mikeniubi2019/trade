package com.mike.trade.service.tradeServiceStrategy;

import com.mike.trade.pojo.serviceEnum.ActionType;
import com.mike.trade.pojo.serviceEnum.TradeType;
import com.mike.trade.pojo.servicePojo.Order;
import com.mike.trade.pojo.servicePojo.Request;
import com.mike.trade.pojo.servicePojo.Trade;
import com.mike.trade.service.utils.TradeCacheServiceUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class BuyLimitPriceServiceStrategy extends AbstractTradeServiceStrategy{
    public BuyLimitPriceServiceStrategy(ActionType actionType, TradeType tradeType) {
        super(actionType, tradeType);
    }

    @Override
    public void doMatchService(Request request, Trade trade) {
        Map.Entry lowPriceEntry = trade.getSellPriceSkipListMap().firstEntry();

        //卖单价格列表不为空
        while (lowPriceEntry!=null){

            //当前价格
            double currentPrice = (Double)lowPriceEntry.getKey();
            //判断买单价格是否大于挂单价格，如果不大于，则挂到买单列表，并返回
            if (currentPrice>request.getPrice()){
                //生成买单，挂到买单列表
                generatorOrderAndPut2List(request,trade.getSellPriceSkipListMap(),trade.getBuyPriceSkipListMap());
                return;
            }
            //买单价格
            double currentCount = request.getAcount();
            //当前价格下的所有订单列表
            ConcurrentSkipListMap lowPriceConcurrentMap = (ConcurrentSkipListMap)lowPriceEntry.getValue();

            //挂单
            Map.Entry orderEntry = lowPriceConcurrentMap.pollFirstEntry();

            while (orderEntry!=null){
                Order order = (Order)orderEntry.getValue();
                //挂单数量
                double orderAccount = order.getAcount();
                //挂单数量高于此买单的数量
                if (orderAccount>currentCount){
                    //1，创建新的买订单 。
                    Order newBuyOrder = new Order(order.getSellId(),request.getRequestId(),System.currentTimeMillis(),order.getPrice(),request.getAcount(),true, TradeType.BUY.getValue(),request.getRequestId(),request.getTradeName());
                    //2，减去买单数量后，把挂单重新添加回列表
                    order.setAcount(orderAccount-currentCount);
                    lowPriceConcurrentMap.put(orderEntry.getKey(),order);
                    sendOKOrder(newBuyOrder);
                    TradeCacheServiceUtils.removeCache(newBuyOrder);
                    //TODO 把成功的newOrder订单发送出去,返回
                    return;
                }else {
                    //因为旧卖单已经卖完，发送旧卖单出去
                    order.setOrderTime(System.currentTimeMillis());
                    order.setOK(true);
                    sendOKOrder(order);
                    TradeCacheServiceUtils.removeCache(order);
                    //减去成交数量后，新买单继续搜寻下一个卖单
                    currentCount -= orderAccount;
                    request.setAcount(currentCount);
                    orderEntry = lowPriceConcurrentMap.pollFirstEntry();
                }
                //设置当前trad价格，给外部使用
                trade.setCurrentPrice(order.getPrice());
            }
            //如果为空，说明当前这个价格的列表为空。则删除这个列表，并进行下一轮价格的循环
            trade.getSellPriceSkipListMap().remove(currentPrice);
            lowPriceEntry = trade.getSellPriceSkipListMap().firstEntry();
        }

        //如果卖单价格列表为空，则直接将买单挂到买单列表
        generatorOrderAndPut2List(request,trade.getSellPriceSkipListMap(),trade.getBuyPriceSkipListMap());
    }

    @Override
    public void generatorOrderAndPut2List(Request request, ConcurrentSkipListMap<Double, ConcurrentSkipListMap<String, Order>> sellPriceSkipListMap, ConcurrentSkipListMap<Double, ConcurrentSkipListMap<String, Order>> buyPriceSkipListMap) {
        Order newBuyOrder = new Order(2,request.getRequestId(),System.currentTimeMillis(),request.getPrice(),request.getAcount(),false,TradeType.BUY.getValue(),request.getRequestId(),request.getTradeName());
        ConcurrentSkipListMap requestPriceConcurrentMap = buyPriceSkipListMap.computeIfAbsent(request.getPrice(),(key)->new ConcurrentSkipListMap());
        requestPriceConcurrentMap.put(""+newBuyOrder.getOrderTime()+newBuyOrder.getSellId(),newBuyOrder);
        TradeCacheServiceUtils.generatorCache(newBuyOrder);
    }
}

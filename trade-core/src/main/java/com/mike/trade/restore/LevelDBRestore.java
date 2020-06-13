package com.mike.trade.restore;

import com.jfireframework.fse.ByteArray;
import com.jfireframework.fse.Fse;
import com.mike.trade.cache.LevelDBCacheBuilder;
import com.mike.trade.pojo.serviceEnum.TradeType;
import com.mike.trade.pojo.servicePojo.Order;
import com.mike.trade.pojo.servicePojo.Trade;
import com.mike.trade.service.MatchServiceImpl;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBIterator;
import org.iq80.leveldb.ReadOptions;
import org.iq80.leveldb.Snapshot;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class LevelDBRestore implements IRestore{
    @Override
    public boolean restore(MatchServiceImpl matchService) {
        DB db = null;
        try {
            db = LevelDBCacheBuilder.getDb();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (db==null){
            return false;
        }
        Fse fse = new Fse();
        Snapshot snapshot= db.getSnapshot();
        ReadOptions readOptions = new ReadOptions();
        readOptions.fillCache(false);
        readOptions.snapshot(snapshot);
        DBIterator dbIterator = db.iterator(readOptions);
        ByteArray byteArray = ByteArray.allocate();
        while (dbIterator.hasNext()){
            Map.Entry<byte[],byte[]> entry = dbIterator.next();
            String key = new String(entry.getKey(), StandardCharsets.UTF_8);
            String tradeName = key.substring(0,key.indexOf(":"));
            Trade trade = matchService.getTrade(Integer.parseInt(tradeName));
            if (trade==null){continue;}
            //经过序列化然后从db获取order
            byteArray.put(entry.getValue());
            Order order =null;
            try {
                order = (Order) fse.deSerialize(byteArray);
            }catch (IllegalArgumentException e){
                db.delete(entry.getKey());
                continue;
            }finally {
                byteArray.clear();
            }
            double price = order.getPrice();
            ConcurrentSkipListMap concurrentSkipListMap;
            //把order放入队列中
            if (order.getTradeType()== TradeType.SELL.getValue()){
                concurrentSkipListMap = trade.getSellPriceSkipListMap().computeIfAbsent(price,(k)->new ConcurrentSkipListMap<>());
            }else {
                concurrentSkipListMap = trade.getBuyPriceSkipListMap().computeIfAbsent(price,(k)->new ConcurrentSkipListMap<>());
            }
            concurrentSkipListMap.put(""+order.getOrderTime()+order.getSellId(),order);
        }
        return true;
    }
}

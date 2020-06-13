import com.mike.trade.client.TradeClient;
import com.mike.trade.pojo.serviceEnum.ActionType;
import com.mike.trade.pojo.serviceEnum.StatuType;
import com.mike.trade.pojo.serviceEnum.TradeName;
import com.mike.trade.pojo.serviceEnum.TradeType;
import com.mike.trade.pojo.servicePojo.Request;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class test {
    public static void main(String[] args) {
        //TradeClient tradeClient = new TradeClient("localhost",8080);

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        new Thread(()->{

            TradeClient tradeClient1 = new TradeClient("192.168.137.19",11100);
            for (int i=1;i<100000000;i++){
                if (i%100000==0){
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                executorService.execute(()->{
                            Random random = ThreadLocalRandom.current();
                            int trad = random.nextInt(2)+1;
                            int action = random.nextInt(2)+1;

                            Request request = new Request();
                            request.setAcount(random.nextDouble());
                            request.setActionType(action);
                            request.setCreatTime(System.currentTimeMillis());
                            request.setPrice(random.nextDouble());
                            request.setRequestId(random.nextLong());
                            request.setStatus(StatuType.FAIL.getValue());
                            request.setTradeName(TradeName.BTCEOS.getValue());
                            request.setTradeType(trad);
                            request.setUserId(random.nextLong());
                            tradeClient1.trySend(request);
                        }
                );
            }
        }).start();

        new Thread(()->{

            TradeClient tradeClient2 = new TradeClient("192.168.137.19",11100);
            for (int i=1;i<100000000;i++){
                if (i%100000==0){
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                executorService.execute(()->{
                            Random random = ThreadLocalRandom.current();
                            int trad = random.nextInt(2)+1;
                            int action = random.nextInt(2)+1;

                            Request request = new Request();
                            request.setAcount(random.nextDouble());
                            request.setActionType(action);
                            request.setCreatTime(System.currentTimeMillis());
                            request.setPrice(random.nextDouble());
                            request.setRequestId(random.nextLong());
                            request.setStatus(StatuType.FAIL.getValue());
                            request.setTradeName(TradeName.BTCEOS.getValue());
                            request.setTradeType(trad);
                            request.setUserId(random.nextLong());
                            tradeClient2.trySend(request);
                        }
                );
            }
        }).start();

        new Thread(()->{

            TradeClient tradeClient3 = new TradeClient("192.168.137.19",11100);
            for (int i=1;i<100000000;i++){
                if (i%100000==0){
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                executorService.execute(()->{
                            Random random = ThreadLocalRandom.current();
                            int trad = random.nextInt(2)+1;
                            int action = random.nextInt(2)+1;

                            Request request = new Request();
                            request.setAcount(random.nextDouble());
                            request.setActionType(action);
                            request.setCreatTime(System.currentTimeMillis());
                            request.setPrice(random.nextDouble());
                            request.setRequestId(random.nextLong());
                            request.setStatus(StatuType.FAIL.getValue());
                            request.setTradeName(TradeName.BTCEOS.getValue());
                            request.setTradeType(trad);
                            request.setUserId(random.nextLong());
                            tradeClient3.trySend(request);
                        }
                );
            }
        }).start();

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

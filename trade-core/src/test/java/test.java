import com.mike.trade.pojo.serviceEnum.StatuType;
import com.mike.trade.pojo.serviceEnum.TradeName;
import com.mike.trade.pojo.servicePojo.Request;
import com.mike.trade.pojo.servicePojo.Trade;
import com.mike.trade.serve.Serve;
import com.mike.trade.service.tradeServiceStrategy.ITradeServiceStratrgyHolds;
import com.mike.trade.service.tradeServiceStrategy.TradeServiceStrategy;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.LongAdder;

public class test {

    public static LongAdder longAdder = new LongAdder();
    public static LongAdder timeCount = new LongAdder();

    public static void main(String[] args) throws Exception {
        Serve serve = new Serve();
        serve.start();
        Trade trade = serve.matchService.getTrade(1);
        ITradeServiceStratrgyHolds iTradeServiceStratrgyHolds = trade.getTradeServiceStratrgyHolds();

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        new Thread(()->{
            for (int i=1;i<100000000;i++){
//                if (i%100000==0){
//                    try {
//                        Thread.sleep(5000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
                executorService.execute(()->{
                            Random random = ThreadLocalRandom.current();
                            int trad = random.nextInt(2)+1;
                            int action = random.nextInt(2)+1;
                            TradeServiceStrategy tradeServiceStrategy = iTradeServiceStratrgyHolds.getTradeServiceStrategy(trad, action);
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
                            //long start = System.currentTimeMillis();
                            tradeServiceStrategy.doMatchService(request,trade);
                            //long end = System.currentTimeMillis();
                            //longAdder.add(end-start);
                    longAdder.add(1);
                        }
                );
            }
        }).start();

//        long start = System.currentTimeMillis();
//        while (true){
//            Thread.sleep(3000);
//            long orderCOunt = AbstractTradeServiceStrategy.orderCountAdder.sum();
//            long qCount = longAdder.sum();
//            long time = (System.currentTimeMillis()-start);
//            System.out.println("当前价格："+serve.trade.getCurrentPrice());
//            System.out.println("请求数量:："+ qCount);
//            System.out.println("成交:："+ orderCOunt);
//            System.out.println("qps："+ qCount*1000/time );
//            System.out.println("tps："+ orderCOunt*1000/ time);
//            System.out.println("-------------------------------------------------------------");
//        }

    }
}

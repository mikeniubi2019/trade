import com.jfireframework.fse.ByteArray;
import com.jfireframework.fse.Fse;
import com.mike.trade.cache.LevelDBCacheBuilder;
import com.mike.trade.pojo.serviceEnum.StatuType;
import com.mike.trade.pojo.serviceEnum.TradeName;
import com.mike.trade.pojo.servicePojo.Request;
import org.iq80.leveldb.DB;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class test2 {
    public static void main(String[] args) throws IOException {
        Fse fse = new Fse();
        DB db = LevelDBCacheBuilder.getDb();

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

        ByteArray byteArray = ByteArray.allocate();
                fse.serialize(request,byteArray);
                byte[] bytes = byteArray.toArray();

                db.put("1:2:3".getBytes(),bytes);
                byte[] bytes1 = db.get("1:2:3".getBytes());
                byteArray.clear();
                byteArray.put(bytes1);
            System.out.println(fse.deSerialize(byteArray));
    }
}

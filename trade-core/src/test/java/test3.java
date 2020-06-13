import com.mike.trade.serve.AllTradeAndAllStrategyServer;
import com.mike.trade.serve.IServeBootstrap;

import java.io.IOException;

public class test3 {
    public static void main(String[] args) {
        IServeBootstrap serveBootstrap = new AllTradeAndAllStrategyServer();
        try {
            serveBootstrap.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

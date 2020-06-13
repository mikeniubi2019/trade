package com.mike.trade.cache;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBFactory;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class LevelDBCacheBuilder implements CacheBuilder{
    private static Map<String,Object> optionsMap = new HashMap<String, Object>();
    private static DBFactory dbFactory;
    private static DB db;
    public static String CHARSET = "charset";
    public static String LEVELDBPATH = "leveldbpath";
    private static String defaultFilePath = "data/levelDB";
    private static Charset defaultCharset = Charset.forName("utf-8");

    public Cache build() throws IOException {
        return new LevelDBCache(db,defaultCharset);
    }

    public CacheBuilder option(String key,Object value) {
        this.optionsMap.put(key,value);
        return this;
    }

    public static DB getDb() throws IOException {
        if (optionsMap.containsKey(CHARSET)){
            defaultCharset = (Charset) optionsMap.get(CHARSET);
        }
        if (optionsMap.containsKey(LEVELDBPATH)){
            defaultFilePath = (String) optionsMap.get(LEVELDBPATH);
        }
        dbFactory = Iq80DBFactory.factory;
        db=dbFactory.open(new File(defaultFilePath),new Options());
        return db;
    }


}

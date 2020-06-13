package com.mike.trade.cache;

import com.jfireframework.fse.ByteArray;
import com.jfireframework.fse.Fse;
import org.iq80.leveldb.DB;

import java.io.IOException;
import java.nio.charset.Charset;

public class LevelDBCache implements Cache{
    private DB db;
    private Charset charset;
    private static ThreadLocal<Fse> fseThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<ByteArray> byteArrayThreadLocal = new ThreadLocal<>();
    private Fse fse2 = new Fse();

    public LevelDBCache(DB db, Charset charset) {
        this.db = db;
        this.charset = charset;
    }

    public Object getObject(String key) {
        ByteArray byteArray = getByteArray();
        byte[] bytes = db.get(key.getBytes());
        if (bytes==null) return null;
        byteArray.put(bytes);
       return getFse().deSerialize(byteArray);
    }

    public String getString(String key) {
        return new String(db.get(key.getBytes()),charset);
    }

    public void putObject(String key, Object value) {
        ByteArray byteArray = getByteArray();
         getFse().serialize(value,byteArray);
         db.put(key.getBytes(),byteArray.toArray());
    }

    public void putString(String key, String value) {
        db.put(key.getBytes(),value.getBytes());
    }


    public void delete(String key) {
        db.delete(key.getBytes());
    }

    public void close() throws IOException {
        db.close();
    }

    public DB getDb() {
        return db;
    }

    public void setDb(DB db) {
        this.db = db;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    private ByteArray getByteArray(){
        ByteArray byteArray = byteArrayThreadLocal.get();
        if (byteArray==null){
            byteArray = ByteArray.allocate();
            byteArrayThreadLocal.set(byteArray);
        }
        byteArray.clear();
        return byteArray;
    }

    private Fse getFse(){
        Fse fse = fseThreadLocal.get();
        if (fse==null) {
            fse = new Fse();
            fseThreadLocal.set(fse);
        }
        return fse;
    }
}

package com.mike.trade.cache;

import java.io.IOException;

public interface Cache {
    Object getObject(String key);
    String getString(String key);
    void putObject(String key,Object value);
    void putString(String key,String value);
    void delete(String key);
    void close() throws IOException;
}

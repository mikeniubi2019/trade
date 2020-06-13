package com.mike.trade.cache;

import java.io.IOException;

public interface CacheBuilder {
    Cache build() throws IOException;
    CacheBuilder option(String key,Object value);
}

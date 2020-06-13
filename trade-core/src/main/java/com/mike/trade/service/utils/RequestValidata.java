package com.mike.trade.service.utils;

import com.mike.trade.pojo.servicePojo.Request;

public class RequestValidata {
    public static boolean validataRequest(Request request){
        if (request.getStatus()==0) {
            return false;
        }else if (request.getPrice()<0){
            return false;
        }else if (request.getAcount()<0){
            return false;
        }else if (request.getUserId()==0){
            return false;
        }else if (request.getRequestId()==0){
            return false;
        }
        return true;
    }

    public static boolean checkRequestRepeat(Request request){
        return null==TradeCacheServiceUtils.getCache(request);
    }
}

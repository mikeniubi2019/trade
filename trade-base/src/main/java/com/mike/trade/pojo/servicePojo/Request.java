package com.mike.trade.pojo.servicePojo;

public class Request {
    private long requestId;
    private long userId;
    private int status;
    private int tradeName;
    private int tradeType;
    private long creatTime;
    private double acount;
    private double price;
    private int actionType;


    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTradeName() {
        return tradeName;
    }

    public void setTradeName(int tradeName) {
        this.tradeName = tradeName;
    }

    public int getTradeType() {
        return tradeType;
    }

    public void setTradeType(int tradeType) {
        this.tradeType = tradeType;
    }

    public long getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(long creatTime) {
        this.creatTime = creatTime;
    }

    public double getAcount() {
        return acount;
    }

    public void setAcount(double acount) {
        this.acount = acount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }


    @Override
    public String toString() {
        return "Request{" +
                "requestId=" + requestId +
                ", userId=" + userId +
                ", status=" + status +
                ", tradeName=" + tradeName +
                ", tradeType=" + tradeType +
                ", creatTime=" + creatTime +
                ", acount=" + acount +
                ", price=" + price +
                ", actionType=" + actionType +
                '}';
    }
}

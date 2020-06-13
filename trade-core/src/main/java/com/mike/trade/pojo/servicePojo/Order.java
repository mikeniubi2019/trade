package com.mike.trade.pojo.servicePojo;

public class Order {
    private long sellId;
    private long buyId;
    private long orderTime;
    private double price;
    private double acount;
    private volatile boolean isOK=false;
    private int tradeType;
    private long requestId;
    private int tradeName;

    public int getTradeName() {
        return tradeName;
    }

    public void setTradeName(int tradeName) {
        this.tradeName = tradeName;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public int getTradeType() {
        return tradeType;
    }

    public void setTradeType(int tradeType) {
        this.tradeType = tradeType;
    }

    public long getSellId() {
        return sellId;
    }

    public void setSellId(long sellId) {
        this.sellId = sellId;
    }

    public long getBuyId() {
        return buyId;
    }

    public void setBuyId(long buyId) {
        this.buyId = buyId;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAcount() {
        return acount;
    }

    public void setAcount(double acount) {
        this.acount = acount;
    }

    public boolean isOK() {
        return isOK;
    }

    public void setOK(boolean OK) {
        isOK = OK;
    }

    public Order(long sellId, long buyId, long orderTime, double price, double acount, boolean isOK, int tradeType, long requestId,int tradeName) {
        this.sellId = sellId;
        this.buyId = buyId;
        this.orderTime = orderTime;
        this.price = price;
        this.acount = acount;
        this.isOK = isOK;
        this.tradeType = tradeType;
        this.requestId = requestId;
        this.tradeName = tradeName;
    }

    public Order() {
    }

    @Override
    public String toString() {
        return "Order{" +
                "sellId=" + sellId +
                ", buyId=" + buyId +
                ", orderTime=" + orderTime +
                ", price=" + price +
                ", acount=" + acount +
                ", isOK=" + isOK +
                ", tradeType=" + tradeType +
                ", requestId=" + requestId +
                ", tradeName=" + tradeName +
                '}';
    }
}

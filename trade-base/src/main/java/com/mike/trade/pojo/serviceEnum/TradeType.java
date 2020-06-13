package com.mike.trade.pojo.serviceEnum;

public enum TradeType {
    //卖单，买单
    SELL(1),BUY(2);
    private int value;

    TradeType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "TradeType{" +
                "value=" + value +
                '}';
    }
}

package com.mike.trade.pojo.serviceEnum;

public enum TradeName {
    //交易对类型
    BTCEOS(1),BTCETH(2),BTCETC(3);
    private int value;

    TradeName(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "TradeName{" +
                "value=" + value +
                '}';
    }
}

package com.mike.trade.pojo.serviceEnum;

public enum StatuType {
    //状态
    SUCSESS(0),FAIL(1),DOING(2);
    private int value;

    StatuType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "StatuType{" +
                "value=" + value +
                '}';
    }
}

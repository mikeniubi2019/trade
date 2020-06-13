package com.mike.trade.pojo.serviceEnum;

public enum ActionType {
    //限价，市价
    LIMIT(1),MARKET(2);

    private int value;

    ActionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ActionType{" +
                "value=" + value +
                '}';
    }
}

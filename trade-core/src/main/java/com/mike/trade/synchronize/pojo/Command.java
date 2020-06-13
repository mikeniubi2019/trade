package com.mike.trade.synchronize.pojo;

public enum Command {
    syn(1),sna(2),log(3);
    private int value;

    Command(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

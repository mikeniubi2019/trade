package com.mike.trade.synchronize.pojo;

public class TransferFile {
    private String locationPath;
    private byte[] datas;

    public String getLocationPath() {
        return locationPath;
    }

    public void setLocationPath(String locationPath) {
        this.locationPath = locationPath;
    }

    public byte[] getDatas() {
        return datas;
    }

    public void setDatas(byte[] datas) {
        this.datas = datas;
    }
}

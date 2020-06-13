package com.mike.trade.synchronize.pojo;

public class LogBody {
    private long requestId;
    private byte[] data;

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public LogBody(long requestId, byte[] data) {
        this.requestId = requestId;
        this.data = data;
    }
}

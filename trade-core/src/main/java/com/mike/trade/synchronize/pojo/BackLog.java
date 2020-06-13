package com.mike.trade.synchronize.pojo;

public class BackLog {
    private long version;
    private int methond;
    private LogBody logBody;

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public int getMethond() {
        return methond;
    }

    public void setMethond(int methond) {
        this.methond = methond;
    }

    public LogBody getLogBody() {
        return logBody;
    }

    public void setLogBody(LogBody logBody) {
        this.logBody = logBody;
    }
}

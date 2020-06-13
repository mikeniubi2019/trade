package com.mike.trade.synchronize.backUpLog;

import com.mike.trade.synchronize.pojo.BackLog;

import java.io.File;
import java.util.ArrayList;

public interface Log {
    void writerLog(BackLog backLog);
    void compactAndFlush(ArrayList<BackLog> arrayList);
    BackLog findLogByVersion(long version);
    File[] findLogFilesAfterVersion(long version);
    String[] findLogFilePathsAfterVersion(long version);

}

package com.mike.trade.synchronize.backUpLog;

import com.jfireframework.fse.ByteArray;
import com.jfireframework.fse.Fse;
import com.mike.trade.synchronize.pojo.BackLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class BackLogUtils implements Log{
    private final String logPath = "backLog";
    private ArrayList<BackLog> noUseLogs = new ArrayList<>(5000);
    private ArrayList<BackLog> currentLogs = new ArrayList<>(5000);
    private ArrayList<BackLog> tempLogs ;
    private AtomicInteger backLogPosition = new AtomicInteger(0);
    private ExecutorService asychronizeLogWriterPoll = Executors.newSingleThreadExecutor();
    private static ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024*1024);

    @Override
    public void writerLog(BackLog backLog) {
        int pos = backLogPosition.getAndIncrement();
        if (pos==5000){
            synchronized (backLogPosition){
                backLogPosition.set(0);
                tempLogs = currentLogs;
                currentLogs = (ArrayList<BackLog>)noUseLogs.clone();
                backLogPosition.notifyAll();
            }
            tempLogs.set(4999,backLog);
            compactAndFlush(tempLogs);
            tempLogs=null;
        }else if (pos>5000){
            synchronized (backLogPosition){
                try {
                    backLogPosition.wait(100);
                    backLogPosition.notifyAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            pos = pos%5000;
            currentLogs.set(pos-1,backLog);
        }else {
            currentLogs.set(pos-1,backLog);
        }
    }

    @Override
    public void compactAndFlush(ArrayList<BackLog> backLogArrayList) {
        asychronizeLogWriterPoll.execute(new writerLogUnnable(backLogArrayList));
    }

    @Override
    public BackLog findLogByVersion(long version) {
        return null;
    }

    @Override
    public File[] findLogFilesAfterVersion(long version) {
        File dirFile = new File(logPath);
        if (!dirFile.exists()){
            dirFile.mkdirs();
            return null;
        }
        File[] files = dirFile.listFiles();
        return Arrays.stream(files).filter(file -> {
            String fileName = file.getName();
            fileName = fileName.substring(0,fileName.lastIndexOf("."));
            Long l = Long.parseLong(fileName);
            return l>=version;
        }).toArray(File[]::new);
    }

    @Override
    public String[] findLogFilePathsAfterVersion(long version) {
        File dirFile = new File(logPath);
        if (!dirFile.exists()){
            dirFile.mkdirs();
            return null;
        }
        File[] files = dirFile.listFiles();
        return Arrays.stream(files).filter(file -> {
            String fileName = file.getName();
            fileName = fileName.substring(0,fileName.lastIndexOf("."));
            Long l = Long.parseLong(fileName);
            return l>=version;
        }).map(File::getName).toArray(String[]::new);
    }

    class writerLogUnnable implements Runnable{
        private ArrayList<BackLog> backLogArrayList;

        public writerLogUnnable(ArrayList<BackLog> backLogArrayList) {
            this.backLogArrayList = backLogArrayList;
        }

        @Override
        public void run() {
            Fse fse = new Fse();
            File dirFile = new File(logPath);
            if (!dirFile.exists()){
                dirFile.mkdirs();
            }
            FileOutputStream fileOutputStream=null;

            File logFile = new File(logPath+"/"+backLogArrayList.get(4999).getVersion()+".mlog");
            try {
                logFile.createNewFile();
                fileOutputStream = new FileOutputStream(logFile);
                FileChannel fileChannel = fileOutputStream.getChannel();
                ByteArray byteArray = ByteArray.allocate();
                backLogArrayList.stream().forEachOrdered(
                        backLog -> {
                           fse.serialize(backLog,byteArray);
                           byte[] bytes = byteArray.toArray();
                            try {
                                if (byteBuffer.capacity()<(bytes.length+4)){
                                    byteBuffer = ByteBuffer.allocateDirect(bytes.length+4);
                                }
                                byteBuffer.putInt(bytes.length);
                                byteBuffer.put(bytes);
                                byteBuffer.flip();
                                while (byteBuffer.hasRemaining()){
                                    fileChannel.write(byteBuffer);
                                }
                                byteBuffer.clear();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            byteArray.clear();
                        }
                );
            fileChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                    try {
                        if (fileOutputStream!=null){
                            fileOutputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
    }
}

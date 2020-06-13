package com.mike.trade.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;
import com.mike.trade.disruptor.exception.DisruptorEceptionHandler;
import com.mike.trade.pojo.channelContextHandlerAndRequestHolder.ChannelContextHandlerAndRequestHolder;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class DisruptorBuilder {
    private List<WorkHandler> workHandlerList = new ArrayList<>();
    private WorkHandler[] workHandlers;
    private Executor executor;
    private int ringbuffSize = 1024*1024;
    RingBuffer ringBuffer;
    WorkerPool workerPool;

    public RingBuffer build() throws Exception {

        init();

        ringBuffer = RingBuffer.create(ProducerType.MULTI,ChannelContextHandlerAndRequestHolder::new,ringbuffSize,new SleepingWaitStrategy());

        workerPool = new WorkerPool(ringBuffer,ringBuffer.newBarrier(),new  DisruptorEceptionHandler(),workHandlers);

        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());

        workerPool.start(executor);

        return this.ringBuffer;
    }

    private void init() throws Exception {
        if (workHandlerList.size()<1){
            throw new Exception("创建失败，请添加业务实现类");
        }
        workHandlers = workHandlerList.toArray(new WorkHandler[workHandlerList.size()]);
        executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors()
                ,Runtime.getRuntime().availableProcessors()
                ,30
                , TimeUnit.SECONDS
                ,new ArrayBlockingQueue<>(1000)
                , new ThreadPoolExecutor.AbortPolicy());
    }

    public void addWorker(WorkHandler workHandler){
        this.workHandlerList.add(workHandler);
    }

    public WorkHandler popWorker(){
        return this.workHandlerList.remove(workHandlerList.size()-1);
    }
}

package com.mike.trade.disruptor.exception;

import com.lmax.disruptor.ExceptionHandler;

import java.util.Arrays;

public class DisruptorEceptionHandler implements ExceptionHandler {
    @Override
    public void handleEventException(Throwable throwable, long l, Object o) {
        Arrays.stream(throwable.getStackTrace()).forEach(System.out::println);
    }

    @Override
    public void handleOnStartException(Throwable throwable) {
        Arrays.stream(throwable.getStackTrace()).forEach(System.out::println);
    }

    @Override
    public void handleOnShutdownException(Throwable throwable) {
        Arrays.stream(throwable.getStackTrace()).forEach(System.out::println);
    }
}

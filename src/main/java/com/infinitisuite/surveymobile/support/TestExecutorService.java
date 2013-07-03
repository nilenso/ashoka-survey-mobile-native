package com.infinitisuite.surveymobile.support;

import java.util.concurrent.*;

public class TestExecutorService extends ThreadPoolExecutor {
    public TestExecutorService(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public Future<?> submit(Runnable task) {
        FutureTask futureTask = new FutureTask(task, null);
        futureTask.run(); // or queue this future to be run when you want it to be run
        return futureTask;
    }
}
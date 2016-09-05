package com.silencedut.router.dispatcher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by SilenceDut on 16/8/2.
 */

class AsyncThreadDispatcher implements Dispatcher {
    private ExecutorService mAsyncExecutor ;

    AsyncThreadDispatcher() {
        mAsyncExecutor = Executors.newCachedThreadPool();
    }

    @Override
    public void dispatch(Runnable runnable) {
        if(mAsyncExecutor.isShutdown()) {
            return;
        }
        mAsyncExecutor.execute(runnable);
    }

    @Override
    public boolean stop() {
        return true;
    }
}

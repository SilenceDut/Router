package com.silencedut.router.dispatcher;

/**
 * Created by SilenceDut on 16/8/1.
 */

class PostingThreadDispatcher implements Dispatcher {
    @Override
    public void dispatch(Runnable runnable) {
        runnable.run();
    }

    @Override
    public boolean stop() {
        return true;
    }
}

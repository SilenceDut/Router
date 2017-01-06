package com.silencedut.router.dispatcher;

/**
 * Created by SilenceDut on 16/8/2.
 */

public interface Dispatcher {
    void dispatch(Runnable runnable);

    boolean stop();
}

package com.silencedut.router;

import com.silencedut.router.Annotation.RunThread;
import com.silencedut.router.Annotation.Subscribe;
import com.silencedut.router.dispatcher.Dispatcher;
import com.silencedut.router.dispatcher.DispatcherFactory;

import java.lang.reflect.Method;

/**
 * Created by SilenceDut on 16/8/1.
 */

class Reception {

    private Object mReceiver;
    private Method mInvokedMethod;
    private Object[] mArgs;
    private Runnable mRunnable;
    Dispatcher mDispatcher;

    Reception(Object receiver, Method invokedMethod, Object[] args) {
        this.mReceiver = receiver;
        this.mInvokedMethod = invokedMethod;
        this.mArgs = args;
        initReception();
    }

    private void initReception() {
        mInvokedMethod.setAccessible(true);
        mRunnable = produceEvent();
        RunThread runThread = RunThread.MAIN;
        Subscribe subscribeAnnotation = mInvokedMethod.getAnnotation(Subscribe.class);
        if (subscribeAnnotation != null) {
            runThread = subscribeAnnotation.runThread();
        }
        mDispatcher = DispatcherFactory.getEventDispatch(runThread);
    }

    void dispatchEvent() {
        mDispatcher.dispatch(mRunnable);
    }

    private Runnable produceEvent() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    if (mInvokedMethod != null && mReceiver != null) {
                        mInvokedMethod.invoke(mReceiver, mArgs);
                    }
                } catch (Exception e) {
                    throw new RouterException("UnHandler Exception when method invoke ," + e.getCause());
                }
            }
        };
    }
}
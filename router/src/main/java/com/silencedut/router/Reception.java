package com.silencedut.router;

import com.silencedut.router.Annotation.RunThread;
import com.silencedut.router.Annotation.Subscribe;
import com.silencedut.router.dispatcher.Dispatcher;
import com.silencedut.router.dispatcher.DispatcherFactory;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by SilenceDut on 16/8/1.
 */

class Reception {

    private Object mReceiver;
    private Method mInvokedMethod;
    private Object[] mArgs;
    private Runnable event;
    Dispatcher dispatcher;

    Reception(Object receiver,Method invokedMethod,Object[] args) {
        this.mReceiver = receiver;
        this.mInvokedMethod = invokedMethod;
        this.mArgs = args;
        initReception();
    }

    private void initReception() {
        mInvokedMethod.setAccessible(true);
        event =produceEvent();
        RunThread runThread = RunThread.MAIN;
        Subscribe subscribeAnnotation = mInvokedMethod.getAnnotation(Subscribe.class);
        if(subscribeAnnotation!=null) {
            runThread = subscribeAnnotation.runThread();
        }
        dispatcher = DispatcherFactory.getEventDispatch(runThread);
    }

    void dispatchEvent() {
        dispatcher.dispatch(event);
    }
    private Runnable produceEvent() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    mInvokedMethod.invoke(mReceiver,mArgs);
                } catch (Exception e) {
                    throw new RouterException("UnHandler Exception when method invoke",e);
                }
            }
        };
    }
}
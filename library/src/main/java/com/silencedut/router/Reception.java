package com.silencedut.router;

import com.silencedut.router.Annotation.RunThread;
import com.silencedut.router.Annotation.Subscribe;

import java.lang.reflect.Method;

/**
 * Created by SilenceDut on 16/8/1.
 */

class Reception {

    RunThread runThread;
    Runnable event;
    private Object mReceiver;
    private Method mInvokedMethod;
    private Object[] mArgs;


    Reception(Object receiver,Method invokedMethod,Object[] args) {
        this.mReceiver = receiver;
        this.mInvokedMethod = invokedMethod;
        this.mInvokedMethod.setAccessible(true);
        this.mArgs = args;
        this.event = produceEvent();
        this.runThread = parseRunThread();
    }

    private RunThread parseRunThread() {
        RunThread runThread = RunThread.MAIN;
        Subscribe subscribeAnnotation = mInvokedMethod.getAnnotation(Subscribe.class);
        if(subscribeAnnotation!=null) {
            runThread = subscribeAnnotation.runThread();
        }
        return runThread;
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

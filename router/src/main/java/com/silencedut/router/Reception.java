package com.silencedut.router;

import android.util.Log;

import com.annotation.RunThread;
import com.annotation.Subscribe;
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
        ThreadFinder threadFinderFinder = null;
        try {
            Class<?> methodThreadCls = Class.forName("com.silencedut.router.MethodThreadFinder");
            threadFinderFinder = (ThreadFinder) methodThreadCls.newInstance();
            String fullMethodName = mReceiver.getClass().getName()+"."+mInvokedMethod.getName();
            runThread = RunThread.valueOf(threadFinderFinder.getMethodThread(fullMethodName));
            Log.d("Reception","Use Annotation ahead of runtime");
        } catch (ClassNotFoundException e) {
            Log.e("Reception","Use Annotation ahead of runtime ClassNotFoundException",e);
        } catch (InstantiationException e) {
            Log.e("Reception","Use Annotation ahead of runtime InstantiationException",e);
        } catch (IllegalAccessException e) {
            Log.e("Reception","Use Annotation ahead of runtime IllegalAccessException",e);
        } catch (NullPointerException e) {
            Log.e("Reception","Use Annotation ahead of runtime NullPointerException",e);
        }
        if(threadFinderFinder==null) {
            Subscribe subscribeAnnotation = mInvokedMethod.getAnnotation(Subscribe.class);
            if (subscribeAnnotation != null) {
                runThread = subscribeAnnotation.runThread();
            }
            Log.d("Reception","Use Annotation Runtime");
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
                    Log.d("Reception","UnHandler Exception when method "
                            +mInvokedMethod.getName()+" of "+mReceiver.getClass().getCanonicalName()+","+e);
                }
            }
        };
    }
}
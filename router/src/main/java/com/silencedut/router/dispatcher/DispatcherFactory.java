package com.silencedut.router.dispatcher;

import android.os.Looper;

import com.silencedut.router.Annotation.RunThread;

/**
 * Created by SilenceDut on 16/8/2.
 */

public class DispatcherFactory {

    private static final Dispatcher MAIN_THREAD_DISPATCHER = new MainThreadDispatcher();
    private static final Dispatcher POSTING_THREAD_DISPATCHER = new PostingThreadDispatcher();
    private static final Dispatcher BACKGROUND_THREAD_DISPATCHER = new BackgroundDispatcher();
    private volatile static Dispatcher sAsyncThreadDispatcher;


    public static Dispatcher getEventDispatch(RunThread runThread) {

        switch (runThread) {
            case MAIN:
                return isMainThread() ? POSTING_THREAD_DISPATCHER : MAIN_THREAD_DISPATCHER;

            case POSTING:
                return POSTING_THREAD_DISPATCHER;

            case BACKGROUND:
                return !isMainThread() ? POSTING_THREAD_DISPATCHER : BACKGROUND_THREAD_DISPATCHER;

            case ASYNC:
                //lazy init , create Thread Pool only needed.
                if (sAsyncThreadDispatcher == null) {
                    synchronized (DispatcherFactory.class) {
                        if (sAsyncThreadDispatcher == null) {
                            sAsyncThreadDispatcher = new AsyncThreadDispatcher();
                        }
                    }
                }
                return sAsyncThreadDispatcher;
        }
        return MAIN_THREAD_DISPATCHER;
    }

    private static boolean isMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }


}

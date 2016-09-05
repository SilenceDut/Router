package com.silencedut.routersimple;

import com.silencedut.router.Annotation.RunThread;
import com.silencedut.router.Annotation.Subscribe;

/**
 * Created by SilenceDut on 16/9/1.
 */

public interface Event {
    interface TestRunThread {

        @Subscribe(runThread = RunThread.POSTING)
        void textPostThread(String posting) ;

        void textMainThread(String main);

        void textBackgroundThread(String background) ;

        void textAsyncThread(String async) ;

    }

    interface TestMultiReceivers {
        void testMulti(String msg);
    }
}

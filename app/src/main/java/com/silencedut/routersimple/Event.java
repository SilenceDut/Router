package com.silencedut.routersimple;

/**
 * Created by SilenceDut on 16/9/1.
 */

public interface Event {
    interface TestRunThread {

        void textPostThread(String posting) ;

        void textMainThread(String main);

        void textBackgroundThread(String background) ;

        void textAsyncThread(String async) ;

    }

    interface TestMultiReceivers {
        void testMulti(String msg);
    }
}

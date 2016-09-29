package com.silencedut.routersimple;

import com.silencedut.router.Router;

/**
 * Created by SilenceDut on 16/9/6.
 */

class MainPresenter {

     void testRunThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Router.getInstance().getReceiver(Event.MainView.class).textMainThread("main");
                Router.getInstance().getReceiver(Event.MainView.class).textPostThread("post");
                Router.getInstance().getReceiver(Event.MainView.class).textBackgroundThread("background");
                Router.getInstance().getReceiver(Event.MainView.class).textAsyncThread("async");

            }
        }).start();
    }

    void testMutiReceivers() {
        Router.getInstance().getReceiver(Event.TestMultiReceivers.class).testMulti("TestMultiReceiver");
    }
}

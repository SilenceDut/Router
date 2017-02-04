package com.silencedut.routersimple.routersimple;

import com.annotation.RunThread;
import com.annotation.Subscribe;
import com.silencedut.routersimple.Event.MainView;

/**
 * Created by SilenceDut on 2017/1/18 .
 */

public class TestClass implements MainView{


    @Override
    public void textPostThread(String posting) {

    }

    @Override
    @Subscribe(runThread = RunThread.POSTING)
    public void textMainThread(String main) {

    }

    @Override
    public void textBackgroundThread(String background) {

    }

    @Override
    public void textAsyncThread(String async) {

    }
}

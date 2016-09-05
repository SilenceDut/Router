package com.silencedut.routersimple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.silencedut.router.Router;

/**
 * Created by SilenceDut on 16/9/1.
 */

public class TopFragment extends Fragment implements Event.TestMultiReceivers{
    private TextView mMsgTv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Router.getInstance().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root;
        root = inflater.inflate(R.layout.fragment_top, container, false);
        mMsgTv = (TextView)root.findViewById(R.id.top_tv);
        return root;
    }

    @Override
    public void testMulti(String msg) {
        mMsgTv.setText("Support multi reciever , receive the msg :" +msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Router.getInstance().unregister(this);
    }
}

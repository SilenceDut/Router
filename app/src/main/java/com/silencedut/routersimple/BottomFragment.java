package com.silencedut.routersimple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by SilenceDut on 16/9/1.
 */

public class BottomFragment extends TopFragment {
    private TextView mMsgTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root;
        root = inflater.inflate(R.layout.fragment_bottom, container, false);
        mMsgTv = (TextView)root.findViewById(R.id.bottom_tv);
        return root;
    }

    @Override
    public void testMulti(String msg) {
        mMsgTv.setText("Support inherit , receive the msg :" +msg);
    }

}

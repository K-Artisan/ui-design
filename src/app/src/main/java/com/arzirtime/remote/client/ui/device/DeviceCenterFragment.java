package com.arzirtime.remote.client.ui.device;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arzirtime.remote.R;
import com.arzirtime.remote.client.ui.ISingleActivity;
import com.arzirtime.remote.client.ui.MainActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceCenterFragment extends Fragment {

    private View view;
    private ISingleActivity parentActivity;

    public DeviceCenterFragment(ISingleActivity parentActivity) {
        // Required empty public constructor
        Log.d("k", "DeviceCenterFragment");
        this.parentActivity = parentActivity;
    }

    public static DeviceCenterFragment newInstance(ISingleActivity parentActivity) {
        DeviceCenterFragment fragment = new DeviceCenterFragment(parentActivity);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("k", "DeviceCenterFragment->onCreateView");
        view = inflater.inflate(R.layout.fragment_device_center, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d("k", "DeviceCenterFragment->onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        parentActivity.initBeforeLoadFragment(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //文字信息
                TextView textView = view.findViewById(R.id.tv_device_content);
                if(textView != null) {
                    textView.setText(copyText("这是一个很长的文字！！！~~~"));
                }
            }
        }).start();

    }

    private String copyText(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 50; i++) {
            stringBuilder.append(text);
        }

        return stringBuilder.toString();
    }
}

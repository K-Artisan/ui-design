package com.arzirtime.remote.client.ui.mycenter;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arzirtime.remote.R;
import com.arzirtime.remote.client.ui.ISingleActivity;
import com.arzirtime.remote.client.ui.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCenterFragment extends Fragment {

    private   View view;
    private ISingleActivity parentActivity;

    public MyCenterFragment(ISingleActivity parentActivity) {
        this.parentActivity = parentActivity;
    }

    public static MyCenterFragment newInstance(MainActivity parentActivity) {
        MyCenterFragment fragment = new MyCenterFragment(parentActivity);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_center, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parentActivity.initBeforeLoadFragment(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //文字信息
                TextView textView = view.findViewById(R.id.mycter_text);
                if(textView != null) {
                    textView.setText(copyText("同一个Activity，多个Fragmet不同布局"));
                }
            }
        }).start();

    }

    private String copyText(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            stringBuilder.append(text);
        }
        return stringBuilder.toString();
    }
}

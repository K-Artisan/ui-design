package com.arzirtime.remote.client.ui.smart;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arzirtime.remote.R;
import com.arzirtime.remote.client.ui.ISingleActivity;
import com.arzirtime.remote.client.ui.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SmartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SmartFragment extends Fragment {
    private View view;
    private ISingleActivity parentActivity;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SmartFragment(ISingleActivity parentActivity) {
       this.parentActivity = parentActivity;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SmartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SmartFragment newInstance(ISingleActivity parentActivity, String param1, String param2) {
        SmartFragment fragment = new SmartFragment(parentActivity);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_smart, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parentActivity.initBeforeLoadFragment(this);
    }
}

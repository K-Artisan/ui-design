package com.arzirtime.remote.client.ui;

import androidx.fragment.app.Fragment;

/**
 * 单Activity多Fragment UI结构
 * */
public interface ISingleActivity {
    /**
     * 在加载Frament之前初始化自身的界面；
     * 此方法是解决如下一些场景场景：
     * 1.有些初始化工作无法在Fragment中进行的，比如：初始状态栏，这样得在Activity中进行
     * 2.在Fragment的一些生命周期回调函数中，使用 getActivity(); 返回null，这样就无法在Fragment中使用到Activity。
     * */
     void initBeforeLoadFragment(Fragment fragment);
}

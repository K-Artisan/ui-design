package com.arzirtime.remote.client.ui;

import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.arzirtime.remote.client.receiver.ForceOfflineReceiver;
import com.arzirtime.remote.client.receiver.NetWorkChangeReciever;
import com.arzirtime.remote.client.ui.ActivityManager;
import com.arzirtime.remote.common.AppConstanct;

/**
 * Activity的基础类
 */
public class BaseActivity extends AppCompatActivity {

    private ForceOfflineReceiver forceOfflineReceiver;
    private NetWorkChangeReciever netWorkChangeReciever;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);
    }

    /**
     * 前台生存期 。活动在 onResume() 方法和 onPause()方法之间所经历的就是前台生存期。在前台生存期内，
     * 活动总是处于运行状态的，此时的活动是可以和用户进行交互的，我们平时看到和接触最多的也就是这个状态下的活动
     */
    @Override
    protected void onResume() {
        super.onResume();

        /*--------------------------------------------------
         活动进入活动栈顶端，注册广播接收器
        * -------------------------------------------------*/

        //注册网络播接收器:强制下线
        IntentFilter ifOffline = new IntentFilter();
        ifOffline.addAction(AppConstanct.BROADCAST_FORCE_OFFLINE);
        forceOfflineReceiver = new ForceOfflineReceiver();
        registerReceiver(forceOfflineReceiver, ifOffline);

        //注册广播接收器：网络变化，当网络变化时系统发送该广播
        IntentFilter ifNetwork = new IntentFilter();
        ifNetwork.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        netWorkChangeReciever = new NetWorkChangeReciever();
        registerReceiver(netWorkChangeReciever, ifNetwork);
    }

    /**
     * 前台生存期 。活动在 onResume() 方法和 onPause()方法之间所经历的就是前台生存期。在前台生存期内，
     * 活动总是处于运行状态的，此时的活动是可以和用户进行交互的，我们平时看到和接触最多的也就是这个状态下的活动
     */
    @Override
    protected void onPause() {
        super.onPause();

        /*--------------------------------------------------
         活动离开活动栈顶端，注销广播接收器
        * -------------------------------------------------*/
        if (forceOfflineReceiver != null) {
            unregisterReceiver(forceOfflineReceiver); //注销广播接收器
            forceOfflineReceiver = null;
        }

        if (netWorkChangeReciever != null) {
            unregisterReceiver(netWorkChangeReciever); //注销广播接收器
            netWorkChangeReciever = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ActivityManager.removeActivity(this);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //按返回键，程序进入后台运行，不关闭程序
        moveTaskToBack(true);
    }
}
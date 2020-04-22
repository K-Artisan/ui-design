package com.arzirtime.remote.client.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.arzirtime.remote.R;
import com.arzirtime.remote.client.ui.AdvActivity;
import com.arzirtime.remote.client.ui.BaseActivity;

/**
 * 启动页:
 * 一般用于展示公司的LOGO，企业文化等
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        autoGotoNextActivity();
    }

    private void autoGotoNextActivity(){
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);//使程序休眠一秒
                    Intent intent = new Intent(getApplicationContext(), AdvActivity.class);
                    startActivity(intent);
                    finish();//关闭当前活动
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();//启动线程
    }
}

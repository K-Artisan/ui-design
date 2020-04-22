package com.arzirtime.remote.client.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.arzirtime.remote.R;

import java.util.Random;

/**
 * 广告页
 */
public class AdvActivity extends AppCompatActivity {

    private int[] advPicsLayout = {R.layout.adv_layout_1, R.layout.adv_layout_2,
            R.layout.adv_layout_3, R.layout.adv_layout_4, R.layout.adv_layout_5};
    private int i;
    private int skipSecond = 5;
    private Button mBtnSkip;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                mBtnSkip.setText("跳过 (" + autoSkipCountDown() + ")");
                handler.sendEmptyMessageDelayed(0, 1000);
            }
        }
    };
    private LinearLayout linearLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Random r = new Random();
        i = r.nextInt(2000);

        if (i < 5) {
            //随机概率会出现广告页
            setContentView(R.layout.activity_adv);
            initView();
        } else {
            goToNextActivity();
        }
    }

    private void initView() {
        linearLayout = findViewById(R.id.advRootLayout);
        View view = View.inflate(AdvActivity.this, advPicsLayout[i], null);
        linearLayout.addView(view);
        mBtnSkip = view.findViewById(R.id.btn_skip);
        mBtnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNextActivity();
                handler.removeMessages(0);
            }
        });
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    /**
     *自动跳转倒计时
     */
    private int autoSkipCountDown() {
        skipSecond--;
        if (skipSecond == 0) {
            goToNextActivity();
        }
        return skipSecond;
    }

    private void goToNextActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

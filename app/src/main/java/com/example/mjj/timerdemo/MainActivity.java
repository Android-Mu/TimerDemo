package com.example.mjj.timerdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.lang.ref.WeakReference;

/**
 * Description：Android开发之计时器总结
 * <p>
 * Created by Mjj on 2016/12/4.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static Button btn1, btn2;
    private TimeCount timeCount;

    private MyHandler myHandler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.tv1);
        btn2 = (Button) findViewById(R.id.tv2);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv1:
                timer1();
                break;
            case R.id.tv2:
                sendMessageClick();
                break;
        }
    }

    private void timer1() {
        timeCount.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        timeCount = new TimeCount(btn1, 10000, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeCount.cancel();
    }

    /**
     * 第二种方式：使用Handler
     * <p>
     * 静态内部类：避免内存泄漏
     */
    private static class MyHandler extends Handler {

        private final WeakReference<MainActivity> weakReference;

        public MyHandler(MainActivity activity) {
            weakReference = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity activity = weakReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case 0:
                        if (msg.arg1 == 0) {
                            btn2.setText("重新获取");
                            btn2.setBackgroundColor(Color.parseColor("#f95353"));
                            btn2.setClickable(true);
                        } else {
                            btn2.setText("(" + msg.arg1 + ")秒");
                            btn2.setBackgroundColor(Color.parseColor("#c1c1c1"));
                            btn2.setClickable(false);
                        }
                        break;
                }
            }
        }
    }

    private void sendMessageClick() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 10; i >= 0; i--) {
                    Message msg = myHandler.obtainMessage();
                    msg.arg1 = i;
                    myHandler.sendMessage(msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
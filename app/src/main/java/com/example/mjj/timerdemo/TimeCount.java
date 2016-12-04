package com.example.mjj.timerdemo;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.Button;

/**
 * Description：自定义Timer
 * <p>
 * Created by Mjj on 2016/12/4.
 */

public class TimeCount extends CountDownTimer {

    private Button button;

    //参数依次为总时长,和计时的时间间隔
    public TimeCount(Button button, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.button = button;
    }

    //计时过程显示
    @Override
    public void onTick(long millisUntilFinished) {
        String time = "(" + millisUntilFinished / 1000 + ")秒";
        setButtonInfo(time, "#c1c1c1", false);
    }

    //计时完毕时触发
    @Override
    public void onFinish() {
        setButtonInfo("重新获取", "#f95353", true);
    }

    /**
     * 验证按钮在点击前后相关设置
     *
     * @param content 要显示的内容
     * @param color   颜色值
     * @param isClick 是否可点击
     */
    private void setButtonInfo(String content, String color, boolean isClick) {
        button.setText(content);
        button.setBackgroundColor(Color.parseColor(color));
        button.setClickable(isClick);
    }
}

package com.example.communication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.communication.R;
import com.example.communication.utils.SharedPreferencesUtils;

/**
 * 主页面
 */
public class MyActivity extends BaseAcitvity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //判断是否加载
        if (spUtils.getBoolean("refresh", false)) {   //如果没有保存数据，默认false
            showLoading();//显示加载进度条
        }

        //每次过后把属性值设置成false
        spUtils.putValues(new SharedPreferencesUtils.ContentValue("refresh", false));

    }

    /**
     * 跳转到下一个页面
     *
     * @param view
     */
    public void jumpToNextActivity(View view) {
        startActivity(new Intent(this, MyActivity2.class));
    }

    /**
     * 关闭页面
     *
     * @param view
     */
    public void closeActivity(View view) {
        finishForDoubleTimes();//点击两次关闭的效果
    }


}

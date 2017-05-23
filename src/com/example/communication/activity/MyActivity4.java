package com.example.communication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.example.communication.R;
import com.example.communication.utils.SharedPreferencesUtils;

/**
 * 第四个页面
 */
public class MyActivity4 extends BaseAcitvity implements CompoundButton.OnCheckedChangeListener {

    CheckBox checkbox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        ((Button) findViewById(R.id.btn_jump)).setText("第四个页面,跳转到第五个页面");
        checkbox = (CheckBox) findViewById(R.id.checkbox);
        checkbox.setOnCheckedChangeListener(this);
    }

    /**
     * 跳转到第一个页面
     *
     * @param view
     */
    public void jumpToNextActivity(View view) {
        //一次性保存几个数据的测试
        spUtils.putValues(new SharedPreferencesUtils.ContentValue("name", "liewnzhi")
                , new SharedPreferencesUtils.ContentValue("sex", "男")
                , new SharedPreferencesUtils.ContentValue("age", 26)
                , new SharedPreferencesUtils.ContentValue("old", true)
                , new SharedPreferencesUtils.ContentValue("love", "meinv")
                , new SharedPreferencesUtils.ContentValue("sport", "...")
        );


        startActivity(new Intent(this, MyActivity5.class));
    }

    /**
     * CheckBox的监听方法
     *
     * @param buttonView
     * @param isChecked
     */
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (isChecked) {   //如果被选中了，就设置刷新属性值为true
            spUtils.putValues(new SharedPreferencesUtils.ContentValue("refresh", true));
        } else {
            spUtils.putValues(new SharedPreferencesUtils.ContentValue("refresh", false));
        }
    }

    /**
     * 关闭页面
     *
     * @param view
     */
    public void closeActivity(View view) {
        finish();
    }
}

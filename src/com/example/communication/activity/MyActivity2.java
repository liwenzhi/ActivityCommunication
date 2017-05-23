package com.example.communication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.example.communication.R;
import com.example.communication.utils.SharedPreferencesUtils;

/**
 * 第二个页面
 */
public class MyActivity2 extends BaseAcitvity implements CompoundButton.OnCheckedChangeListener {

    CheckBox checkbox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        checkbox = (CheckBox) findViewById(R.id.checkbox);
        checkbox.setOnCheckedChangeListener(this);
    }

    /**
     * 跳转到下一个页面
     *
     * @param view
     */
    public void jumpToNextActivity(View view) {
        startActivity(new Intent(this, MyActivity3.class));
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

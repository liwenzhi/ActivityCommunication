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
 * 演示获取多个数据的页面
 */
public class MyActivity5 extends BaseAcitvity implements CompoundButton.OnCheckedChangeListener {

    CheckBox checkbox;
    Button btn_jump;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        btn_jump = (Button) findViewById(R.id.btn_jump);
        btn_jump.setText("第五个页面，跳转到第一个页面");
        checkbox = (CheckBox) findViewById(R.id.checkbox);
        checkbox.setOnCheckedChangeListener(this);
        getData();
    }

    /**
     * 获取第四个页面发来的数据
     */
    public void getData() {
        String name = spUtils.getString("name");
        String sex = spUtils.getString("sex");
        Integer age = spUtils.getInt("age");
        boolean old = spUtils.getBoolean("old", false);
        btn_jump.append("\n\n" + name);
        btn_jump.append("\n\n" + sex);
        btn_jump.append("\n\n" + age);
        btn_jump.append("\n\n" + old);
    }

    /**
     * 跳转到第一个页面
     *
     * @param view
     */
    public void jumpToNextActivity(View view) {
        startActivity(new Intent(this, MyActivity.class));
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

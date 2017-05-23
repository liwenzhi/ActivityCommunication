package com.example.communication.activity;

import android.app.Activity;
import android.os.Bundle;
import com.example.communication.R;
import com.example.communication.dialog.LoadingDialog;
import com.example.communication.utils.SharedPreferencesUtils;

/**
 * Base类
 */
public class BaseAcitvity extends Activity {

    private LoadingDialog mLoadingDialog; //加载数据时显示的简单的对话框

    SharedPreferencesUtils spUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spUtils = new SharedPreferencesUtils(this, "setting");
    }

    /**
     * 显示进度条
     */
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this, getString(R.string.loading), false);
        }
        mLoadingDialog.show();
    }

    /**
     * 隐藏进度条
     */
    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.hide();
        }
    }

    /**
     * 页面关闭时关闭进度条
     */
    protected void onDestroy() {
        super.onDestroy();
        if (mLoadingDialog != null) {
            mLoadingDialog.cancel();
            mLoadingDialog = null;
        }
    }

    /**
     * 监听回退键
     */
    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (mLoadingDialog != null) {
            mLoadingDialog.cancel();
            mLoadingDialog = null;
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 两次点击关闭退出页面
     */
    long currentTime = 0;

    public void finishForDoubleTimes() {
        if ((System.currentTimeMillis() - currentTime) < 3000) {
            super.onBackPressed();
        } else {
            // CommonUtils.showToast(this, "再次点击关闭程序");
            currentTime = System.currentTimeMillis();
        }
    }
}

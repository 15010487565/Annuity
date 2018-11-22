package com.denong.doluck.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.denong.doluck.R;

import java.io.IOException;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;


public class LoginActivity extends SimpleTopbarActivity {

    private TextView tvWexinLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
//        tvWexinLogin = findViewById(R.id.tv_WexinLogin);
//        tvWexinLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
//            case R.id.tv_WexinLogin:
//                loginByWeixin();
//                break;
        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {

    }

    @Override
    public void onCancelResult() {

    }

    @Override
    public void onErrorResult(int errorCode, IOException errorExcep) {

    }

    @Override
    public void onParseErrorResult(int errorCode) {

    }

    @Override
    public void onFinishResult() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

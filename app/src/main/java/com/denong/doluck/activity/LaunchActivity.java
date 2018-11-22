package com.denong.doluck.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cmic.sso.sdk.auth.AuthnHelper;
import com.cmic.sso.sdk.auth.TokenListener;
import com.denong.doluck.application.BaseApplication;
import com.denong.doluck.util.Constant;
import com.denong.doluck.util.StringFormat;

import org.json.JSONObject;

import www.xcd.com.mylibrary.utils.SharePrefHelper;

/**
 * Created by chen on 2018/10/16.
 *
 * 引导页或者启动页过后的广告页面  点击跳过或者自动3秒后跳到首页 不缓存图片
 */
public class LaunchActivity extends AppCompatActivity{

    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE_PRE = 1000;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE_IMPLICIT_LOGIN = 2000;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE_DISPLAY_LOGIN = 3000;
    AuthnHelper mAuthnHelper;
    TokenListener mListener;
    public String mResultString;
    private String mAccessToken;
    private Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_launch);
        isFirstOpen();
        mAuthnHelper = AuthnHelper.getInstance(this);
        //实现取号回调
        mListener = new TokenListener() {
            @Override
            public void onGetTokenComplete(JSONObject jObj) {
                // 应用接收到回调后的处理逻辑
                if (jObj != null) {
                    Log.e("TAG_","一键登录="+jObj.toString());
                    try {
                        //时间
                        if ("200060".equals(jObj.getString("resultCode"))) {
//                            Intent intent = new Intent(getContext(), YourSmsAuthActivity.class);
//                            startActivity(intent);
                            return;
                        }
                        mResultString = jObj.toString();

                        if (jObj.has("token")) {
                            mAccessToken = jObj.optString("token");

                            BaseApplication.getInstance().setTokenSSO(mAccessToken);

                        }
                        BaseApplication.getInstance().setResultSSO(jObj.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        //调用取号方法
        getPhoneInfo();
    }


    private void getPhoneInfo() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE_PRE);
            } else {
                mAuthnHelper.getPhoneInfo(Constant.APP_ID, Constant.APP_KEY, 8000, mListener);
            }
        } else {
            mAuthnHelper.getPhoneInfo(Constant.APP_ID, Constant.APP_KEY, 8000, mListener);
        }
    }

    private void isFirstOpen() {
        isFirstOpen(3000);
    }
    private void isFirstOpen(int skipTime) {
        Handler handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                //是否首次登陆
                boolean isFristLogin = SharePrefHelper.getInstance(LaunchActivity.this).getSpBoolean("ISFRISTLOGIN", false);
                Log.e("TAG_isShowState","isFristLogin="+isFristLogin);
                if (!isFristLogin){
                    startActivity(new Intent(LaunchActivity.this, WelcomeActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(LaunchActivity.this, MainActivity.class));
//                    startActivity(new Intent(LaunchActivity.this,LoginActivity.class));
                    finish();
                }
            }
        };
        handler.postDelayed(runnable, skipTime);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_PHONE_STATE_PRE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getPhoneInfo();
                }else {
                    mListener.onGetTokenComplete(StringFormat.getLoginResult("200005", "用户未授权READ_PHONE_STATE"));
                }
                break;
            case PERMISSIONS_REQUEST_READ_PHONE_STATE_IMPLICIT_LOGIN:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    implicitLogin();
                }else {
                    mListener.onGetTokenComplete(StringFormat.getLoginResult("200005", "用户未授权READ_PHONE_STATE"));
                }
                break;
            case PERMISSIONS_REQUEST_READ_PHONE_STATE_DISPLAY_LOGIN:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    displayLogin();
                }else {
                    mListener.onGetTokenComplete(StringFormat.getLoginResult("200005", "用户未授权READ_PHONE_STATE"));
                }
                break;
            default:
                break;
        }
    }
    private void implicitLogin() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE_IMPLICIT_LOGIN);
            } else {

                mAuthnHelper.mobileAuth(Constant.APP_ID, Constant.APP_KEY, mListener);
            }
        } else {

            mAuthnHelper.mobileAuth(Constant.APP_ID, Constant.APP_KEY, mListener);
        }
    }private void displayLogin() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE_DISPLAY_LOGIN);
            } else {
                mAuthnHelper.loginAuth(Constant.APP_ID, Constant.APP_KEY, mListener);
            }
        } else {
            mAuthnHelper.loginAuth(Constant.APP_ID, Constant.APP_KEY, mListener);
        }
    }


}

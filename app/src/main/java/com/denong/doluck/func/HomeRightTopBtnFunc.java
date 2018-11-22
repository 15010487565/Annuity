package com.denong.doluck.func;

import android.app.Activity;
import android.view.View;

import com.denong.doluck.R;

import www.xcd.com.mylibrary.func.BaseTopImageBtnFunc;


public class HomeRightTopBtnFunc extends BaseTopImageBtnFunc {

    public HomeRightTopBtnFunc(Activity activity) {
        super(activity);
    }

    @Override
    public int getFuncId() {
        return R.id.home_right;
    }

    @Override
    public int getFuncIcon() {
        return R.mipmap.ic_launcher;
    }

    @Override
    public void onclick(View v) {

    }
}

package com.denong.doluck.func;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.denong.doluck.R;

import www.xcd.com.mylibrary.func.BaseTopImageBtnFunc;


public class MeLeftTopBtnFunc extends BaseTopImageBtnFunc {

	public MeLeftTopBtnFunc(Activity activity) {
		super(activity);
	}

	@Override
	public int getFuncId() {
		return R.id.me_left;
	}

	@Override
	public int getFuncIcon() {
		return R.mipmap.ic_launcher;
	}

	@Override
	public void onclick(View v) {
		Log.e("TAG_我的","设置");
//		getActivity().startActivity(new Intent(getActivity(), SettingActivity.class));
	}
}

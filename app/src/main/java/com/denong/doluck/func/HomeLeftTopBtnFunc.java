package com.denong.doluck.func;

import android.app.Activity;
import android.view.View;

import com.denong.doluck.R;

import www.xcd.com.mylibrary.func.BaseTopImageBtnFunc;


public class HomeLeftTopBtnFunc extends BaseTopImageBtnFunc {

	public HomeLeftTopBtnFunc(Activity activity) {
		super(activity);
	}

	@Override
	public int getFuncId() {
		return R.id.home_left;
	}

	@Override
	public int getFuncIcon() {
		return R.mipmap.ic_launcher;
	}

	@Override
	public void onclick(View v) {

//		getActivity().startActivity(new Intent(getActivity(), SystemActivity.class));
	}
}

package com.denong.doluck.fragment;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.denong.doluck.R;
import com.denong.doluck.func.ContactRightTopBtnFunc;
import com.denong.doluck.listener.AppBarStateChangeListener;
import com.denong.doluck.util.EventBusMsg;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import www.xcd.com.mylibrary.activity.PermissionsChecker;
import com.denong.doluck.application.SimpleTopbarFragment;
import www.xcd.com.mylibrary.view.MultiSwipeRefreshLayout;


/**
 * Created by gs on 2018/10/16.
 */

public class ManageFragment extends SimpleTopbarFragment implements SwipeRefreshLayout.OnRefreshListener{

    private static final String[] AUTHORIMAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.WRITE_CONTACTS
            , Manifest.permission.READ_CONTACTS
    };
    private PermissionsChecker mChecker;

    private RecyclerView rcContact,rcFriend;
    private LinearLayoutManager mLinearLayoutManager;
    private RelativeLayout reSearch;
    private LinearLayout llInviteFriend;
    private MultiSwipeRefreshLayout contactSwLayout;

    private static Class<?> rightFuncArray[] = {ContactRightTopBtnFunc.class};

    @Override
    protected Object getTopbarTitle() {
        return R.string.contact;
    }

    @Override
    protected Class<?>[] getTopbarRightFuncArray() {
        return rightFuncArray;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contact;
    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {

        initSwipeRefreshLayout(view);
        reSearch = view.findViewById(R.id.re_ContactSearch);
        reSearch.setOnClickListener(this);
        llInviteFriend = view.findViewById(R.id.ll_InviteFriend);
        llInviteFriend.setOnClickListener(this);
    }
    private void initSwipeRefreshLayout(View view) {
        contactSwLayout = view.findViewById(R.id.swipe_layout);
//        contactSwLayout.setOnRefreshListener(this);
        //禁止下拉
//        contactSwLayout.setEnabled(false);
        //下拉刷新监听
        contactSwLayout.setOnRefreshListener(this);
        //设置样式刷新显示的位置
        contactSwLayout.setProgressViewOffset(true, -20, 100);
        contactSwLayout.setColorSchemeResources(R.color.red, R.color.orange, R.color.blue, R.color.black);
        AppBarLayout appBarLayout =  view.findViewById(R.id.appbar);

        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
//                Log.e("STATE", state.name());
                if (state == State.EXPANDED) {
                    //展开状态
                    contactSwLayout.setEnabled(true);
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    contactSwLayout.setEnabled(false);
                } else {
                    //中间状态
                    contactSwLayout.setEnabled(false);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.re_ContactSearch:
//                Intent intent = new Intent(getActivity(), SearchActivity.class);
//                startActivity(intent);
                break;
            case R.id.ll_InviteFriend:
//                Intent intent1 = new Intent(getActivity(), InviteFriendBitActivity.class);
//                intent1.putExtra("FriendType",1);
//                startActivity(intent1);
                break;

        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        if (returnCode == 200) {
            switch (requestCode) {
                case 100:
                    contactSwLayout.setRefreshing(false);
//                    FriendModel friendModel = JSON.parseObject(returnData, FriendModel.class);

                    break;
                case 101:

                    break;
            }
        }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().register(this);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusMsg event){
        String msg = event.getMsg();
        if ("RefreshContact".equals(msg)) {

        }
    }

    @Override
    public void onRefresh() {
        setDialogShow(false);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                msg.what = 0;
                msg.obj = getActivity();
                handler.sendMessage(msg);
//                swipeRefreshLayout.setRefreshing(false);
            }
        };
        new Timer().schedule(timerTask, 2000);
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    contactSwLayout.setRefreshing(false);
                    break;
            }
        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}

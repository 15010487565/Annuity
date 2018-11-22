package com.denong.doluck.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.denong.doluck.R;
import com.denong.doluck.activity.WebViewActivity;
import com.denong.doluck.adapter.HomeHotCityPagerAdapter;
import com.denong.doluck.adapter.HomeRcBottomAdapter;
import com.denong.doluck.adapter.HomeRcHotCityAdapter;
import com.denong.doluck.adapter.HomeRcTopAdapter;
import com.denong.doluck.adapter.HomeTopPagerAdapter;
import com.denong.doluck.application.SimpleTopbarFragment;
import com.denong.doluck.util.GlideImageLoader;
import com.denong.doluck.util.RecyclerViewDecoration;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.utils.ToastUtil;


/**
 * Created by gs on 2018/10/16.
 */

public class HomeFragment extends SimpleTopbarFragment implements OnBannerListener,
        HomeRcTopAdapter.OnHomeTopItemClickListener,
        HomeRcBottomAdapter.OnHomeBottomItemClickListener,
        HomeRcHotCityAdapter.OnHomehotCityItemClickListener{


    private LinearLayout reHomeSearch,llHomeSearch;

    private LinearLayout llHomeLogin;
    private LinearLayout llHomeLoginUn, ivHomeLoginUn;
    private TextView tvHomeLogin;
    private ImageView ivHomeScan;
    private Banner bannerHome;
    private TabLayout tabHome, tabHomeHotCity;
    private List<Fragment> viewList,hotCityViewList;
    private ViewPager vpTabHome, vpTabHomeHotCity;

    @Override
    protected Object getTopbarTitle() {
        return R.string.home;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(LayoutInflater inflater, View view) {
        initMaterialRefreshLayout(view);
        RelativeLayout reTopPar = view.findViewById(R.id.topbat_parent);
        reTopPar.setVisibility(View.GONE);
        //顶部布局
        reHomeSearch = view.findViewById(R.id.re_HomeSearch);
        //搜索
        llHomeSearch = view.findViewById(R.id.ll_HomeSearch);
        llHomeSearch.setOnClickListener(this);
        //已登录展示界面
        llHomeLogin = view.findViewById(R.id.ll_HomeLogin);
        llHomeLogin.setVisibility(View.INVISIBLE);
        //未登录展示布局
        llHomeLoginUn = view.findViewById(R.id.ll_HomeLoginUn);
        llHomeLoginUn.setVisibility(View.VISIBLE);
        ivHomeLoginUn = view.findViewById(R.id.iv_HomeLoginUn);
        ivHomeLoginUn.setVisibility(View.VISIBLE);
        //登录按钮
        tvHomeLogin = view.findViewById(R.id.tv_HomeLogin);
        tvHomeLogin.setOnClickListener(this);
        //扫一扫
        ivHomeScan = view.findViewById(R.id.iv_HomeScan);
        ivHomeScan.setOnClickListener(this);
        // //开始轮播
        bannerHome = view.findViewById(R.id.banner_Home);
        bannerHome.startAutoPlay();
        initViewPagerImage(view);
        //初始化tab 线上
        initTabLayout(view);
        initTabLayoutData(inflater);
        //线下消费
        initHomeBottom(view);
        //初始化热门城市
        initHotCityTabLayout(view);
        initHotCityTabLayoutData();
    }

    private void initHomeBottom(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rc_HomeBottem);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        HomeRcBottomAdapter adapter = new HomeRcBottomAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        RecyclerViewDecoration recyclerViewDecoration = new RecyclerViewDecoration(
                getActivity(), LinearLayoutManager.HORIZONTAL, 20, getResources().getColor(R.color.black_f7));
        recyclerView.addItemDecoration(recyclerViewDecoration);
        //临时数据
        List<String> listBottom = new ArrayList<>();
        for (int i = 0; i < 5 ; i++) {
            listBottom.add("i"+i);
        }
        adapter.setData(listBottom);
    }

    private void initTabLayoutData(final LayoutInflater inflater) {
        viewList = new ArrayList<>();

        final List<String> tabList = new ArrayList();
        tabList.add("最近使用");
        tabList.add("热门推荐");
        tabList.add("充值服务");
        tabList.add("旅游出行");
        for (int i = 0, j = tabList.size(); i < j; i++) {
            String s = tabList.get(i);
            TabLayout.Tab tab = tabHome.newTab().setText(s);
            if (i == 0) {
                tabHome.addTab(tab, true);
            } else {
                tabHome.addTab(tab, false);
            }
            HomeTopVpFragment fragment = HomeTopVpFragment.newInstance(tabList.get(i), this);
            viewList.add(fragment);
        }

        HomeTopPagerAdapter mAdapter = new HomeTopPagerAdapter(getChildFragmentManager(),viewList,tabList);
        vpTabHome.setAdapter(mAdapter);

        tabHome.setupWithViewPager(vpTabHome);//将TabLayout和ViewPager关联起来。
        tabHome.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
    }
    private void initHotCityTabLayoutData() {
        hotCityViewList = new ArrayList<>();

        final List<String> tabList = new ArrayList();
        tabList.add("北京");
        tabList.add("上海");
        tabList.add("广州");
        tabList.add("深圳");
        tabList.add("苏州");
        tabList.add("杭州");
        tabList.add("海南");
        for (int i = 0, j = tabList.size(); i < j; i++) {
            String s = tabList.get(i);
            TabLayout.Tab tab = tabHomeHotCity.newTab().setText(s);
            if (i == 0) {
                tabHomeHotCity.addTab(tab, true);
            } else {
                tabHomeHotCity.addTab(tab, false);
            }
            HomeHotCityVpFragment fragment = HomeHotCityVpFragment.newInstance(tabList.get(i), this);
            hotCityViewList.add(fragment);
        }

        HomeHotCityPagerAdapter mAdapter = new HomeHotCityPagerAdapter(getChildFragmentManager(),hotCityViewList,tabList);
        vpTabHomeHotCity.setAdapter(mAdapter);

        tabHomeHotCity.setupWithViewPager(vpTabHomeHotCity);//将TabLayout和ViewPager关联起来。
        tabHomeHotCity.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
    }
    //轮播
    private void initViewPagerImage(View view) {
        WindowManager wm = getActivity().getWindowManager();//获取屏幕宽高
        int width1 = wm.getDefaultDisplay().getWidth();
//        int height1 = wm.getDefaultDisplay().getHeight();
        ViewGroup.LayoutParams para = bannerHome.getLayoutParams();//获取drawerlayout的布局
        para.height = width1 * 330 / 750;//修改宽度
//        para.height = height1;//修改高度
        bannerHome.setLayoutParams(para); //设布局。
        List<String> advs = new ArrayList<>();
        advs.add("http://pic31.nipic.com/20130722/9252150_095713523386_2.jpg");
//        advs.add("http://pic27.nipic.com/20130305/9252150_153617685375_2.jpg");
//        advs.add("http://img5.imgtn.bdimg.com/it/u=1506310031,2048450892&fm=26&gp=0.jpg");
        if (advs != null && advs.size() > 0) {
            bannerHome.setImages(advs)
                    .setImageLoader(new GlideImageLoader())
                    .setOnBannerListener(this)
                    .start();
        } else {
            bannerHome.setVisibility(View.GONE);
        }
    }


    private void initTabLayout(View view) {
        tabHome = view.findViewById(R.id.tab_Home);
        vpTabHome = view.findViewById(R.id.vp_TabHome);
    }
    private void initHotCityTabLayout(View view) {
        tabHomeHotCity = view.findViewById(R.id.tab_HomeHotCity);
        vpTabHomeHotCity = view.findViewById(R.id.vp_TabHomeHotCity);
    }

    //轮播图点击事件
    @Override
    public void OnBannerClick(int position) {
//        FindBannerModel findBannerModel = listBanner.get(position);
//        String url = findBannerModel.getUrl();
//        startWebView(url);
    }

    private void startWebView(String url) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("isShowTopBar", true);
        intent.putExtra("Url", url);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        //结束轮播
        bannerHome.stopAutoPlay();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_HomeSearch://搜索

                break;
            case R.id.tv_HomeLogin://登录
                isLogin(typeLogin);
                break;
            case R.id.iv_HomeScan://登录
                isLogin(false);
                break;
        }
    }

    Boolean typeLogin = true;

    private void isLogin(boolean isLogin) {
        if (!isLogin) {
            llHomeLogin.setVisibility(View.INVISIBLE);
            llHomeLoginUn.setVisibility(View.VISIBLE);
            ivHomeLoginUn.setVisibility(View.VISIBLE);
            typeLogin = true;
        } else {
            llHomeLogin.setVisibility(View.VISIBLE);
            llHomeLoginUn.setVisibility(View.INVISIBLE);
            ivHomeLoginUn.setVisibility(View.INVISIBLE);
            typeLogin = true;
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

    protected void initMaterialRefreshLayout(View view) {
//        AppBarLayout appBarLayout =  view.findViewById(R.id.appbar);
//
//        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
//            @Override
//            public void onStateChanged(AppBarLayout appBarLayout, State state) {
////                Log.e("STATE", state.name());
//                if (state == State.EXPANDED) {
//                    //展开状态
//                    contactSwLayout.setEnabled(true);
//                } else if (state == State.COLLAPSED) {
//                    //折叠状态
//                    contactSwLayout.setEnabled(false);
//                } else {
//                    //中间状态
//                    contactSwLayout.setEnabled(false);
//                }
//            }
//        });

        refresh = view.findViewById(R.id.refresh);
        refresh.setCanLoadMore(false);
        refresh.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 结束刷新
                        refresh.finishRefresh();
                    }
                }, 2000);
            }

            @Override
            public void loadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 结束加载更多
                        refresh.finishLoadMore();
                    }
                }, 2000);
            }
        });

    }

    //线上消费返金
    @Override
    public void OnTopItemClick(View view, int position) {
        ToastUtil.showToast("线上消费返金");
    }
    //线下消费返金
    @Override
    public void OnBottomItemClick(View view, int position) {
        ToastUtil.showToast("线下消费返金");
    }
    //热门城市
    @Override
    public void OnHotCityItemClick(View view, int position) {
        ToastUtil.showToast("热门城市");
    }
}

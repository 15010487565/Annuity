package com.denong.doluck.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by gs on 21/11/2018.
 */

public class HomeHotCityPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> hotCityViewList;
    private List<String> tabList;
    public HomeHotCityPagerAdapter(FragmentManager fm, List<Fragment> hotCityViewList, List<String> tabList) {
        super(fm);
        this.hotCityViewList = hotCityViewList;
        this.tabList = tabList;
        fm.beginTransaction().commitAllowingStateLoss();
    }

    @Override
    public Fragment getItem(int position) {
        return hotCityViewList.get(position);
    }

    @Override
    public int getCount() {
        return hotCityViewList.size();
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return tabList.get(position);
    }
}

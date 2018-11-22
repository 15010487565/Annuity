package com.denong.doluck.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by gs on 21/11/2018.
 */

public class HomeTopPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> viewList;
    private List<String> tabList;
    public HomeTopPagerAdapter(FragmentManager fm, List<Fragment> viewList, List<String> tabList) {
        super(fm);
        this.viewList = viewList;
        this.tabList = tabList;
        fm.beginTransaction().commitAllowingStateLoss();
    }

    @Override
    public Fragment getItem(int position) {
        return viewList.get(position);
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return tabList.get(position);
    }
}

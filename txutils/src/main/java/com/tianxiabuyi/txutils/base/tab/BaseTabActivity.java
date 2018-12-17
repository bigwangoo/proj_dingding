package com.tianxiabuyi.txutils.base.tab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.tianxiabuyi.txutils.R;
import com.tianxiabuyi.txutils.base.activity.BaseTxActivity;

import java.util.ArrayList;

/**
 * Created by xjh1994 on 2016/7/6.
 * 通用Tab界面
 */
public abstract class BaseTabActivity extends BaseTxActivity {

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private String[] mTitles;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    protected ViewPager mViewPager;
    protected CommonTabLayout mTabLayout_1;

    @Override
    public int getViewByXml() {
        return R.layout.tx_activity_base_tab;
    }

    @Override
    public void initView() {
        mTitles = getTitles();  // return new String[] {"待安排", "已安排"};
        int[] iconUnselectIds = getIconUnselectIds();
        int[] iconSelectIds = getIconSelectIds();
        int currentItem = getCurrentItem();

        addFragments(mFragments);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], iconSelectIds[i], iconUnselectIds[i]));
        }
        mViewPager = (ViewPager) findViewById(R.id.vp_2);
        mViewPager.setOffscreenPageLimit(mFragments.size() - 1);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        mTabLayout_1 = (CommonTabLayout) findViewById(R.id.tl_1);
        mTabLayout_1.setTabData(mTabEntities);

        mTabLayout_1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout_1.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(currentItem);
    }

    @Override
    public void initData() {

    }

    private int getCurrentItem() {
        return 0;
    }

    protected abstract String[] getTitles();

    protected abstract int[] getIconUnselectIds();

    protected abstract int[] getIconSelectIds();

    protected abstract void addFragments(ArrayList<Fragment> fragments);

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}

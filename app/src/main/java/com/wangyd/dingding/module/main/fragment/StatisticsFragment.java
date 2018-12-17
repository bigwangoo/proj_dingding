package com.wangyd.dingding.module.main.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.tianxiabuyi.txutils.base.fragment.BaseLazyFragment;
import com.tianxiabuyi.villagedoctor.R;
import com.wangyd.dingding.module.main.adapter.HomePagerAdapter;
import com.tianxiabuyi.villagedoctor.module.statistics.fragment.DataMonthFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author:jjj
 * @data:2018/6/28
 * @description:
 */

public class StatisticsFragment extends BaseLazyFragment {

    private String[] mTitles = {"月度", "年度"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @BindView(R.id.tlStatistic)
    SegmentTabLayout tl;
    @BindView(R.id.vpStatistic)
    ViewPager vp;

    @Override
    public int getLayoutByXml() {
        return R.layout.statistics_main_fragment;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        DataMonthFragment monthFragment = new DataMonthFragment();
//        DataYearFragment yearFragment = new DataYearFragment();
        mFragments.add(monthFragment);
//        mFragments.add(yearFragment);

        vp.setAdapter(new HomePagerAdapter(getActivity().getSupportFragmentManager(), mFragments, mTitles));
        vp.setOffscreenPageLimit(mFragments.size() - 1);
        vp.setCurrentItem(0);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tl.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tl.setTabData(mTitles);
        tl.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vp.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        tl.setVisibility(View.GONE);
    }

}

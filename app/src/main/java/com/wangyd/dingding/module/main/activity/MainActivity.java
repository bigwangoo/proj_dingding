package com.wangyd.dingding.module.main.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.tianxiabuyi.txutils.base.activity.BaseTxActivity;
import com.tianxiabuyi.txutils.base.tab.TabEntity;
import com.wangyd.dingding.R;
import com.wangyd.dingding.core.adapter.BasePagerAdapter;
import com.wangyd.dingding.module.main.fragment.HomeFragment;
import com.wangyd.dingding.module.personal.fragment.PersonalFragment;
import com.wangyd.dingding.module.work.fragment.WorkFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author wangyd
 * @date 2018/12/10
 */
public class MainActivity extends BaseTxActivity {

    @BindView(R.id.vp_main)
    ViewPager vpMain;
    @BindView(R.id.tabMain)
    CommonTabLayout tabMain;

    private String[] mTitles = {"消息", "工作", "记加班", "我的"};
    private int[] mIconUnSelectIds = {
            R.mipmap.ic_tab_home, R.mipmap.ic_tab_villager,
            R.mipmap.ic_tab_question, R.mipmap.ic_tab_statistics};
    private int[] mIconSelectIds = {
            R.mipmap.ic_tab_home_presses, R.mipmap.ic_tab_villager_pressed,
            R.mipmap.ic_tab_question_presses, R.mipmap.ic_tab_statistics_presses};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    public int getViewByXml() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnSelectIds[i]));
        }
        mFragments.add(new HomeFragment());
        mFragments.add(new WorkFragment());
        mFragments.add(new WorkFragment());
        mFragments.add(new PersonalFragment());

        vpMain.setAdapter(new BasePagerAdapter(getSupportFragmentManager(), mTitles, mFragments));
        vpMain.setOffscreenPageLimit(mTitles.length - 1);
        vpMain.setCurrentItem(0);
        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tabMain.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        tabMain.setTabData(mTabEntities);
        tabMain.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpMain.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    @Override
    public void initData() {

    }

}

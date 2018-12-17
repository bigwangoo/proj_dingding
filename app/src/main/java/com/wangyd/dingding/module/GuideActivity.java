package com.wangyd.dingding.module;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tianxiabuyi.txutils.base.activity.BaseTxActivity;
import com.tianxiabuyi.villagedoctor.R;
import com.wangyd.dingding.core.utils.AppSpUtils;
import com.tianxiabuyi.villagedoctor.module.login.activity.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author wangyd
 * @date 2018/6/29
 * @description 用户引导界面
 */
public class GuideActivity extends BaseTxActivity {

    @BindView(R.id.vp_guide)
    ViewPager vpGuide;
    @BindView(R.id.tv_next)
    Button tvNext;

    private int[] imgs = {R.drawable.splash_1, R.drawable.splash_2, R.drawable.splash_3};

    @Override
    public int getViewByXml() {
        return R.layout.activity_guide;
    }

    @Override
    public void initView() {
        vpGuide.setAdapter(new GuidePageAdapter(this, imgs));
        vpGuide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == imgs.length - 1) {
                    tvNext.setVisibility(View.VISIBLE);
                } else {
                    tvNext.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initData() {
        AppSpUtils.getInstance().setFirstOpen(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @OnClick(R.id.tv_next)
    public void onViewClicked() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private static class GuidePageAdapter extends PagerAdapter {
        private Context context;
        private int[] imgs;
        private ImageView[] imageViews;

        public GuidePageAdapter(Context context, int[] imgs) {
            this.context = context;
            this.imgs = imgs;
            imageViews = new ImageView[imgs.length];
        }

        @Override
        public int getCount() {
            return imgs.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(imageViews[position]);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ViewGroup.LayoutParams lp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            ImageView iv = new ImageView(context);
            iv.setLayoutParams(lp);
            iv.setImageResource(imgs[position]);
            imageViews[position] = iv;
            container.addView(iv);
            return iv;
        }
    }
}

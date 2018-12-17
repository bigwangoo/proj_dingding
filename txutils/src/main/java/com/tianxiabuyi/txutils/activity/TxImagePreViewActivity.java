package com.tianxiabuyi.txutils.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.tianxiabuyi.txutils.R;
import com.tianxiabuyi.txutils.TxImageLoader;
import com.tianxiabuyi.txutils.base.activity.BaseTxTitleActivity;

import java.io.File;

/**
 * @author wangyd
 * @date 2018/8/24
 * @description 单张图片浏览
 */
public class TxImagePreViewActivity extends BaseTxTitleActivity {

    private String title;
    private String url;
    private File file;

    public static void newInstance(Context context, String title, String url) {
        context.startActivity(new Intent(context, TxImagePreViewActivity.class)
                .putExtra("title", title)
                .putExtra("url", url));
    }

    public static void newInstance(Context context, String title, File file) {
        context.startActivity(new Intent(context, TxImagePreViewActivity.class)
                .putExtra("title", title)
                .putExtra("file", file));
    }

    @Override
    protected String getTitleString() {
        return TextUtils.isEmpty(title) ? "图片预览" : title;
    }

    @Override
    public int getViewByXml() {
        return R.layout.tx_activtiy_image_preview;
    }

    @Override
    public void getIntentData(Intent intent) {
        super.getIntentData(intent);
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        file = (File) getIntent().getSerializableExtra("file");
    }

    @Override
    public void initView() {
        RelativeLayout rlContainer = findViewById(R.id.rl_container);
        ImageView ivPreview = findViewById(R.id.iv_preview);
        rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivPreview.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                return true;
            }
        });
        ivPreview.setOnGenericMotionListener(new View.OnGenericMotionListener() {
            @Override
            public boolean onGenericMotion(View v, MotionEvent event) {
                return true;
            }
        });

        if (file != null) {
            Glide.with(this).load(file).into(ivPreview);
        } else {
            TxImageLoader.getInstance().loadImage(this, url, ivPreview);
        }
    }

    @Override
    public void initData() {

    }
}

package com.tianxiabuyi.txutils.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tianxiabuyi.txutils.TxFileManager;
import com.tianxiabuyi.txutils.network.callback.ResponseCallback;
import com.tianxiabuyi.txutils.network.exception.TxException;
import com.tianxiabuyi.txutils.network.model.TxFileResult;
import com.tianxiabuyi.txutils.util.ToastUtils;

/**
 * 图片选择并上传
 *
 * @author xjh1994
 * @date 2016/8/24
 */
public class TxImagePickerActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_PICK_IMAGE = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_PICK_IMAGE) {
                Uri uri = data.getData();
                if (uri != null) {
                    upload(uri);
                    return;
                }
            }
        }
        finish();
    }

    private void upload(Uri uri) {
        TxFileManager.upload(uri, new ResponseCallback<TxFileResult>() {
            @Override
            public void onSuccess(TxFileResult result) {
                String imgUrl = result.getImg();
                Intent intent = new Intent(imgUrl);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }

            @Override
            public void onError(TxException e) {
                ToastUtils.show(TxImagePickerActivity.this, e.getDetailMessage());
                finish();
            }
        });
    }
}

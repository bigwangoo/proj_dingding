package com.tianxiabuyi.txutils.util.avatar;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tianxiabuyi.txutils.R;
import com.tianxiabuyi.txutils.util.FileProvider7;

import java.io.File;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static com.tianxiabuyi.txutils.util.avatar.TxPhotoHelper.RC_CAMERA_PERM;
import static com.tianxiabuyi.txutils.util.avatar.TxPhotoHelper.RC_GALLERY_PERM;
import static com.tianxiabuyi.txutils.util.avatar.TxPhotoHelper.RC_SETTINGS_SCREEN;

/**
 * @author xjh1994
 * @date 16/12/2
 * @description 上传头像辅助用Activity
 */
public class TxPhotoHelperActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private int mType;
    private int mQuality;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mType = getIntent().getIntExtra(TxPhotoHelper.EXTRA_TYPE, 0);
        mQuality = getIntent().getIntExtra(TxPhotoHelper.EXTRA_QUALITY, 100);
        if (mType == TxPhotoHelper.TAKE_PHOTO) {
            cameraTask();
        } else if (mType == TxPhotoHelper.CHOOSE_PICTURE) {
            galleryTask();
        } else if (mType == TxPhotoHelper.CROP_PICTURE) {
            // 暂无
        }
    }

    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void cameraTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            TxPhotoHelper.takePhoto(this);
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.tx_rationale_camera),
                    RC_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    @AfterPermissionGranted(RC_GALLERY_PERM)
    public void galleryTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            TxPhotoHelper.pickFromGallery(this);
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.tx_rationale_gallery),
                    RC_GALLERY_PERM, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            finish();
            return;
        }

        //拍照
        if (requestCode == TxPhotoHelper.TAKE_PHOTO) {
            File tempFile = TxPhotoHelper.getTempFile();
            if (tempFile.exists()) {
                // 获取Uri
                Uri uri = FileProvider7.getUriForFile(this, tempFile);
                TxPhotoHelper.cropPicture(this, uri);
                TxPhotoHelper.setTempFile(null);
            } else {
                finish();
            }
        }
        //相册
        else if (requestCode == TxPhotoHelper.CHOOSE_PICTURE) {
            if (data != null && data.getData() != null) {
                Uri uri = data.getData();
                TxPhotoHelper.cropPicture(this, uri);
            } else {
                finish();
            }
        }
        //裁剪
        else if (requestCode == TxPhotoHelper.CROP_PICTURE) {
            TxPhotoHelper.uploadAvatar(this, mQuality);
        }

        //设置
        if (requestCode == RC_SETTINGS_SCREEN) {
            Toast.makeText(this, R.string.tx_returned_from_app_settings_to_activity, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle(getString(R.string.tx_title_settings_dialog))
                    .setRationale(getString(R.string.tx_rationale_ask_again))
                    .setPositiveButton(getString(R.string.tx_setting))
                    .setNegativeButton(getString(R.string.tx_cancel))
                    .setRequestCode(RC_SETTINGS_SCREEN)
                    .build()
                    .show();
        } else {
            finish();
        }
    }
}

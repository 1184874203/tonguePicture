package com.hali.xiaoyangchun.tonguepicture.presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.hali.xiaoyangchun.tonguepicture.ui.base.BaseActivity;
import com.hali.xiaoyangchun.tonguepicture.utils.FileUtil;

public class MinePresenter {

    // 请求相册
    public static final int REQUEST_PICK = 101;

    private BaseActivity activity;


    public void attach(BaseActivity activity) {
        this.activity = activity;
    }

    public void gotoPhoto() {
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PICK:
                    Uri uri = data.getData();
                    String path = FileUtil.INSTANCE.getRealFilePathFromUri(activity, uri);
                    break;
            }
        }
    }
}

package com.tonguePicture.support.Permission;

import android.app.Activity;

public interface PermissionRequestListener {

    int REQUEST_CODE_ASK_PERMISSIONS_PHONE = 1;
    int REQUEST_CODE_ASK_PERMISSIONS_LOCATION = 2;
    int REQUEST_CODE_ASK_PERMISSIONS_STORAGE = 3;
    int REQUEST_CODE_ASK_PERMISSIONS_CAMERA = 4;
    int REQUEST_CODE_ASK_PERMISSIONS_RECORD = 5;
    int REQUEST_CODE_ASK_PERMISSIONS_MULTIPLE = 6;

    // 权限已获取
    void onGranted(Activity context, int requestCode);

    // 权限被询问
    void onAsked(Activity context, int requestCode);

    // 权限被拒绝
    void onDenied(Activity context, int requestCode);
}

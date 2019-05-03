package com.tonguePicture.support.Permission;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.tonguePicture.support.R;

public class PermissionModel {

    private Context context;
    private String[] permissions;
    private int requestCode;
    private String explainString;
    private String settingGuide;
    private String tag;
    private PermissionRequestListener lstener;

    private PermissionModel(Context context) {
        this.context = context;
    }

    public static PermissionModel with(@NonNull Context context) {
        return new PermissionModel(context);
    }

    public PermissionModel permission(@NonNull String... permissions) {
        this.permissions = permissions;
        return this;
    }

    public PermissionModel requestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    public PermissionModel callback(PermissionRequestListener listener) {
        this.lstener = listener;
        return this;
    }

    public PermissionModel explainString(String desc) {
        this.explainString = desc;
        return this;
    }

    public PermissionModel settingGuide(String desc) {
        this.settingGuide = desc;
        return this;
    }

    public PermissionModel tag(String tag) {
        this.tag = tag;
        return this;
    }

    public void requestPermissions() {
        if (TextUtils.isEmpty(explainString)) {
            explainString = context.getString(R.string.permission_explainDesc);
        }
        if (TextUtils.isEmpty(settingGuide)) {
            settingGuide = context.getString(R.string.permission_settingDesc);
        }
        PermissionActivity.enter(context, tag, permissions, requestCode, explainString, settingGuide, lstener);
    }

}

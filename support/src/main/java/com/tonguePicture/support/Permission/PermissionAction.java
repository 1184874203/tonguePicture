package com.tonguePicture.support.Permission;

import android.app.Activity;

public interface PermissionAction {
    void onExplain(Activity context, String reason, int requestCode, PermissionRequestListener listener);

    void onSettingGuide(Activity context, String settingDesc, int requestCode, PermissionRequestListener listener);
}

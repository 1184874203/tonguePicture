package com.tonguePicture.support.Permission;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

public class PermissionActivity extends AppCompatActivity {

    public final String TAG = "PermissionActivity";

    private static final String PARAMS_PERMISSIONS = "permissions";
    private static final String PARAMS_REQUESTCODE = "requestCode";
    private static final String PARAMS_PERMISSION_DESC = "permission_desc";
    private static final String PARAMS_SETTING_DESC = "setting_desc";
    private static final String PARAMS_LAST_CONTEXT_TAG = "last_context_tag";

    private static PermissionRequestListener listener;

    private int requestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        requestPermission();
    }

    protected void requestPermission() {
        String[] requestPermissions = getIntent().getStringArrayExtra(PARAMS_PERMISSIONS);
        requestCode = getIntent().getIntExtra(PARAMS_REQUESTCODE, -1);
        String desc = getIntent().getStringExtra(PARAMS_PERMISSION_DESC);
        String settingDesc = getIntent().getStringExtra(PARAMS_SETTING_DESC);
        String lastTag = getIntent().getStringExtra(PARAMS_LAST_CONTEXT_TAG);
        PermissionManager.getInstance().setExplainDesc(desc);
        PermissionManager.getInstance().setPermissionSettingDesc(settingDesc);
        PermissionManager.getInstance().setmPermissionRequestListener(listener);
        PermissionManager.getInstance().setLastContextTag(lastTag);
        PermissionManager.getInstance().requestPermission(this, requestPermissions, requestCode);
        PermissionManager.getInstance().setRequesting(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (this.requestCode == requestCode) {
            PermissionManager.getInstance().requestCallback(this, requestCode, permissions, grantResults);
            PermissionManager.getInstance().setRequesting(false);
        }
    }

    public static void enter(Context context, String tag, String[] permissions, int requestCode, String permissionDesc, String permissionSettingDesc, PermissionRequestListener l) {
        Intent intent = new Intent(context, PermissionActivity.class);
        intent.putExtra(PARAMS_PERMISSIONS, permissions);
        intent.putExtra(PARAMS_REQUESTCODE, requestCode);
        intent.putExtra(PARAMS_PERMISSION_DESC, permissionDesc);
        intent.putExtra(PARAMS_SETTING_DESC, permissionSettingDesc);
        intent.putExtra(PARAMS_LAST_CONTEXT_TAG, tag);
        listener = l;
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!isFinishing()) {
            finish();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PermissionManager.getInstance().destroy();
    }
}

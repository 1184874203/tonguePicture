package com.tonguePicture.support.Permission;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;

import com.tonguePicture.support.R;

public class PermissionHelper {

    public static void requestPhonePermission(Context context, PermissionRequestListener listener) {
        requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE},
                PermissionRequestListener.REQUEST_CODE_ASK_PERMISSIONS_PHONE,
                context.getString(R.string.permission_phone_explainDesc),
                context.getString(R.string.permission_phone_settingDesc),
                listener);
    }

    public static void requestLocationPermission(Context context, PermissionRequestListener listener) {
        requestPermissions(context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                PermissionRequestListener.REQUEST_CODE_ASK_PERMISSIONS_LOCATION,
                context.getString(R.string.permission_location_explainDesc),
                context.getString(R.string.permission_location_settingDesc),
                listener);
    }

    public static void requestStoragePermission(Context context, PermissionRequestListener listener) {
        requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PermissionRequestListener.REQUEST_CODE_ASK_PERMISSIONS_STORAGE,
                context.getString(R.string.permission_storage_explainDesc),
                context.getString(R.string.permission_storage_settingDesc),
                listener);
    }

    public static void requestCameraPermission(Context context, PermissionRequestListener listener) {
        requestPermissions(context, new String[]{Manifest.permission.CAMERA},
                PermissionRequestListener.REQUEST_CODE_ASK_PERMISSIONS_CAMERA,
                context.getString(R.string.permission_camera_explainDesc),
                context.getString(R.string.permission_camera_settingDesc),
                listener);
    }

    public static void requestRecordPermission(Context context, PermissionRequestListener listener) {
        requestPermissions(context, new String[]{Manifest.permission.RECORD_AUDIO},
                PermissionRequestListener.REQUEST_CODE_ASK_PERMISSIONS_RECORD,
                context.getString(R.string.permission_record_explainDesc),
                context.getString(R.string.permission_record_settingDesc),
                listener);
    }

    public static void requestRecordPermission(Context context, String tag, PermissionRequestListener listener) {
        requestPermissions(context, tag, new String[]{Manifest.permission.RECORD_AUDIO},
                PermissionRequestListener.REQUEST_CODE_ASK_PERMISSIONS_RECORD,
                context.getString(R.string.permission_record_explainDesc),
                context.getString(R.string.permission_record_settingDesc),
                listener);
    }

    public static boolean hasLocationPermissions(Context context) {
        return checkPermissons(context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION});
    }

    public static boolean hasPhonePermissions(Context context) {
        return checkPermissons(context, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE});
    }

    public static boolean hasStoragePermissions(Context context) {
        return checkPermissons(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE});
    }

    public static boolean hasCameraPermissions(Context context) {
        return checkPermissons(context, new String[]{Manifest.permission.CAMERA});
    }

    public static boolean hasRecordPermissions(Context context) {
        return checkPermissons(context, new String[]{Manifest.permission.RECORD_AUDIO});
    }

    public static boolean checkPermissons(Context context, String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;
        return PermissionManager.getInstance().hasPermission(context, permissions);
    }

    public static void requestPermissions(@NonNull Context context, @NonNull String[] permissions, int requestCode, String explain, String guide, PermissionRequestListener listener) {
        PermissionModel.with(context)
                .permission(permissions)
                .requestCode(requestCode)
                .explainString(explain)
                .settingGuide(guide)
                .callback(listener)
                .requestPermissions();
    }

    public static void requestPermissions(@NonNull Context context, String tag, @NonNull String[] permissions, int requestCode, String explain, String guide, PermissionRequestListener listener) {
        PermissionModel.with(context)
                .tag(tag)
                .permission(permissions)
                .requestCode(requestCode)
                .explainString(explain)
                .settingGuide(guide)
                .callback(listener)
                .requestPermissions();
    }
}

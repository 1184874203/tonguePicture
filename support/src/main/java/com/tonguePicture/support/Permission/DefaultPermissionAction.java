package com.tonguePicture.support.Permission;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Toast;

import com.tonguePicture.support.R;

public class DefaultPermissionAction implements PermissionAction {

    @Override
    public void onExplain(Activity context, String reason, int requestCode, PermissionRequestListener listener) {
        if (!TextUtils.isEmpty(reason)) {
            Toast.makeText(context, reason, Toast.LENGTH_SHORT).show();
        }
        if (listener != null) {
            listener.onAsked(context, requestCode);
        }
        context.finish();
    }

    @Override
    public void onSettingGuide(@NonNull final Activity context, String settingDesc, final int requestCode, final PermissionRequestListener listener) {
        new AlertDialog.Builder(context)
                .setMessage(settingDesc)
                .setNegativeButton(context.getString(R.string.permission_negative), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(context.getString(R.string.permission_positive), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
                        if (intent.resolveActivity(context.getPackageManager()) != null) {
                            context.startActivity(intent);
                        } else {
                            Toast.makeText(context, context.getString(R.string.permission_open_action), Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        context.finish();
                        if (listener != null) {
                            listener.onDenied(context, requestCode);
                        }
                    }
                }).show();
    }
}

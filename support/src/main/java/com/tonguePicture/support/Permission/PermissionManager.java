package com.tonguePicture.support.Permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PermissionManager {

    public static final String TAG = "PermissionManager";

    private PermissionAction permissionAction;

    private List<Integer> permissionStatusList = new LinkedList<>();

    private PermissionRequestListener mPermissionRequestListener;

    private String explainDesc;

    private String permissionSettingDesc;

    private static volatile PermissionManager mInstance;

    private String lastContextTag;

    private boolean requesting = false;

    private PermissionManager() {
        permissionAction = new DefaultPermissionAction();
    }

    public static PermissionManager getInstance() {
        if (mInstance == null) {
            synchronized (PermissionManager.class) {
                if (mInstance == null) {
                    mInstance = new PermissionManager();
                }
            }
        }
        return mInstance;
    }

    public boolean hasPermission(@NonNull Context context, @NonNull String[] permissions) {
        for (String item : permissions) {
            if (ContextCompat.checkSelfPermission(context, item) != PackageManager.PERMISSION_GRANTED
                    || PermissionChecker.checkSelfPermission(context, item) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public void requestPermission(@NonNull Activity context, @NonNull String[] permissions, int requestCode) {
        permissionStatusList.clear();
        List<String> refusedPermission = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            permissionStatusList.add(PermissionChecker.checkSelfPermission(context, permissions[i]));
            if (permissionStatusList.get(i) != PackageManager.PERMISSION_GRANTED) {
                refusedPermission.add(permissions[i]);
            }
        }

        int refusedNum = refusedPermission.size();
        if (refusedNum > 0) {
            ActivityCompat.requestPermissions(context, refusedPermission.toArray(new String[refusedNum]), requestCode);
        } else {
            if (mPermissionRequestListener != null) {
                mPermissionRequestListener.onGranted(context, requestCode);
            }
            context.finish();
        }
    }

    public void requestCallback(Activity context, int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == -1) return;
        int refusedIndex = -1;
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED
                    || permissionStatusList.get(i) != PackageManager.PERMISSION_GRANTED) {
                grantResults[i] = PermissionChecker.checkSelfPermission(context, permissions[i]);
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        if (refusedIndex == -1) {
                            refusedIndex = i;
                        }
                    }
                }
            } else {
                if (refusedIndex == -1) {
                    refusedIndex = i;
                }
            }
        }

        if (refusedIndex != -1) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permissions[refusedIndex])) {
                if (permissionAction != null) {
                    permissionAction.onExplain(context, explainDesc, requestCode, mPermissionRequestListener);
                }
            } else {
                if (permissionAction != null) {
                    permissionAction.onSettingGuide(context, permissionSettingDesc, requestCode, mPermissionRequestListener);
                }
            }
        } else {
            if (mPermissionRequestListener != null) {
                mPermissionRequestListener.onGranted(context, requestCode);
            }
            context.finish();
        }
    }

    public void destroy() {
        permissionStatusList.clear();
    }

    public PermissionRequestListener getmPermissionRequestListener() {
        return mPermissionRequestListener;
    }

    public void setmPermissionRequestListener(PermissionRequestListener mPermissionRequestListener) {
        this.mPermissionRequestListener = mPermissionRequestListener;
    }

    public PermissionAction getPermissionAction() {
        return permissionAction;
    }

    public void setPermissionAction(PermissionAction permissionAction) {
        this.permissionAction = permissionAction;
    }

    public String getExplainDesc() {
        return explainDesc;
    }

    public void setExplainDesc(String explainDesc) {
        this.explainDesc = explainDesc;
    }

    public String getPermissionSettingDesc() {
        return permissionSettingDesc;
    }

    public void setPermissionSettingDesc(String permissionSettingDesc) {
        this.permissionSettingDesc = permissionSettingDesc;
    }

    public String getLastContextTag() {
        return lastContextTag;
    }

    public void setLastContextTag(String lastContextTag) {
        this.lastContextTag = lastContextTag;
    }

    public boolean isRequesting() {
        return requesting;
    }

    public void setRequesting(boolean requesting) {
        this.requesting = requesting;
    }
}

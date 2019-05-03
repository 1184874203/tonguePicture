package com.hali.xiaoyangchun.tonguepicture.presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.hali.xiaoyangchun.tonguepicture.listener.ChangeListenerManager;
import com.hali.xiaoyangchun.tonguepicture.listener.ChangeListenr;
import com.hali.xiaoyangchun.tonguepicture.ui.activity.SingleFAHelper;
import com.hali.xiaoyangchun.tonguepicture.ui.base.BaseActivity;

import org.jetbrains.annotations.NotNull;

public class MinePresenter implements ChangeListenr {

    // 请求相册
    public static final int REQUEST_PICK = 101;

    // 更改昵称
    public static final int REQUEST_NICK = 102;

    private BaseActivity activity;

    private MineCallback callback;

    public MineCallback getCallback() {
        return callback;
    }

    public void setCallback(MineCallback callback) {
        this.callback = callback;
    }

    public void attach(BaseActivity activity) {
        this.activity = activity;
        ChangeListenerManager.Companion.getInstance().registerListener("nick_change", this);
    }

    public void gotoPhoto() {
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
    }

    public void changeNick() {
        SingleFAHelper.INSTANCE.gotoNickChangeFragment(activity);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PICK:
                    Uri uri = data.getData();
                    if (uri != null && callback != null) {
                        callback.onImgPicked(uri);
                    }
                    break;
            }
        }
    }

    @Override
    public void onChange(@NotNull String key, @NotNull Object data) {
        if ("nick_change".equals(key) && callback != null) {
            callback.onNickChange((String) data);
        }
    }

    public interface MineCallback {
        void onImgPicked(Uri path);

        void onNickChange(String nick);
    }
}

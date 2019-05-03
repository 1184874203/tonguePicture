package com.hali.xiaoyangchun.tonguepicture.dao.Manager;

import android.content.Context;

public class UserInfo {

    private Context mContext;
    private static UserInfo instance;

    private String SHARE_KEY_HEADIMG = "head_img";
    private String SHARE_KEY_NICK = "nick";

    private UserInfo(Context context) {
        mContext = context.getApplicationContext();
    }

    public static UserInfo getInstance(Context context) {
        if (instance == null) {
            instance = new UserInfo(context);
        }
        return instance;
    }

    public String getHeadImg() {
        return PreferenceManager.Companion.getInstance(mContext).getString(SHARE_KEY_HEADIMG);
    }

    public void setHeadImg(String headImg) {
        PreferenceManager.Companion.getInstance(mContext).putString(SHARE_KEY_HEADIMG, headImg);
    }

    public String getNick() {
        return PreferenceManager.Companion.getInstance(mContext).getString(SHARE_KEY_NICK);
    }

    public void setNick(String nick) {
        PreferenceManager.Companion.getInstance(mContext).putString(SHARE_KEY_NICK, nick);
    }
}

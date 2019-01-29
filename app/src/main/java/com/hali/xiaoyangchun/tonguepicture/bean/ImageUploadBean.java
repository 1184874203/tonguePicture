package com.hali.xiaoyangchun.tonguepicture.bean;

import com.hali.xiaoyangchun.tonguepicture.model.net.interfaces.GsonBean;

public class ImageUploadBean implements GsonBean {
    private String url;
    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }
    public String toString() {
        return "url : " + url;
    }
}

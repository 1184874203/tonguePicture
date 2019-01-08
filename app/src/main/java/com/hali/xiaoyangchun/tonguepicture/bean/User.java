package com.hali.xiaoyangchun.tonguepicture.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User {
    @Id(autoincrement = true)
    private long id;
    private String PicPath;
    private String name;
    private int age;
    private String sex;
    private String otherString;
    private Long time;
    @Generated(hash = 1978371067)
    public User(long id, String PicPath, String name, int age, String sex,
            String otherString, Long time) {
        this.id = id;
        this.PicPath = PicPath;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.otherString = otherString;
        this.time = time;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getOtherString() {
        return this.otherString;
    }
    public void setOtherString(String otherString) {
        this.otherString = otherString;
    }
    public String getPicPath() {
        return this.PicPath;
    }
    public void setPicPath(String PicPath) {
        this.PicPath = PicPath;
    }
    public Long getTime() {
        return this.time;
    }
    public void setTime(Long time) {
        this.time = time;
    }
}

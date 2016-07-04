package com.lostfound.bean;

import cn.bmob.v3.BmobObject;
public class Tell extends BmobObject {

    private String title;
    private String describe;
    private String name;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescribe() {
        return describe;
    }
    public void setDescribe(String describe) {
        this.describe = describe;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}


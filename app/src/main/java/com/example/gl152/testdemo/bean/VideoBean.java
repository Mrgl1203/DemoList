package com.example.gl152.testdemo.bean;

/**
 * Created by gl152 on 2017/3/21.
 */

public class VideoBean {
    private String name;
    private boolean isChecked;

    public VideoBean(String name, boolean isChecked) {
        this.name = name;
        this.isChecked = isChecked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}

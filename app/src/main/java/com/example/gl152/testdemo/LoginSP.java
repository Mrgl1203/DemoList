package com.example.gl152.testdemo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gl152 on 2017/2/23.
 */

public enum LoginSP {
    instance;
    public static final String SHARENAME = "TestDemo";
    public static final String USERNAME = "username";
    public static final String PWD = "password";
    public static final String FIRSTLOGIN = "firstlogin";
    SharedPreferences sharedPreferences;


    public void init(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(SHARENAME, context.MODE_PRIVATE);
        }
    }

    public void saveLoginInfo(String username, String password, Boolean firstlogin) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME, username);
        editor.putString(PWD, password);
        editor.putBoolean(FIRSTLOGIN, firstlogin);
        editor.commit();
    }

    public String getUsername() {
        String username = sharedPreferences.getString(USERNAME, "");
        return username;
    }

    public String getPassword() {
        String password = sharedPreferences.getString(PWD, "");
        return password;
    }

    public Boolean getIsFirstLogin() {
        Boolean isFirstLogin = sharedPreferences.getBoolean(FIRSTLOGIN, true);
        return isFirstLogin;
    }


}

package common;

import android.app.Application;
import android.content.Context;

import com.example.gl152.testdemo.LoginSP;

/**
 * Created by gl152 on 2017/2/23.
 */

public class GlApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        LoginSP.instance.init(context);

    }

    public void saveLoginInfo(String username, String password, Boolean isfirst) {
        LoginSP.instance.saveLoginInfo(username, password, isfirst);
    }

    public String getPassword() {
        return LoginSP.instance.getPassword();
    }

    public String getUsername() {
        return LoginSP.instance.getUsername();
    }

    public Boolean getIsFirstLogin() {
        return LoginSP.instance.getIsFirstLogin();
    }

    public static Context getContext() {
        return context;
    }

    /**
     * 当终止应用程序对象时调用，不保证一定被调用，当程序是被内核终止以便为其他应用程序释放资源，那
     * 么将不会提醒，并且不调用应用程序的对象的onTerminate方法而直接终止进 程
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 当后台程序已经终止资源还匮乏时会调用这个方法。好的应用程序一般会在这个方法里面释放一些不必
     * 要的资源来应付当后台程序已经终止，前台应用程序内存还不够时的情况。
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}

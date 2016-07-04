package com.tongxin.e_guide;

/**
 * Created by Administrator on 2015/9/26.
 */
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.Application;
public class AgentApp extends Application

{ private List<Activity> activities =new ArrayList<Activity>();

    private static AgentApp instance;

    private AgentApp(){ } //单例模式中获取唯一的application

    public static AgentApp getInstance()

    { if(null==instance)

    { instance=new AgentApp();

    } return instance;

    } //存放Activity到list中

    public void addActivity(Activity activity){ activities.add(activity); }

    @Override //遍历存放在list中的Activity并退出

    public void onTerminate()

    { super.onTerminate(); for(Activity activity : activities){ activity.finish(); }

        android.os.Process.killProcess(android.os.Process.myPid()); }

}

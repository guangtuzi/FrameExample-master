package com.otcyan.jutil;

import android.app.Application;
import android.content.Context;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * util包的全局变量 .
 */

public class UtilGlobal {

    private static Context sContext;

    static Context getContext(){
        if(sContext ==null){
            synchronized (UtilGlobal.class){
                sContext = getSystemApp();
            }
        }
        return sContext ;
    }

    private static Application getSystemApp() {

        try {
            Class<?> activitythread = Class.forName("android.app.ActivityThread");
            Method m_currentActivityThread = activitythread.getDeclaredMethod("currentActivityThread");
            Field f_mInitialApplication = activitythread.getDeclaredField("mInitialApplication");
            f_mInitialApplication.setAccessible(true);
            Object current = m_currentActivityThread.invoke(null);

            Object app = f_mInitialApplication.get(current);

            return (Application) app;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

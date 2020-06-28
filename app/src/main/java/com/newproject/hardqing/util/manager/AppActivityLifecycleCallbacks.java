package com.newproject.hardqing.util.manager;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.newproject.hardqing.util.AppManager;


@RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class AppActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks
{
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState)
    {
        AppManager.getAppManager().addActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity)
    {

    }

    @Override
    public void onActivityResumed(Activity activity)
    {

    }

    @Override
    public void onActivityPaused(Activity activity)
    {

    }

    @Override
    public void onActivityStopped(Activity activity)
    {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState)
    {

    }

    @Override
    public void onActivityDestroyed(Activity activity)
    {
        AppManager.getAppManager().finishActivity(activity);
    }
}

package com.santong.nock.framework;

import android.app.Application;

/**
 * Created by santong.
 * At 15/12/24 15:24
 */
public class NockApplication extends Application {

    private static NockApplication nockApplication;

    public static NockApplication getNockApplication() {
        if (null == nockApplication){
            nockApplication = new NockApplication();
        }
        return nockApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
}

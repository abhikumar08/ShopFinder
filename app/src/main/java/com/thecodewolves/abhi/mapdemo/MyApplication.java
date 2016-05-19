package com.thecodewolves.abhi.mapdemo;

import android.app.Application;

import com.thecodewolves.abhi.mapdemo.Modules.AppModule;
import com.thecodewolves.abhi.mapdemo.Modules.NetModule;

/**
 * Created by Abhi on 19-05-2016.
 */
public class MyApplication extends Application {

    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule("https://maps.googleapis.com/maps/api/place/"))
                .build();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}

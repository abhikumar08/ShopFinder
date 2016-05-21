package com.thecodewolves.abhi.mapdemo;

import com.thecodewolves.abhi.mapdemo.Modules.AppModule;
import com.thecodewolves.abhi.mapdemo.Modules.NetModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Abhi on 19-05-2016.
 */
@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(MainActivity mainActivity);
    void inject(ShopDetailActivity shopDetailActivity);

}

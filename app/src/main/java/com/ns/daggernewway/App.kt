package com.ns.daggernewway

import com.ns.daggernewway.di.app.beans.AppModule
import com.ns.daggernewway.di.app.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

}
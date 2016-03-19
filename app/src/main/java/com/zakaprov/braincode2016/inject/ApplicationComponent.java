package com.zakaprov.braincode2016.inject;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {ApplicationModule.class})
@Singleton
public interface ApplicationComponent {

    Application getApplication();
}

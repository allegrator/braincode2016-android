package com.zakaprov.braincode2016.inject;

import android.app.Application;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Application mApplication;

    public ApplicationModule(@NonNull Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    protected Application provideApplication() {
        return mApplication;
    }
}

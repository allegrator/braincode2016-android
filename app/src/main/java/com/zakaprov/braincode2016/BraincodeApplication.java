package com.zakaprov.braincode2016;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class BraincodeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("ubuntu-light.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}

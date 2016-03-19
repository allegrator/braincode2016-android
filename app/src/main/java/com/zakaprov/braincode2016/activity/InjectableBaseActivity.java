package com.zakaprov.braincode2016.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class InjectableBaseActivity<T> extends AppCompatActivity {

    private T mComponent;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mComponent == null) {
            mComponent = createComponent();
        }

        inject(mComponent);
    }

    protected abstract T createComponent();

    protected abstract void inject(T component);

    public final void setComponent(T component) {
        mComponent = component;
    }
}

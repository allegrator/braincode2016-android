package com.zakaprov.braincode2016.inject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zakaprov.braincode2016.network.BraincodeNetworkClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    protected BraincodeNetworkClient provideBraincodeNetworkClient() {
        return new BraincodeNetworkClient();
    }

    @Provides
    @Singleton
    protected Gson provideGson() {
        return new GsonBuilder()
                .create();
    }

    @Provides
    @Singleton
    protected OkHttpClient provideHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }
}

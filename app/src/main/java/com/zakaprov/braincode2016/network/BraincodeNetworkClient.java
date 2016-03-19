package com.zakaprov.braincode2016.network;

import com.zakaprov.braincode2016.model.Category;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class BraincodeNetworkClient {

    private static final String BASE_URL = "http://192.168.43.14:5000";

    private BraincodeApi mApi;

    public BraincodeNetworkClient() {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient())
                .build();

        mApi = adapter.create(BraincodeApi.class);
    }

    public void uploadFile(TypedFile photo, Callback<Category> callback) {
        mApi.uploadFile(photo, callback);
    }
}

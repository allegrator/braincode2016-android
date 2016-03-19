package com.zakaprov.braincode2016.network;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class BraincodeNetworkClient {

    private static final String BASE_URL = "http://10.3.8.27:5000";

    private BraincodeApi mApi;

    public BraincodeNetworkClient() {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient())
                .build();

        mApi = adapter.create(BraincodeApi.class);
    }

    public void uploadFile(TypedFile photo, Callback<Response> callback) {
        mApi.uploadFile(photo, callback);
    }
}
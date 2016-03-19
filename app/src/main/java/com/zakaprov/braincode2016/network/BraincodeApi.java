package com.zakaprov.braincode2016.network;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

public interface BraincodeApi {

    @Multipart
    @POST("/compute")
    void uploadFile(@Part("img") TypedFile photoFile, Callback<Response> callback);
}

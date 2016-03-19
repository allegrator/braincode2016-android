package com.zakaprov.braincode2016.activity;

import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.FrameLayout;

import com.zakaprov.braincode2016.R;
import com.zakaprov.braincode2016.inject.ApplicationModule;
import com.zakaprov.braincode2016.inject.NetworkModule;
import com.zakaprov.braincode2016.network.BraincodeNetworkClient;
import com.zakaprov.braincode2016.util.WritePictureTask;
import com.zakaprov.braincode2016.view.CameraPreview;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class CameraPreviewActivity extends InjectableBaseActivity<CameraPreviewActivity.Component> {

    @Bind(R.id.camera_preview_container) FrameLayout mCameraPreviewContainer;
    @Bind(R.id.camera_button_capture) FloatingActionButton mCameraButtonCapture;

    @Inject BraincodeNetworkClient mBraincodeNetworkClient;

    private CameraPreview mPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mPreview = new CameraPreview(this);
        mCameraPreviewContainer.addView(mPreview);

        mCameraButtonCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPreview.takePicture(new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        new WritePictureTask(new PhotoTaskFinishedListener()).execute(data);
                    }
                });
            }
        });
    }

    private class PhotoTaskFinishedListener implements WritePictureTask.OnTaskFinishedListener {

        @Override
        public void onTaskFinished(String fileName) {
            File photo = new File(Environment.getExternalStorageDirectory(), fileName);

            TypedFile photoTypedFile = new TypedFile("image/jpeg", photo);
            mBraincodeNetworkClient.uploadFile(photoTypedFile, new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {

                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
    }

    @Override
    protected Component createComponent() {
        return DaggerCameraPreviewActivity_Component.builder()
                .applicationModule(new ApplicationModule(getApplication()))
                .networkModule(new NetworkModule())
                .build();
    }

    @Override
    protected void inject(Component component) {
        component.inject(this);
    }

    @dagger.Component(modules = {ApplicationModule.class, NetworkModule.class})
    @Singleton
    interface Component {

        void inject(CameraPreviewActivity activity);
    }
}

package com.zakaprov.braincode2016.activity;

import android.animation.Animator;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.jorgecastilloprz.FABProgressCircle;
import com.zakaprov.braincode2016.R;
import com.zakaprov.braincode2016.inject.ApplicationModule;
import com.zakaprov.braincode2016.inject.NetworkModule;
import com.zakaprov.braincode2016.model.Category;
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
    @Bind(R.id.category_container) RelativeLayout mCategoryContainer;
    @Bind(R.id.category_text_view) TextView mTextView;
    @Bind(R.id.camera_button_progress_circle) FABProgressCircle mProgressCircle;
    @Bind(R.id.button_result_intent) FloatingActionButton mResultIntentButton;

    @Inject BraincodeNetworkClient mBraincodeNetworkClient;

    private CameraPreview mPreview;
    private String mObtainedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mPreview = new CameraPreview(this);
        mCameraPreviewContainer.addView(mPreview);

        mResultIntentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mObtainedCategory == null || mObtainedCategory.equals("null")) {
                    return;
                }

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://allegro.pl/gunwo-" + mObtainedCategory));
                startActivity(i);
            }
        });

        mCameraButtonCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressCircle.show();
                mCameraButtonCapture.setClickable(false);

                hideContainer();
                hideResultFab();

                mPreview.takePicture(new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        new WritePictureTask(new PhotoTaskFinishedListener()).execute(data);
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mCategoryContainer.animate()
                .setDuration(0)
                .translationY(-150)
                .start();

        hideResultFab();
    }

    private class PhotoTaskFinishedListener implements WritePictureTask.OnTaskFinishedListener {

        @Override
        public void onTaskFinished(String fileName) {
            mProgressCircle.beginFinalAnimation();

            File photo = new File(Environment.getExternalStorageDirectory(), fileName);

            TypedFile photoTypedFile = new TypedFile("image/jpeg", photo);
            mBraincodeNetworkClient.uploadFile(photoTypedFile, new Callback<Category>() {

                @Override
                public void success(Category category, Response response) {
                    mTextView.setText(category.getName());
                    showContainer();
                    mObtainedCategory = category.getCategoryId();

                    mCameraButtonCapture.setClickable(true);
                    showResultFab();
                }

                @Override
                public void failure(RetrofitError error) {
                    mProgressCircle.hide();
                    mCameraButtonCapture.setClickable(true);

                    hideContainer();
                    hideResultFab();
                }
            });

            mPreview.startPreview();
        }
    }

    private void showContainer() {
        mCategoryContainer.animate()
                .setDuration(500)
                .translationY(0)
                .start();
    }

    private void hideContainer() {
        mCategoryContainer.animate()
                .setDuration(500)
                .translationY(-150)
                .start();
    }

    private void showResultFab() {
        mResultIntentButton.animate()
                .setDuration(300)
                .alpha(1)
                .start();
    }

    private void hideResultFab() {
        mResultIntentButton.animate()
                .setDuration(300)
                .alpha(0)
                .start();
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

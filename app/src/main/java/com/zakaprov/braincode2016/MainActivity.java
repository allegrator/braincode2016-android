package com.zakaprov.braincode2016;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.zakaprov.braincode2016.view.CameraPreview;

public class MainActivity extends AppCompatActivity {

    private CameraPreview mPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this);
        FrameLayout previewContainer = (FrameLayout) findViewById(R.id.camera_preview_container);

        previewContainer.addView(mPreview);
    }
}

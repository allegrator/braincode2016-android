package com.zakaprov.braincode2016.view;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.zakaprov.braincode2016.util.GlobalConstants;

import java.io.IOException;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;

    public CameraPreview(Context context) {
        super(context);

        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.stopPreview();
        } catch (Exception ignored) { }

        mCamera = getCameraInstance();

        try {
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            parameters.setPictureSize(800, 600);
            mCamera.setParameters(parameters);

            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(GlobalConstants.LOG_TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.release();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        if (mSurfaceHolder.getSurface() == null) {
            return;
        }

        try {
            mCamera.stopPreview();
        } catch (Exception ignored) { }

        if (mCamera == null) {
            mCamera = getCameraInstance();
        }

        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();

        } catch (Exception e) {
            Log.d(GlobalConstants.LOG_TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    public void takePicture(Camera.PictureCallback jpegPictureCallback) {
        mCamera.takePicture(null, null, jpegPictureCallback);
    }

    public void startPreview() {
        mCamera.startPreview();
    }

    public static Camera getCameraInstance() {
        Camera camera = null;

        try {
            camera = Camera.open();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return camera;
    }
}

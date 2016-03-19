package com.zakaprov.braincode2016.util;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

public class WritePictureTask extends AsyncTask<byte[], String, String> {

    private Context mContext;
    private OnTaskFinishedListener mListener;

    public WritePictureTask(Context context, OnTaskFinishedListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    protected String doInBackground(byte[]... jpegData) {
        String photoName = String.format("android-%s.jpg", System.currentTimeMillis());

        File photo = new File(mContext.getApplicationInfo().dataDir, photoName);

        try {
            FileOutputStream outputStream = new FileOutputStream(photo.getPath());

            outputStream.write(jpegData[0]);
            outputStream.close();
        }
        catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return(photoName);
    }

    @Override
    protected void onPostExecute(String photoName) {
        super.onPostExecute(photoName);

        mListener.onTaskFinished(photoName);
    }

    public interface OnTaskFinishedListener {

        public void onTaskFinished(String filePath);
    }
}

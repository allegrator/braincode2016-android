package com.zakaprov.braincode2016.util;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

public class WritePictureTask extends AsyncTask<byte[], String, String> {

    private OnTaskFinishedListener mListener;

    public WritePictureTask(OnTaskFinishedListener listener) {
        mListener = listener;
    }

    @Override
    protected String doInBackground(byte[]... jpegData) {
        String photoName = String.format("android-%s.jpg", System.currentTimeMillis());

        File photo = new File(Environment.getExternalStorageDirectory(), photoName);

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

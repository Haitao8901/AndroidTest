package com.example.cyber.testone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Cyber on 26/04/2018.
 */

public class BitmapWorkerTask extends AsyncTask<String, Void, BitmapDrawable> {
    private String path;
    private WeakReference<Context> contextReference;
    private WeakReference<CustImageView> imageReference;
    public BitmapWorkerTask(String path, CustImageView imageView, Context context) {
        this.path = path;
        this.contextReference = new WeakReference<Context>(context);
        this.imageReference = new WeakReference<CustImageView>(imageView);
    }

    @Override
    protected BitmapDrawable doInBackground(String... strings) {
        if(contextReference == null){
            return null;
        }

        BitmapDrawable drawable = null;
        Bitmap bitmap = decodeBitmapFromDisk(path, 150, 150);

        //Bitmap转换成BitmapDrawable
        if (bitmap != null) {
            drawable = new BitmapDrawable(contextReference.get().getResources(), bitmap);
        }
        return drawable;
    }

    @Override
    protected void onPostExecute(BitmapDrawable bitmapDrawable) {
        if(isCancelled() || imageReference == null || bitmapDrawable == null){
            return;
        }
        CustImageView imageView = imageReference.get();
        imageView.setImageDrawable(bitmapDrawable);
    }

    public String getPath() {
        return path;
    }

    public Bitmap decodeBitmapFromDisk(String path, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        //初始压缩比例
        options.inSampleSize = calculateBitmapSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap bmp = BitmapFactory.decodeFile(path, options);
        return bmp;
    }

    public int calculateBitmapSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public Bitmap loadNetWorkBitmap(){
        Bitmap bitmap = null;
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            //10 seconds
            connection.setConnectTimeout(10000);
            //30 seconds
            connection.setReadTimeout(30000);
            int code = connection.getResponseCode();
            if (code == 200) {
                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}

package com.example.cyber.testone;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cyber on 26/04/2018.
 */

public class PhotoPickActivity extends Activity {
    private List<ImageModel> images;
    private Adapter gridViewAdapter;
    private GridView gridView;
    private int mScreenHeight, mScreenWidth;
    private int eachWidth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photopicker);
        Display display = getWindowManager().getDefaultDisplay();
        mScreenHeight= display.getHeight();
        mScreenWidth = display.getWidth();
        eachWidth = (mScreenWidth - 10)/3;
        if(isGrantExternalRW()){
            getImages(this);
            gridView = findViewById(R.id.gridview);
            gridView.setAdapter(new GridAdapter());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public List<ImageModel> getImages(Context context) {
        List<ImageModel> list = new ArrayList<ImageModel>();
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA,};
        String sortOrder = MediaStore.Images.Media.DATE_ADDED + " desc";
        Cursor cursor = contentResolver.query(uri, projection, null, null, sortOrder);
        int iId = cursor.getColumnIndex(MediaStore.Images.Media._ID);
        int iPath = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String id = cursor.getString(iId);
            String path = cursor.getString(iPath);
            ImageModel imageModel = new ImageModel(id, path);
            list.add(imageModel);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public class GridAdapter extends BaseAdapter implements View.OnClickListener {
        private List<ImageModel> images;
        private int selectedCnt;


        @Override
        public int getCount() {
            return 30;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//            ImageModel imageModel = this.images.get(position);
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.griditem, null);
            }
            CustImageView imageview = convertView.findViewById(R.id.imageview);
            TextView textView = convertView.findViewById(R.id.textview);
            imageview.setOnClickListener(this);
//            loadImage(imageModel.getPath(), imageview);\
            imageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_background));
            ViewGroup.LayoutParams params = imageview.getLayoutParams();
//            if(position == 3){
//                params.height = eachWidth + 200;
//            }
            params.width = eachWidth;
            imageview.setScaleType(ImageView.ScaleType.FIT_XY);
            textView.setText(position+"");
            textView.setTextSize(20);
//            Glide.with(convertView)
//                    .load(Uri.parse("http://192.168.0.133:8900/iplatform-ruleeditor/images/icon_unlock.png"))
//                    .into(imageview);
            return convertView;
        }

        @Override
        public void onClick(View v) {

        }
    }


    public void loadImage(String path, CustImageView imageView) {
        BitmapWorkerTask task = new BitmapWorkerTask(path, imageView, this);
        if (imageView.checkAndSetTask(task)) {
            task.execute();
        }
    }

    public boolean isGrantExternalRW() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];
                if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) { //授权成功后的逻辑 ... } else { requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_CODE); } } } } }
                            this.getImages(this);
                    }else{
                        finish();
                    }
                }
            }
        }else{
            finish();
        }
    }
}

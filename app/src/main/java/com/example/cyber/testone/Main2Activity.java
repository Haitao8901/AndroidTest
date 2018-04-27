package com.example.cyber.testone;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private ListView listview;
    private int mScreenHeight, mScreenWidth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlayout);
        listview = findViewById(R.id.listview);
        Display display = getWindowManager().getDefaultDisplay();
        mScreenHeight= display.getHeight();
        mScreenWidth = display.getWidth();
        listview.setAdapter(new MyAdapter());
    }


    public class MyAdapter extends BaseAdapter{
        private List<DataItem> dataItems = new ArrayList<DataItem>();

        public MyAdapter(){
            for(int i=0; i<5; i++){
                String content = "this is data " + i;
                List<String> images = new ArrayList<String>();
                images.add("http://192.168.0.133:8900/iplatform-ruleeditor/images/icon_unlock.png");
                images.add("http://192.168.0.133:8900/iplatform-ruleeditor/images/icon_lock.png");
                images.add("http://192.168.0.133:8900/iplatform-ruleeditor/images/icon_sparql.png");
                images.add("http://192.168.0.133:8900/iplatform-ruleeditor/images/icon_db.png");
                images.add("http://192.168.0.133:8900/iplatform-ruleeditor/images/icon_rex.png");
                images.add("http://192.168.0.133:8900/iplatform-ruleeditor/images/icon_xml.png");
                dataItems.add(new DataItem(content, images));
            }
        }

        @Override
        public int getCount() {
            return dataItems.size();
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
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.teslistitem, null);
            }
            TextView textView = convertView.findViewById(R.id.content);
            textView.setText(dataItems.get(position).getContent());
            ViewPager viewPager = convertView.findViewById(R.id.viewpager);
            viewPager.setAdapter(new MyPagerAdapter(dataItems.get(position).getImages()));
            ViewGroup.LayoutParams params = convertView.getLayoutParams();
            if(params == null){
                params = new ViewGroup.LayoutParams(mScreenWidth, mScreenHeight/4);
                convertView.setLayoutParams(params);
            }
            return convertView;
        }
    }

    public class MyPagerAdapter extends android.support.v4.view.PagerAdapter{
        private List<View> views = new ArrayList<View>();
        public MyPagerAdapter(List<String> images) {
            for(int i =0; i<images.size(); i++){
                CustImageView imageview = new CustImageView(getApplicationContext());
                imageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_background));
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                imageview.setScaleType(ImageView.ScaleType.FIT_XY);
                imageview.setLayoutParams(params);
                views.add(imageview);
            }
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            loadImage(position, mScreenWidth, eachWidth, fileUrl, imageview);
            container.addView(views.get(position));
            return views.get(position);
        }

    }


    public void loadImage(int position, int width, int height, String path, CustImageView imageView) {
//        BitmapWorkerTask task = new BitmapWorkerTask(position,path, imageView, this);
//        if (imageView.checkAndSetTask(task)) {
//            task.execute();
//        }
        imageView.checkAndSetTask(position, width, height, path);
    }

    public class DataItem{
        private String content;
        private List<String> images;

        public DataItem(String content) {
            this.content = content;
        }

        public DataItem(String content, List<String> images) {
            this.content = content;
            this.images = images;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}

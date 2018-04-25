package com.example.cyber.testone;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cyber on 25/04/2018.
 */

public class BodyView extends LinearLayout implements View.OnClickListener {
    private MyViewPager pager;
    private TextView content;
    private Context context;
    private LinearLayout dots;
    public BodyView(Context context) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.photopostbody, this, true);
        pager = (MyViewPager)findViewById(R.id.pager);
        content = (TextView)findViewById(R.id.content);
        content.setText("Hello there, this is a content.");

        dots = (LinearLayout)findViewById(R.id.dots);

        List<String> images = new ArrayList<String>();
        images.add("http://192.168.0.133:8900/iplatform-ruleeditor/images/icon_unlock.png");
        images.add("http://192.168.0.133:8900/iplatform-ruleeditor/images/icon_lock.png");
        images.add("http://192.168.0.133:8900/iplatform-ruleeditor/images/icon_sparql.png");
        images.add("http://192.168.0.133:8900/iplatform-ruleeditor/images/icon_db.png");
        images.add("http://192.168.0.133:8900/iplatform-ruleeditor/images/icon_rex.png");
        images.add("http://192.168.0.133:8900/iplatform-ruleeditor/images/icon_xml.png");
        initPager(images);
    }

    private void initPager(List<String> images){
        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                Toast.makeText(context, "pageselected " + position, Toast.LENGTH_SHORT);
                changeDots(position);
            }
        });
        pager.setAdapter(new MyPagerAdapter(images));
        initDots(images);
    }

    private void initDots(List<String> images){
        for(int i = 0; i< images.size(); i++){
            Button btn = new Button(context);
            btn.setText(i + "");
            btn.setOnClickListener(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dots.addView(btn, params);
        }

    }

    private void changeDots(int position){
        for(int i = 0; i< dots.getChildCount(); i++){
            if(i == position){
                dots.getChildAt(i).setBackgroundColor(0);
            }else{
                dots.getChildAt(i).setBackgroundColor(1);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Button btn = (Button) v;
        String text = btn.getText().toString();
        pager.setCurrentItem(Integer.valueOf(text));
    }

    private class MyPagerAdapter extends PagerAdapter {
        private List<String> images;
        public MyPagerAdapter(List<String> images) {
            this.images = images;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0==arg1;
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return images.size();
        }
        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            // TODO Auto-generated method stub
//            super.destroyItem(container, position, object);
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            NetWorkImageView view = new NetWorkImageView(context);
            view.setImageResource(R.drawable.ic_launcher_background);
//            Button btn = new Button(context);
//            btn.setText(images.get(position));
            ViewPager.LayoutParams params = new ViewPager.LayoutParams();
            params.gravity = Gravity.CENTER;
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setImageURL(images.get(position));
            container.addView(view, params);
//            container.addView(btn);
            return view;
        }
    }
}

package com.example.cyber.testone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import static android.widget.AdapterView.*;

/**
 * Created by Cyber on 22/03/2018.
 */

public class ListViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_listview);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ListView listView = findViewById(R.id.listview);

        String[] strs = new String[]{
                "first", "second", "third", "fourth", "fifth","999999",
                "first", "second", "third", "fourth", "fifth","999999",
                "first", "second", "third", "fourth", "fifth","999999"
        };

        listView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strs));


        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setTitle(position);
            }
        });
    }
}



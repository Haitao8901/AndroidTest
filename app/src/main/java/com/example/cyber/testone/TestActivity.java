package com.example.cyber.testone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Cyber on 21/03/2018.
 */

public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        Log.d("aaa", "testlayout creating");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("aaa", "testlayout started.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("aaa", "testlayout resume.");
    }
}

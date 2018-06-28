package com.example.cyber.testone.serviceTest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cyber.testone.R;
import com.example.cyber.testone.danmu.BarrageView;

public class DanmuActivity extends AppCompatActivity {
    private BarrageView barrageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danmu);
        barrageView = this.findViewById(R.id.barrageView);
    }
}

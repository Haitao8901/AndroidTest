package com.example.cyber.testone;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("testone", "backThread running");
                        for(int i = 0; i < 10; i++){
                            try {
                                Thread.sleep(500);
                            }catch (Exception e){

                            }
                        }
                        Message message = handler.obtainMessage();
                        Bundle data = new Bundle();
                        data.putString("value", "I'm comming from another thread.");
                        message.setData(data);
                        handler.sendMessage(message);
                        Log.d("testone", "backThread finished.");
                    }
                }).run();
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Log.d("testone", msg.getData().toString());
            Toast.makeText(getApplicationContext(), "handle message done!", Toast.LENGTH_SHORT);
        }
    };
}

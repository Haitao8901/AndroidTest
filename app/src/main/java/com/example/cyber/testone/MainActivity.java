package com.example.cyber.testone;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cyber.testone.serviceTest.DanmuActivity;
import com.example.cyber.testone.serviceTest.DownloadService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity {
    private LinearLayout body;
    private TextView timeview,timeview1 ;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeview = (TextView) findViewById(R.id.timeview);
        timeview1 = (TextView) findViewById(R.id.timeview1);
        body = (LinearLayout)findViewById(R.id.body);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.leftfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Message mes = Message.obtain();
                    mes.replyTo = receiveMessenger;
                    Bundle bundle = new Bundle();
                    bundle.putString("msg", "hello service. I am client. What cnt is it now ?");
                    mes.setData(bundle);
                    sendMessenger.send(mes);
                    addBodyView();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        FloatingActionButton rfab = (FloatingActionButton) findViewById(R.id.rightfab);
        rfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PhotoPickActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton mfab = (FloatingActionButton) findViewById(R.id.middlefab);
        rfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DanmuActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = new Intent(MainActivity.this, DownloadService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    private void addBodyView(){
        body.addView(new BodyView(this));
    }

    private Messenger receiveMessenger = new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            Log.d("<<<<<<<<<<<<<", bundle.getString("msg"));
            Log.d("<<<<<<<<<<<<<", "server said current cnt is:---->>>" + bundle.getString("cnt"));
        }
    });

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Log.d("testone", msg.getData().toString());
            Toast.makeText(getApplicationContext(), "handle message done!", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(MainActivity.this, PageActivity.class);
//            getApplicationContext().startActivity(intent);
        }
    };

    private class MyAsyncTask extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(), "onPreExecute!", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String o) {
            Toast.makeText(getApplicationContext(), "onPreExecute!---" + o, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... datas) {
            Log.d("testone", "doInBackground");
            return datas[0];
        }
    }

    private Messenger sendMessenger = null;
    private ServiceConnection  conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            sendMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(">>>>>>>>>>>","service disconnected.");
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("testone", "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
        long milliseconds = System.currentTimeMillis();
        Date date = new Date(milliseconds);
        timeview.setText(sdf.format(date));
        System.out.println("============" + sdf.format(date) + "==========");

        Timetest test = new Timetest();
        long times = test.getMilliseconds();
        Date date1 = new Date(times);
        timeview1.setText(sdf.format(date1));

        Calendar ca = Calendar.getInstance();
        System.out.println(ca.getTimeZone());
        Log.d("testone", "onResume()");
    }

    private class Timetest{
        long milliseconds = 1525773284253L;

        public long getMilliseconds() {
            return milliseconds;
        }

        public void setMilliseconds(long milliseconds) {
            this.milliseconds = milliseconds;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sp = getSharedPreferences("com.example.cyber.testone", Context.MODE_PRIVATE);
        Log.d("testone", "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("testone", "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("testone", "onDestroy()");
    }
}

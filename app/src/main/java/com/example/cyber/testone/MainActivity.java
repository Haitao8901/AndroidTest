package com.example.cyber.testone;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.cyber.testone.serviceTest.DownloadService;

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
                try {
                    Message mes = Message.obtain();
                    mes.replyTo = receiveMessenger;
                    Bundle bundle = new Bundle();
                    bundle.putString("msg", "hello service. I am client. What cnt is it now ?");
                    mes.setData(bundle);
                    sendMessenger.send(mes);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.d("testone", "backThread running");
//                        for(int i = 0; i < 10; i++){
//                            try {
//                                Thread.sleep(500);
//                            }catch (Exception e){
//
//                            }
//                        }
//                        Message message = handler.obtainMessage();
//                        Bundle data = new Bundle();
//                        data.putString("value", "I'm comming from another thread.");
//                        message.setData(data);
//                        handler.sendMessage(message);
//                        Log.d("testone", "backThread finished.");
//                    }
//                }).start();
//                String str = "This is asyncTask.";
//                new MyAsyncTask().execute(str);
//                Log.d("testone", "main thread....");
            }
        });

        Intent intent = new Intent(MainActivity.this, DownloadService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);
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
        Log.d("testone", "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
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

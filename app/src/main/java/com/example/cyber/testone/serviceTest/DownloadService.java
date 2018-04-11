package com.example.cyber.testone.serviceTest;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/**
 * Created by Cyber on 27/03/2018.
 */

public class DownloadService extends Service {

    private Messenger messenger;
    private Handler handler;
    private Thread workThread;
    private int cnt;
    private boolean runyes;
    public DownloadService() {
        super();
        handler  = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String action = bundle.getString("action", "getcount");
                if("getcount".equalsIgnoreCase(action)){
                    runyes = true;
                    doWork();
                }
                Log.d(">>>>>>>>>>>>>>>>>", bundle.getString("msg"));
                Messenger sendMessenger = msg.replyTo;
                Message mes = Message.obtain();
                Bundle mbundle = new Bundle();
                mbundle.putString("msg", "Hello, server received your message.");
                mbundle.putString("cnt", String.valueOf(cnt));
                mes.setData(mbundle);
                try {
                    sendMessenger.send(mes);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        };
        messenger = new Messenger(handler);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    public int getCnt() {
        return cnt;
    }

    private void doWork(){
        if(this.workThread == null) {
            this.workThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        while (runyes) {
                            cnt++;
                            Thread.sleep(1000);
                        }
                    }catch (Exception e){

                    }
                }
            });
        }
        if(this.workThread.isAlive()){
            return;
        }
        this.workThread.start();
    }

    private void stopWork(){
        if(this.workThread == null){
            return;
        }
        try{
            this.workThread.interrupt();
            this.workThread.stop();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}

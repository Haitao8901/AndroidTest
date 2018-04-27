package com.example.cyber.testone;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by Cyber on 26/04/2018.
 */

public class CustImageView extends ImageView {
    private WeakReference<BitmapWorkerTask> taskWeakReference;
    public CustImageView(Context context) {
        super(context);
    }

    public CustImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void checkAndSetTask(int position, int width, int height, String path){
        Log.d("======================", "checkAndSetTask==" + position + "==" + path);
        if(taskWeakReference == null || taskWeakReference.get() == null){
            BitmapWorkerTask task = new BitmapWorkerTask(position,width, height, path, this, this.getContext());
            taskWeakReference = new WeakReference<BitmapWorkerTask>(task);
            task.execute();
            return;
        }
        BitmapWorkerTask current = taskWeakReference.get();
        if(current == null || !path.equals(current.getPath()) || current.isComplete()){
            BitmapWorkerTask task = new BitmapWorkerTask(position,width, height, path, this, this.getContext());
            current.cancel(true);
            this.taskWeakReference = new WeakReference<BitmapWorkerTask>(task);
            task.execute();
            return;
        }
    }
}

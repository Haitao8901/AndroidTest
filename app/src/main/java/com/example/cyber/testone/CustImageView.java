package com.example.cyber.testone;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
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

    public boolean checkAndSetTask(BitmapWorkerTask task) {
        if(taskWeakReference == null){
            taskWeakReference = new WeakReference<BitmapWorkerTask>(task);
            return true;
        }
        BitmapWorkerTask current = taskWeakReference.get();
        if(task.getPath().equals(current.getPath())){
            return false;
        }
        current.cancel(true);
        this.taskWeakReference = new WeakReference<BitmapWorkerTask>(task);
        return true;
    }
}

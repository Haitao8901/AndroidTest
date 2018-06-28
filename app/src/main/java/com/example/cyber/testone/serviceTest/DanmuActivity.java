package com.example.cyber.testone.serviceTest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cyber.testone.R;
import com.example.cyber.testone.danmu.BarrageView;

public class DanmuActivity extends AppCompatActivity {
    private BarrageView barrageView;
    private EditText comment;
    private ImageView send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danmu);
        barrageView = this.findViewById(R.id.barrageView);
        comment = this.findViewById(R.id.comment);
        comment.setOnEditorActionListener(new EditText.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEND){
                    barrageView.addBarrage(comment.getText().toString());
                }
                return true;
            }
        });
        send = this.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.send:
                        barrageView.addBarrage(comment.getText().toString());
                        break;
                }
            }
        });
    }
}

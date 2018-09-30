package com.mcuhq.waterino;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;

public class ManualActivity extends MyBaseActivity {

    private ImageButton mHomeBtn, mUpBtn, mDownBtn, mLeftBtn, mRightBtn, mStopBtn;
    private CheckBox mValve;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_cmds);


        mValve = (CheckBox) findViewById(R.id.checkboxVALVE);
        mUpBtn = (ImageButton) findViewById(R.id.imageButtonUp);
        mDownBtn = (ImageButton) findViewById(R.id.imageButtonDown);
        mLeftBtn = (ImageButton) findViewById(R.id.imageButtonLeft);
        mRightBtn = (ImageButton) findViewById(R.id.imageButtonRight);
        mStopBtn = (ImageButton) findViewById(R.id.imageButtonStop);
        mHomeBtn = (ImageButton) findViewById(R.id.imageButtonHome);

        mValve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMyApp.mConnectedThread != null) //First check to make sure thread created
                {
                    if (mValve.isChecked())
                        mMyApp.mConnectedThread.writeBytes(new byte[]{2, 2, 14, 1, 3});
                    else
                        mMyApp.mConnectedThread.writeBytes(new byte[]{2, 2, 14, 0, 3});
                }
            }
        });

        mHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        mUpBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        mMyApp.mConnectedThread.writeBytes(new byte[]{2, 2, 10, 1, 3});
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        mMyApp.mConnectedThread.writeBytes(new byte[]{2, 2, 10, 0, 3});
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });

        mDownBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        mMyApp.mConnectedThread.writeBytes(new byte[]{2, 2, 11, 1, 3});
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        mMyApp.mConnectedThread.writeBytes(new byte[]{2, 2, 11, 0, 3});
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });

        mLeftBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        mMyApp.mConnectedThread.writeBytes(new byte[]{2, 2, 12, 1, 3});
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        mMyApp.mConnectedThread.writeBytes(new byte[]{2, 2, 12, 0, 3});
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });

        mRightBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        mMyApp.mConnectedThread.writeBytes(new byte[]{2, 2, 13, 1, 3});
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        mMyApp.mConnectedThread.writeBytes(new byte[]{2, 2, 13, 0, 3});
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });

        mStopBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        mMyApp.mConnectedThread.writeBytes(new byte[]{2, 1, 15, 1, 3});
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });

    }


}

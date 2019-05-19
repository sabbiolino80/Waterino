package com.mcuhq.waterino;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;


public class GCodesActivity extends MyBaseActivity {

    private Button mHomeBtn, mPresetBtn, mResetAlBtn, mGetStatusBtn, mGetParams, mGetLines, mGoToBtn ;
    private ImageButton mMainBtn;
    private NumberPicker mXNum, mYNum, mFNum;
    private TextView mXVal, mYVal, mFVal;
    private TextView mBluetoothStatus;
    private TextView mReadBuffer;

    private int xTarget, yTarget, fTarget;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gcode_cmds);

        mHomeBtn = findViewById(R.id.buttonHome);
        mPresetBtn = findViewById(R.id.buttonPreset);
        mResetAlBtn = findViewById(R.id.buttonResetAl);
        mGetStatusBtn = findViewById(R.id.buttonGetStatus);
        mGetParams = findViewById(R.id.buttonGetParams);
        mGetLines = findViewById(R.id.buttonGetLines);
        mGoToBtn = findViewById(R.id.buttonGoTo);
        mMainBtn = findViewById(R.id.buttonMain);
        mXNum = findViewById(R.id.npX);
        mYNum = findViewById(R.id.npY);
        mFNum = findViewById(R.id.npF);
        mXVal = findViewById(R.id.XValText);
        mYVal = findViewById(R.id.YValText);
        mFVal = findViewById(R.id.FValText);
        mBluetoothStatus = findViewById(R.id.bluetoothStatus);
        mReadBuffer = findViewById(R.id.readBuffer);

        mXNum.setMinValue(1);
        mXNum.setMaxValue(357);
        mXNum.setWrapSelectorWheel(false);
        mYNum.setMinValue(1);
        mYNum.setMaxValue(69);
        mYNum.setWrapSelectorWheel(false);
        mFNum.setMinValue(10);
        mFNum.setMaxValue(3000);
        mFNum.setWrapSelectorWheel(false);



        mMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        mHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMyApp.mConnectedThread != null) {
                    mMyApp.mConnectedThread.writeLine(new String("$H"));
                }
            }
        });

        mPresetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMyApp.mConnectedThread != null) {
                    mMyApp.mConnectedThread.writeLine(new String("G92 X3 Y3"));
                }
            }
        });

        mResetAlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMyApp.mConnectedThread != null) {
                    mMyApp.mConnectedThread.writeLine(new String("$X"));
                }
            }
        });

        mGetStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMyApp.mConnectedThread != null) {
                    mMyApp.mConnectedThread.writeLine(new String("$?"));
                }
            }
        });

        mGetParams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMyApp.mConnectedThread != null) {
                    mMyApp.mConnectedThread.writeLine(new String("$$"));
                }
            }
        });

        mGetLines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMyApp.mConnectedThread != null) {
                    mMyApp.mConnectedThread.writeLine(new String("$N"));
                }
            }
        });

        mGoToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMyApp.mConnectedThread != null) {
                    mMyApp.mConnectedThread.writeLine(new String("G1 X" + xTarget + " Y" + yTarget + " F" + fTarget));
                }
            }
        });

        mXNum.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                xTarget = newVal;
                mXVal.setText("X: " + newVal);
            }
        });

        mYNum.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                yTarget = newVal;
                mYVal.setText("Y: " + newVal);
            }
        });

        mFNum.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                fTarget = newVal;
                mFVal.setText("F: " + newVal);
            }
        });

    }

    @Override
    public void SetBTMessage(String text)
    {
        mReadBuffer.setText(text);
    }

    @Override
    public void SetBTStatus(String text)
    {
        mBluetoothStatus.setText(text);
    }
}

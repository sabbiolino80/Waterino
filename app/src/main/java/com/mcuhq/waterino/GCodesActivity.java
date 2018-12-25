package com.mcuhq.waterino;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;


public class GCodesActivity extends MyBaseActivity {

    private Button mHomeBtn, mPresetBtn, mResetAlBtn, mGetStatusBtn, mGetParams, mGoToBtn ;
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

        mHomeBtn = (Button) findViewById(R.id.buttonHome);
        mPresetBtn = (Button) findViewById(R.id.buttonPreset);
        mResetAlBtn = (Button) findViewById(R.id.buttonResetAl);
        mGetStatusBtn = (Button) findViewById(R.id.buttonGetStatus);
        mGetParams = (Button) findViewById(R.id.buttonGetParams);
        mGoToBtn = (Button) findViewById(R.id.buttonGoTo);
        mMainBtn = (ImageButton) findViewById(R.id.buttonMain);
        mXNum = (NumberPicker) findViewById(R.id.npX);
        mYNum = (NumberPicker) findViewById(R.id.npY);
        mFNum = (NumberPicker) findViewById(R.id.npF);
        mXVal = (TextView) findViewById(R.id.XValText);
        mYVal = (TextView) findViewById(R.id.YValText);
        mFVal = (TextView) findViewById(R.id.FValText);
        mBluetoothStatus = (TextView) findViewById(R.id.bluetoothStatus);
        mReadBuffer = (TextView) findViewById(R.id.readBuffer);

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
                mMyApp.mConnectedThread.write(new String("$H"));
            }
        });

        mPresetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyApp.mConnectedThread.write(new String("G92 X3 Y3"));
            }
        });

        mResetAlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyApp.mConnectedThread.write(new String("$X"));
            }
        });

        mGetStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyApp.mConnectedThread.write(new String("$?"));
            }
        });

        mGetParams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyApp.mConnectedThread.write(new String("$$"));
            }
        });

        mGoToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyApp.mConnectedThread.write(new String("G1 X"+xTarget+" Y"+yTarget+" F"+ fTarget));
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

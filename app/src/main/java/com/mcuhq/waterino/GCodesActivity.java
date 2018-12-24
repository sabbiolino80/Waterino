package com.mcuhq.waterino;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;


public class GCodesActivity extends MyBaseActivity {

    private Button mHomeBtn, mPresetBtn, mResetAlBtn, mGetStatusBtn, mGetParams, mGoToBtn ;
    private ImageButton mMainBtn;

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
                mMyApp.mConnectedThread.write(new String("G1 X90 Y35 F2000"));
            }
        });

    }


}

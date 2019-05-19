package com.mcuhq.waterino;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Handler;
import android.util.Xml;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

public class ProgramEditorActivity extends MyBaseActivity {

    static final int MAX_ROW = 30;

    private ImageButton mMainBtn, mFlashBtn, mRunBtn, mDeleteStepBtn, mSaveBtn, mExecuteStepBtn;
    private ImageButton mReferenceBtn, mPowerBtn, mValveBtn, mValveOffBtn, mGotoBtn;
    private NumberPicker mXNum, mYNum, mFNum;
    private TextView mXVal, mYVal, mFVal, mCurrentRow;
    private TextView mBluetoothStatus, mReadBuffer;
    private LinearLayout[] mRowLayouts;
    private TextView[] mRowTexts;

    private int xTarget, yTarget, fTarget, selectedRow;
    ArrayList<Step> steps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_editor);

        NumPickerInit();

        mBluetoothStatus = findViewById(R.id.bluetoothStatus);
        mReadBuffer = findViewById(R.id.readBuffer);

        ButtonInit();

        mCurrentRow = findViewById(R.id.currentRowText);
        selectedRow = 0;

        mRowLayouts = new LinearLayout[MAX_ROW];
        mRowTexts = new TextView[MAX_ROW];
        for (int i = 1; i <= MAX_ROW; i++) {
            int id = getResources().getIdentifier("RowLayout" + i, "id", GetContext().getPackageName());
            mRowLayouts[i - 1] = findViewById(id);
            id = getResources().getIdentifier("text" + i, "id", GetContext().getPackageName());
            mRowTexts[i - 1] = findViewById(id);
        }
        for (int i = 1; i <= MAX_ROW; i++) {
            final int finalI = i;
            mRowLayouts[i - 1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SetCurrentRow(finalI);
                }
            });
        }

        parseXML();
    }

    private void NumPickerInit() {
        mXNum = findViewById(R.id.npX);
        mYNum = findViewById(R.id.npY);
        mFNum = findViewById(R.id.npF);
        mXVal = findViewById(R.id.XValText);
        mYVal = findViewById(R.id.YValText);
        mFVal = findViewById(R.id.FValText);

        mXNum.setMinValue(1);
        mXNum.setMaxValue(357);
        mXNum.setWrapSelectorWheel(false);
        mYNum.setMinValue(1);
        mYNum.setMaxValue(69);
        mYNum.setWrapSelectorWheel(false);
        mFNum.setMinValue(10);
        mFNum.setMaxValue(3000);
        mFNum.setWrapSelectorWheel(false);


        mXNum.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                xTarget = newVal;
                mXVal.setText("X: " + newVal);
            }
        });

        mYNum.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                yTarget = newVal;
                mYVal.setText("Y: " + newVal);
            }
        });

        mFNum.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                fTarget = newVal;
                mFVal.setText("F: " + newVal);
            }
        });

        mXNum.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                xTarget = newVal;
                mXVal.setText("X: " + newVal);
            }
        });

        mYNum.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                yTarget = newVal;
                mYVal.setText("Y: " + newVal);
            }
        });

        mFNum.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                fTarget = newVal;
                mFVal.setText("F: " + newVal);
            }
        });
    }

    private void ButtonInit() {
        mMainBtn = findViewById(R.id.buttonMain);
        mFlashBtn = findViewById(R.id.buttonFlash);
        mRunBtn = findViewById(R.id.buttonRun);
        mDeleteStepBtn = findViewById(R.id.buttonDelete);
        mSaveBtn = findViewById(R.id.buttonSave);
        mExecuteStepBtn = findViewById(R.id.buttonExecute);


        mReferenceBtn = findViewById(R.id.buttonReference);
        mPowerBtn = findViewById(R.id.buttonPower);
        mValveBtn = findViewById(R.id.buttonValve);
        mValveOffBtn = findViewById(R.id.buttonValveStop);
        mGotoBtn = findViewById(R.id.buttonGoto);

        mMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        mFlashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlashProgram();
            }
        });

        mRunBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyApp.mConnectedThread.writeLine("F6");
            }
        });

        mDeleteStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedRow > 2 && selectedRow < MAX_ROW)
                    mRowTexts[selectedRow - 1].setText("");
            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//TODO writeLine xml file
            }
        });

        mExecuteStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedRow > 0 && selectedRow <= MAX_ROW)
                    mMyApp.mConnectedThread.writeLine((String) mRowTexts[selectedRow - 1].getText());
            }
        });


        mReferenceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 32 because it takes two steps ;)
                if (selectedRow > 0 && selectedRow < MAX_ROW - 1) {
                    mRowTexts[selectedRow - 1].setText("$H");
                    mRowTexts[selectedRow].setText("G92 X3 Y3");
                }
            }
        });

        mPowerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedRow > 2 && selectedRow < MAX_ROW)
                    mRowTexts[selectedRow - 1].setText("");
            }
        });

        mValveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedRow > 2 && selectedRow < MAX_ROW)
                    mRowTexts[selectedRow - 1].setText("F2");
            }
        });

        mValveOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedRow > 2 && selectedRow < MAX_ROW)
                    mRowTexts[selectedRow - 1].setText("F3");
            }
        });

        mGotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedRow > 2 && selectedRow < MAX_ROW)
                    mRowTexts[selectedRow - 1].setText(new String("G1 X" + xTarget + " Y" + yTarget + " F" + fTarget));
            }
        });

        //F0 led on F1 led off
    }

    @Override
    public void SetBTMessage(String text) {
        mReadBuffer.setText(text);
    }

    @Override
    public void SetBTStatus(String text) {
        mBluetoothStatus.setText(text);
    }

    private void SetCurrentRow(int row) {
        selectedRow = row;
        mCurrentRow.setText("row " + selectedRow);
    }

    private void parseXML() {
        XmlPullParserFactory parserFactory;
        try {
            //parserFactory = XmlPullParserFactory.newInstance();
            //XmlPullParser parser = parserFactory.newPullParser();
            //InputStream is = getAssets().open("programs.xml");
            //String path = getApplicationContext().getDataDir().getAbsolutePath();
            //InputStream is = new FileInputStream(new File(path + "/res/programs.xml"));
            Resources res = getResources();
            XmlResourceParser parser = res.getXml(R.xml.programs);
            //parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            //parser.setInput(is, null);

            processParsing(parser);

            XmlToLayout();

        } catch (XmlPullParserException e) {

        } catch (IOException e) {
        }
    }

    private void XmlToLayout() {
        for (Step s : steps) {
            mRowTexts[s.number - 1].setText(s.text);
        }
    }

    private void processParsing(XmlResourceParser parser) throws IOException, XmlPullParserException {
        steps = new ArrayList<>();
        int eventType = parser.getEventType();
        Step currentStep = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName = null;

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();

                    if ("step".equals(eltName)) {
                        currentStep = new Step();
                        int n = parser.getAttributeCount();
                        for (int i = 0; i < n; i++) {
                            String att = parser.getAttributeName(i);
                            switch (att) {
                                case "number":
                                    currentStep.number = Integer.parseInt(parser.getAttributeValue(i));
                                    break;
                                case "type":
                                    currentStep.type = parser.getAttributeValue(i);
                                    break;
                                case "text":
                                    currentStep.text = parser.getAttributeValue(i);
                                    break;
                            }
                        }

                        steps.add(currentStep);
                    }
                    break;
                case XmlPullParser.TEXT:

                    break;
            }

            eventType = parser.next();
        }

        //printPlayers(players);
    }


    private void writeToFile() {
        final String xmlFile = "userData";
        String userName = "username";
        String password = "password";
        try {
            FileOutputStream fos = new FileOutputStream("userData.xml");
            FileOutputStream fileos = getApplicationContext().openFileOutput(xmlFile, getApplicationContext().MODE_PRIVATE);
            XmlSerializer xmlSerializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag(null, "userData");
            xmlSerializer.startTag(null, "userName");
            xmlSerializer.text("...");
            xmlSerializer.endTag(null, "userName");
            xmlSerializer.startTag(null, "password");
            xmlSerializer.text("...");
            xmlSerializer.endTag(null, "password");
            xmlSerializer.endTag(null, "userData");
            xmlSerializer.endDocument();
            xmlSerializer.flush();
            String dataWrite = writer.toString();
            fileos.write(dataWrite.getBytes());
            fileos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    Handler timerHandler = new Handler();
    //int lineNum = 0;
    int index = 0;
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            //if (mRowTexts[index].getText() != "") {
                //$Nn=...
                mMyApp.mConnectedThread.writeLine(new String("$N" + index + "=" + mRowTexts[index].getText()));
                //lineNum++;
            //}
            index++;
            if (index < MAX_ROW)
                timerHandler.postDelayed(this, 100);
            else
                timerHandler.removeCallbacks(timerRunnable);
        }
    };

    private void FlashProgram() {
        //lineNum = 0;
        index = 0;
        mMyApp.mConnectedThread.writeLine(new String("$X"));
        timerHandler.postDelayed(timerRunnable, 1000);
    }


}

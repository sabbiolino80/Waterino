package com.mcuhq.waterino;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;

public class ProgramEditorActivity extends MyBaseActivity {

    private ImageButton mMainBtn, mAddBtn, mRemoveBtn, mMoveUpBtn, mMoveDownBtn, mCopyBtn;
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

        mBluetoothStatus = (TextView) findViewById(R.id.bluetoothStatus);
        mReadBuffer = (TextView) findViewById(R.id.readBuffer);

        ButtonInit();

        mCurrentRow = (TextView) findViewById(R.id.currentRowText);
        selectedRow = 0;

        mRowLayouts = new LinearLayout[32];
        mRowTexts = new TextView[32];
        for (int i = 1; i < 33; i++) {
            int id = getResources().getIdentifier("RowLayout" + i, "id", GetContext().getPackageName());
            mRowLayouts[i - 1] = (LinearLayout) findViewById(id);
            id = getResources().getIdentifier("text" + i, "id", GetContext().getPackageName());
            mRowTexts[i - 1] = (TextView) findViewById(id);
        }
        for (int i = 1; i < 33; i++) {
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
        mXNum = (NumberPicker) findViewById(R.id.npX);
        mYNum = (NumberPicker) findViewById(R.id.npY);
        mFNum = (NumberPicker) findViewById(R.id.npF);
        mXVal = (TextView) findViewById(R.id.XValText);
        mYVal = (TextView) findViewById(R.id.YValText);
        mFVal = (TextView) findViewById(R.id.FValText);

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
        mMainBtn = (ImageButton) findViewById(R.id.buttonMain);
        mAddBtn = (ImageButton) findViewById(R.id.buttonAdd);
        mRemoveBtn = (ImageButton) findViewById(R.id.buttonRemove);
        mMoveUpBtn = (ImageButton) findViewById(R.id.buttonMoveUp);
        mMoveDownBtn = (ImageButton) findViewById(R.id.buttonMoveUp);
        mCopyBtn = (ImageButton) findViewById(R.id.buttonCopy);


        mReferenceBtn = (ImageButton) findViewById(R.id.buttonReference);
        mPowerBtn = (ImageButton) findViewById(R.id.buttonPower);
        mValveBtn = (ImageButton) findViewById(R.id.buttonValve);
        mValveOffBtn = (ImageButton) findViewById(R.id.buttonValveStop);
        mGotoBtn = (ImageButton) findViewById(R.id.buttonGoto);

        mMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mMoveUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mMoveDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mCopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        mReferenceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedRow > 0 && selectedRow < 32) {
                    mRowTexts[selectedRow - 1].setText("$H");
                    mRowTexts[selectedRow].setText("G92 X3 Y3");
                }
            }
        });

        mPowerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedRow > 0 && selectedRow < 33)
                    mRowTexts[selectedRow - 1].setText("");
            }
        });

        mValveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedRow > 0 && selectedRow < 33)
                    mRowTexts[selectedRow - 1].setText("F2");
            }
        });

        mValveOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedRow > 0 && selectedRow < 33)
                    mRowTexts[selectedRow - 1].setText("F3");
            }
        });

        mGotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedRow > 0 && selectedRow < 33)
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


    private void writeoFile()
    {
        final String xmlFile = "userData";
        String userNAme = "username";
        String password = "password";
        try {
            FileOutputStream fos = new  FileOutputStream("userData.xml");
            FileOutputStream fileos= getApplicationContext().openFileOutput(xmlFile, getApplicationContext().MODE_PRIVATE);
            XmlSerializer xmlSerializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag(null, "userData");
            xmlSerializer.startTag(null, "userName");
            xmlSerializer.text("...");
            xmlSerializer.endTag(null, "userName");
            xmlSerializer.startTag(null,"password");
            xmlSerializer.text("...");
            xmlSerializer.endTag(null, "password");
            xmlSerializer.endTag(null, "userData");
            xmlSerializer.endDocument();
            xmlSerializer.flush();
            String dataWrite = writer.toString();
            fileos.write(dataWrite.getBytes());
            fileos.close();
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

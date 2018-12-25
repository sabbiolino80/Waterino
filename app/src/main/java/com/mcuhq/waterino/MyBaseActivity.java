package com.mcuhq.waterino;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class MyBaseActivity extends Activity {
    protected MyApp mMyApp;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMyApp = (MyApp)this.getApplicationContext();
    }
    protected void onResume() {
        super.onResume();
        mMyApp.setCurrentActivity(this);
    }
    protected void onPause() {
        clearReferences();
        super.onPause();
    }
    protected void onDestroy() {
        clearReferences();
        super.onDestroy();
    }

    private void clearReferences(){
        Activity currActivity = mMyApp.getCurrentActivity();
        if (this.equals(currActivity))
            mMyApp.setCurrentActivity(null);
    }

    protected Context GetContext()
    {
        return mMyApp.getApplicationContext();
    }

    protected Activity GetCurrentActivity()
    {
        return mMyApp.getCurrentActivity();
    }


    public void SetBTMessage(String text)
    {

    }

    public void SetBTStatus(String text)
    {

    }

}

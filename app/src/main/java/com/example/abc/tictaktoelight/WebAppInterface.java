package com.example.abc.tictaktoelight;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

public class WebAppInterface {
    Context mContext;
    String data;

    WebAppInterface(Context ctx){
        this.mContext=ctx;
    }


    @JavascriptInterface
    public void sendData(String data) {
        //Get the string value to process
        this.data=data;
        Log.i("Webview_data",data);
    }
}
package com.example.abc.tictaktoelight;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class WebviewActivity extends AppCompatActivity  {
    private TextView tvText;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);
        tvText = findViewById(R.id.textview);
        progressBar = findViewById(R.id.progress_circular);
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new AppWebViewClients(progressBar));

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setDomStorageEnabled(true);

        int flag = getIntent().getIntExtra("flag",0);
        if(flag==1){
            webView.loadUrl("https://rawgit.com/danishFarooqAndroidDev/privatepolicy/master/privacy_policy_android.html");
        }else{
            webView.loadUrl("https://danishitm.wixsite.com/danishfarooqandroid");
        }


        // for sending data from webview to android

//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.addJavascriptInterface(new WebAppInterface(WebviewActivity.this), "Android");
//        File dir = Environment.getExternalStorageDirectory();
//        String path = dir.getAbsolutePath();
//        path = "file:///"+path + "/index.html";
//        Toast.makeText(this, ""+path, Toast.LENGTH_LONG).show();
//        webView.loadUrl(path);
    }

    public class AppWebViewClients extends WebViewClient {
        private ProgressBar progressBar;

        public AppWebViewClients(ProgressBar progressBar) {
            this.progressBar=progressBar;
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }


}


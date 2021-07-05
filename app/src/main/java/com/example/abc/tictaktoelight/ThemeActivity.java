package com.example.abc.tictaktoelight;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ThemeActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout ll0, ll1, ll2, ll3, ll4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme_activity);
        ll0 = findViewById(R.id.ll_0);
        ll1 = findViewById(R.id.ll_1);
        ll2 = findViewById(R.id.ll_2);
        ll3 = findViewById(R.id.ll_3);
        ll4 = findViewById(R.id.ll_4);
        ll0.setOnClickListener(this);
        ll1.setOnClickListener(this);
        ll2.setOnClickListener(this);
        ll3.setOnClickListener(this);
        ll4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent previousScreen = new Intent(this, MainActivity.class);
        switch (view.getId()) {
            case R.id.ll_0:
                previousScreen.putExtra("theme", 0);
                setResult(-1, previousScreen);
                finish();
                break;
            case R.id.ll_1:
                previousScreen.putExtra("theme", 1);
                setResult(-1, previousScreen);
                finish();
                break;
            case R.id.ll_2:
                previousScreen.putExtra("theme", 2);
                setResult(-1, previousScreen);
                finish();
                break;
            case R.id.ll_3:
                previousScreen.putExtra("theme", 3);
                setResult(-1, previousScreen);
                finish();
                break;
            case R.id.ll_4:
                previousScreen.putExtra("theme", 4);
                setResult(-1, previousScreen);
                finish();
                break;
        }
    }
}


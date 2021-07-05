package com.example.abc.tictaktoelight;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvLocalPlay, tvComputer;
    private LinearLayout llShare, llRate, llTheme, llSetting;
    String popUpContents[];
    PopupWindow popupWindowDogs;
    List<String> dogsList = new ArrayList<String>();
    int optionChoose = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLocalPlay = findViewById(R.id.tv_local);
        tvLocalPlay.setOnClickListener(this);
        tvComputer = findViewById(R.id.tv_computer);
        tvComputer.setOnClickListener(this);
        llShare = findViewById(R.id.ll_share);
        llRate = findViewById(R.id.ll_rate);
        llTheme = findViewById(R.id.ll_theme);
        llSetting = findViewById(R.id.ll_setting);
        llShare.setOnClickListener(this);
        llRate.setOnClickListener(this);
        llTheme.setOnClickListener(this);
        llSetting.setOnClickListener(this);
        dogsList.add("Privacy Policy::1");
        dogsList.add("About Developer::2");
        // convert to simple array
        popUpContents = new String[dogsList.size()];
        dogsList.toArray(popUpContents);

        // initialize pop up window
        popupWindowDogs = popupWindowDogs();
    }

    public PopupWindow popupWindowDogs() {

        // initialize a pop up window type
        PopupWindow popupWindow = new PopupWindow(this);

        // the drop down list is a list view
        ListView listViewDogs = new ListView(this);

        // set our adapter and pass our pop up window contents
        listViewDogs.setAdapter(dogsAdapter(popUpContents));

        // set the item click listener
        listViewDogs.setOnItemClickListener(new DogsDropdownOnItemClickListener());

        // some other visual settings
        popupWindow.setFocusable(true);
        popupWindow.setWidth(250);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // set the list view as pop up window content
        popupWindow.setContentView(listViewDogs);

        return popupWindow;
    }

    private ArrayAdapter<String> dogsAdapter(String dogsArray[]) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dogsArray) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                // setting the ID and text for every items in the list
                String item = getItem(position);
                String[] itemArr = item.split("::");
                String text = itemArr[0];
                String id = itemArr[1];

                // visual settings for the list item
                TextView listItem = new TextView(MainActivity.this);

                listItem.setText(text);
                listItem.setTag(id);
                listItem.setTextSize(22);
                listItem.setPadding(10, 10, 10, 10);
                listItem.setTextColor(Color.WHITE);

                return listItem;
            }
        };

        return adapter;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_local:
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                intent.putExtra("option", optionChoose);
                startActivity(intent);
                break;
            case R.id.tv_computer:
                Intent intentComputer = new Intent(MainActivity.this, PlayComputerActivity.class);
                intentComputer.putExtra("option", optionChoose);
                startActivity(intentComputer);
                break;
            case R.id.ll_share:
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Tic Tak Toe Light");
                    String sAux = "\nLet me recommend you this application\n\n";
                    sAux = sAux + "http://play.google.com/store/apps/details?id="+ MainActivity.this.getPackageName()+ "\n\n";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }                   break;
            case R.id.ll_rate:
                Uri uri = Uri.parse("market://details?id=" + MainActivity.this.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName())));
                }                break;
            case R.id.ll_theme:
                Intent intentTheme = new Intent(MainActivity.this, ThemeActivity.class);
                startActivityForResult(intentTheme, 1000);
                break;
            case R.id.ll_setting:
                popupWindowDogs.showAsDropDown(view, -5, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    optionChoose = data.getIntExtra("theme", 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}


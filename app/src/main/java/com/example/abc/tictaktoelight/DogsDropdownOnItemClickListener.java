package com.example.abc.tictaktoelight;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

public class DogsDropdownOnItemClickListener implements AdapterView.OnItemClickListener {

    String TAG = "DogsDropdownOnItemClickListener.java";

    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {

        // get the context and main activity to access variables
        Context mContext = v.getContext();
        MainActivity mainActivity = ((MainActivity) mContext);

        // add some animation when a list item was clicked
        Animation fadeInAnimation = AnimationUtils.loadAnimation(v.getContext(), android.R.anim.fade_in);
        fadeInAnimation.setDuration(10);
        v.startAnimation(fadeInAnimation);

        // dismiss the pop up
        mainActivity.popupWindowDogs.dismiss();

        // get the text and set it as the button text

        // get the id
        String selectedItemTag = ((TextView) v).getTag().toString();
        switch (selectedItemTag) {
            case "1":
                Intent intent = new Intent(mainActivity, WebviewActivity.class);
                intent.putExtra("flag",1);
                mainActivity.startActivity(intent);
                break;
            case "2":
                Intent intentNew = new Intent(mainActivity, WebviewActivity.class);
                intentNew.putExtra("flag",2);
                mainActivity.startActivity(intentNew);
                break;
        }

    }

}
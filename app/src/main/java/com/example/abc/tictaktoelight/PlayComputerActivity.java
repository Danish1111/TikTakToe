package com.example.abc.tictaktoelight;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import java.util.Random;

public class PlayComputerActivity extends AppCompatActivity implements RewardedVideoAdListener {
    int activiePlayer = 0;   // 0 for 0  , 1 for x
    int[] playedList = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPosition = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    ArrayList<Integer> runningPostions = new ArrayList<>();
    ArrayList<Integer> runningPostionsOfPlayer = new ArrayList<>();
    ArrayList<Integer> runningPostionsOfComputer = new ArrayList<>();
    private Button btPlayAgain, btPlayAgainSecond;
    private GridLayout gridLayout;
    private LinearLayout llPlayAgain;
    int count = 0;
    FrameLayout fL036, fl258;
    LinearLayout ll012, ll345, ll678, fl246, fl048, fl147;
    private boolean gamecontinue = true;
    MediaPlayer mediaPlayer;
    private TextView tvCirleWins, tvXwins;
    int circleWins = 0;
    int xWins = 0;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private RewardedVideoAd mRewardedVideoAd;
    private int gameCount = 0;
    private int gameCountUpto15 = 0;
    Handler mHandler = new Handler();
    int optionChoose;
    RelativeLayout rlRoot;
    ImageView imageView, ivZeroCount, ivCrossCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);
        try {
            optionChoose = getIntent().getIntExtra("option", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mHandler = new Handler(Looper.getMainLooper());
        ivZeroCount = findViewById(R.id.iv_zero_count);
        ivCrossCount = findViewById(R.id.iv_cross_count);
        rlRoot = findViewById(R.id.rl_root);
        MobileAds.initialize(this, "ca-app-pub-6939649863727842~2129091003");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-6939649863727842/1740425809");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
//                displayInterstitial();
            }

            public void onAdClosed() {
                requestNewInterstitial();
            }

        });


        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(getApplicationContext());
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        tvCirleWins = (TextView) findViewById(R.id.tv_circle_wins);
        tvXwins = (TextView) findViewById(R.id.tv_x_wins);
        llPlayAgain = (LinearLayout) findViewById(R.id.ll_play_again);
        btPlayAgain = (Button) findViewById(R.id.bt_play_again);
        btPlayAgainSecond = (Button) findViewById(R.id.bt_play_again_second);
        gridLayout = (GridLayout) findViewById(R.id.gl_play);
        fL036 = (FrameLayout) findViewById(R.id.fl_036);
        fl258 = (FrameLayout) findViewById(R.id.fl_258);
        fl147 = (LinearLayout) findViewById(R.id.fl_147);
        fl246 = (LinearLayout) findViewById(R.id.fl_246);
        fl048 = (LinearLayout) findViewById(R.id.fl_048);
        ll012 = (LinearLayout) findViewById(R.id.ll_012);
        ll345 = (LinearLayout) findViewById(R.id.ll_345);
        ll678 = (LinearLayout) findViewById(R.id.ll_678);
        btPlayAgainSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameCount = gameCount + 1;
                gameCountUpto15 = gameCountUpto15 + 1;
                btPlayAgainSecond.setVisibility(View.INVISIBLE);
                restartGame();
            }
        });
        btPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameCount = gameCount + 1;
                gameCountUpto15 = gameCountUpto15 + 1;
                btPlayAgainSecond.setVisibility(View.INVISIBLE);
                restartGame();
            }
        });
        initTheme();
    }

    private void initTheme() {
        switch (optionChoose) {
            case 0:
                rlRoot.setBackgroundResource(R.drawable.woodentweleve);
                ivCrossCount.setImageResource(R.drawable.ic_cross);
                ivZeroCount.setImageResource(R.drawable.ic_circl);
                break;
            case 1:
                rlRoot.setBackgroundResource(R.drawable.superhero);
                ivCrossCount.setImageResource(R.drawable.ic_superman);
                ivZeroCount.setImageResource(R.drawable.ic_batman);
                break;
            case 2:
                rlRoot.setBackgroundResource(R.drawable.technology);
                ivCrossCount.setImageResource(R.drawable.ic_android_logo);
                ivZeroCount.setImageResource(R.drawable.ic_apple_logo);
                break;
            case 3:
                rlRoot.setBackgroundResource(R.drawable.loveimage);
                ivCrossCount.setImageResource(R.drawable.ic_business_woman);
                ivZeroCount.setImageResource(R.drawable.ic_man_with_tie);
                break;
            case 4:
                rlRoot.setBackgroundResource(R.drawable.hulkbackground);
                ivCrossCount.setImageResource(R.drawable.ic_iron_man);
                ivZeroCount.setImageResource(R.drawable.ic_spider_man);
                break;
        }
    }

    private void restartGame() {
        runningPostions.clear();
        runningPostionsOfPlayer.clear();
        runningPostionsOfComputer.clear();
        activiePlayer = 0;
        gamecontinue = true;
        for (int i = 0; i < playedList.length; i++) {
            playedList[i] = 2;
            llPlayAgain.setVisibility(View.GONE);
            gridLayout.setVisibility(View.VISIBLE);
            count = 0;
            ll012.setBackgroundResource(0);
            ll345.setBackgroundResource(0);
            ll678.setBackgroundResource(0);

            fL036.setVisibility(View.GONE);
            fl147.setVisibility(View.GONE);
            fl258.setVisibility(View.GONE);
            fl048.setVisibility(View.GONE);
            fl246.setVisibility(View.GONE);


            ImageView imageView0 = (ImageView) findViewById(R.id.tv_00);
            imageView0.setImageResource(0);
            ImageView imageView1 = (ImageView) findViewById(R.id.tv_01);
            imageView1.setImageResource(0);
            ImageView imageView2 = (ImageView) findViewById(R.id.tv_02);
            imageView2.setImageResource(0);

            ImageView imageView3 = (ImageView) findViewById(R.id.tv_10);
            imageView3.setImageResource(0);
            ImageView imageView4 = (ImageView) findViewById(R.id.tv_11);
            imageView4.setImageResource(0);
            ImageView imageView5 = (ImageView) findViewById(R.id.tv_12);
            imageView5.setImageResource(0);


            ImageView imageView6 = (ImageView) findViewById(R.id.tv_20);
            imageView6.setImageResource(0);
            ImageView imageView7 = (ImageView) findViewById(R.id.tv_21);
            imageView7.setImageResource(0);
            ImageView imageView8 = (ImageView) findViewById(R.id.tv_22);
            imageView8.setImageResource(0);

        }
    }

    public void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequest);
    }


    public void displayInterstitial() {
// If Ads are loaded, show Interstitial else show nothing.
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }

    public void dropIn(View view) {
        imageView = (ImageView) view;
        int tabbedNo = Integer.parseInt(imageView.getTag().toString());
        if (gamecontinue) {
            if (playedList[tabbedNo] == 2) {
              /*  AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0);*/
                count = count + 1;
                playedList[tabbedNo] = activiePlayer;
                imageView.animate().setDuration(300);
                if (activiePlayer == 0) {
                    setZero(tabbedNo);
                    activiePlayer = 1;
                    mediaPlayer = MediaPlayer.create(this, R.raw.chin_up);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.reset();
                            mp.release();
                            mediaPlayer = null;
                        }
                    });
                    mediaPlayer.start();

                } else {
                    setCross(tabbedNo);
                    activiePlayer = 0;
                    mediaPlayer = MediaPlayer.create(this, R.raw.chin_up);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.reset();
                            mp.release();
                            mediaPlayer = null;
                        }
                    });
                    mediaPlayer.start();
                }

                for (int[] winningPos : winningPosition) {
                    if (playedList[winningPos[0]] == playedList[winningPos[1]] && playedList[winningPos[1]] == playedList[winningPos[2]] && playedList[winningPos[0]] != 2) {
                        if (playedList[winningPos[0]] == 0) {
//                            Toast.makeText(MainActivity.this, "0 wins", Toast.LENGTH_LONG).show();
                            circleWins = circleWins + 1;
                            tvCirleWins.setText(circleWins + " Wins");

                        } else {
//                            Toast.makeText(MainActivity.this, "x wins", Toast.LENGTH_LONG).show();
                            xWins = xWins + 1;
                            tvXwins.setText(xWins + " Wins");

                        }

                        int[] pos012 = {0, 1, 2};
                        int[] pos345 = {3, 4, 5};
                        int[] pos678 = {6, 7, 8};

                        int[] pos036 = {0, 3, 6};
                        int[] pos147 = {1, 4, 7};
                        int[] pos258 = {2, 5, 8};

                        int[] pos048 = {0, 4, 8};
                        int[] pos246 = {2, 4, 6};

                        if (Arrays.equals(winningPos, pos012)) {
                            ll012.setBackgroundResource(R.drawable.ic_line);
                            gamecontinue = false;
                            btPlayAgainSecond.setVisibility(View.VISIBLE);
                            mediaPlayer = MediaPlayer.create(this, R.raw.definite);
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mp.reset();
                                    mp.release();
                                    mediaPlayer = null;
                                }
                            });
                            mediaPlayer.start();
                            playAds();
                            return;
                        } else if (Arrays.equals(winningPos, pos345)) {
                            gamecontinue = false;
                            ll345.setBackgroundResource(R.drawable.ic_line);
                            btPlayAgainSecond.setVisibility(View.VISIBLE);
                            mediaPlayer = MediaPlayer.create(this, R.raw.definite);
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mp.reset();
                                    mp.release();
                                    mediaPlayer = null;
                                }
                            });
                            mediaPlayer.start();
                            playAds();
                            return;
                        } else if (Arrays.equals(winningPos, pos678)) {
                            gamecontinue = false;
                            ll678.setBackgroundResource(R.drawable.ic_line);
                            btPlayAgainSecond.setVisibility(View.VISIBLE);
                            mediaPlayer = MediaPlayer.create(this, R.raw.definite);
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mp.reset();
                                    mp.release();
                                    mediaPlayer = null;
                                }
                            });
                            mediaPlayer.start();
                            playAds();
                            return;
                        } else if (Arrays.equals(winningPos, pos036)) {
                            gamecontinue = false;
                            fL036.setVisibility(View.VISIBLE);
                            btPlayAgainSecond.setVisibility(View.VISIBLE);
                            mediaPlayer = MediaPlayer.create(this, R.raw.definite);
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mp.reset();
                                    mp.release();
                                    mediaPlayer = null;
                                }
                            });
                            mediaPlayer.start();
                            playAds();
                            return;
                        } else if (Arrays.equals(winningPos, pos147)) {
                            gamecontinue = false;
                            fl147.setVisibility(View.VISIBLE);
                            btPlayAgainSecond.setVisibility(View.VISIBLE);
                            mediaPlayer = MediaPlayer.create(this, R.raw.definite);
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mp.reset();
                                    mp.release();
                                    mediaPlayer = null;
                                }
                            });
                            mediaPlayer.start();
                            return;
                        } else if (Arrays.equals(winningPos, pos258)) {
                            gamecontinue = false;
                            fl258.setVisibility(View.VISIBLE);
                            btPlayAgainSecond.setVisibility(View.VISIBLE);
                            mediaPlayer = MediaPlayer.create(this, R.raw.definite);
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mp.reset();
                                    mp.release();
                                    mediaPlayer = null;
                                }
                            });
                            mediaPlayer.start();
                            playAds();
                            return;
                        } else if (Arrays.equals(winningPos, pos048)) {
                            gamecontinue = false;
                            fl048.setVisibility(View.VISIBLE);
                            btPlayAgainSecond.setVisibility(View.VISIBLE);
                            mediaPlayer = MediaPlayer.create(this, R.raw.definite);
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mp.reset();
                                    mp.release();
                                    mediaPlayer = null;
                                }
                            });
                            mediaPlayer.start();
                            playAds();
                            return;

                        } else if (Arrays.equals(winningPos, pos246)) {
                            gamecontinue = false;
                            fl246.setVisibility(View.VISIBLE);
                            btPlayAgainSecond.setVisibility(View.VISIBLE);
                            mediaPlayer = MediaPlayer.create(this, R.raw.definite);
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mp.reset();
                                    mp.release();
                                    mediaPlayer = null;
                                }
                            });
                            mediaPlayer.start();
                            playAds();
                            return;
                        }

                    }

                }
               /* if(mPlayer.isPlaying()){
                    mPlayer.stop();
                }
*/
                if ((count == 9) && gamecontinue) {
                    llPlayAgain.setVisibility(View.VISIBLE);
                    count = 0;
                    gridLayout.setVisibility(View.GONE);
                    ll012.setBackgroundResource(0);
                    ll345.setBackgroundResource(0);
                    ll678.setBackgroundResource(0);

                    fL036.setVisibility(View.GONE);
                    fl147.setVisibility(View.GONE);
                    fl258.setVisibility(View.GONE);
                    fl048.setVisibility(View.GONE);
                    fl246.setVisibility(View.GONE);
                    btPlayAgainSecond.setVisibility(View.INVISIBLE);
                    playAds();
                    return;
                }
                disableEnableControls(false, rlRoot);
                mHandler.postDelayed(takePictureRunnable, 500);
            }
        }
    }

    Runnable takePictureRunnable = new Runnable() {
        @Override
        public void run() {
            machineLearningLogic();
        }
    };

    private void machineLearningLogic() {

        // A.E for winning

        if (runningPostionsOfComputer.contains(0) && !runningPostionsOfComputer.contains(1) && runningPostionsOfComputer.contains(2) && !runningPostions.contains(1)) {
            dropIn(1);
        } else if (runningPostionsOfComputer.contains(0) && runningPostionsOfComputer.contains(1) && !runningPostionsOfComputer.contains(2) && !runningPostions.contains(2)) {
            dropIn(2);
        } else if (!runningPostionsOfComputer.contains(0) && runningPostionsOfComputer.contains(1) && runningPostionsOfComputer.contains(2) && !runningPostions.contains(0)) {
            dropIn(0);
        } else if (runningPostionsOfComputer.contains(2) && !runningPostionsOfComputer.contains(5) && runningPostionsOfComputer.contains(8) && !runningPostions.contains(5)) {
            dropIn(5);
        } else if (runningPostionsOfComputer.contains(2) && runningPostionsOfComputer.contains(5) && !runningPostionsOfComputer.contains(8) && !runningPostions.contains(8)) {
            dropIn(8);
        } else if (!runningPostionsOfComputer.contains(2) && runningPostionsOfComputer.contains(5) && runningPostionsOfComputer.contains(8) && !runningPostions.contains(2)) {
            dropIn(2);
        } else if (runningPostionsOfComputer.contains(8) && !runningPostionsOfComputer.contains(7) && runningPostionsOfComputer.contains(6) && !runningPostions.contains(7)) {
            dropIn(7);
        } else if (runningPostionsOfComputer.contains(8) && runningPostionsOfComputer.contains(7) && !runningPostionsOfComputer.contains(6) && !runningPostions.contains(6)) {
            dropIn(6);
        } else if (!runningPostionsOfComputer.contains(8) && runningPostionsOfComputer.contains(7) && runningPostionsOfComputer.contains(6) && !runningPostions.contains(8)) {
            dropIn(8);
        } else if (runningPostionsOfComputer.contains(6) && !runningPostionsOfComputer.contains(3) && runningPostionsOfComputer.contains(0) && !runningPostions.contains(3)) {
            dropIn(3);
        } else if (runningPostionsOfComputer.contains(6) && runningPostionsOfComputer.contains(3) && !runningPostionsOfComputer.contains(0) && !runningPostions.contains(0)) {
            dropIn(0);
        } else if (!runningPostionsOfComputer.contains(6) && runningPostionsOfComputer.contains(3) && runningPostionsOfComputer.contains(0) && !runningPostions.contains(6)) {
            dropIn(6);
        } else if (runningPostionsOfComputer.contains(2) && !runningPostionsOfComputer.contains(4) && runningPostionsOfComputer.contains(6) && !runningPostions.contains(4)) {
            dropIn(4);
        } else if (runningPostionsOfComputer.contains(2) && runningPostionsOfComputer.contains(4) && !runningPostionsOfComputer.contains(6) && !runningPostions.contains(6)) {
            dropIn(6);
        } else if (!runningPostionsOfComputer.contains(2) && runningPostionsOfComputer.contains(4) && runningPostionsOfComputer.contains(6) && !runningPostions.contains(2)) {
            dropIn(2);
        } else if (runningPostionsOfComputer.contains(0) && !runningPostionsOfComputer.contains(4) && runningPostionsOfComputer.contains(8) && !runningPostions.contains(4)) {
            dropIn(4);
        } else if (runningPostionsOfComputer.contains(0) && runningPostionsOfComputer.contains(4) && !runningPostionsOfComputer.contains(8) && !runningPostions.contains(8)) {
            dropIn(8);
        } else if (!runningPostions.contains(0) && runningPostionsOfComputer.contains(4) && runningPostionsOfComputer.contains(8) && !runningPostions.contains(0)) {
            dropIn(0);
        } else if (runningPostionsOfComputer.contains(3) && !runningPostionsOfComputer.contains(4) && runningPostionsOfComputer.contains(5) && !runningPostions.contains(4)) {
            dropIn(4);
        } else if (runningPostionsOfComputer.contains(3) && runningPostionsOfComputer.contains(4) && !runningPostionsOfComputer.contains(5) && !runningPostions.contains(5)) {
            dropIn(5);
        } else if (!runningPostions.contains(3) && runningPostionsOfComputer.contains(4) && runningPostionsOfComputer.contains(5) && !runningPostions.contains(3)) {
            dropIn(3);
        } else if (runningPostionsOfComputer.contains(1) && !runningPostionsOfComputer.contains(4) && runningPostionsOfComputer.contains(7) && !runningPostions.contains(4)) {
            dropIn(4);
        } else if (runningPostionsOfComputer.contains(1) && runningPostionsOfComputer.contains(4) && !runningPostionsOfComputer.contains(7) && !runningPostions.contains(7)) {
            dropIn(7);
        } else if (!runningPostions.contains(1) && runningPostionsOfComputer.contains(4) && runningPostionsOfComputer.contains(7) && !runningPostions.contains(1)) {
            dropIn(1);
        }

        // A.E for Stop

        if (runningPostionsOfPlayer.contains(0) && !runningPostionsOfPlayer.contains(1) && runningPostionsOfPlayer.contains(2) && !runningPostions.contains(1)) {
            dropIn(1);
        } else if (runningPostionsOfPlayer.contains(0) && runningPostionsOfPlayer.contains(1) && !runningPostionsOfPlayer.contains(2) && !runningPostions.contains(2)) {
            dropIn(2);
        } else if (!runningPostionsOfPlayer.contains(0) && runningPostionsOfPlayer.contains(1) && runningPostionsOfPlayer.contains(2) && !runningPostions.contains(0)) {
            dropIn(0);
        } else if (runningPostionsOfPlayer.contains(2) && !runningPostionsOfPlayer.contains(5) && runningPostionsOfPlayer.contains(8) && !runningPostions.contains(5)) {
            dropIn(5);
        } else if (runningPostionsOfPlayer.contains(2) && runningPostionsOfPlayer.contains(5) && !runningPostionsOfPlayer.contains(8) && !runningPostions.contains(8)) {
            dropIn(8);
        } else if (!runningPostionsOfPlayer.contains(2) && runningPostionsOfPlayer.contains(5) && runningPostionsOfPlayer.contains(8) && !runningPostions.contains(2)) {
            dropIn(2);
        } else if (runningPostionsOfPlayer.contains(8) && !runningPostionsOfPlayer.contains(7) && runningPostionsOfPlayer.contains(6) && !runningPostions.contains(7)) {
            dropIn(7);
        } else if (runningPostionsOfPlayer.contains(8) && runningPostionsOfPlayer.contains(7) && !runningPostionsOfPlayer.contains(6) && !runningPostions.contains(6)) {
            dropIn(6);
        } else if (!runningPostionsOfPlayer.contains(8) && runningPostionsOfPlayer.contains(7) && runningPostionsOfPlayer.contains(6) && !runningPostions.contains(8)) {
            dropIn(8);
        } else if (runningPostionsOfPlayer.contains(6) && !runningPostionsOfPlayer.contains(3) && runningPostionsOfPlayer.contains(0) && !runningPostions.contains(3)) {
            dropIn(3);
        } else if (runningPostionsOfPlayer.contains(6) && runningPostionsOfPlayer.contains(3) && !runningPostionsOfPlayer.contains(0) && !runningPostions.contains(0)) {
            dropIn(0);
        } else if (!runningPostionsOfPlayer.contains(6) && runningPostionsOfPlayer.contains(3) && runningPostionsOfPlayer.contains(0) && !runningPostions.contains(6)) {
            dropIn(6);
        } else if (runningPostionsOfPlayer.contains(2) && !runningPostionsOfPlayer.contains(4) && runningPostionsOfPlayer.contains(6) && !runningPostions.contains(4)) {
            dropIn(4);
        } else if (runningPostionsOfPlayer.contains(2) && runningPostionsOfPlayer.contains(4) && !runningPostionsOfPlayer.contains(6) && !runningPostions.contains(6)) {
            dropIn(6);
        } else if (!runningPostionsOfPlayer.contains(2) && runningPostionsOfPlayer.contains(4) && runningPostionsOfPlayer.contains(6) && !runningPostions.contains(2)) {
            dropIn(2);
        } else if (runningPostionsOfPlayer.contains(0) && !runningPostionsOfPlayer.contains(4) && runningPostionsOfPlayer.contains(8) && !runningPostions.contains(4)) {
            dropIn(4);
        } else if (runningPostionsOfPlayer.contains(0) && runningPostionsOfPlayer.contains(4) && !runningPostionsOfPlayer.contains(8) && !runningPostions.contains(8)) {
            dropIn(8);
        } else if (!runningPostions.contains(0) && runningPostionsOfPlayer.contains(4) && runningPostionsOfPlayer.contains(8) && !runningPostions.contains(0)) {
            dropIn(0);
        } else if (runningPostionsOfPlayer.contains(3) && !runningPostionsOfPlayer.contains(4) && runningPostionsOfPlayer.contains(5) && !runningPostions.contains(4)) {
            dropIn(4);
        } else if (runningPostionsOfPlayer.contains(3) && runningPostionsOfPlayer.contains(4) && !runningPostionsOfPlayer.contains(5) && !runningPostions.contains(5)) {
            dropIn(5);
        } else if (!runningPostions.contains(3) && runningPostionsOfPlayer.contains(4) && runningPostionsOfPlayer.contains(5) && !runningPostions.contains(3)) {
            dropIn(3);
        } else if (runningPostionsOfPlayer.contains(1) && !runningPostionsOfPlayer.contains(4) && runningPostionsOfPlayer.contains(7) && !runningPostions.contains(4)) {
            dropIn(4);
        } else if (runningPostionsOfPlayer.contains(1) && runningPostionsOfPlayer.contains(4) && !runningPostionsOfPlayer.contains(7) && !runningPostions.contains(7)) {
            dropIn(7);
        } else if (!runningPostions.contains(1) && runningPostionsOfPlayer.contains(4) && runningPostionsOfPlayer.contains(7) && !runningPostions.contains(1)) {
            dropIn(1);
        }else if (runningPostionsOfPlayer.size() == 1 && (runningPostionsOfPlayer.contains(0) || runningPostionsOfPlayer.contains(2) || runningPostionsOfPlayer.contains(6) || runningPostionsOfPlayer.contains(8))) {
            dropIn(4);     // for first to run in center
        }else if (runningPostionsOfPlayer.size() == 1 && runningPostionsOfPlayer.contains(4)) {
            Random random = new Random();
            int randomNo = random.nextInt(8 - 0 + 1) + 0;
            while (randomNo==1 || randomNo == 5 || randomNo == 7  || randomNo == 3  || randomNo == 4) {
                randomNo = random.nextInt(8 - 0 + 1) + 0;
            }
            dropIn(randomNo)  ;        // if player run on middle then i have to run on 0,2,6,8
        }
        else if (runningPostionsOfPlayer.size() == 2 && (  (runningPostionsOfPlayer.get(0)==0 && runningPostionsOfPlayer.get(1)==2) || (runningPostionsOfPlayer.get(0)==2 && runningPostionsOfPlayer.get(1)==0 ) || (runningPostionsOfPlayer.get(0)==2 && runningPostionsOfPlayer.get(1)==8 )   ||  (runningPostionsOfPlayer.get(0)==8 && runningPostionsOfPlayer.get(1)==2 )   || (runningPostionsOfPlayer.get(0)==8 && runningPostionsOfPlayer.get(1)==6 )  || (runningPostionsOfPlayer.get(0)==6 && runningPostionsOfPlayer.get(1)==8 )  || (runningPostionsOfPlayer.get(0)==6 && runningPostionsOfPlayer.get(1)==0 )  ||  (runningPostionsOfPlayer.get(0)==0 && runningPostionsOfPlayer.get(1)==6 ) ||  (runningPostionsOfPlayer.get(0)==6 && runningPostionsOfPlayer.get(1)==2 )  ||  (runningPostionsOfPlayer.get(0)==2 && runningPostionsOfPlayer.get(1)==6 )  ||  (runningPostionsOfPlayer.get(0)==0 && runningPostionsOfPlayer.get(1)==8 ) ||  (runningPostionsOfPlayer.get(0)==8 && runningPostionsOfPlayer.get(1)==0 ) )) {
            Random random = new Random();
            int randomNo = random.nextInt(8 - 0 + 1) + 0;
            while (randomNo==0 || randomNo == 2 || randomNo == 8  || randomNo == 6  || randomNo == 4) {
                randomNo = random.nextInt(8 - 0 + 1) + 0;
            }
            dropIn(randomNo)  ;      // for running middle column
        } else {
            Random random = new Random();
            int randomNo = random.nextInt(8 - 0 + 1) + 0;
            while (runningPostions.contains(randomNo)) {
                randomNo = random.nextInt(8 - 0 + 1) + 0;
            }
            dropIn(randomNo);
        }
    }

    public void dropIn(int tabbedNo) {
//        int tabbedNo = Integer.parseInt(imageView.getTag().toString());
        if (gamecontinue) {
            if (playedList[tabbedNo] == 2) {
              /*  AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0);*/
                count = count + 1;
                playedList[tabbedNo] = activiePlayer;
                imageView.animate().setDuration(300);
                if (activiePlayer == 0) {
                    setZero(tabbedNo);
                    activiePlayer = 1;
                    mediaPlayer = MediaPlayer.create(this, R.raw.chin_up);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.reset();
                            mp.release();
                            mediaPlayer = null;
                        }
                    });
                    mediaPlayer.start();

                } else {
                    setCross(tabbedNo);
                    activiePlayer = 0;
                    mediaPlayer = MediaPlayer.create(this, R.raw.chin_up);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.reset();
                            mp.release();
                            mediaPlayer = null;
                        }
                    });
                    mediaPlayer.start();
                }

                for (int[] winningPos : winningPosition) {
                    if (playedList[winningPos[0]] == playedList[winningPos[1]] && playedList[winningPos[1]] == playedList[winningPos[2]] && playedList[winningPos[0]] != 2) {
                        if (playedList[winningPos[0]] == 0) {
//                            Toast.makeText(MainActivity.this, "0 wins", Toast.LENGTH_LONG).show();
                            circleWins = circleWins + 1;
                            tvCirleWins.setText(circleWins + " Wins");

                        } else {
//                            Toast.makeText(MainActivity.this, "x wins", Toast.LENGTH_LONG).show();
                            xWins = xWins + 1;
                            tvXwins.setText(xWins + " Wins");

                        }

                        int[] pos012 = {0, 1, 2};
                        int[] pos345 = {3, 4, 5};
                        int[] pos678 = {6, 7, 8};

                        int[] pos036 = {0, 3, 6};
                        int[] pos147 = {1, 4, 7};
                        int[] pos258 = {2, 5, 8};

                        int[] pos048 = {0, 4, 8};
                        int[] pos246 = {2, 4, 6};

                        if (Arrays.equals(winningPos, pos012)) {
                            runningPostions.clear();
                            runningPostionsOfPlayer.clear();
                            runningPostionsOfComputer.clear();
                            ll012.setBackgroundResource(R.drawable.ic_line);
                            gamecontinue = false;
                            btPlayAgainSecond.setVisibility(View.VISIBLE);
                            mediaPlayer = MediaPlayer.create(this, R.raw.definite);
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mp.reset();
                                    mp.release();
                                    mediaPlayer = null;
                                }
                            });
                            mediaPlayer.start();
                            playAds();
                            return;
                        } else if (Arrays.equals(winningPos, pos345)) {
                            runningPostions.clear();
                            runningPostionsOfPlayer.clear();
                            runningPostionsOfComputer.clear();
                            gamecontinue = false;
                            ll345.setBackgroundResource(R.drawable.ic_line);
                            btPlayAgainSecond.setVisibility(View.VISIBLE);
                            mediaPlayer = MediaPlayer.create(this, R.raw.definite);
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mp.reset();
                                    mp.release();
                                    mediaPlayer = null;
                                }
                            });
                            mediaPlayer.start();
                            playAds();
                            return;
                        } else if (Arrays.equals(winningPos, pos678)) {
                            runningPostions.clear();
                            runningPostionsOfPlayer.clear();
                            runningPostionsOfComputer.clear();
                            gamecontinue = false;
                            ll678.setBackgroundResource(R.drawable.ic_line);
                            btPlayAgainSecond.setVisibility(View.VISIBLE);
                            mediaPlayer = MediaPlayer.create(this, R.raw.definite);
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mp.reset();
                                    mp.release();
                                    mediaPlayer = null;
                                }
                            });
                            mediaPlayer.start();
                            playAds();
                            return;
                        } else if (Arrays.equals(winningPos, pos036)) {
                            runningPostions.clear();
                            runningPostionsOfPlayer.clear();
                            runningPostionsOfComputer.clear();
                            gamecontinue = false;
                            fL036.setVisibility(View.VISIBLE);
                            btPlayAgainSecond.setVisibility(View.VISIBLE);
                            mediaPlayer = MediaPlayer.create(this, R.raw.definite);
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mp.reset();
                                    mp.release();
                                    mediaPlayer = null;
                                }
                            });
                            mediaPlayer.start();
                            playAds();
                            return;
                        } else if (Arrays.equals(winningPos, pos147)) {
                            runningPostions.clear();
                            runningPostionsOfPlayer.clear();
                            runningPostionsOfComputer.clear();
                            gamecontinue = false;
                            fl147.setVisibility(View.VISIBLE);
                            btPlayAgainSecond.setVisibility(View.VISIBLE);
                            mediaPlayer = MediaPlayer.create(this, R.raw.definite);
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mp.reset();
                                    mp.release();
                                    mediaPlayer = null;
                                }
                            });
                            mediaPlayer.start();
                            return;
                        } else if (Arrays.equals(winningPos, pos258)) {
                            runningPostions.clear();
                            runningPostionsOfPlayer.clear();
                            runningPostionsOfComputer.clear();
                            gamecontinue = false;
                            fl258.setVisibility(View.VISIBLE);
                            btPlayAgainSecond.setVisibility(View.VISIBLE);
                            mediaPlayer = MediaPlayer.create(this, R.raw.definite);
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mp.reset();
                                    mp.release();
                                    mediaPlayer = null;
                                }
                            });
                            mediaPlayer.start();
                            playAds();
                            return;
                        } else if (Arrays.equals(winningPos, pos048)) {
                            runningPostions.clear();
                            runningPostionsOfPlayer.clear();
                            runningPostionsOfComputer.clear();
                            gamecontinue = false;
                            fl048.setVisibility(View.VISIBLE);
                            btPlayAgainSecond.setVisibility(View.VISIBLE);
                            mediaPlayer = MediaPlayer.create(this, R.raw.definite);
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mp.reset();
                                    mp.release();
                                    mediaPlayer = null;
                                }
                            });
                            mediaPlayer.start();
                            playAds();
                            return;

                        } else if (Arrays.equals(winningPos, pos246)) {
                            runningPostions.clear();
                            runningPostionsOfPlayer.clear();
                            runningPostionsOfComputer.clear();
                            gamecontinue = false;
                            fl246.setVisibility(View.VISIBLE);
                            btPlayAgainSecond.setVisibility(View.VISIBLE);
                            mediaPlayer = MediaPlayer.create(this, R.raw.definite);
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mp.reset();
                                    mp.release();
                                    mediaPlayer = null;
                                }
                            });
                            mediaPlayer.start();
                            playAds();
                            return;
                        }

                    }

                }
               /* if(mPlayer.isPlaying()){
                    mPlayer.stop();
                }
*/
                if ((count == 9) && gamecontinue) {
                    runningPostions.clear();
                    llPlayAgain.setVisibility(View.VISIBLE);
                    count = 0;
                    gridLayout.setVisibility(View.GONE);
                    ll012.setBackgroundResource(0);
                    ll345.setBackgroundResource(0);
                    ll678.setBackgroundResource(0);

                    fL036.setVisibility(View.GONE);
                    fl147.setVisibility(View.GONE);
                    fl258.setVisibility(View.GONE);
                    fl048.setVisibility(View.GONE);
                    fl246.setVisibility(View.GONE);
                    btPlayAgainSecond.setVisibility(View.INVISIBLE);
                    playAds();
                    return;
                }

            }

        }
    }


    private void setZero(int tabbedNo) {
        runningPostions.add(tabbedNo);
        runningPostionsOfPlayer.add(tabbedNo);
        switch (optionChoose) {
            case 0:
                imageView.setImageResource(R.drawable.ic_circl);
                break;
            case 1:
                imageView.setImageResource(R.drawable.ic_batman);
                break;
            case 2:
                imageView.setImageResource(R.drawable.ic_apple_logo);
                break;
            case 3:
                imageView.setImageResource(R.drawable.ic_man_with_tie);
                break;
            case 4:
                imageView.setImageResource(R.drawable.ic_spider_man);
                break;
        }
    }

    private void setCross(int tabbedNo) {
        runningPostions.add(tabbedNo);
        runningPostionsOfComputer.add(tabbedNo);
        ImageView img = (ImageView) findViewById(R.id.rl_root).findViewWithTag(tabbedNo + "");
        disableEnableControls(true, rlRoot);
        switch (optionChoose) {
            case 0:
                img.setImageResource(R.drawable.ic_cross);
//                imageView.setImageResource(R.drawable.ic_cross);
                break;
            case 1:
                img.setImageResource(R.drawable.ic_superman);
                break;
            case 2:
                img.setImageResource(R.drawable.ic_android_logo);
                break;
            case 3:
                img.setImageResource(R.drawable.ic_business_woman);
                break;
            case 4:
                img.setImageResource(R.drawable.ic_iron_man);
                break;
        }
    }


    public void playAds() {

        if (gameCountUpto15 == 15) {
            if (mRewardedVideoAd.isLoaded()) {
                mRewardedVideoAd.show();
            }
            gameCount = 0;
            gameCountUpto15 = 0;
        } else {
            if (gameCount == 5) {
                displayInterstitial();
                gameCount = 0;

            }

        }
    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-6939649863727842/1140277115",
                new AdRequest.Builder().build());
    }

    @Override
    public void onRewardedVideoAdLoaded() {
//        Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRewardedVideoAdOpened() {
//        Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRewardedVideoStarted() {
//        Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRewardedVideoAdClosed() {
//        Toast.makeText(this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
//        Toast.makeText(this, "onRewarded! currency: " + rewardItem.getType() + "  amount: " +
//                rewardItem.getAmount(), Toast.LENGTH_SHORT).show();
        // Reward the user.
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
//        Toast.makeText(this, "onRewardedVideoAdLeftApplication",
//                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
//        Toast.makeText(this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRewardedVideoCompleted() {
//        Toast.makeText(this, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show();

    }

    private void disableEnableControls(boolean enable, ViewGroup vg) {
        for (int i = 0; i < vg.getChildCount(); i++) {
            View child = vg.getChildAt(i);
            child.setEnabled(enable);
            if (child instanceof ViewGroup) {
                disableEnableControls(enable, (ViewGroup) child);
            }
        }
    }
}


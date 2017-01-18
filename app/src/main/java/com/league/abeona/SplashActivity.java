package com.league.abeona;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.league.abeona.util.AppPreferenceManager;
import com.league.abeona.util.Constants;

public class SplashActivity extends Activity {

    private boolean bFirstLunch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initVariable();

        Runnable r = new Runnable() {
            @Override
            public void run() {
                goProperPage();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(r, 2000);

    }

    public void initVariable()
    {
        AppPreferenceManager.initializePreferenceManager(SplashActivity.this);
        bFirstLunch = AppPreferenceManager.getBoolean(Constants.kFirstLunch, false);
    }

    public void goProperPage()
    {
        if(bFirstLunch)
        {
            Intent mIntent = new Intent(SplashActivity.this, StartupActivity.class);
            startActivity(mIntent);
            finish();
        }
        else {
            Intent mIntent = new Intent(SplashActivity.this, IntroductionActivity.class);
            startActivity(mIntent);
            finish();
        }
    }
}

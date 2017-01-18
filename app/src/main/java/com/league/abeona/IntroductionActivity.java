package com.league.abeona;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.league.abeona.fragments.ScreenSlidePageFragment;
import com.league.abeona.util.AppPreferenceManager;
import com.league.abeona.util.Constants;

public class IntroductionActivity extends FragmentActivity {

    private static final int NUM_PAGES = 3;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        btnNext = (Button) findViewById(R.id.btn_introduction_next);


        mPager = (ViewPager) findViewById(R.id.vp_introduction_page);
        mPagerAdapter = new ScreenSlidePagerAdater(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(0);

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mPager.getCurrentItem() < 2)
                {
                    mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                }
                else
                {
                    Intent mIntent = new Intent(IntroductionActivity.this, StartupActivity.class);
                    startActivity(mIntent);
                    finish();
                }
            }
        });

    }

    private class ScreenSlidePagerAdater extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdater(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ScreenSlidePageFragment fragIntro = new ScreenSlidePageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("page_number", position);
            fragIntro.setArguments(bundle);
            return fragIntro;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }


}

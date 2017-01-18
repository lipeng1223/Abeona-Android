package com.league.abeona;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.league.abeona.fragments.ExploreListFragment;
import com.league.abeona.fragments.ExploreMapFragment;
import com.league.abeona.fragments.PlanJourneyFragment;


public class JourneyActivity extends Activity implements View.OnClickListener{

    //UI Variables
    private Button btnPlan;
    private Button btnExplore;
    private Button btnHelp;

    private Button btnBack;
    private ImageButton btnMaplist;

    //Variable
    private int nCurTab;
    private boolean isMapShowing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);
        initVariable();
        initUI();

        showProperTab(nCurTab);
    }

    public void initVariable()
    {
        nCurTab = getIntent().getIntExtra("tab", 0);
        isMapShowing = false;

    }

    public void initUI()
    {
        btnPlan = (Button) findViewById(R.id.btn_journey_plan);
        btnExplore = (Button) findViewById(R.id.btn_journey_explore);
        btnHelp = (Button) findViewById(R.id.btn_journey_help);
        btnBack = (Button) findViewById(R.id.btn_journey_back);
        btnMaplist = (ImageButton) findViewById(R.id.btn_journey_maplist);

        btnBack.setOnClickListener(this);
        btnPlan.setOnClickListener(this);
        btnExplore.setOnClickListener(this);
        btnHelp.setOnClickListener(this);
        btnMaplist.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btn_journey_plan:
                nCurTab = 0;
                showProperTab(0);

                break;
            case R.id.btn_journey_explore:
                nCurTab = 1;
                showProperTab(1);
                break;
            case R.id.btn_journey_help:
                nCurTab = 2;
                showProperTab(2);

                break;
            case R.id.btn_journey_back:
                finish();
                break;
            case R.id.btn_journey_maplist:
                if(!isMapShowing) {
                    FragmentManager fragMgr = getFragmentManager();
                    FragmentTransaction fragTrans = fragMgr.beginTransaction();
                    Fragment mFrag = new ExploreMapFragment();
                    fragTrans.replace(R.id.lay_home_container, mFrag);
                    fragTrans.commit();
                    btnMaplist.setImageDrawable(getResources().getDrawable(R.drawable.icon_list));
                    isMapShowing = true;
                }
                else
                {
                    FragmentManager fragMgr = getFragmentManager();
                    FragmentTransaction fragTrans = fragMgr.beginTransaction();
                    Fragment mFrag = new ExploreListFragment();
                    fragTrans.replace(R.id.lay_home_container, mFrag);
                    fragTrans.commit();
                    btnMaplist.setImageDrawable(getResources().getDrawable(R.drawable.icon_map));
                    isMapShowing = false;
                }
                break;
        }
    }

    public void showProperTab(int nIndex)
    {
        FragmentManager fragMgr = getFragmentManager();
        FragmentTransaction fragTrans = fragMgr.beginTransaction();
        Fragment mFrag = new ExploreListFragment();

        switch(nIndex)
        {
            case 0:
                mFrag = new PlanJourneyFragment();
                break;
            case 1:
                if(!isMapShowing) {
                    mFrag = new ExploreListFragment();
                }
                else {
                    mFrag = new ExploreMapFragment();
                }
                break;
            case 2:
                break;
        }

        fragTrans.replace(R.id.lay_home_container, mFrag);
        fragTrans.commit();
        updateRightButton();
    }

    public void updateRightButton()
    {
        if(nCurTab != 1)
        {
            btnMaplist.setVisibility(View.GONE);
        }
        else {
            btnMaplist.setVisibility(View.VISIBLE);
        }
    }

}

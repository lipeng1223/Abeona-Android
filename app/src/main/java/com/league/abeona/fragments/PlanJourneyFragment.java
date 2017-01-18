package com.league.abeona.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.league.abeona.JourneyDetailActivity;
import com.league.abeona.R;

/**
 * Created by Jimmy on 1/3/2017.
 */

public class PlanJourneyFragment extends Fragment implements View.OnClickListener{

    private Button btnPlan;
    private Button btnFanzone;
    private Button btnFinal;
    private Button btnElse;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan_journey, null);
        initUI(view);
        return view;
    }

    public void initUI(View v)
    {
        btnPlan = (Button) v.findViewById(R.id.btn_planjourney_plan);
        btnFanzone = (Button) v.findViewById(R.id.btn_planjourney_fanzone);
        btnFinal = (Button) v.findViewById(R.id.btn_planjourney_final);
        btnElse = (Button) v.findViewById(R.id.btn_planjourney_else);

        btnPlan.setOnClickListener(this);
        btnFanzone.setOnClickListener(this);
        btnFinal.setOnClickListener(this);
        btnElse.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btn_planjourney_plan:
                goPlanPage();
                break;
            case R.id.btn_planjourney_fanzone:
                break;
            case R.id.btn_planjourney_final:
                break;
            case R.id.btn_planjourney_else:
                break;

        }
    }

    //Navigation
    public void goPlanPage()
    {
        Intent mIntent = new Intent(getActivity(), JourneyDetailActivity.class);
        startActivity(mIntent);
    }
}

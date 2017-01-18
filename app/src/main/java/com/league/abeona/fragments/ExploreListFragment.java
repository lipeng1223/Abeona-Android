package com.league.abeona.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.league.abeona.ExploreDetailActivity;
import com.league.abeona.R;
import com.league.abeona.adapter.ExploreAdapter;
import com.league.abeona.model.Cardiff;
import com.league.abeona.util.AppData;

import java.util.ArrayList;

/**
 * Created by Jimmy on 1/3/2017.
 */

public class ExploreListFragment extends Fragment implements View.OnClickListener{

    private TextView txtAll;
    private TextView txtDrink;
    private TextView txtSee;
    private TextView txtShop;

    private ListView lstCardiff;

    //Variable
    private ArrayList<Cardiff> arrItem;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore_list, null);
        initVariable();
        initUI(view);
        updateList();
        return view;
    }

    public void initVariable()
    {
        arrItem = new ArrayList<Cardiff>();
        for(int i = 0; i < AppData.arrCardiff.size(); i++)
        {
            arrItem.add(AppData.arrCardiff.get(i));
        }
    }


    public void initUI(View v)
    {
        txtAll = (TextView) v.findViewById(R.id.txt_explorelist_category_all);
        txtDrink = (TextView) v.findViewById(R.id.txt_explorelist_category_eat);
        txtSee = (TextView) v.findViewById(R.id.txt_explorelist_category_see);
        txtShop = (TextView) v.findViewById(R.id.txt_explorelist_category_shop);

        txtAll.setOnClickListener(this);
        txtDrink.setOnClickListener(this);
        txtSee.setOnClickListener(this);
        txtShop.setOnClickListener(this);
        lstCardiff = (ListView) v.findViewById(R.id.lst_explorelist_cardiff);
        lstCardiff.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent mIntent = new Intent(getActivity(), ExploreDetailActivity.class);
                mIntent.putExtra("position", i);
                startActivity(mIntent);
            }
        });
    }

    public void updateList()
    {

        ExploreAdapter adpExplore = new ExploreAdapter(getActivity(), R.layout.row_cardiff, arrItem);
        lstCardiff.setAdapter(adpExplore);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.txt_explorelist_category_all:
                filter("all");
                break;
            case R.id.txt_explorelist_category_eat:
                filter("Eat");
                break;
            case R.id.txt_explorelist_category_see:
                filter("See");
                break;
            case R.id.txt_explorelist_category_shop:
                filter("Shop");
                break;
        }
    }

    public void filter(String key)
    {
        arrItem.clear();
        for(int i = 0; i < AppData.arrCardiff.size(); i++)
        {
            if(key.contains("all"))
            {
                arrItem.add(AppData.arrCardiff.get(i));
            }
            else {
                if(AppData.arrCardiff.get(i).getType().contains(key))
                {
                    arrItem.add(AppData.arrCardiff.get(i));
                }
            }
        }

        updateList();
    }
}

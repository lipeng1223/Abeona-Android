package com.league.abeona.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.league.abeona.R;

/**
 * Created by Jimmy on 1/2/2017.
 */

public class ScreenSlidePageFragment extends Fragment {
    private int nPageNum;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        nPageNum = bundle.getInt("page_number");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
        switch(nPageNum)
        {
            case 0:
                rootView = (ViewGroup) inflater.inflate(R.layout.fragment_journey, container, false);
                break;
            case 1:
                rootView = (ViewGroup) inflater.inflate(R.layout.fragment_supporting, container, false);
                break;
            case 2:
                rootView = (ViewGroup) inflater.inflate(R.layout.fragment_location, container, false);
                break;
        }

        return rootView;
    }
}

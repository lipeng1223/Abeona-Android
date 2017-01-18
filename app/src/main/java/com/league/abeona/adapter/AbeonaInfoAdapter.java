package com.league.abeona.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.league.abeona.R;

/**
 * Created by Jimmy on 1/4/2017.
 */

public class AbeonaInfoAdapter implements GoogleMap.InfoWindowAdapter {

    private View view;
    private LayoutInflater inflater;
    public AbeonaInfoAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.pin_info_window, null);
    }


    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}

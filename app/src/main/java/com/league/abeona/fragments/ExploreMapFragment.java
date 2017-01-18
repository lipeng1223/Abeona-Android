package com.league.abeona.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.league.abeona.ExploreDetailActivity;
import com.league.abeona.R;
import com.league.abeona.util.AppData;
import com.league.abeona.util.StringUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Jimmy on 1/4/2017.
 */

public class ExploreMapFragment extends Fragment  implements OnMapReadyCallback{

    private MapView mapView;
    private GoogleMap googleMap;

    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    boolean not_first_time_showing_info_window = false;

    private ArrayList<Marker> arrShownMarker = new ArrayList<Marker>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.img_empty)
                .showImageForEmptyUri(R.drawable.img_empty)
                .cacheInMemory()
                .cacheOnDisc()
                //  .displayer(new RoundedBitmapDisplayer(20))
                .build();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore_map, null);
        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        MapsInitializer.initialize(getActivity().getApplicationContext());
        mapView.getMapAsync(this);
        return view;
    }


    @Override
    public void onMapReady(GoogleMap map) {
        this.googleMap = map;
        setCardiffMarks();
    }

    public void setCardiffMarks()
    {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(int i = 0; i < AppData.arrCardiff.size(); i++)
        {
            builder.include(new LatLng(AppData.arrCardiff.get(i).getLatitude(), AppData.arrCardiff.get(i).getLongitude()));
            googleMap.addMarker(new MarkerOptions().position(new LatLng(AppData.arrCardiff.get(i).getLatitude(), AppData.arrCardiff.get(i).getLongitude())).title("Marker Title").snippet("Marker Description").icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_pin_football)));
        }

        LatLngBounds bounds = builder.build();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 15));

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                View v= getActivity().getLayoutInflater().inflate(R.layout.pin_info_window, null);
                ImageView imgImage = (ImageView) v.findViewById(R.id.pin_info_image);
                TextView txtTitle = (TextView) v.findViewById(R.id.pin_info_title);
                for(int i = 0; i < AppData.arrCardiff.size(); i++)
                {
                    if((marker.getPosition().latitude == AppData.arrCardiff.get(i).getLatitude()) && (marker.getPosition().longitude == AppData.arrCardiff.get(i).getLongitude()))
                    {
                        if(!arrShownMarker.contains(marker)) {
                            arrShownMarker.add(marker);
                            Picasso.with(getActivity()).load(AppData.arrCardiff.get(i).getImage()).into(imgImage, new InfoWindowRefresher(marker));
                        }
                        else{
                            Picasso.with(getActivity()).load(AppData.arrCardiff.get(i).getImage()).into(imgImage);
                        }
                        txtTitle.setText(StringUtil.convertString(AppData.arrCardiff.get(i).getTitle()));
                    }
                }

                return v;
            }

            @Override
            public View getInfoContents(Marker marker) {

               /* ContextThemeWrapper cw = new ContextThemeWrapper(getActivity().getApplicationContext(), R.style.Transparent);
                LayoutInflater inflater = (LayoutInflater) cw.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v= inflater.inflate(R.layout.pin_info_window, null);

                ImageView imgImage = (ImageView) v.findViewById(R.id.pin_info_image);
                TextView txtTitle = (TextView) v.findViewById(R.id.pin_info_title);

                for(int i = 0; i < AppData.arrCardiff.size(); i++)
                {
                    if((marker.getPosition().latitude == AppData.arrCardiff.get(i).getLatitude()) && (marker.getPosition().longitude == AppData.arrCardiff.get(i).getLongitude()))
                    {
                        if(!arrShownMarker.contains(marker)) {
                            arrShownMarker.add(marker);
                            Picasso.with(getActivity()).load(AppData.arrCardiff.get(i).getImage()).into(imgImage, new InfoWindowRefresher(marker));

                        }
                        else{
                            Picasso.with(getActivity()).load(AppData.arrCardiff.get(i).getImage()).into(imgImage);
                        }
                        txtTitle.setText(StringUtil.convertString(AppData.arrCardiff.get(i).getTitle()));
                    }
                }*/

                return null;
            }
        });

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                for(int i = 0; i < AppData.arrCardiff.size(); i++)
                {
                    if((marker.getPosition().latitude == AppData.arrCardiff.get(i).getLatitude()) && (marker.getPosition().longitude == AppData.arrCardiff.get(i).getLongitude()))
                    {
                        Intent mIntent = new Intent(getActivity(), ExploreDetailActivity.class);
                        mIntent.putExtra("position", i);
                        startActivity(mIntent);
                    }
                }
            }
        });

    }

    private class InfoWindowRefresher implements Callback {

        private Marker markerToRefresh;

        private InfoWindowRefresher(Marker markerToRefresh) {
            this.markerToRefresh = markerToRefresh;
        }

        @Override
        public void onSuccess() {
            markerToRefresh.showInfoWindow();
        }

        @Override
        public void onError() {
            Log.e("Error", "Error on refresh");
        }
    }
}

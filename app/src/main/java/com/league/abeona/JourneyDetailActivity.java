package com.league.abeona;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.league.abeona.model.Cardiff;
import com.league.abeona.util.Constants;
import com.league.abeona.view.MessageDialog;
import com.league.abeona.view.ProgressDlg;

import java.util.List;

public class JourneyDetailActivity extends Activity implements View.OnClickListener, LocationListener{

    //Constant
    LatLng lPRINCIPAL_STADIUM = new LatLng(51.4781849, -3.1826133);

    //51.7480702,-2.2229329

    private TextView txtDestName;
    private TextView txtDestMatch;
    private TextView txtDestDate;
    private TextView txtDestTime;

    private Spinner spnArrive;
    private Spinner spnOption;

    private Button btnRoute;
    private Button btnBack;

    //Variable
    private static final long LOCATION_REFRESH_DISTANCE = 100;
    private static final long LOCATION_REFRESH_TIME = 1000 * 60 * 1;

    private LocationManager mLocationManager;
    private Location curLocation;

    private Cardiff mCardiff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_detail);
        initVaraible();
        initUI();
    }

    public void initVaraible()
    {
        curLocation = null;

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (checkLocationPermission()) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, this);
            curLocation = getLastKnownLocation();
            if (curLocation != null) {

            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1001);
        }
    }

    public void initUI()
    {
        txtDestName = (TextView) findViewById(R.id.txt_journeydetail_destname);
        txtDestMatch = (TextView) findViewById(R.id.txt_journeydetail_destmatch);
        txtDestDate = (TextView) findViewById(R.id.txt_journeydetail_destdate);
        txtDestTime = (TextView) findViewById(R.id.txt_journeydetail_desttime);

        spnArrive = (Spinner) findViewById(R.id.spn_journeydetail_arrive);
        spnOption = (Spinner) findViewById(R.id.spn_journeydetail_option);

        btnRoute = (Button) findViewById(R.id.btn_journeydetail_getroute);
        btnBack = (Button) findViewById(R.id.btn_journeydetail_back);

        btnRoute.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btn_journeydetail_back:
                finish();
                break;
            case R.id.btn_journeydetail_getroute:
                getDirection();
                break;
        }
    }

    //Location Service
    public boolean checkLocationPermission() {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1001:
                final int numOfRequest = grantResults.length;
                final boolean isGranted = numOfRequest == 1 && PackageManager.PERMISSION_GRANTED == grantResults[numOfRequest - 1];
                if (isGranted) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, this);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1001);
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }


    //Web Service
    public void getDirection()
    {
        String strURL = Constants.API_GET_ROUTE + "origin=" + curLocation.getLatitude() + "," + curLocation.getLongitude() + "&destination=" + lPRINCIPAL_STADIUM.latitude + "," + lPRINCIPAL_STADIUM.longitude +
                "&key=" + getString(R.string.api_direction_key);

        ProgressDlg.showProcess(JourneyDetailActivity.this, "Please wait...");

        Ion.with(JourneyDetailActivity.this)
                .load(strURL)
                .setTimeout(20 * 1000)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject jsonObject) {

                        ProgressDlg.closeprocess(JourneyDetailActivity.this);

                        if(e != null)
                        {
                            MessageDialog.showAlertMsg(JourneyDetailActivity.this, getString(R.string.app_name), e.getLocalizedMessage());
                            return;
                        }

                        if(jsonObject.get("status").getAsString().equals("OK"))
                        {
                            Log.e("aaaa","Aaa");

                        }
                        else {
                            MessageDialog.showAlertMsg(JourneyDetailActivity.this, getString(R.string.app_name), jsonObject.get("status").getAsString());
                        }


                    }
                });
    }


    @Override
    public void onLocationChanged(Location location) {
        curLocation = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {
    }
}

package com.league.abeona;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.league.abeona.model.Cardiff;
import com.league.abeona.net.ZyGet;
import com.league.abeona.util.AppData;
import com.league.abeona.util.Constants;
import com.league.abeona.util.StringUtil;
import com.league.abeona.view.MessageDialog;
import com.league.abeona.view.ProgressDlg;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StartupActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnPlan;
    private TextView txtExplore;
    private TextView txtSeeAll;

    private ImageView imgCardiff1;
    private ImageView imgCardiff2;
    private TextView  txtTitle1;
    private TextView txtTitle2;
    private TextView txtType1;
    private TextView txtType2;

    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    private ZyGet mGet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        initUI();
        getCardiff();
    }

    public void initUI()
    {
        btnPlan = (Button) findViewById(R.id.btn_startup_plan);
        txtExplore = (TextView) findViewById(R.id.txt_startup_explore);
        txtSeeAll = (TextView) findViewById(R.id.txt_startup_seeall);

        btnPlan.setOnClickListener(this);
        txtExplore.setOnClickListener(this);
        txtSeeAll.setOnClickListener(this);

        imgCardiff1 = (ImageView) findViewById(R.id.img_startup_cardiff1);
        imgCardiff2 = (ImageView) findViewById(R.id.img_startup_cardiff2);
        txtTitle1 = (TextView) findViewById(R.id.txt_startup_cardiff1_title);
        txtTitle2 = (TextView) findViewById(R.id.txt_startup_cardiff2_title);
        txtType1 = (TextView) findViewById(R.id.txt_startup_cardiff1_type);
        txtType2 = (TextView) findViewById(R.id.txt_startup_cardiff2_type);
    }

    public void updateUI()
    {
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(StartupActivity.this));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.img_empty)
                .showImageForEmptyUri(R.drawable.img_empty)
                .cacheInMemory()
                .cacheOnDisc()
                //  .displayer(new RoundedBitmapDisplayer(20))
                .build();
        txtTitle1.setText(StringUtil.convertString(AppData.arrCardiff.get(0).getTitle()));
        txtType1.setText(StringUtil.convertString(AppData.arrCardiff.get(0).getType()));

        txtTitle2.setText(StringUtil.convertString(AppData.arrCardiff.get(1).getTitle()));
        txtType2.setText(StringUtil.convertString(AppData.arrCardiff.get(1).getType()));

        imageLoader.displayImage(AppData.arrCardiff.get(0).getImage(), imgCardiff1, options);
        imageLoader.displayImage(AppData.arrCardiff.get(1).getImage(), imgCardiff2, options);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btn_startup_plan:
                goJourneyPage();
                break;
            case R.id.txt_startup_explore:
                goCardiffPage();
                break;
            case R.id.txt_startup_seeall:
                goCardiffPage();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(StartupActivity.this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage("Are you sure to exit Abeona?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.show();
    }

    //Navigation
    public void goJourneyPage()
    {
        Intent mIntent = new Intent(StartupActivity.this, JourneyActivity.class);
        mIntent.putExtra("tab", 0);
        startActivity(mIntent);
    }

    public void goCardiffPage()
    {
        Intent mIntent = new Intent(StartupActivity.this, JourneyActivity.class);
        mIntent.putExtra("tab", 1);
        startActivity(mIntent);
    }

    //Web Service
    public void getCardiff() {
        ProgressDlg.showProcess(StartupActivity.this, "Please wait...");

        Ion.with(StartupActivity.this)
                .load("GET", Constants.API_GET_CARDIFF)
                .setTimeout(20 * 1000)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray jsonArray) {
                        ProgressDlg.closeprocess(StartupActivity.this);
                        if(e != null)
                        {
                            MessageDialog.showAlertMsg(StartupActivity.this, "Abeona", e.getLocalizedMessage());
                            return;
                        }
                        try {
                            JSONArray mResult = new JSONArray((String) jsonArray.toString());
                            for(int i = 0; i < mResult.length(); i++)
                            {
                                Cardiff mTemp = new Cardiff();
                                JSONObject mItem = (JSONObject) mResult.get(i);
                                if(mItem.has("id"))
                                {
                                    mTemp.setID(mItem.getInt("id"));
                                }

                                if(mItem.has("title"))
                                {
                                    mTemp.setTitle(mItem.getJSONObject("title").getString("rendered"));
                                }

                                if(mItem.has("content"))
                                {
                                    mTemp.setContent(mItem.getJSONObject("content").getString("rendered"));
                                }

                                if(mItem.has("attraction_image"))
                                {
                                    if(!mItem.get("attraction_image").toString().equals("null")) {
                                        mTemp.setImage(mItem.getJSONObject("attraction_image").getString("source_url"));
                                    }
                                }

                                if(mItem.has("acf"))
                                {
                                    if(mItem.getJSONObject("acf").has("address"))
                                    {
                                        mTemp.setAddress(mItem.getJSONObject("acf").getString("address"));
                                    }

                                    if(mItem.getJSONObject("acf").has("longitude"))
                                    {
                                        mTemp.setLongitude(Double.parseDouble(mItem.getJSONObject("acf").getString("longitude")));
                                    }

                                    if(mItem.getJSONObject("acf").has("latitude"))
                                    {
                                        mTemp.setLatitude(Double.parseDouble(mItem.getJSONObject("acf").getString("latitude")));
                                    }

                                    if(mItem.getJSONObject("acf").has("hours"))
                                    {
                                        mTemp.setHours(mItem.getJSONObject("acf").getString("hours"));
                                    }

                                    if(mItem.getJSONObject("acf").has("phone"))
                                    {
                                        mTemp.setPhone(mItem.getJSONObject("acf").getString("phone"));
                                    }

                                    if(mItem.getJSONObject("acf").has("attraction_type"))
                                    {
                                        mTemp.setType(mItem.getJSONObject("acf").getString("attraction_type"));
                                    }

                                    if(mItem.getJSONObject("acf").has("target_market"))
                                    {
                                        mTemp.setTargetMarket(mItem.getJSONObject("acf").getString("target_market"));
                                    }
                                }

                                AppData.arrCardiff.add(mTemp);

                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                        updateUI();
                    }
                });
    }
}

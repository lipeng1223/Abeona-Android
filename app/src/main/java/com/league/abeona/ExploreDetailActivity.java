package com.league.abeona;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.abeona.util.AppData;
import com.league.abeona.util.StringUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ExploreDetailActivity extends AppCompatActivity {

    private ImageView imgThumb;
    private TextView txtTitle;
    private TextView txtType;
    private TextView txtAddress;
    private TextView txtHours;

    private WebView wvContent;

    //Variable
    private int nIndex;

    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_detail);
        initVariable();
        initUI();
    }

    public void initVariable()
    {
        nIndex = getIntent().getIntExtra("position", 0);

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(ExploreDetailActivity.this));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.img_empty)
                .showImageForEmptyUri(R.drawable.img_empty)
                .cacheInMemory()
                .cacheOnDisc()
                //  .displayer(new RoundedBitmapDisplayer(20))
                .build();
    }

    public void initUI()
    {
        imgThumb = (ImageView) findViewById(R.id.img_exploredetail_image);
        txtTitle = (TextView) findViewById(R.id.txt_exploredetail_title);
        txtType = (TextView) findViewById(R.id.txt_exploredetail_target);
        txtAddress = (TextView) findViewById(R.id.txt_exploredetail_address);
        txtHours = (TextView) findViewById(R.id.txt_exploredetail_opening);

        wvContent =(WebView) findViewById(R.id.wv_exploredetail_content);

        txtTitle.setText(StringUtil.convertString(AppData.arrCardiff.get(nIndex).getTitle()));
        txtType.setText(StringUtil.convertString(AppData.arrCardiff.get(nIndex).getTargetMarket()));
        txtAddress.setText(StringUtil.convertString(AppData.arrCardiff.get(nIndex).getAddress()));
        txtHours.setText(StringUtil.convertString(AppData.arrCardiff.get(nIndex).getHours()));

        wvContent.loadData(AppData.arrCardiff.get(nIndex).getContent(), "text/html", "UTF-8");

        imageLoader.displayImage(AppData.arrCardiff.get(nIndex).getImage(), imgThumb, options);
    }

}

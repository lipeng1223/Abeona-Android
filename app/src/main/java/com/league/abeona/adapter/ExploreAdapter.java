package com.league.abeona.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.league.abeona.R;
import com.league.abeona.model.Cardiff;
import com.league.abeona.util.StringUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by Jimmy on 1/3/2017.
 */

public class ExploreAdapter extends ArrayAdapter<Cardiff> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Cardiff> arrItem;


    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public ExploreAdapter(Context context, int resource, ArrayList<Cardiff> objects) {
        super(context, resource, objects);

        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        arrItem = objects;

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.img_empty)
                .showImageForEmptyUri(R.drawable.img_empty)
                .cacheInMemory()
                .cacheOnDisc()
                //  .displayer(new RoundedBitmapDisplayer(20))
                .build();
    }

    @Override
    public int getCount() {
        return arrItem.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null)
        {
            view = inflater.inflate(R.layout.row_cardiff, null);
        }

        ImageView imgThumb = (ImageView) view.findViewById(R.id.img_rowcardiff_image);
        imageLoader.displayImage(arrItem.get(position).getImage(), imgThumb, options);

        TextView txtTitle = (TextView) view.findViewById(R.id.txt_rowcardiff_title);
        txtTitle.setText(StringUtil.convertString(arrItem.get(position).getTitle()));

        TextView txtType = (TextView) view.findViewById(R.id.txt_rowcardiff_type);
        txtType.setText(StringUtil.convertString(arrItem.get(position).getType()));

        TextView txtAddress = (TextView) view.findViewById(R.id.txt_rowcardiff_address);
        txtAddress.setText(StringUtil.convertString(arrItem.get(position).getAddress()));

        TextView txtHours = (TextView) view.findViewById(R.id.txt_rowcardiff_hour);
        txtHours.setText(StringUtil.convertString(arrItem.get(position).getHours()));

        return view;
    }
}

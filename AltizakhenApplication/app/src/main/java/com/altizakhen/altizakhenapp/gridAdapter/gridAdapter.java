package com.altizakhen.altizakhenapp.gridAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by t-mansh on 1/8/2015.
 */
public class gridAdapter extends BaseAdapter {

    private Context context;
    private final ArrayList<Integer> values;

    public gridAdapter(Context context, ArrayList<Integer> values) {
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(500, 300));
/*            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);*/
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(values.get(position));
        return imageView;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}

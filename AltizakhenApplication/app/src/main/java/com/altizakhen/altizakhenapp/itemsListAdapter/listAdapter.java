package com.altizakhen.altizakhenapp.itemsListAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.altizakhen.altizakhenapp.R;
import com.altizakhen.altizakhenapp.backend.altizakhenApi.model.Item;

import java.util.ArrayList;

/**
 * Created by t-mansh on 1/7/2015.
 */
public class listAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Item> Items;
    private int visibility;

    public listAdapter(Context context, ArrayList<Item> Items, int visibility){
        this.context = context;
        this.Items = Items;
        this.visibility = visibility;
    }

    @Override
    public int getCount() {
        return Items.size();
    }

    @Override
    public Object getItem(int position) {
        return Items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_view, null);
        }
        //find the note to work with
        Item currentItem = Items.get(position);
        // Picture
        ImageView imageView = (ImageView) convertView.findViewById(R.id.item_icon);
        imageView.setImageResource(currentItem.getIconId());
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        // Content
        TextView contentText = (TextView) convertView.findViewById(R.id.Name);
        contentText.setText(currentItem.getName());
        //Author
        TextView authorText = (TextView) convertView.findViewById(R.id.Price);
        authorText.setText(String.valueOf(currentItem.getPrice()));
        //Date
        TextView dateText = (TextView) convertView.findViewById(R.id.Date);
        dateText.setText(currentItem.getLocation());

        Button addToCart = (Button)convertView.findViewById(R.id.add_to_cart);
        if (visibility == 0){
            addToCart.setVisibility(View.VISIBLE);
        } else {
            addToCart.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

}

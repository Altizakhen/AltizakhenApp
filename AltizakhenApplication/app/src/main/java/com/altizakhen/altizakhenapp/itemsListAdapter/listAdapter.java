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
import android.widget.Toast;

import com.altizakhen.altizakhenapp.MainActivity;
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
        viewHolder holder;
        View view;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.list_item_view, null);
            //create new Holder
            holder = new viewHolder();
            holder.itemIcon = (ImageView) view.findViewById(R.id.item_photo);
            holder.Name = (TextView) view.findViewById(R.id.Name);
            holder.Price = (TextView) view.findViewById(R.id.Price);
            holder.Location = (TextView) view.findViewById(R.id.Location);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (viewHolder) view.getTag();
        }
        //find the item to work with
        final Item currentItem = Items.get(position);

        holder.itemIcon.setImageResource(currentItem.getIconId());
        holder.itemIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        holder.Name.setText(currentItem.getName());
        holder.Price.setText(String.valueOf(currentItem.getPrice()));
        holder.Location.setText(currentItem.getLocation());

        Button addToCart = (Button)view.findViewById(R.id.add_to_cart);
        if (visibility == 0){
            addToCart.setVisibility(View.VISIBLE);
        } else {
            addToCart.setVisibility(View.INVISIBLE);
        }

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.cart.contains(currentItem)){
                    Toast.makeText(context, "The item is already in the cart", Toast.LENGTH_SHORT).show();
                }else {
                    MainActivity.cart.add(currentItem);
                    Toast.makeText(context, "Item was added to Cart", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

}

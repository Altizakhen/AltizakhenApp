package com.altizakhen.altizakhenapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.altizakhen.altizakhenapp.backend.itemApi.model.Item;
import com.altizakhen.altizakhenapp.itemsListAdapter.viewHolder;

import java.util.List;

/**
 * Created by משתמש on 15/01/14.
 */
public class allItemsListAdapter extends BaseAdapter {
    private Context context;
    private List<Item> Items;
    private int visibility;
    private Bitmap[] images;
    private boolean[] isImageRequestSent;
    ListView list;

    public allItemsListAdapter(Context context, List<Item> Items, int visibility, ListView lst){
        this.context = context;
        this.Items = Items;
        this.visibility = visibility;
        list = lst;
        if (Items != null) {
            this.images = new Bitmap[Items.size()];
            this.isImageRequestSent = new boolean[Items.size()];

        }
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

        if (images[position] == null) {
            if (isImageRequestSent[position] != true){
                ImageDownloader downloadImg = new ImageDownloader(context,currentItem.getId(),images,position,list);
                downloadImg.getAndSetImageOfItem();
                isImageRequestSent[position] = true;
            }
        } else {
            holder.itemIcon.setImageBitmap(images[position]);
        }


//       holder.itemIcon.setImageResource(currentItem.getIconId());
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

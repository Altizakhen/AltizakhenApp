package com.altizakhen.altizakhenapp.itemsListAdapter;

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

import com.altizakhen.altizakhenapp.ImageDownloader;
import com.altizakhen.altizakhenapp.MainActivity;
import com.altizakhen.altizakhenapp.R;
import com.altizakhen.altizakhenapp.backend.itemApi.model.Item;
import com.altizakhen.altizakhenapp.cartView;

import java.util.List;

/**
 * Created by t-mansh on 1/7/2015.
 */
public class listAdapter extends BaseAdapter{
    private Context context;
    private List<Item> Items;
    private int visibility_add;
    private int visibility_remove;
    private Bitmap[] images;
    private boolean[] isImageRequestSent;
    ListView list;

    public listAdapter(Context context, List<Item> Items, int visibility_add, int visibility_remove, ListView lst){
        this.context = context;
        this.Items = Items;
        this.visibility_add = visibility_add;
        this.visibility_remove = visibility_remove;
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
    public Bitmap getImage(int pos) {
        return images[pos];
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
                holder.itemIcon.setImageBitmap(null);
            }
        } else {
            holder.itemIcon.setImageBitmap(images[position]);
        }

        holder.itemIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        holder.Name.setText(currentItem.getName());
        holder.Price.setText(String.valueOf(currentItem.getPrice()));
        holder.Location.setText(currentItem.getLocation());

        Button addToCart = (Button)view.findViewById(R.id.add_to_cart);
        Button removeFromCart = (Button) view.findViewById(R.id.button3);
        if (visibility_add == 0){
            addToCart.setVisibility(View.VISIBLE);
        } else {
            addToCart.setVisibility(View.INVISIBLE);
        }
        if (visibility_remove == 0){
            removeFromCart.setVisibility(View.VISIBLE);
        } else {
            removeFromCart.setVisibility(View.INVISIBLE);
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

        removeFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.cart.remove(currentItem);
                Toast.makeText(context, "Item deleted from Cart", Toast.LENGTH_SHORT).show();
                cartView.adapter = new listAdapter(context, MainActivity.cart, 1, 0, cartView.cartView);
                cartView.cartView.setAdapter(cartView.adapter);
            }
        });

        return view;
    }


}

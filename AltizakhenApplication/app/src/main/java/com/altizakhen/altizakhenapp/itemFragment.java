package com.altizakhen.altizakhenapp;

;
import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.altizakhen.altizakhenapp.backend.altizakhenApi.model.Item;

/**
 * Created by t-mansh on 1/12/2015.
 */
public class itemFragment extends Fragment {

    public itemFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.item, container, false);
/*        Item item = MainActivity.currentItemInView;
        ((TextView) rootView.findViewById(R.id.item_name)).setText(item.getName());
        ((TextView) rootView.findViewById(R.id.item_price)).setText(item.getPrice());
        ((TextView) rootView.findViewById(R.id.item_location)).setText(item.getLocation());
        ((TextView) rootView.findViewById(R.id.item_desc)).setText(item.getDescription());
        ((ImageView) rootView.findViewById(R.id.item_photo)).setImageResource(item.getIconId());

        if (MainActivity.userId == null){
            rootView.findViewById(R.id.delete_button).setVisibility(View.INVISIBLE);
            rootView.findViewById(R.id.contact_button).setVisibility(View.VISIBLE);
        }
        else if(MainActivity.userId.equals(item.getSellerId())){
            rootView.findViewById(R.id.delete_button).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.contact_button).setVisibility(View.INVISIBLE);
        } else {
            rootView.findViewById(R.id.delete_button).setVisibility(View.INVISIBLE);
            rootView.findViewById(R.id.contact_button).setVisibility(View.VISIBLE);

        }*/
        return rootView;
    }

}

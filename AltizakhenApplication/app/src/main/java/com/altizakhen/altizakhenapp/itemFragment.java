package com.altizakhen.altizakhenapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

;

/**
 * Created by t-mansh on 1/12/2015.
 */
public class itemFragment extends Fragment {

    public itemFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.item, container, false);
//        ImageView im = (ImageView) rootView.findViewById(R.id.item_photo);
//        Item item = MainActivity.currentItemInView;
//        new ApiHelper(getActivity()).getImageOfItem(item.getId(), im);

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

package com.altizakhen.altizakhenapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

;import com.altizakhen.altizakhenapp.backend.itemApi.model.Item;

/**
 * Created by t-mansh on 1/12/2015.
 */
public class itemFragment extends Fragment {

    public itemFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.item, container, false);
        Item item = MainActivity.currentItemInView;
//        ImageView im = (ImageView) rootView.findViewById(R.id.item_photo);
//        new ApiHelper(getActivity()).getImageOfItem(item.getId(), im);


        ((TextView) rootView.findViewById(R.id.item_name)).setText(item.getName());
        ((TextView) rootView.findViewById(R.id.item_price)).setText(item.getPrice().toString());
        ((TextView) rootView.findViewById(R.id.item_location)).setText(item.getLocation());
        ((TextView) rootView.findViewById(R.id.item_desc)).setText(item.getDescription());
        ((ImageView) rootView.findViewById(R.id.item_photo)).setImageResource(item.getIconId());

        Button removeButton = (Button) rootView.findViewById(R.id.delete_button);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiHelper api = new ApiHelper(getActivity());
                // TODO: delete the item
            }
        });

        Button contact = (Button) rootView.findViewById(R.id.contact_button);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: contact the seller
            }
        });

        if (MainActivity.userServerId == "null"){

            rootView.findViewById(R.id.delete_button).setVisibility(View.INVISIBLE);
            rootView.findViewById(R.id.contact_button).setVisibility(View.VISIBLE);
        }
        else if(MainActivity.userServerId.equals(item.getUserId())){
            rootView.findViewById(R.id.delete_button).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.contact_button).setVisibility(View.INVISIBLE);
        } else {
            rootView.findViewById(R.id.delete_button).setVisibility(View.INVISIBLE);
            rootView.findViewById(R.id.contact_button).setVisibility(View.VISIBLE);

        }
        return rootView;
    }

}

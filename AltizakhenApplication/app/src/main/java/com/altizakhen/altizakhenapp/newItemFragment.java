package com.altizakhen.altizakhenapp;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.altizakhen.altizakhenapp.backend.altizakhenApi.model.Item;


/**
* Created by t-mansh on 1/2/2015.
*/
public class newItemFragment extends Fragment {

    public newItemFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.new_item, container, false);
        Button b = (Button) rootView.findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickHandler();
            }
        });
        return rootView;
    }


    void onClickHandler() {
        String productName = ((EditText) getView().findViewById(R.id.name)).getText().toString();
        String price = ((EditText) getView().findViewById(R.id.price)).getText().toString();
        String description = ((EditText) getView().findViewById(R.id.description)).getText().toString();
        String location = ((EditText) getView().findViewById(R.id.place)).getText().toString();

        if (productName.isEmpty()) {
            Toast.makeText(getActivity(), "Enter the product's Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (price.isEmpty()) {
            Toast.makeText(getActivity(), "Enter the product's Price", Toast.LENGTH_SHORT).show();
            return;
        }
        if (location.isEmpty()) {
            Toast.makeText(getActivity(), "Enter the pick up location", Toast.LENGTH_SHORT).show();
            return;
        }
        ApiHelper api = new ApiHelper(getActivity());
        Item item = new Item();
        item.setDescription(description);
        item.setName(productName);
        item.setLocation(location);
        item.setPrice(Integer.parseInt(price));
        item.setSellerId(11);
        item.setSellerName("seller");

        api.addItem(item);
    }


}

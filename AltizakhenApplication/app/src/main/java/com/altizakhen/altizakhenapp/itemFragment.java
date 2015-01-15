package com.altizakhen.altizakhenapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
public class itemFragment extends FragmentActivity {

    public itemFragment(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item);
        Item item = MainActivity.currentItemInView;
//        ImageView im = (ImageView) rootView.findViewById(R.id.item_photo);
//        new ApiHelper(getActivity()).getImageOfItem(item.getId(), im);


        ((TextView) findViewById(R.id.item_name)).setText(item.getName());
        ((TextView) findViewById(R.id.item_price)).setText(item.getPrice().toString());
        ((TextView) findViewById(R.id.item_location)).setText(item.getLocation());
        ((TextView) findViewById(R.id.item_desc)).setText(item.getDescription());
        ((ImageView) findViewById(R.id.item_photo)).setImageBitmap(MainActivity.currentItemBitmap);

        Button removeButton = (Button) findViewById(R.id.delete_button);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiHelper api = new ApiHelper(getApplication());
                // TODO: delete the item
            }
        });

        Button contact = (Button) findViewById(R.id.contact_button);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: contact the seller
            }
        });

        if (MainActivity.userServerId == null){

            findViewById(R.id.delete_button).setVisibility(View.INVISIBLE);
            findViewById(R.id.contact_button).setVisibility(View.VISIBLE);
        }
        else if(MainActivity.userServerId.equals(item.getUserId())){
           findViewById(R.id.delete_button).setVisibility(View.VISIBLE);
            findViewById(R.id.contact_button).setVisibility(View.INVISIBLE);
        } else {
            findViewById(R.id.delete_button).setVisibility(View.INVISIBLE);
            findViewById(R.id.contact_button).setVisibility(View.VISIBLE);

        }
    }

}

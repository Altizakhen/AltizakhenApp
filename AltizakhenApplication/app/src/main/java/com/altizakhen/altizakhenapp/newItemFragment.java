package com.altizakhen.altizakhenapp;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.altizakhen.altizakhenapp.backend.altizakhenApi.model.Item;

import java.io.InputStream;


/**
* Created by t-mansh on 1/2/2015.
*/
public class newItemFragment extends Fragment {
    ImageButton img;
    public newItemFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.new_item, container, false);
       CustomizeFont(rootView);
        Button b = (Button) rootView.findViewById(R.id.button);
        img = (ImageButton) rootView.findViewById(R.id.imageButton);
        img.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                OnImageButtonClickHandler();
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickHandler();
            }
        });
        return rootView;
    }

    private void CustomizeFont(View rootView) {
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"djb.ttf");
        TextView name = (TextView) rootView.findViewById(R.id.textView3);
        TextView price = (TextView) rootView.findViewById(R.id.textView4);
        TextView description = (TextView) rootView.findViewById(R.id.textView5);
        TextView location = (TextView) rootView.findViewById(R.id.textView6);
        name.setTypeface(type);
        price.setTypeface(type);
        description.setTypeface(type);
        location.setTypeface(type);

    }

    private void OnImageButtonClickHandler() {
        Intent imageSelect = new Intent(Intent.ACTION_PICK);
        imageSelect.setType("image/*");
        startActivityForResult(imageSelect, 100);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case 100:
                if(resultCode == Activity.RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    try {
                        InputStream imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                        Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                        img.setImageBitmap(yourSelectedImage);

                    } catch (Exception e) {
                        Toast.makeText(getActivity(),"oh fuck !! ", Toast.LENGTH_SHORT).show();
                    }

                }
        }
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
        // TODO: should return the item's id
    }


}

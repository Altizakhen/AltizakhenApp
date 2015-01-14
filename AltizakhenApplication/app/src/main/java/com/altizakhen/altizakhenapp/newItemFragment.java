package com.altizakhen.altizakhenapp;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.altizakhen.altizakhenapp.Categories.Books;
import com.altizakhen.altizakhenapp.backend.itemApi.model.Item;

import java.io.InputStream;


/**
* Created by t-mansh on 1/2/2015.
*/
public class newItemFragment extends Activity {
    ImageButton img;
    static Bitmap yourSelectedImage;
    int category;

    public newItemFragment() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_item);
        CustomizeFont();
        Button b = (Button) findViewById(R.id.button);
        img = (ImageButton) findViewById(R.id.imageButton);
        img.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                OnImageButtonClickHandler();
            }
        });
        if (yourSelectedImage != null) {
            img.setImageBitmap(yourSelectedImage);
        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickHandler();
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                category = 0;

            }
        });

    }

    private void CustomizeFont() {
        Typeface type = Typeface.createFromAsset(getAssets(),"djb.ttf");
        TextView name = (TextView) findViewById(R.id.textView3);
        TextView price = (TextView) findViewById(R.id.textView4);
        TextView description = (TextView) findViewById(R.id.textView5);
        TextView location = (TextView) findViewById(R.id.textView6);
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
                        InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                        yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                        yourSelectedImage = Bitmap.createScaledBitmap(yourSelectedImage, 200, 200, false);
                        img.setImageBitmap(yourSelectedImage);

                    } catch (Exception e) {
                        Toast.makeText(this,"oh fuck !! ", Toast.LENGTH_SHORT).show();
                    }

                }
        }
    }


    void onClickHandler() {
        String productName = ((EditText) findViewById(R.id.name)).getText().toString();
        String price = ((EditText) findViewById(R.id.price)).getText().toString();
        String description = ((EditText) findViewById(R.id.description)).getText().toString();
        String location = ((EditText) findViewById(R.id.place)).getText().toString();

        if (productName.isEmpty()) {
            Toast.makeText(this, "Enter the product's Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (category == 0){
            Toast.makeText(this, "Select a category first", Toast.LENGTH_SHORT).show();
            return;
        }
        if (price.isEmpty()) {
            Toast.makeText(this, "Enter the product's Price", Toast.LENGTH_SHORT).show();
            return;
        }
        if (location.isEmpty()) {
            Toast.makeText(this, "Enter the pick up location", Toast.LENGTH_SHORT).show();
            return;
        }
        if (img.getDrawable() == null) {
            Toast.makeText(this, "Enter image please", Toast.LENGTH_SHORT).show();
            return;
        }
        if(MainActivity.userServerId == null) {
            Toast.makeText(this, "Loging-in isn't complete yet, try again later", Toast.LENGTH_LONG).show();
            return;
        }


        ApiHelper api = new ApiHelper(this);
        Item item = new Item();
        item.setDescription(description);
        item.setName(productName);
        item.setLocation(location);
        item.setPrice(Integer.parseInt(price));
        item.setUserId(MainActivity.userServerId);

        switch (category){
            case 1:
                item.setCategoryName("Books");
                break;
            case 2:
                item.setCategoryName("Furniture");
                break;
            case 3:
                item.setCategoryName("Electronics");
                break;
        }
        api.addItem(item, ((BitmapDrawable)img.getDrawable()).getBitmap());
        this.finish();
    }

}

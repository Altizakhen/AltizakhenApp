package com.altizakhen.altizakhenapp;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.altizakhen.altizakhenapp.backend.itemApi.model.Item;
import com.altizakhen.altizakhenapp.itemsListAdapter.listAdapter;

import java.util.ArrayList;

/**
 * Created by t-mansh on 1/12/2015.
 */
public class cartView extends Activity{
    ListView cartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        CustomizeFont();
        cartView = (ListView) findViewById(R.id.cartList);

        listAdapter adapter = new listAdapter(this, MainActivity.cart, 1);
        cartView.setAdapter(adapter);
    }

    private void CustomizeFont() {
        Typeface type = Typeface.createFromAsset(getAssets(),"djb.ttf");
        TextView title = (TextView) findViewById(R.id.cart_text);

        title.setTypeface(type);
    }

}

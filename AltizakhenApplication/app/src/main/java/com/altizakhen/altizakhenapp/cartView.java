package com.altizakhen.altizakhenapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.altizakhen.altizakhenapp.backend.itemApi.model.Item;
import com.altizakhen.altizakhenapp.itemsListAdapter.listAdapter;

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

        listAdapter adapter = new listAdapter(this, MainActivity.cart, 1, 0, cartView);
        cartView.setAdapter(adapter);
        cartView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.currentItemInView = (Item)adapterView.getAdapter().getItem(i);
                MainActivity.currentItemBitmap = ((listAdapter)adapterView.getAdapter()).getImage(i);
                Intent intent = new Intent(getApplication(), itemFragment.class);
                startActivity(intent);
            }
        });
    }

    protected void onResume(Bundle savedInstanceState) {
        cartView = (ListView) findViewById(R.id.cartList);
        listAdapter adapter = new listAdapter(this, MainActivity.cart, 1, 0, cartView);
        cartView.setAdapter(adapter);
    }

    private void CustomizeFont() {
        Typeface type = Typeface.createFromAsset(getAssets(),"djb.ttf");
        TextView title = (TextView) findViewById(R.id.cart_text);

        title.setTypeface(type);
    }

}

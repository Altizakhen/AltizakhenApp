package com.altizakhen.altizakhenapp.Categories;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.altizakhen.altizakhenapp.R;
import com.altizakhen.altizakhenapp.backend.altizakhenApi.model.Item;
import com.altizakhen.altizakhenapp.itemsListAdapter.listAdapter;
import com.altizakhen.altizakhenapp.itemsListAdapter.listItem;

import java.util.ArrayList;

/**
 * Created by t-mansh on 1/7/2015.
 */
public class Books extends Activity {

    private ArrayList<Item> items;
    private ListView itemList;

    public Books(){ }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books);

        itemList = (ListView) findViewById(R.id.listView);

        items = new ArrayList<Item>();
        populateList();
        listAdapter adapter = new listAdapter(this, items, 0);
        itemList.setAdapter(adapter);
    }


    private void populateList() {

       /* items.add(new listItem("bed", 75, "2015.01.02", R.drawable.ic_cat));
        items.add(new listItem("A thousand Splendid Suns", 100, "2015.01.02", R.drawable.ic_cat));*/
    }

}

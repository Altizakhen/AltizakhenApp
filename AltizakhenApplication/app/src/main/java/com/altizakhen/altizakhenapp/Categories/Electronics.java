package com.altizakhen.altizakhenapp.Categories;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.altizakhen.altizakhenapp.MainActivity;
import com.altizakhen.altizakhenapp.R;
import com.altizakhen.altizakhenapp.backend.itemApi.model.Item;
import com.altizakhen.altizakhenapp.itemFragment;
import com.altizakhen.altizakhenapp.itemsListAdapter.listAdapter;

import java.util.ArrayList;

/**
 * Created by t-mansh on 1/8/2015.
 */
public class Electronics extends FragmentActivity {
    private ListView itemList;

    public Electronics(){}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.electronics);
        itemList = (ListView) findViewById(R.id.electronics_list);
/*        items = new ArrayList<Item>();
        populateList();*/
        listAdapter adapter = new listAdapter(this, MainActivity.catItems, 0);
        itemList.setAdapter(adapter);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.currentItemInView = (Item)adapterView.getAdapter().getItem(i);
                Intent intent = new Intent(getApplication(), itemFragment.class);
                startActivity(intent);
            }
        });

    }

    /*private void populateList() {

        Item item1 = new Item();
        item1.setName("bed");
        item1.setPrice(75);
        item1.setLocation("haifa");
        item1.setIconId(R.drawable.ic_bed);
        item1.setUserId("13");
        item1.setSellerName("Manar");
        item1.setDescription("Brand New");
        items.add(item1);

        Item item2 = new Item();
        item2.setName("sun");
        item2.setPrice(75);
        item2.setLocation("haifa");
        item2.setIconId(R.drawable.ic_suns);
        item1.setUserId("13");
        item2.setSellerName("Manar");
        item2.setDescription("Brand New");
        items.add(item2);
        *//*
        list.add(new listItem("A thousand Splendid Suns", 100, "2015.01.02", R.drawable.ic_suns));
        list.add(new listItem("Winter hat", 40, "2015.01.11", R.drawable.ic_hat));
        list.add(new listItem("necklace", 140, "2014.10.02", R.drawable.ic_necklace));*//*


    }*/

}

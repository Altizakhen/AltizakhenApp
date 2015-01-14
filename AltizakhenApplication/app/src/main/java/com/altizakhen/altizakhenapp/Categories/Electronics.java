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
        listAdapter adapter = new listAdapter(this, MainActivity.catItems, 0, itemList);
        itemList.setAdapter(adapter);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.currentItemInView = (Item)adapterView.getAdapter().getItem(i);
                MainActivity.currentItemBitmap = ((listAdapter)adapterView.getAdapter()).getImage(i);
                Intent intent = new Intent(getApplication(), itemFragment.class);
                startActivity(intent);
            }
        });

    }
}

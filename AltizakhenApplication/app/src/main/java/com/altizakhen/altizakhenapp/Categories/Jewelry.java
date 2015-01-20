package com.altizakhen.altizakhenapp.Categories;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.altizakhen.altizakhenapp.MainActivity;
import com.altizakhen.altizakhenapp.R;
import com.altizakhen.altizakhenapp.backend.itemApi.model.Item;
import com.altizakhen.altizakhenapp.itemFragment;
import com.altizakhen.altizakhenapp.itemsListAdapter.listAdapter;

/**
 * Created by t-mansh on 1/20/2015.
 */
public class Jewelry extends FragmentActivity {
    private ListView itemList;

    public Jewelry(){ }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books);
        itemList = (ListView) findViewById(R.id.listView);
        listAdapter adapter = new listAdapter(this, MainActivity.catItems, 0, 1, itemList);
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

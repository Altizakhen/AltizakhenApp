package com.altizakhen.altizakhenapp;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.altizakhen.altizakhenapp.backend.itemApi.model.Item;
import com.altizakhen.altizakhenapp.itemsListAdapter.listAdapter;

import java.util.ArrayList;

/**
 * Created by t-mansh on 1/17/2015.
 */
public class SearchResultsActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            doSearch(query);
        }
    }

    private void doSearch(String query) {
        ArrayList<Item> result = new ArrayList<Item>();
        String[] splited = query.split("\\s+");
        for (String s : splited){
            for ( Item item : MainActivity.items){
                if (item.getName().contains(s) || item.getLocation().contains(s)
                        || item.getDescription().contains(s)){
                    if (!result.contains(item)){
                        result.add(item);
                    }
                }
            }
        }

        listAdapter adapter = new listAdapter(getApplication(),result, 0, 1,this.getListView());
        this.getListView().setCacheColorHint(getResources().getColor(R.color.black));
        this.getListView().setDrawingCacheBackgroundColor(getResources().getColor(R.color.black));
        this.setTitleColor(getResources().getColor(R.color.black));

        this.getListView().setAdapter(adapter);
        this.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

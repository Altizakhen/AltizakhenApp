package com.altizakhen.altizakhenapp.Categories;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.altizakhen.altizakhenapp.R;
import com.altizakhen.altizakhenapp.itemsListAdapter.listAdapter;
import com.altizakhen.altizakhenapp.itemsListAdapter.listItem;

import java.util.ArrayList;

/**
 * Created by t-mansh on 1/7/2015.
 */
public class Books extends Fragment {

    private ArrayList<listItem> items;
    private ListView itemList;

    public Books(){ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.books, container, false);
        itemList = (ListView) rootView.findViewById(R.id.listView);

        items = new ArrayList<listItem>();
        populateList();
        listAdapter adapter = new listAdapter(getActivity(), items);
        itemList.setAdapter(adapter);

        return rootView;
    }

    private void populateList() {

        items.add(new listItem("bed", 75, "2015.01.02", R.drawable.ic_cat));
    }

}

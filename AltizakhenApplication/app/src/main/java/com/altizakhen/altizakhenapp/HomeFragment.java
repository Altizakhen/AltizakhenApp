package com.altizakhen.altizakhenapp;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.altizakhen.altizakhenapp.backend.altizakhenApi.model.Item;
import com.altizakhen.altizakhenapp.itemsListAdapter.listAdapter;
import com.altizakhen.altizakhenapp.itemsListAdapter.listItem;

import java.util.ArrayList;

/**
 * Created by t-mansh on 12/29/2014.
 */
public class HomeFragment extends Fragment{

    ArrayList<Item> list;
    private ListView itemList;

    public HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        CustomizeFont(rootView);
        itemList = (ListView) rootView.findViewById(R.id.listView4);

        list = new ArrayList<Item>();
        populateList();
        listAdapter adapter = new listAdapter(getActivity(), list, 0);
        itemList.setAdapter(adapter);
        return rootView;
    }

    private void CustomizeFont(View rootView) {
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"djb.ttf");
        TextView title = (TextView) rootView.findViewById(R.id.txtLabel);

        title.setTypeface(type);
    }


    private void populateList() {

        Item item1 = new Item();
        item1.setName("bed");
        item1.setPrice(75);
        item1.setLocation("haifa");
        item1.setIconId(R.drawable.ic_bed);
        item1.setSellerId(13);
        item1.setSellerName("Manar");
        item1.setDescription("Brand New");
        list.add(item1);
        /*
        list.add(new listItem("A thousand Splendid Suns", 100, "2015.01.02", R.drawable.ic_suns));
        list.add(new listItem("Winter hat", 40, "2015.01.11", R.drawable.ic_hat));
        list.add(new listItem("necklace", 140, "2014.10.02", R.drawable.ic_necklace));*/


    }
}

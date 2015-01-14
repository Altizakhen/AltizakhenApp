package com.altizakhen.altizakhenapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.altizakhen.altizakhenapp.backend.itemApi.model.Item;
import com.altizakhen.altizakhenapp.itemsListAdapter.listAdapter;

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
        /*for (Item t : MainActivity.items ){
            list.add(t);
        }*/
//        populateList();
        allItemsListAdapter adapter = new allItemsListAdapter(getActivity(), MainActivity.items, 0, itemList);
        itemList.setAdapter(adapter);
        itemList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.currentItemInView = (Item)adapterView.getAdapter().getItem(i);
                Fragment fragment = new itemFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
            }
        });
        return rootView;
    }

    private void CustomizeFont(View rootView) {
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"djb.ttf");
        TextView title = (TextView) rootView.findViewById(R.id.txtLabel);

        title.setTypeface(type);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        itemList = null;

    }
}

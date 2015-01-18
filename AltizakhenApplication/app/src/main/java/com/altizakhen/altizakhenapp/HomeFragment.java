package com.altizakhen.altizakhenapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.altizakhen.altizakhenapp.backend.itemApi.model.Item;
import com.altizakhen.altizakhenapp.itemsListAdapter.listAdapter;

import java.util.ArrayList;
import java.util.List;

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
        listAdapter adapter = new listAdapter(getActivity(), MainActivity.items, 0, 1, itemList);
        itemList.setAdapter(adapter);

        Spinner spinner = (Spinner) rootView.findViewById(R.id.sort_spinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),
                R.array.sort_by_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (MainActivity.items == null) {
                    return;
                }
                ArrayList<Item> sortedItems = new ArrayList<Item>(MainActivity.items);
                if (i == 1) {

                    for( int i1 = 0; i1 < sortedItems.size(); ++i1) {
                        int max = i1;
                        for(int j1 = i1; j1 < sortedItems.size(); ++j1) {
                            if ( sortedItems.get(max).getPrice() < sortedItems.get(j1).getPrice()){
                                max = j1;
                            }
                        }
                        sortedItems.set(max,sortedItems.set(i1, sortedItems.get(max)));
                    }
                    MainActivity.items = sortedItems;
                } else if (i==2) {

                }
                listAdapter adapter = new listAdapter(getActivity(), MainActivity.items, 0, 1, itemList);
                itemList.setAdapter(adapter);
                itemList.invalidateViews();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });


        /*Button b = (Button)rootView.findViewById(R.id.button3);
        b.setVisibility(View.INVISIBLE);*/
        itemList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.currentItemInView = (Item)adapterView.getAdapter().getItem(i);
                MainActivity.currentItemBitmap = ((listAdapter)adapterView.getAdapter()).getImage(i);
                Intent intent = new Intent(getActivity(), itemFragment.class);
                startActivity(intent);
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

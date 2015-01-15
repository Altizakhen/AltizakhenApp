package com.altizakhen.altizakhenapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.altizakhen.altizakhenapp.backend.itemApi.model.Item;
import com.altizakhen.altizakhenapp.chat.ChatActivity;
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
        ((Button) rootView.findViewById(R.id.open_chat)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getActivity(), ChatActivity.class);
                String omarId = "ag5zfmFsdGl6YWtoZW4tMXImCxIEVXNlciILQWhtYWQgQWJiYXMMCxIEVXNlchiAgICAgICACgw";
                String ahmadId = "ag5zfmFsdGl6YWtoZW4tMXImCxIEVXNlciILQWhtYWQgQWJiYXMMCxIEVXNlchiAgICAgICACgw";
                myIntent.putExtra("otherUserId", ahmadId);
                startActivity(myIntent);
            }
        });
        itemList = (ListView) rootView.findViewById(R.id.listView4);

        list = new ArrayList<Item>();
        listAdapter adapter = new listAdapter(getActivity(), MainActivity.items, 0, itemList);
        itemList.setAdapter(adapter);
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

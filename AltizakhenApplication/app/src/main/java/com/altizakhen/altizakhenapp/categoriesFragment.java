package com.altizakhen.altizakhenapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.altizakhen.altizakhenapp.Categories.Books;
import com.altizakhen.altizakhenapp.Categories.Electronics;
import com.altizakhen.altizakhenapp.Categories.Furniture;
import com.altizakhen.altizakhenapp.gridAdapter.gridAdapter;

import java.util.ArrayList;

/**
 * Created by t-mansh on 12/30/2014.
 */
public class categoriesFragment extends Fragment {

    private ArrayList<Integer> items;
    private GridView itemList;

    public categoriesFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_categories, container, false);
        // TODO: set the listener for the rest of the buttons
        populateArray();

        itemList = (GridView) rootView.findViewById(R.id.gridView1);
        gridAdapter adapter = new gridAdapter(getActivity(), items);
        itemList.setAdapter(adapter);

        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = null;
                switch (position){
                    case 0:
                        intent = new Intent(getActivity(), Books.class);
                        break;
                    case 1:
                        intent = new Intent(getActivity(), Electronics.class);
                        break;
                    case 3:
                        intent = new Intent(getActivity(), Furniture.class);

                        break;
                }
                /*FragmentManager fragmentManager = ((MainActivity)getActivity()).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();*/

                startActivity(intent);

            }
        });

        return rootView;
}

    private void populateArray() {
        items = new ArrayList<Integer>();
        items.add(R.drawable.ic_books);
        items.add(R.drawable.ic_furniture);
        items.add(R.drawable.ic_electonics);
    }

}

package com.altizakhen.altizakhenapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class pleaseLoginFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.please_login, container, false);
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"djb.ttf");
        TextView text = (TextView) rootView.findViewById(R.id.item_price);
        text.setTypeface(type);

        Button b = (Button) rootView.findViewById(R.id.delete_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, new MyAltizakhen()).commit();
            }
        });
        return rootView;
    }

}

package com.altizakhen.altizakhenapp.Categories;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.altizakhen.altizakhenapp.R;

/**
 * Created by t-mansh on 1/8/2015.
 */
public class Electronics extends Fragment {
    public Electronics(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.electronics, container, false);
        return rootView;
    }
}

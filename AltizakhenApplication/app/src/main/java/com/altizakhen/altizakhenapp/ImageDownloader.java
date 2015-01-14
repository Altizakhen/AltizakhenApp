package com.altizakhen.altizakhenapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


/**
 * Created by משתמש on 15/01/14.
 */
public class ImageDownloader {
    String itemId;
    Bitmap[] imagesArray;
    int index;
    Context context;
    ListView list;
    public ImageDownloader(Context cntx, String id, Bitmap[] images, int i , ListView lst){
        itemId = id;
        imagesArray = images;
        index = i;
        context = cntx;
        list = lst;
    }
    public void getAndSetImageOfItem() {
        Firebase myFirebaseRef = new Firebase("https://altizaken.firebaseio.com/");
        myFirebaseRef.child(itemId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Bitmap b = decodeBase64((String) snapshot.getValue());
                imagesArray[index]= Bitmap.createScaledBitmap(b, 200, 200, false);
                list.invalidateViews();
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

    }
    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}

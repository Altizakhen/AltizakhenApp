package com.altizakhen.altizakhenapp;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.altizakhen.altizakhenapp.backend.altizakhenApi.AltizakhenApi;
import com.altizakhen.altizakhenapp.backend.altizakhenApi.model.Item;
import com.altizakhen.altizakhenapp.backend.altizakhenApi.model.ItemCollection;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.repackaged.com.google.common.base.Objects;

import java.io.IOException;
import java.util.List;

/**
 * Created by personal on 1/2/15.
 */
public class ApiHelper {

    private final AltizakhenApi service;
    private final Context context;

    public ApiHelper(Context context) {
        this.context = context;
        // Initialize the service object that allows us to call API methods.
        AltizakhenApi.Builder builder =
                new AltizakhenApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
        builder.setRootUrl("https://altizakhen-1.appspot.com/_ah/api");
        service = builder.build();

    }

    // An example of how to use this API. Shows the first item in Toast.
    public void getAllItems() {
        QueryItemsTask task = new QueryItemsTask();
        task.execute();
    }

    public void addItem(Item item) {
        new AddItemTask().execute(item);
    }

    public class QueryItemsTask extends AsyncTask<Void, Void, ItemCollection> {
        @Override
        protected ItemCollection doInBackground(Void... voids) {
            ItemCollection items = null;
            try {
                items = service.getAllItems().execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return items;
        }

        @Override
        protected void onPostExecute(ItemCollection itemCollection) {
            super.onPostExecute(itemCollection);
            if (itemCollection != null) {
                Item firstItem = itemCollection.getItems().get(0);
                Toast.makeText(context, "first item: " + firstItem.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public class AddItemTask extends AsyncTask<Item, Item, Item> {
        @Override
        protected Item doInBackground(Item... items) {
            Item item = items[0];
            try {
                service.addItem(item.getName(), item.getLocation(), item.getPrice(), item.getSellerId(), item.getSellerName(), item.getDescription()).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return item;
        }

        @Override
        protected void onPostExecute(Item item) {
            super.onPostExecute(item);
            Toast.makeText(context, "Added: " + item.toString(), Toast.LENGTH_LONG).show();
        }
    }

    }

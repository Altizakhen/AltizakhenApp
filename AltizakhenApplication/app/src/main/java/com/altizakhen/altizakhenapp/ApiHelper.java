package com.altizakhen.altizakhenapp;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.altizakhen.altizakhenapp.backend.altizakhenApi.AltizakhenApi;
import com.altizakhen.altizakhenapp.backend.altizakhenApi.model.Item;
import com.altizakhen.altizakhenapp.backend.altizakhenApi.model.ItemCollection;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

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

    // An example of how to use this API.
    public void example() {
        QueryItemsTask task = new QueryItemsTask();
        task.execute();
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

}

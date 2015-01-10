package com.altizakhen.altizakhenapp;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.altizakhen.altizakhenapp.backend.itemApi.*;
import com.altizakhen.altizakhenapp.backend.itemApi.model.Item;
import com.altizakhen.altizakhenapp.backend.itemApi.model.ItemCollection;
import com.altizakhen.altizakhenapp.backend.userApi.UserApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

/**
* Created by personal on 1/2/15.
*/
public class ApiHelper {

    private final ItemApi itemApi;
    private final UserApi userApi;
    private final Context context;

    public ApiHelper(Context context) {
        this.context = context;
        // Initialize the itemApi object that allows us to call API methods.
        ItemApi.Builder builderItem =
                new ItemApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
        builderItem.setRootUrl("https://altizakhen-1.appspot.com/_ah/api");
        itemApi = builderItem.build();

        UserApi.Builder builderUser =
                new UserApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
        builderUser.setRootUrl("https://altizakhen-1.appspot.com/_ah/api");
        userApi = builderUser.build();

    }

    // An example of how to use this API. Shows the first item in Toast.
    public void getAllItems() {
        QueryItemsTask task = new QueryItemsTask();
        task.execute();
    }

    public void addItem(Item item) {
        new AddItemTask().execute(item);
    }

    public void increaseViewCount(Item item) {
        new IncreaseViewCountTask().execute(item.getId());
    }

    public class QueryItemsTask extends AsyncTask<Void, Void, ItemCollection> {
        @Override
        protected ItemCollection doInBackground(Void... voids) {
            ItemCollection items = null;
            try {
                items = itemApi.getAllItems().execute();
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
                itemApi.addItem(item.getName(), item.getLocation(), item.getPrice(), item.getUserId(), item.getDescription()).execute();
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

    public class DeleteItemTask extends AsyncTask<Item, Item, Item> {
        @Override
        protected Item doInBackground(Item... items) {
            Item item = items[0];
            try {
                itemApi.deleteItem(item.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return item;
        }

        @Override
        protected void onPostExecute(Item item) {
            super.onPostExecute(item);
            Toast.makeText(context, "Deleted: " + item.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public class IncreaseViewCountTask extends AsyncTask<String, String, Void> {
        @Override
        protected Void doInBackground(String... items) {
            String itemId = items[0];
            try {
                itemApi.increaseViewCount(itemId);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}

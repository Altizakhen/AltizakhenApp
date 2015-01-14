package com.altizakhen.altizakhenapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.altizakhen.altizakhenapp.backend.firebaseChatApi.FirebaseChatApi;
import com.altizakhen.altizakhenapp.backend.firebaseChatApi.model.FirebaseChat;
import com.altizakhen.altizakhenapp.backend.itemApi.ItemApi;
import com.altizakhen.altizakhenapp.backend.itemApi.model.Item;
import com.altizakhen.altizakhenapp.backend.itemApi.model.ItemCollection;
import com.altizakhen.altizakhenapp.backend.userApi.UserApi;
import com.altizakhen.altizakhenapp.backend.userApi.model.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
* Created by personal on 1/2/15.
*/
public class ApiHelper {

    private final ItemApi itemApi;
    private final UserApi userApi;
    private final FirebaseChatApi firebaseChatApi;
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

        FirebaseChatApi.Builder builderFirebase =
                new FirebaseChatApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
        builderUser.setRootUrl("https://altizakhen-1.appspot.com/_ah/api");
        firebaseChatApi = builderFirebase.build();
    }

    // An example of how to use this API. Shows the first item in Toast.
    public void getAllItems() {
        QueryItemsTask task = new QueryItemsTask();
        task.execute();
    }

    public void getItemsInCategory(String categoryName, Intent intent) {
        new GetItemsInCategory(intent).execute(categoryName);
    }

    public class GetItemsInCategory extends AsyncTask<String, String, List<Item> > {
        Intent intent;
        public GetItemsInCategory(Intent i) {
            intent = i;
        }
        @Override
        protected List<Item> doInBackground(String... strings) {
            String categoryName = strings[0];
            ItemCollection items = null;
            try {
                items = itemApi.getItemsInCategory(categoryName).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return items == null ? null : items.getItems();
        }

        @Override
        protected void onPostExecute(List<Item> list) {
            super.onPostExecute(list);
            MainActivity.catItems = new ArrayList<Item>();
            for (Item i : list){
                MainActivity.catItems.add(i);
            }
            context.startActivity(intent);

        }
    }

    public void addItem(Item item, Bitmap itemImage) {
        new AddItemTask(itemImage).execute(item);
    }

    public void addUser(String name, String facebookId) {
        new AddUserTask().execute(name, facebookId);
    }

    public void getUserItems(String userId) {
       new GetUserItemsTask().execute(userId);
    }

    public void generateFirebaseChat(String userId1, String userId2, FirebaseChatCallback callback) {
        new GenerateFirebaseChatId(callback).execute(userId1, userId2);
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
                MainActivity.items = itemCollection.getItems();
                if (MainActivity.items != null) {
                    Fragment fragment = new HomeFragment();
                    FragmentManager fragmentManager =
                            ((FragmentActivity)context).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, fragment).commit();
                }
            }
        }
    }

    public class AddUserTask extends AsyncTask<String, String, User> {
        @Override
        protected User doInBackground(String... strings) {
            String userName = strings[0];
            String facebookId = strings[1];
            User user = null;

            try {
                user = userApi.addUser(userName, facebookId).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            MainActivity.userServerId = user.getId();

        }
    }

    public class AddItemTask extends AsyncTask<Item, Item, Item> {
        private Bitmap itemImage;

        public AddItemTask(Bitmap itemImage) {
            this.itemImage = itemImage;
        }

        @Override
        protected Item doInBackground(Item... items) {
            Item item = items[0];
            try {
                item = itemApi.addItem(item.getName(), item.getLocation(), item.getCategoryName(), item.getPrice(), item.getUserId(), item.getDescription()).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return item;
        }

        @Override
        protected void onPostExecute(Item item) {
            super.onPostExecute(item);
            if (itemImage != null) {
                addImageToItem(item.getId(), itemImage);
            }
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

    public class GetUserItemsTask extends AsyncTask<String, String, List> {

        @Override
        protected List<Item> doInBackground(String... strings) {
            String userId = strings[0];
            ItemCollection items = null;
            try {
                items = itemApi.getUserItems(userId).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return items.getItems();
        }

        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);
            // items has the items of the user.

        }
    }

    public interface FirebaseChatCallback {
        public void onFirebaseChat(FirebaseChat fbchat);
    }

    public class GenerateFirebaseChatId extends AsyncTask<String, String, FirebaseChat> {
        private FirebaseChatCallback callback;
        public GenerateFirebaseChatId(FirebaseChatCallback callback) {
            this.callback = callback;
        }
        @Override
        protected FirebaseChat doInBackground(String... strings) {
            String userId1 = strings[0];
            String userId2 = strings[0];
            FirebaseChat fbchat = null;
            try {
                fbchat = firebaseChatApi.getFirebaseChat(userId1, userId2).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return fbchat;
        }

        @Override
        protected void onPostExecute(FirebaseChat fbchat) {
            super.onPostExecute(fbchat);
            // items has the items of the user.
            callback.onFirebaseChat(fbchat);
        }
    }

    /**
     * Helper functions below!
     */

    private void addImageToItem(String itemId, Bitmap bitmap) {
        Firebase myFirebaseRef = new Firebase("https://altizaken.firebaseio.com/");
        String ss = encodeTobase64(bitmap);
        myFirebaseRef.child(itemId).setValue(ss);
    }

    public void getAndSetImageOfItem(String itemId, final ImageView imageview) {
        Firebase myFirebaseRef = new Firebase("https://altizaken.firebaseio.com/");
        myFirebaseRef.child(itemId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Bitmap bitmap = decodeBase64((String) snapshot.getValue());
                imageview.setImageBitmap(bitmap);
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });

    }

    public static String encodeTobase64(Bitmap image)
    {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }
    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}

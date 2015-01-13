
package com.altizakhen.altizakhenapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.altizakhen.altizakhenapp.backend.altizakhenApi.model.Item;
import com.altizakhen.altizakhenapp.itemsListAdapter.listAdapter;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by t-mansh on 12/29/2014.
 */
public class MyAltizakhen extends Fragment {
    private TextView userInfoTextView;
    private ListView list;
    private ArrayList<Item> list1;
    private ImageButton img;
    private String userId;
    private  Bitmap bitmap;
    private int MyId;

    public int getMyId() {
        return MyId;
    }

    public MyAltizakhen() {
    }


    private static final String TAG = "MainFragment";
    private UiLifecycleHelper uiHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);
    }

    private void onSessionStateChange(Session session, SessionState state,
                                      Exception exception) {
        if (state.isOpened()) {
            // hon bte3'dar et3'ayer el log-in buttun et7ot ma7alo log-out
//            Log.i(TAG, "Logged in...");
            userInfoTextView.setVisibility(View.VISIBLE);
            list.setVisibility(View.VISIBLE);
            img.setVisibility(View.VISIBLE);
            // Request user data and show the results
            Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

                @Override
                public void onCompleted(GraphUser user, Response response) {
                    if (user != null) {
                        // Display the parsed user info
                        userInfoTextView.setText(buildUserInfoDisplay(user));
                        userId = user.getId();
//                        bitmap = getFacebookProfilePicture();
//                        if (bitmap != null) {
//                            img.setImageBitmap(bitmap);
//                        }
                    }
                }
            });

//            new Request(
//                    session,
//                    "/me/picture",
//                    null,
//                    HttpMethod.GET,
//                    new Request.Callback() {
//                        public void onCompleted(Response response) {
//                        response.
//                        }
//                    }
//            ).executeAsync();
        } else if (state.isClosed()) {
//            Log.i(TAG, "Logged out...");
            userInfoTextView.setVisibility(View.INVISIBLE);
            list.setVisibility(View.INVISIBLE);
            img.setVisibility(View.INVISIBLE);
            userId = null;
            MainActivity.userId = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Session session = Session.getActiveSession();
        if (session != null && (session.isOpened() || session.isClosed())) {
            onSessionStateChange(session, session.getState(), null);
        }
        uiHelper.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state,
                         Exception exception) {
            onSessionStateChange(session, state, exception);

        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fblogin_layout, container,
                false);
        list = (ListView) rootView.findViewById(R.id.listView);
        img = (ImageButton) rootView.findViewById(R.id.imageButton2);
        list1 = new ArrayList<Item>();
        populateList();
        list.setAdapter(new listAdapter(getActivity(), list1, 1));
        userInfoTextView = (TextView) rootView.findViewById(R.id.userInfoTextView);
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "djb.ttf");
        userInfoTextView.setTypeface(type);

        LoginButton authButton = (LoginButton) rootView
                .findViewById(R.id.authButton);
        authButton.setReadPermissions(Arrays.asList("public_profile", "user_location"));

        authButton.setFragment(this);
        return rootView;
    }

    private String buildUserInfoDisplay(GraphUser user) {
        StringBuilder userInfo = new StringBuilder("");

        // Example: typed access (name)
        // - no special permissions required
        userInfo.append(String.format("Hello %s,\n\n",
                user.getName()));
//        userInfo.append(String.format("Id: %s\n\n",
//                user.getId()));
        MainActivity.userId = user.getId();

        // Example: partially typed access, to location field,
        // name key (location)
        // - requires user_location permission
//        userInfo.append(String.format("Location: %s\n\n",
//                user.getLocation().getProperty("name")));

        // Example: access via property name (locale)
        // - no special permissions required
//        userInfo.append(String.format("Locale: %s\n\n",
//                user.getProperty("locale")));


        return userInfo.toString();
    }

    private void populateList() {

        for (int i = 0; i < 10; ++i) {
            Item item1 = new Item();
            item1.setName("bed");
            item1.setPrice(75);
            item1.setLocation("haifa");
            item1.setIconId(R.drawable.ic_bed1);
            item1.setSellerId(13);
            item1.setSellerName("Manar");
            item1.setDescription("Brand New");
            list1.add(item1);
        }

        /*
        list.add(new listItem("A thousand Splendid Suns", 100, "2015.01.02", R.drawable.ic_suns));
        list.add(new listItem("Winter hat", 40, "2015.01.11", R.drawable.ic_hat));
        list.add(new listItem("necklace", 140, "2014.10.02", R.drawable.ic_necklace));*/


    }

    //    public class MyAdapter extends BaseAdapter {
//        public MyAdapter() {
//
//        }
//        @Override
//        public int getCount() {
//            return 10;
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return i;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            if (view == null) {
//                LayoutInflater inflator = LayoutInflater.from(getActivity());
//                view = inflator.inflate(R.layout.list_item_view , null);
//
//            }
//            return view;
//        }
//    }
//    public Bitmap getFacebookProfilePicture() {
//        try {
//            Toast.makeText(getActivity(), "https://graph.facebook.com/" + userId + "/picture?type=large",Toast.LENGTH_LONG).show();
//            // return fetchImageUsinHttpClient("http://graph.facebook.com/" + userId + "/picture?type=large");
////            URL imageURL = new URL("https://graph.facebook.com/" + userId + "/picture?type=large");
////            Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
//            return bitmap;
//        } catch (Exception e) {
//            Toast.makeText(getActivity() , "couldn't fetch image from facebook ",Toast.LENGTH_LONG).show();
//            return null;
//        }

//    }


//    private Bitmap fetchImageUsinHttpClient(String url) {
//        try {
//            HttpClient client = new DefaultHttpClient();
//
//            HttpGet request = new HttpGet(url);
//            HttpResponse response = client.execute(request);
//            HttpEntity body = response.getEntity();
//            InputStream is = body.getContent();
//            Bitmap myBitmap = BitmapFactory.decodeStream(is);
//            return myBitmap;
//
//        } catch (Exception e) {
//            Toast.makeText(getActivity(),
//                    "there were a connection problem while downloading the image", Toast.LENGTH_SHORT)
//                    .show();
//        }
//        return null;
//    }

}


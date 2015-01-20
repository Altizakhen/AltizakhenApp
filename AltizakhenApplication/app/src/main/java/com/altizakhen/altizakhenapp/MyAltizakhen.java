
package com.altizakhen.altizakhenapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.altizakhen.altizakhenapp.backend.itemApi.model.Item;
import com.altizakhen.altizakhenapp.chat.MyChatsFragments;
import com.altizakhen.altizakhenapp.itemsListAdapter.listAdapter;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;


/**
 * Created by t-mansh on 12/29/2014.
 */

public class MyAltizakhen extends Fragment {
    private TextView userInfoTextView;
    private ListView list;
    public static List<Item> userItemslist;
    private ImageView img;
    private String userId;
    private int MyId;
    private static Boolean downloadFlag;
    public static listAdapter Mylistadapter;
    public static Button myChatsButton;

    public int getMyId() {
        return MyId;
    }

    public MyAltizakhen() {
        downloadFlag = false;
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
            myChatsButton.setVisibility(View.VISIBLE);
            img.setVisibility(View.VISIBLE);
            // Request user data and show the results
            Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

                @Override
                public void onCompleted(GraphUser user, Response response) {
                    if (user != null) {
                        // Display the parsed user info
                        userInfoTextView.setText(buildUserInfoDisplay(user));
                        userId = user.getId();
                        MainActivity.userFacebookId = user.getId();
                        new DownloadImageTask(img)
                                .execute("https://graph.facebook.com/" + userId + "/picture?type=large");
                        if (downloadFlag == false) {
                            downloadFlag = true;
                            ApiHelper api = new ApiHelper(getActivity());
                            api.addUser(user.getName(), user.getId(), list);
                        }
                    }
                }
            });

        } else if (state.isClosed()) {
//            Log.i(TAG, "Logged out...");
            userInfoTextView.setVisibility(View.INVISIBLE);
            list.setVisibility(View.INVISIBLE);
            img.setVisibility(View.INVISIBLE);
            myChatsButton.setVisibility(View.INVISIBLE);
            userId = null;
            MainActivity.userFacebookId = null;
            MainActivity.userServerId = null;
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
        img = (ImageView) rootView.findViewById(R.id.profileImg);

        if(Mylistadapter != null) {
            list.setAdapter(Mylistadapter);
            list.setOnItemClickListener(new ListView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    MainActivity.currentItemInView = (Item)adapterView.getAdapter().getItem(i);
                    MainActivity.currentItemBitmap = ((listAdapter)adapterView.getAdapter()).getImage(i);
                    Intent intent = new Intent(getActivity(), itemFragment.class);
                    startActivity(intent);
                }
            });
        }

        userInfoTextView = (TextView) rootView.findViewById(R.id.userInfoTextView);
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "djb.ttf");
        userInfoTextView.setTypeface(type);

        myChatsButton = (Button) rootView.findViewById(R.id.go_to_my_chats);
        myChatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.userServerId != null){
                    Intent intent = new Intent(getActivity(), MyChatsFragments.class);
                    getActivity().startActivity(intent);
                }
            }
        });

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
        return userInfo.toString();
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}


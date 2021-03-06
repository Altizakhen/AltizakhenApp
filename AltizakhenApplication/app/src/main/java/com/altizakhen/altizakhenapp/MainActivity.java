package com.altizakhen.altizakhenapp;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.altizakhen.altizakhenapp.adapter.NavDrawerListAdapter;
import com.altizakhen.altizakhenapp.backend.itemApi.model.Item;
import com.altizakhen.altizakhenapp.backend.userApi.model.User;
import com.altizakhen.altizakhenapp.model.NavDrawerItem;
import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity {

    static public ArrayList<Item> cart;
    // item parameters
    public static Item currentItemInView;
    public static Bitmap currentItemBitmap;

    public int currentFragmentIndex ;
    private Fragment[] fragments;


    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    static String userFacebookId;
    public static String userServerId;
    public static User user;

    static public ArrayList<Item> catItems;


    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    static public boolean isConnected = true;

    static public List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isConnected = isConnected();
        if (!isConnected) {
            Toast.makeText(this, "Please connect to the internet.", Toast.LENGTH_LONG).show();
            finish();
            return;
        } else {

            Firebase.setAndroidContext(this);
            fragments = new Fragment[3];
            fragments[0] = new HomeFragment();
            fragments[1] = new categoriesFragment();
            fragments[2] = new MyAltizakhen();

            this.cart = new ArrayList<Item>();

            ApiHelper api = new ApiHelper(this);
            api.getAllItems();
            mTitle = mDrawerTitle = getTitle();

            // load slide menu items
            navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

            // nav drawer icons from resources
            navMenuIcons = getResources()
                    .obtainTypedArray(R.array.nav_drawer_icons);

            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

            navDrawerItems = new ArrayList<NavDrawerItem>();

            navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));


            // Recycle the typed array
            navMenuIcons.recycle();

            mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

            // setting the nav drawer list adapter
            adapter = new NavDrawerListAdapter(getApplicationContext(),
                    navDrawerItems);
            mDrawerList.setAdapter(adapter);

            // enabling action bar app icon and behaving it as toggle button
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setHomeButtonEnabled(true);

            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                    R.drawable.ic_list
                    , //nav menu toggle icon
                    R.string.app_name, // nav drawer open - description for accessibility
                    R.string.app_name // nav drawer close - description for accessibility
            ) {
                public void onDrawerClosed(View view) {
                    getActionBar().setTitle(mDrawerTitle);
                    // calling onPrepareOptionsMenu() to show action bar icons
                    invalidateOptionsMenu();
                }

                public void onDrawerOpened(View drawerView) {
                    getActionBar().setTitle(mDrawerTitle);
                    // calling onPrepareOptionsMenu() to hide action bar icons
                    invalidateOptionsMenu();
                }
            };
            mDrawerLayout.setDrawerListener(mDrawerToggle);

            if (savedInstanceState == null) {
                // on first time display view for first nav item
                displayView(0);
            } else {
                currentFragmentIndex = savedInstanceState.getInt("fragmentIndex");
                displayView(currentFragmentIndex);
            }


            BroadcastReceiver networkStateReceiver = new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {
//                Toast.makeText(this, "Please connect to internet.", Toast.LENGTH_LONG).show();
//                String action = intent.getAction();
//                if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                    if (!isConnected()) {
                        Toast.makeText(context, "No intenet. Please connect to internet", Toast.LENGTH_LONG).show();
                        finish();
                    }
//                }


                }
            };

            IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(networkStateReceiver, filter);

            if (savedInstanceState == null) {
                currentFragmentIndex = 0;
                getSupportFragmentManager().beginTransaction()
                        .add(new PlaceholderFragment(), " ")
                        .commit();
            } else {

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .attach( fragments[currentFragmentIndex]).commit();
            }
        }
    }

    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) searchView.findViewById(id);
        textView.setTextColor(Color.BLACK);


        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!isConnected) {
            return false;
        }
        // Handle presses on the action bar items
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        Intent intent = null;

        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.cart:
                intent = new Intent(this, cartView.class);
                break;
            case R.id.new_item:
                intent = new Intent(this, newItemFragment.class);
                break;
            case R.id.search:
                  return true;
        }

        Fragment fragment = null;
        if(MainActivity.userFacebookId == null) {
            fragment = new pleaseLoginFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
            return true;
        }
        startActivity(intent);

        return super.onOptionsItemSelected(item);

    }

    /**
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
//        if (mDrawerLayout != null) {
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        if (!isConnected) {
            return false;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
//                fragment = new HomeFragment();
                if(MainActivity.items == null) {
                    fragment = null;
                }else {
                    fragment = fragments[0];
                    currentFragmentIndex = 0;
                }

                break;
            case 1:

                fragment = fragments[1];
                currentFragmentIndex = 1;
                break;
            case 2:
                fragment = fragments[2];
                currentFragmentIndex = 2;
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mDrawerTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("fragmentIndex",currentFragmentIndex);
    }



    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            return rootView;
        }
    }

}




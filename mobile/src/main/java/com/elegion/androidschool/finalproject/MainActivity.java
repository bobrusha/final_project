package com.elegion.androidschool.finalproject;


import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.elegion.androidschool.finalproject.db.Contract;
import com.elegion.androidschool.finalproject.loader.GeofencesLoader;
import com.elegion.androidschool.finalproject.loader.LoadersId;
import com.elegion.androidschool.finalproject.service.GeofenceService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<Status>,
        LoaderManager.LoaderCallbacks<Cursor> {

    public static final int REQUEST_CODE = 12;
    protected GoogleApiClient mGoogleApiClient;
    protected ArrayList<Geofence> mGeofenceArrayList;

    private SharedPreferences mSharedPreferences;
    private PendingIntent mGeofencePendingInten;


    private Fragment mFragment;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private static final String SHARED_PREFERENCES_NAME = "markets_shared_preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        mFragment = new ListsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.must_be_replaced, mFragment)
                .commit();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.left_drawer);
        mNavigationView.setNavigationItemSelectedListener(this);


        mGeofenceArrayList = new ArrayList<>();

        mSharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        buildGoogleAoiClient();
        getSupportLoaderManager().initLoader(LoadersId.GEOFENCES_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(mNavigationView);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Fragment getFragment() {
        return mFragment;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        mDrawerLayout.closeDrawer(mNavigationView);
        int oldFragmentId = mFragment.getId();
        switch (id) {
            case R.id.navigation_item_lists:
                mFragment = new ListsFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(oldFragmentId, mFragment)
                        .commit();
                return true;
            case R.id.navigation_item_products:
                mFragment = new ProductsListFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(oldFragmentId, mFragment)
                        .commit();
                return true;
            case R.id.navigation_item_markets:
                mFragment = new MarketsFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(oldFragmentId, mFragment)
                        .commit();
                return true;
        }
        return false;
    }

    private synchronized void buildGoogleAoiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        Log.v("qq", "Connect GoogleApi");
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    public void addGeofences() {
        if (!mGoogleApiClient.isConnected()) {
            //TODO: show snackBar
            Log.v("qq", "googleApi isnt connected");
            return;
        }
        try {
            LocationServices.GeofencingApi.addGeofences(
                    mGoogleApiClient,
                    getGeofencingRequest(),
                    getGeofencePendingIntent()
            ).setResultCallback(this);
            Log.v("qq", "In addGeofences");
        } catch (SecurityException e) {
            Log.v("qq", "security exception");
        }
    }

    protected GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceArrayList);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        if (mGeofencePendingInten != null) {
            return mGeofencePendingInten;
        }
        Intent intent = new Intent(this, GeofenceService.class);
        Log.v("qq", "In getGeofencePendingIntent");
        return PendingIntent.getService(this, REQUEST_CODE, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.v("qq", "Connected");
        //addGeofences();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.v("qq", "Not connected");
    }

    @Override
    public void onResult(Status status) {
        Log.v("qq", "In on result");
        if (status.isSuccess()) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putBoolean("geofences_was_added", true);
            editor.apply();
        }
    }

    public void populateList(Cursor cursor) {
        float radius = 100;
        String marketName = null;
        Double lat;
        Double lng;

        while (cursor.isAfterLast()) {
            marketName = cursor.getString(cursor.getColumnIndex(Contract.MarketEntity.TABLE_NAME +
                    "." + Contract.MarketEntity.COLUMN_NAME));
            lat = cursor.getDouble(cursor.getColumnIndex(Contract.MarketEntity.TABLE_NAME +
                    "." + Contract.MarketEntity.COLUMN_LATITUDE));

            lng = cursor.getDouble(cursor.getColumnIndex(Contract.MarketEntity.TABLE_NAME +
                    "." + Contract.MarketEntity.COLUMN_LONGITUDE));

            mGeofenceArrayList.add(new Geofence.Builder()
                            .setRequestId(marketName)
                            .setCircularRegion(lat, lng, radius)
                            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                            .setExpirationDuration(Geofence.NEVER_EXPIRE)
                            .build()
            );
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new GeofencesLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() > 0) {
            data.moveToFirst();
            populateList(data);
            addGeofences();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

package com.elegion.androidschool.finalproject;


import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

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
        ResultCallback<Status> {

    public static final int REQUEST_CODE = 12;
    private GoogleApiClient mGoogleApiClient;
    private ArrayList<Geofence> mGeofenceArrayList;
    private PendingIntent mGeofencePendingInten;


    private Fragment mFragment;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        mFragment = new ListsFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.must_be_replaced, mFragment)
                .commit();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.left_drawer);
        mNavigationView.setNavigationItemSelectedListener(this);

        mGeofenceArrayList = new ArrayList<>();
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
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    public void addGeofences() {
        if (!mGoogleApiClient.isConnected()) {
            //TODO: show snackBar
            return;
        }
        LocationServices.GeofencingApi.addGeofences(
                mGoogleApiClient,
                getGeofencingRequest(),
                getGeofencePendingIntent()
        ).setResultCallback(this);
    }

    private GeofencingRequest getGeofencingRequest() {
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
        return PendingIntent.getService(this, REQUEST_CODE, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onResult(Status status) {
        if (status.isSuccess()) {
            //TODO: impl
        }
    }

    public void populateList() {
        mGeofenceArrayList.add(new Geofence.Builder()
                        .setRequestId("SEMYA")
                        .setCircularRegion(59.844900, 30.322769, 100)
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                        .build()
        );
        mGeofenceArrayList.add(new Geofence.Builder()
                        .setRequestId("LETO")
                        .setCircularRegion(59.819479, 30.316482, 100)
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                        .build()
        );
    }
}

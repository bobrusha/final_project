package com.elegion.androidschool.finalproject;

import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.elegion.androidschool.finalproject.adapter.Constants;
import com.elegion.androidschool.finalproject.db.Contract;
import com.elegion.androidschool.finalproject.loader.InfoAboutMarketLoader;
import com.elegion.androidschool.finalproject.loader.LoadersId;
import com.elegion.androidschool.finalproject.model.Market;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A placeholder fragment containing a simple view.
 */
public class InfoAboutMarketFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final float ZOOM = 18;
    private static final float BEARING = 40;
    private static final float TITLE = 0;


    private Market mMarketModel;

    protected GoogleApiClient mGoogleApiClient;
    private MapFragment mMapFragment;
    private Marker mMarker;

    private Toolbar mToolbar;
    private EditText mMarketNameEditText;
    private FloatingActionButton mFab;

    private GoogleMapOptions mGoogleMapOptions = new GoogleMapOptions();

    public InfoAboutMarketFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_about_market, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar = (Toolbar) view.findViewById(R.id.info_about_market_toolbar);
        mMarketNameEditText = (EditText) view.findViewById(R.id.edit_text_market_name);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mFab = (FloatingActionButton) activity.findViewById(R.id.fab_save_info_about_market);
        mFab.setOnClickListener(new OnSaveButtonClickListener());

        long marketId = getActivity().getIntent().getLongExtra(Extras.EXTRA_MARKET_ID, -1);
        if (marketId > 0) {
            Bundle bundle = new Bundle();
            bundle.putLong(Extras.EXTRA_MARKET_ID, marketId);
            getLoaderManager().initLoader(LoadersId.INFO_ABOUT_MARKET_LOADER, bundle, this);
        }

        buildGoogleApiClient();

        if (savedInstanceState == null) {
            mMapFragment = MapFragment.newInstance(mGoogleMapOptions);
            activity
                    .getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.info_about_market_map_container, mMapFragment)
                    .commit();
        }

        mMapFragment.getMapAsync(this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new InfoAboutMarketLoader(getActivity(), args.getLong(Extras.EXTRA_MARKET_ID));

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() > 0) {
            data.moveToFirst();
            long marketId = data.getLong(data.getColumnIndex(Contract.MarketEntity._ID));
            String marketName = data.getString(data.getColumnIndex(Contract.MarketEntity.COLUMN_NAME));
            double latitude = data.getDouble(data.getColumnIndex(Contract.MarketEntity.COLUMN_LATITUDE));
            double longitude = data.getDouble(data.getColumnIndex(Contract.MarketEntity.COLUMN_LONGITUDE));
            mMarketModel = new Market(marketId, marketName, latitude, longitude);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void setData() {
        LatLng latLng = new LatLng(59.931930, 30.343385);
        if (mMarketModel == null) {
            Location userLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (userLocation != null) {
                latLng = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
            }
        } else {
            mMarketNameEditText.setText(mMarketModel.getName());
            latLng = new LatLng(mMarketModel.getLatitude(), mMarketModel.getLongitude());
            mMarker = mMapFragment.getMap()
                    .addMarker(
                            new MarkerOptions()
                                    .position(latLng).draggable(true)
                    );
        }
        mMapFragment.getMap()
                .moveCamera(
                        CameraUpdateFactory.newCameraPosition(
                                new CameraPosition(latLng, ZOOM, TITLE, BEARING)));
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (mMarker != null) {
                    mMarker.remove();
                }
                mMarker = googleMap.addMarker(
                        new MarkerOptions()
                                .position(latLng)
                                .draggable(true)
                                .alpha(0.7f)
                );
            }
        });
        setData();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.v("qq", "connected");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private void buildGoogleApiClient() {

        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(getActivity());
        builder
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API);
        mGoogleApiClient = builder.build();
    }

    public class OnSaveButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String marketName = mMarketNameEditText.getText().toString();
            LatLng position = mMarker.getPosition();

            if (marketName.isEmpty()) {
                //TODO: show dialog
                Log.v(Constants.LOG_TAG, "Name is empty");
                return;
            }
            if (position == null) {
                //TODO:show dialog
                Log.v(Constants.LOG_TAG, "Position is null");
                return;
            }
            if (mMarketModel == null) {
                mMarketModel = new Market(marketName);
            }
            mMarketModel.setName(marketName);
            mMarketModel.setLatitude(position.latitude);
            mMarketModel.setLongitude(position.longitude);
            MyApplication.getStorIOSQLite()
                    .put()
                    .object(mMarketModel)
                    .prepare()
                    .executeAsBlocking();
            getActivity().onBackPressed();
        }
    }


}

package com.elegion.androidschool.finalproject;

import android.app.FragmentManager;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
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

import com.elegion.androidschool.finalproject.db.Contract;
import com.elegion.androidschool.finalproject.loader.InfoAboutMarketLoader;
import com.elegion.androidschool.finalproject.model.Market;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

/**
 * A placeholder fragment containing a simple view.
 */
public class InfoAboutMarketFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final float ZOOM = 18;
    private static final float BEARING = 40;


    private Market mMarketModel;

    protected GoogleApiClient mGoogleApiClient;
    private MapFragment mMapFragment;
    private EditText mMarketNameEditText;

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

        mMarketNameEditText = (EditText) view.findViewById(R.id.edit_text_market_name);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        long marketId = getActivity().getIntent().getLongExtra(Extras.EXTRA_MARKET_ID, -1);
        if (marketId > 0) {
            Bundle bundle = new Bundle();
            bundle.putLong(Extras.EXTRA_MARKET_ID, marketId);
            getLoaderManager().initLoader(LoadersId.INFO_ABOUT_MARKET_LOADER, null, this);
        }
        buildGoogleApiClient();
        setData();
        if (savedInstanceState == null) {
            mMapFragment = MapFragment.newInstance(mGoogleMapOptions);
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            FragmentManager ma = activity.getFragmentManager();
            Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.info_about_market_map_container);
            android.app.Fragment fragment1 = activity.getFragmentManager().findFragmentById(R.id.info_about_market_map_container);
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
        if (mMarketModel == null) {
            Location userLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (userLocation != null) {
                mGoogleMapOptions.camera(
                        CameraPosition
                                .builder()
                                .target(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()))
                                .zoom(ZOOM)
                                .bearing(BEARING)
                                .build()
                );
            } else {
                mGoogleMapOptions.camera(
                        CameraPosition
                                .builder()
                                .target(new LatLng(59.931930, 30.343385))
                                .zoom(ZOOM)
                                .bearing(BEARING)
                                .build()
                );
            }

        } else {
            mMarketNameEditText.setText(mMarketModel.getName());
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //TODO: implement
            }
        });
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
}

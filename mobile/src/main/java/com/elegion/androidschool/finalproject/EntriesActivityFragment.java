package com.elegion.androidschool.finalproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class EntriesActivityFragment extends Fragment {

    public EntriesActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("qq", "onCreatedView EnriesActivity");
        return inflater.inflate(R.layout.fragment_entries, container, false);
    }
}

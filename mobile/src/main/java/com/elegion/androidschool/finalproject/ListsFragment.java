package com.elegion.androidschool.finalproject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elegion.androidschool.finalproject.event.ListSelectedEvent;
import com.elegion.androidschool.finalproject.event.MyBus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListsFragment extends Fragment {
    private static final String ARG_LISTS_ARRAY = "lists_array";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<String> mStringArrayList = new ArrayList<>();

    public static ListsFragment newInstance(ArrayList<String> lists) {
        ListsFragment frag = new ListsFragment();

        Bundle b = new Bundle();
        b.putStringArrayList(ARG_LISTS_ARRAY, lists);
        frag.setArguments(b);
        return frag;
    }

    public ListsFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        MyBus.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MyBus.getInstance().unregister(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStringArrayList = getArguments().getStringArrayList(ARG_LISTS_ARRAY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_lists, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.lists_list);
        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(new ListToCardAdapter(mStringArrayList));

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab_add_new_list);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: dialog for creating new list
            }
        });
        return root;
    }

    @Subscribe
    public void listSelectedEventAvailable(ListSelectedEvent event) {
        Log.d("qq", "Bus transport event to target methods");
        event.getId();

        startActivity(new Intent(getActivity(), ItemsActivity.class));
    }
}

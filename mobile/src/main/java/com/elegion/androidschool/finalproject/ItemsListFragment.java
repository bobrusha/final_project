package com.elegion.androidschool.finalproject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class ItemsListFragment extends Fragment {
    private static final String ARG_ITEMS_ARRAY = "items_array";
    private RecyclerView mItemsListView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> mItemsArrayString;

    public static ItemsListFragment newInstance(ArrayList<String> items) {
        ItemsListFragment frag = new ItemsListFragment();

        Bundle b = new Bundle();
        b.putStringArrayList(ARG_ITEMS_ARRAY, items);
        frag.setArguments(b);
        return frag;
    }

    public ItemsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItemsArrayString = getArguments().getStringArrayList(ARG_ITEMS_ARRAY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_items_list, container, false);

        mItemsListView = (RecyclerView) root.findViewById(R.id.items_list);
        mItemsListView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mItemsListView.setLayoutManager(mLayoutManager);
        mItemsListView.setAdapter(new ItemsAdapter(mItemsArrayString));

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab_add_new_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CreateNewItemActivity.class));
            }
        });
        return root;
    }


}

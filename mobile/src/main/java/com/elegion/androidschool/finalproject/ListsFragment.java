package com.elegion.androidschool.finalproject;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        return root;
    }
}

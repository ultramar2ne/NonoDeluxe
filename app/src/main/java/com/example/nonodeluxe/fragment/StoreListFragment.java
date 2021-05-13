package com.example.nonodeluxe.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.adapter.StoreListAdapter;
import com.example.nonodeluxe.model.PrdItem;
import com.example.nonodeluxe.model.StoreItem;

import java.util.ArrayList;

public class StoreListFragment extends Fragment {

    private OnFragmentInteractionListener listener;

    ArrayList<StoreItem> storeItems = new ArrayList<>();
    RecyclerView recyclerView;
    Toolbar toolbar;
    StoreListAdapter adapter;

    public static StoreListFragment newInstance(ArrayList<PrdItem> prdItems) {
        StoreListFragment fragment = new StoreListFragment();
        Bundle args = new Bundle();
        args.putSerializable("PrdItems",prdItems);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            storeItems = (ArrayList<StoreItem>)getArguments().getSerializable("StoreItems");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_list, container, false);
        recyclerView = view.findViewById(R.id.frag_storeList_recyclerview);
        toolbar = view.findViewById(R.id.frag_storeList_toolbar);

        toolbar.setTitle("제품 선택");

        setRecyclerView();


        // Inflate the layout for this fragment
        return view;
    }

    private void setRecyclerView() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new StoreListAdapter(storeItems);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new StoreListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                StoreItem selectedItem = storeItems.get(position);
                if (listener != null){
                    listener.onFragmentInteraction(selectedItem);
                }
            }
        });

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(StoreItem prdItem);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
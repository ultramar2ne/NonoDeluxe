package com.example.nonodeluxe.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.adapter.PrdListAdapter;
import com.example.nonodeluxe.model.ViewHolderCasePrd;
import com.example.nonodeluxe.model.PrdItem;

import java.util.ArrayList;

public class PrdListFragment extends Fragment {

    private OnFragmentInteractionListener listener;

    ArrayList<PrdItem> prdItems = new ArrayList<>();
    RecyclerView recyclerView;
    Toolbar toolbar;
    PrdListAdapter adapter;

    public static PrdListFragment newInstance(ArrayList<PrdItem> prdItems) {
        PrdListFragment fragment = new PrdListFragment();
        Bundle args = new Bundle();
        args.putSerializable("PrdItems",prdItems);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            prdItems = (ArrayList<PrdItem>)getArguments().getSerializable("PrdItems");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prd_list, container, false);
        recyclerView = view.findViewById(R.id.frag_prdList_recyclerview);
        toolbar = view.findViewById(R.id.frag_prdList_toolbar);
        toolbar.setTitle("제품 선택");

        setRecyclerView();

        // Inflate the layout for this fragment
        return view;
    }

    private void setRecyclerView() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new PrdListAdapter(ViewHolderCasePrd.LIST,prdItems);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new PrdListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                PrdItem selectedItem = prdItems.get(position);
                if (listener != null){
                    listener.onFragmentInteraction(selectedItem);
                }
            }
        });
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(PrdItem prdItem);
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
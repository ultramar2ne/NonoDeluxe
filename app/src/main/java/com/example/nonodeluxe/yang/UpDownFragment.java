package com.example.nonodeluxe.yang;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.adapter.InventoryAdapterYang;
import com.example.nonodeluxe.fragment.PrdListFragment;
import com.example.nonodeluxe.model.InventoryItemVerYang;
import com.example.nonodeluxe.viewmodel.InventoryYangViewModel;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class UpDownFragment extends Fragment {

    private PrdListFragment.OnFragmentInteractionListener listener;

    ArrayList<InventoryItemVerYang> inventoryItems = new ArrayList<>();

    RecyclerView recyclerView;
    Toolbar toolbar;
    InventoryAdapterYang adapter;
    InventoryYangViewModel viewModel;

    public UpDownFragment() {
        // Required empty public constructor
    }

    public static UpDownFragment newInstance( ) {
        return new UpDownFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_up_down,container,false);

        recyclerView = rootView.findViewById(R.id.frag_updown_recycler);
        toolbar = rootView.findViewById(R.id.frag_updown_toolbar);
        toolbar.setTitle("입출고 관리");
        viewModel = new ViewModelProvider(requireActivity()).get(InventoryYangViewModel.class);

        LiveData<DataSnapshot> liveData = viewModel.getRestrictedSnapshotLiveData();
        liveData.observe(requireActivity(), new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                for (DataSnapshot currentSnapshot : dataSnapshot.getChildren()){
                    InventoryItemVerYang currentItem = currentSnapshot.getValue(InventoryItemVerYang.class);
                    if (currentItem.getType() == 2){
                        inventoryItems.add(currentItem);
                    }

                }

                setRecyclerView();
            }
        });

        return rootView;
    }

    private void setRecyclerView() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new InventoryAdapterYang(inventoryItems);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

//        adapter.setOnItemClickListener(new PrdListAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                InventoryItemVerYang selectedItem = inventoryItems.get(position);
//                if (listener != null){
//                    listener.onFragmentInteraction(selectedItem);
//                }
//            }
//        });
    }
}
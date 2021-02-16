package com.example.nonodeluxe.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.adapter.StoreAdapter;
import com.example.nonodeluxe.model.StoreItem;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UnitListSheetDialog extends BottomSheetDialogFragment implements StoreAdapter.OnItemClickListener {
    private BottomSheetListener listener;

    private RecyclerView recyclerView;
    private StoreAdapter adapter;
    private ArrayList<StoreItem> storeItems = new ArrayList<>();

    private String unitName;

    private int unit_code = 5001;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_bottom_sheet, container, false);

        recyclerView = view.findViewById(R.id.recycler_bottomSheet);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        setStoreData();

        adapter = new StoreAdapter(storeItems);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onItemClick(int position) {
        unitName = storeItems.get(position).getStore_name();
        String sendBackText = unitName;
        sendBack(sendBackText);
    }

    private void sendBack(String sendBackText) {
        if (listener != null){
            listener.bottomSheetListener(sendBackText);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
//        listener = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface BottomSheetListener{
        void bottomSheetListener(String sedBackText);
    }

    private void setStoreData() {
        FirebaseDatabase.getInstance().getReference()
                .child("info").child("store").orderByChild("unit_code").equalTo(unit_code).
                addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                storeItems.clear();
                for (DataSnapshot currentSnapshot : snapshot.getChildren()){
                    String key = currentSnapshot.getKey();
                    StoreItem storeItem = currentSnapshot.getValue(StoreItem.class);
                    storeItems.add(storeItem);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

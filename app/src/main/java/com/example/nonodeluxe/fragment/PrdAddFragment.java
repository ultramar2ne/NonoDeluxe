package com.example.nonodeluxe.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.adapter.PrdListAdapter;
import com.example.nonodeluxe.model.MyItem;
import com.example.nonodeluxe.model.PrdCase;
import com.example.nonodeluxe.model.PrdItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PrdAddFragment extends Fragment {

    RecyclerView recyclerView;
    PrdListAdapter adapter;

    ArrayList<MyItem> prdItems = new ArrayList<>();


    public PrdAddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_prd_add, container, false);
        recyclerView = view.findViewById(R.id.prdAdd_recycler);

        fillData();
        setRecyclerView();


        // Inflate the layout for this fragment
        return view;
    }

    private void fillData() {
        FirebaseDatabase.getInstance().getReference()
                .child("real").child("product")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot currentSnapshot:snapshot.getChildren()){
                            PrdItem prdItem = currentSnapshot.getValue(PrdItem.class);
                            prdItems.add(prdItem);
                        }
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new PrdListAdapter(PrdCase.ADD,prdItems);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new PrdListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                String currentPrdName = prdItems.get(position).getName();
//                Toast.makeText(getApplicationContext(),currentPrdName,Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getApplicationContext(), PrdInfoActivity.class);
//                intent.putExtra("prd_name",currentPrdName);
//                startActivity(intent);
            }
        });
    }
}
package com.example.nonodeluxe.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nonodeluxe.PrdListActivity;
import com.example.nonodeluxe.R;
import com.example.nonodeluxe.adapter.MainMenuAdapter;
import com.example.nonodeluxe.adapter.StoreAdapterSpinner;
import com.example.nonodeluxe.model.MainMenuItem;
import com.example.nonodeluxe.model.StoreItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements MainMenuAdapter.OnItemClickListener, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private MainMenuAdapter mainMenuAdapter;
    private StoreAdapterSpinner spinnerAdapter;

    Spinner spinner;
    TextView unit_name;

    private ArrayList<MainMenuItem> menuItems;
    private ArrayList<StoreItem> storeItems;
    private ArrayList<String> strings;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        fillStoreList();
        fillEampleList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unit_name = view.findViewById(R.id.home_unitName);
        unit_name.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.recycler_home);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);

        mainMenuAdapter = new MainMenuAdapter(menuItems);
        mainMenuAdapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mainMenuAdapter);


        spinner = view.findViewById(R.id.home_spinner);

        ArrayAdapter<String> adpater1 = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,strings);
        adpater1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerAdapter = new StoreAdapterSpinner(getActivity(),storeItems);
        spinner.setAdapter(adpater1);

        spinner.setOnItemSelectedListener(this);

        return view;
    }

    private void fillStoreList() {
        storeItems = new ArrayList<>();
        strings = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference()
                .child("info").child("store").orderByChild("unit_code").equalTo(5001).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        storeItems.clear();
                        for (DataSnapshot currentSnapshot : snapshot.getChildren()){
                            String key = currentSnapshot.getKey();
                            StoreItem storeItem = currentSnapshot.getValue(StoreItem.class);
                            storeItems.add(storeItem);
                            strings.add(storeItem.getStore_name());
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void fillEampleList() {
        menuItems = new ArrayList<>();

        menuItems.add(new MainMenuItem(R.drawable.ic_circle_blue,"입고"));
        menuItems.add(new MainMenuItem(R.drawable.ic_circle_red,"출고"));
        menuItems.add(new MainMenuItem(R.drawable.ic_circle_green,"수정"));
        menuItems.add(new MainMenuItem(R.drawable.ic_circle_navy,"제품목록"));
        menuItems.add(new MainMenuItem(R.drawable.ic_circle_blue,"hello"));
    }

    @Override
    public void onItemClick(int position) {
        if (position == 1){

        }
        if (position == 2){

        }
        if (position == 3){
            Intent intent = new Intent(getActivity(), PrdListActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == unit_name){

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Object string = adapterView.getItemAtPosition(i);
        if (string != null){
            Toast.makeText(getActivity(),strings.get(i),Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(),"hello",Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(getActivity(),"젠장 제발좀",Toast.LENGTH_SHORT).show();
    }
}
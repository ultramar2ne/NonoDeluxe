package com.example.nonodeluxe.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
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

import com.example.nonodeluxe.MainActivity;
import com.example.nonodeluxe.PrdListActivity;
import com.example.nonodeluxe.Preferences;
import com.example.nonodeluxe.R;
import com.example.nonodeluxe.StockChangeActivity;
import com.example.nonodeluxe.adapter.MainMenuAdapter;
import com.example.nonodeluxe.model.MainMenuItem;
import com.example.nonodeluxe.model.StoreItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainEmpFragment extends Fragment implements MainMenuAdapter.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private MainMenuAdapter mainMenuAdapter;
    ArrayAdapter<String> arrayAdapter;

    TextView empName;
    Spinner spinner;
    Toolbar toolbar;

    private ArrayList<MainMenuItem> menuItems;
    private ArrayList<StoreItem> storeItems;
    private ArrayList<String> strings;

    int stringIndex = 0;
    String inputData;

    public MainEmpFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            inputData = getArguments().getString("store_name");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_emp, container, false);

        fillStoreList();
        fillExampleList();

        toolbar = view.findViewById(R.id.main_emp_toolbar);
        empName = view.findViewById(R.id.main_empName);
        spinner = view.findViewById(R.id.spinner);

        arrayAdapter = new ArrayAdapter<>(getActivity(),R.layout.style_spinner,strings);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

        toolbar.setTitle(Preferences.getString(getActivity(),"unitName"));
        empName.setText("담당자: " +  Preferences.getString(getActivity(),"id"));


        recyclerView = view.findViewById(R.id.recycler_home);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);

        mainMenuAdapter = new MainMenuAdapter(menuItems);
        mainMenuAdapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mainMenuAdapter);

        return view;
    }

    private void fillStoreList() {
        storeItems = new ArrayList<>();
        strings = new ArrayList<>();
        int unitCode = Preferences.getInt(getActivity(),"unitCode");

        FirebaseDatabase.getInstance().getReference()
                .child("info").child("store").orderByChild("unit_code").equalTo(unitCode).
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
                        arrayAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fillExampleList() {
        menuItems = new ArrayList<>();

        menuItems.add(new MainMenuItem(R.drawable.ic_compare_arrows,"입출고"));
        menuItems.add(new MainMenuItem(R.drawable.ic_main_list,"제품목록"));
    }

    @Override
    public void onItemClick(int position) {
        switch (position){
            case 0:
                startActivity(new Intent(getActivity(),StockChangeActivity.class));
                break;
            case 1:
                startActivity(new Intent(getActivity(),PrdListActivity.class));
                break;
            case 2:
                break;
            case 3:

                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        MainActivity.currentStoreCode = storeItems.get(position).getStore_code();
//        Toast.makeText(getActivity(),storeItems.get(position).getStore_name(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
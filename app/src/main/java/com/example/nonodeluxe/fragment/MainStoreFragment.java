package com.example.nonodeluxe.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nonodeluxe.PrdListActivity;
import com.example.nonodeluxe.PrdNewActivity;
import com.example.nonodeluxe.R;
import com.example.nonodeluxe.StockChangeActivity;
import com.example.nonodeluxe.adapter.MainMenuAdapter;
import com.example.nonodeluxe.model.MainMenuItem;

import java.util.ArrayList;


public class MainStoreFragment extends Fragment implements MainMenuAdapter.OnItemClickListener {

    private ArrayList<MainMenuItem> menuItems;

    MainMenuAdapter mainMenuAdapter;
    RecyclerView recyclerView;
    TextView textView;

    String inputData;


    public MainStoreFragment() {
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
        View view = inflater.inflate(R.layout.fragment_main_store,container,false);

        fillMenuList();

        textView = view.findViewById(R.id.main_store_txt);

        recyclerView = view.findViewById(R.id.main_store_recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);

        mainMenuAdapter = new MainMenuAdapter(menuItems);
        mainMenuAdapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mainMenuAdapter);

        return view;
    }

    private void fillMenuList() {
        menuItems = new ArrayList<>();

        menuItems.add(new MainMenuItem(R.drawable.ic_arrow_downward,"입고"));
        menuItems.add(new MainMenuItem(R.drawable.ic_arrow_upward,"출고"));
        menuItems.add(new MainMenuItem(R.drawable.ic_main_list,"제품목록"));
    }

    @Override
    public void onItemClick(int position) {
        switch (position){
            case 0:
                startActivity(new Intent(getActivity(), StockChangeActivity.class));
                break;
            case 1:
//                startActivity(new Intent(getActivity(), StockChangeActivity.class));
                break;
            case 2:
                startActivity(new Intent(getActivity(), PrdListActivity.class));
                break;
            case 3:

                break;
        }
    }
}
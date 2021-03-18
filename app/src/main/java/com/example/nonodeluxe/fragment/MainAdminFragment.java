package com.example.nonodeluxe.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.adapter.MainMenuAdapter;
import com.example.nonodeluxe.model.MainMenuItem;

import java.util.ArrayList;

public class MainAdminFragment extends Fragment implements MainMenuAdapter.OnItemClickListener {

    private ArrayList<MainMenuItem> menuItems;

    MainMenuAdapter mainMenuAdapter;
    RecyclerView recyclerView;
    Toolbar toolbar;

    public MainAdminFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_admin, container, false);
        fillMenuItems();

        toolbar = view.findViewById(R.id.main_admin_toolbar);
        toolbar.setTitle("관리자모드");

        recyclerView = view.findViewById(R.id.main_admin_recycler);
        recyclerView.setHasFixedSize(true);
        mainMenuAdapter = new MainMenuAdapter(menuItems);
        mainMenuAdapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(mainMenuAdapter);

        return view;
    }

    private void fillMenuItems() {
        menuItems = new ArrayList<>();

        menuItems.add(new MainMenuItem(R.drawable.ic_circle_blue,"입고"));
        menuItems.add(new MainMenuItem(R.drawable.ic_circle_red,"출고"));
        menuItems.add(new MainMenuItem(R.drawable.ic_circle_green,"수정"));
        menuItems.add(new MainMenuItem(R.drawable.ic_circle_navy,"제품목록"));
    }

    @Override
    public void onItemClick(int position) {
        switch (position){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }
}
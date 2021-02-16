package com.example.nonodeluxe.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.nonodeluxe.PrdListActivity;
import com.example.nonodeluxe.R;
import com.example.nonodeluxe.adapter.MenuAdapter;
import com.example.nonodeluxe.model.ItemMenu;

import java.util.ArrayList;
import java.util.Objects;


public class HomeFragment extends Fragment implements MenuAdapter.OnItemClickListener, View.OnClickListener, UnitListSheetDialog.BottomSheetListener{

    private RecyclerView recyclerView;
    private MenuAdapter menuAdapter;

    TextView unit_name;

    private ArrayList<ItemMenu> menuItems;

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

        fillEampleList();
    }

    private void fillEampleList() {
        menuItems = new ArrayList<>();

        menuItems.add(new ItemMenu(R.drawable.ic_circle_blue,"입고"));
        menuItems.add(new ItemMenu(R.drawable.ic_circle_red,"출고"));
        menuItems.add(new ItemMenu(R.drawable.ic_circle_green,"수정"));
        menuItems.add(new ItemMenu(R.drawable.ic_circle_navy,"제품목록"));
        menuItems.add(new ItemMenu(R.drawable.ic_circle_blue,"hello"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        unit_name = view.findViewById(R.id.home_unitName);
        unit_name.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.recycler_home);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);

        menuAdapter = new MenuAdapter(menuItems);

        menuAdapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(menuAdapter);

        return view;
    }

    @Override
    public void onItemClick(int position) {
        if (position == 1){
            UnitListSheetDialog bottomSheet = new UnitListSheetDialog();
            bottomSheet.show(getActivity().getSupportFragmentManager(), "TAG");
        }
        if (position == 3){
            Intent intent = new Intent(getContext(), PrdListActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == unit_name){
            UnitListSheetDialog bottomSheet = new UnitListSheetDialog();
            bottomSheet.show(getActivity().getSupportFragmentManager(), "TAG");
        }
    }

    @Override
    public void bottomSheetListener(String sendBackText) {
        unit_name.setText(sendBackText);
    }

}
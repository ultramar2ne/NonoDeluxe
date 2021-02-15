package com.example.nonodeluxe.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nonodeluxe.PrdListActivity;
import com.example.nonodeluxe.R;
import com.example.nonodeluxe.adapter.MenuAdapter;
import com.example.nonodeluxe.model.ItemMenu;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements MenuAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private MenuAdapter menuAdapter;
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
        if (position == 3){
            Intent intent = new Intent(getContext(), PrdListActivity.class);
            startActivity(intent);
        }
    }
}
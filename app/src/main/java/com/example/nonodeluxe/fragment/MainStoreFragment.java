package com.example.nonodeluxe.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.adapter.MainMenuAdapter;
import com.example.nonodeluxe.model.MainMenuItem;

import java.util.ArrayList;


public class MainStoreFragment extends Fragment {

    private ArrayList<MainMenuItem> menuItems;

    MainMenuAdapter mainMenuAdapter;
    RecyclerView recyclerView;
    Toolbar toolbar;


    public MainStoreFragment() {
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
        View view = inflater.inflate(R.layout.fragment_main_store,container,false);




        return view;
    }
}
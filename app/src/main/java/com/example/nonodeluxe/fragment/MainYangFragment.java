package com.example.nonodeluxe.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nonodeluxe.R;


public class MainYangFragment extends Fragment {


    public MainYangFragment() {
        // Required empty public constructor
    }


    public static MainYangFragment newInstance(String param1, String param2) {
        return new MainYangFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //null
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_yang, container, false);
    }
}
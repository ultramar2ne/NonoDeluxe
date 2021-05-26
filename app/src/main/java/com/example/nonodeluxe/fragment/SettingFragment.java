package com.example.nonodeluxe.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nonodeluxe.R;

public class SettingFragment extends Fragment implements View.OnClickListener {

    Button btn_logout;

    public SettingFragment() {
        // Required empty public constructor
    }


    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        btn_logout = view.findViewById(R.id.setting_btn_logout);
        btn_logout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == btn_logout){

        }
    }
}
package com.example.nonodeluxe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PrdInfoActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;

    Button btn_input;
    Button btn_output;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prd_info);

        toolbar = (Toolbar)findViewById(R.id.prdInfo_toolbar);
        btn_input = (Button)findViewById(R.id.info_input_btn);;
        btn_output = (Button)findViewById(R.id.info_output_btn);
        btn_input.setOnClickListener(this);
        btn_output.setOnClickListener(this);

        Intent intent = getIntent();
        String prd_name = intent.getStringExtra("prd_name");

        toolbar.setTitle(prd_name);

    }

    @Override
    public void onClick(View v) {

    }
}
package com.example.nonodeluxe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String store_name = intent.getStringExtra("store_name");

        textView = (TextView)findViewById(R.id.main_txt_storeName);
        textView.setText(store_name);
    }
}
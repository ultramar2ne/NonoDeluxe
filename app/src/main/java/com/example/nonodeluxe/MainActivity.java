package com.example.nonodeluxe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView;
    Button btn_prdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String store_name = intent.getStringExtra("store_name");

        textView = (TextView)findViewById(R.id.main_txt_storeName);
        textView.setText(store_name);

        btn_prdList = (Button)findViewById(R.id.main_btn_prdList);
        btn_prdList.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_prdList){
            Intent intent = new Intent(this,PrdListActivity.class);
            startActivity(intent);
        }
    }
}
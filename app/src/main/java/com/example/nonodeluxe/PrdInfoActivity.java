package com.example.nonodeluxe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.nonodeluxe.adapter.HistoryAdapter;
import com.example.nonodeluxe.fragment.NumberPickerDialog;
import com.example.nonodeluxe.model.HistoryItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class PrdInfoActivity extends AppCompatActivity implements View.OnClickListener, NumberPicker.OnValueChangeListener {

    RecyclerView recyclerView;
    HistoryAdapter historyAdapter;
    NumberPickerDialog numberPickerDialog;

    Button btn_input;
    Button btn_output;
    Toolbar toolbar;

    String prd_name;
    int type;

    private ArrayList<HistoryItem> historyItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prd_info);

        numberPickerDialog = new NumberPickerDialog();
        numberPickerDialog.setValueChangeListener(this);

        toolbar = (Toolbar)findViewById(R.id.prdInfo_toolbar);
        btn_input = (Button)findViewById(R.id.info_input_btn);;
        btn_output = (Button)findViewById(R.id.info_output_btn);
        btn_input.setOnClickListener(this);
        btn_output.setOnClickListener(this);

        Intent intent = getIntent();
        prd_name = intent.getStringExtra("prd_name");

        toolbar.setTitle(prd_name);
        getPrdHistory();
        setRecyclerView();

    }

    private void setRecyclerView() {
        recyclerView = (RecyclerView)findViewById(R.id.recycler_info);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        historyAdapter = new HistoryAdapter(historyItems);
//        historyAdapter.setOnItemClickListener();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(historyAdapter);
    }

    private void getPrdHistory() {
        FirebaseDatabase.getInstance().getReference()
                .child("real").child("history").child("32").child(prd_name)
                .orderByKey()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        historyItems.clear();
                        for (DataSnapshot currentSnapshot : snapshot.getChildren()){
                            HistoryItem historyItem = currentSnapshot.getValue(HistoryItem.class);
                            historyItems.add(0, historyItem);
                        }
                        historyAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == btn_input){
            Bundle bundle = new Bundle(6);
            bundle.putString("title", prd_name); // key , value
            bundle.putString("subtitle", btn_input.getText().toString()); // key , value
            bundle.putInt("maxvalue", 100); // key , value
            bundle.putInt("minvalue", 0); // key , value
            bundle.putInt("step", 1); // key , value
            bundle.putInt("defvalue", 1); // key , value
            type = 1;
            numberPickerDialog.setArguments(bundle);
            numberPickerDialog.show(getSupportFragmentManager(),"hello");

        }
        if (view == btn_output){
            Bundle bundle = new Bundle(6);
            bundle.putString("title", prd_name); // key , value
            bundle.putString("subtitle", btn_output.getText().toString()); // key , value
            bundle.putInt("maxvalue", 100); // key , value
            bundle.putInt("minvalue", 0); // key , value
            bundle.putInt("step", 1); // key , value
            bundle.putInt("defvalue", 1); // key , value
            type = 2;
            numberPickerDialog.setArguments(bundle);
            numberPickerDialog.show(getSupportFragmentManager(),"hello");
        }

    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Toast.makeText(getApplicationContext(),picker.getValue() + "",Toast.LENGTH_SHORT).show();

        Date mDate = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
        String getTime = simpleDateFormat.format(mDate);

        int currentStock;
        int stock = 0;

        if (historyItems.size() == 0){
            if (type == 1){
                stock = picker.getValue();
            } else {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }

        } else {
            currentStock = historyItems.get(0).getStock();

            if (type == 1){
                stock = currentStock + picker.getValue();
            } else {
                stock = currentStock - picker.getValue();
            }
        }

        HistoryItem newItem = new HistoryItem(getTime,picker.getValue(),stock,type);
        FirebaseDatabase.getInstance().getReference().
                child("real").child("history").child("32").child(prd_name)
                .child(String.valueOf(historyItems.size())).setValue(newItem);

        historyAdapter.notifyDataSetChanged();

    }
}
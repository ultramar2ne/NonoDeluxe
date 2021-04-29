package com.example.nonodeluxe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nonodeluxe.adapter.InventoryAdapter;
import com.example.nonodeluxe.fragment.NumberPickerDialog;
import com.example.nonodeluxe.model.InventoryItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class PrdInfoActivity extends AppCompatActivity implements View.OnClickListener, NumberPicker.OnValueChangeListener {

    private int storeCode = MainActivity.currentStoreCode;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    private Date mDate = new Date(System.currentTimeMillis());

    int mYear, mMonth, mDay;

    RecyclerView recyclerView;
    InventoryAdapter inventoryAdapter;
    NumberPickerDialog numberPickerDialog;

    Button btn_input;
    Button btn_output;
    ImageButton btn_datePicker;
    TextView txt_date;
    Toolbar toolbar;

    String prd_name;
    int type;

    private ArrayList<InventoryItem> inventoryItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prd_info);

        numberPickerDialog = new NumberPickerDialog();
        numberPickerDialog.setValueChangeListener(this);

        mYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(mDate));
        mMonth = Integer.parseInt(new SimpleDateFormat("MM").format(mDate));
        mDay = Integer.parseInt(new SimpleDateFormat("dd").format(mDate));

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                mYear = year;
                mMonth = month+1;
                mDay = day;
                txt_date.setText(mYear + "/" + mMonth + "/" + mDay);
            }
        };

        toolbar = (Toolbar)findViewById(R.id.prdInfo_toolbar);
        txt_date = (TextView)findViewById(R.id.info_txt_date);
        btn_input = (Button)findViewById(R.id.info_input_btn);;
        btn_output = (Button)findViewById(R.id.info_output_btn);
        btn_datePicker = (ImageButton) findViewById(R.id.info_btn_dateChange);

        btn_input.setOnClickListener(this);
        btn_output.setOnClickListener(this);
        btn_datePicker.setOnClickListener(this);

        txt_date.setText(mYear + "/" + mMonth + "/" + mDay);

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

        inventoryAdapter = new InventoryAdapter(inventoryItems);
//        inventoryAdapter.setOnItemClickListener();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(inventoryAdapter);
    }

    private void getPrdHistory() {
        FirebaseDatabase.getInstance().getReference()
                .child("real").child("history").child(String.valueOf(storeCode)).child(prd_name)
                .orderByKey()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        inventoryItems.clear();
                        for (DataSnapshot currentSnapshot : snapshot.getChildren()){
                            InventoryItem inventoryItem = currentSnapshot.getValue(InventoryItem.class);
                            inventoryItems.add(0, inventoryItem);
                        }
                        inventoryAdapter.notifyDataSetChanged();
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

        if (view == btn_datePicker){

            DatePickerDialog dialog = new DatePickerDialog(this,onDateSetListener,mYear,(mMonth - 1),mDay);
            dialog.show();
        }

    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Toast.makeText(getApplicationContext(),picker.getValue() + "",Toast.LENGTH_SHORT).show();

        int currentStock;
        int stock = 0;

        if (inventoryItems.size() == 0){
            if (type == 1){
                stock = picker.getValue();
            } else {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }

        } else {
            currentStock = inventoryItems.get(0).getStock();

            if (type == 1){
                stock = currentStock + picker.getValue();
            } else {
                stock = currentStock - picker.getValue();
            }
        }

        InventoryItem newItem = new InventoryItem(mYear,mMonth,mDay,picker.getValue(),stock,type);

        String key = FirebaseDatabase.getInstance().getReference()
                .child("real").child("history").child(String.valueOf(storeCode)).child(prd_name).push().getKey();

        FirebaseDatabase.getInstance().getReference()
                .child("real").child("history").child(String.valueOf(storeCode)).child(prd_name)
                .child(key).setValue(newItem);

        FirebaseDatabase.getInstance().getReference()
                .child("real").child("stock").child(String.valueOf(storeCode)).child(prd_name)
                .setValue(stock);

        inventoryAdapter.notifyDataSetChanged();
    }

    public void onSort(ArrayList<InventoryItem> inventoryItems){

        ArrayList<InventoryItem> sortedItems = new ArrayList<>();

        for (int i = 0; i <= inventoryItems.size() ; i ++){
        }

        Collections.sort(inventoryItems, new Comparator<InventoryItem>() {
            @Override
            public int compare(InventoryItem o1, InventoryItem o2) {
                return o1.getMonth();
            }
        });



    }
}
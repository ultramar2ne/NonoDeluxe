package com.example.nonodeluxe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nonodeluxe.adapter.HistoryAdapter;
import com.example.nonodeluxe.fragment.HomeFragment;
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

    private int storeCode = HomeFragment.currentStoreCode;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    private Date mDate = new Date(System.currentTimeMillis());

    int mYear, mMonth, mDay;

    RecyclerView recyclerView;
    HistoryAdapter historyAdapter;
    NumberPickerDialog numberPickerDialog;

    Button btn_input;
    Button btn_output;
    ImageButton btn_datePicker;
    TextView txt_date;
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

        mYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(mDate));
        mMonth = Integer.parseInt(new SimpleDateFormat("MM").format(mDate)) - 1;
        mDay = Integer.parseInt(new SimpleDateFormat("dd").format(mDate));

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                mYear = year;
                mMonth = month;
                mDay = day;
                txt_date.setText(mYear + "/" + (mMonth + 1) + "/" + mDay);
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

        txt_date.setText(mYear + "/" + (mMonth + 1) + "/" + mDay);

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
                .child("real").child("history").child(String.valueOf(storeCode)).child(prd_name)
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

        if (view == btn_datePicker){

            DatePickerDialog dialog = new DatePickerDialog(this,onDateSetListener,mYear,mMonth,mDay);
            dialog.show();
        }

    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Toast.makeText(getApplicationContext(),picker.getValue() + "",Toast.LENGTH_SHORT).show();

        Date mDate = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd hh:mm");
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
        FirebaseDatabase.getInstance().getReference()
                .child("real").child("history").child(String.valueOf(storeCode)).child(prd_name)
                .child(String.valueOf(historyItems.size())).setValue(newItem);

        FirebaseDatabase.getInstance().getReference()
                .child("real").child("stock").child(String.valueOf(storeCode)).child(prd_name)
                .setValue(stock);

        historyAdapter.notifyDataSetChanged();

    }
}
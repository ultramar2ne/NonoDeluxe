package com.example.nonodeluxe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;

import com.example.nonodeluxe.fragment.MainEmpFragment;
import com.example.nonodeluxe.model.PrdItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class StockChangeActivity extends AppCompatActivity implements View.OnClickListener {

    private int storeCode = MainEmpFragment.currentStoreCode;

    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private Date mDate = new Date(System.currentTimeMillis());

    private ArrayList<Integer> stockItems = new ArrayList<>();
    private ArrayList<String> stockKeys = new ArrayList<>();
    private ArrayList<PrdItem> prdItems = new ArrayList<>();
    private ArrayList<PrdItem> etcItems = new ArrayList<>();

    int mYear, mMonth, mDay;

    Button btn_search;
    ImageButton btn_scanner;
    Button btn_type;
    Button btn_changeStock;
    Button btn_date;
    Button btn_add;
    ImageButton btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_change);

        setStockData();
        setPrdData();

        btn_search = (Button)findViewById(R.id.change_btn_search);
        btn_type = (Button)findViewById(R.id.change_btn_type);
        btn_changeStock = (Button)findViewById(R.id.change_btn_changeStock);
        btn_date = (Button)findViewById(R.id.change_btn_date);
        btn_add = (Button)findViewById(R.id.change_btn_add);
        btn_scanner = (ImageButton)findViewById(R.id.change_btn_barcode);
        btn_save = (ImageButton)findViewById(R.id.change_btn_save);

        btn_search.setOnClickListener(this);
        btn_type.setOnClickListener(this);
        btn_changeStock.setOnClickListener(this);
        btn_date.setOnClickListener(this);
        btn_date.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_scanner.setOnClickListener(this);
        btn_save.setOnClickListener(this);

        mYear = Integer.parseInt(new SimpleDateFormat("yyyy", Locale.KOREA).format(mDate));
        mMonth = Integer.parseInt(new SimpleDateFormat("MM",Locale.KOREA).format(mDate));
        mDay = Integer.parseInt(new SimpleDateFormat("dd",Locale.KOREA).format(mDate));

        btn_type.setText("입고");
        btn_date.setText(mYear + "/" + mMonth + "/" + mDay);

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                mYear = year;
                mMonth = month+1;
                mDay = day;
                btn_date.setText(mYear + "/" + mMonth + "/" + mDay);
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_btn_search:
                break;

            case R.id.change_btn_type:
                if (btn_type.getText().equals("입고"))
                    btn_type.setText("출고");
                else
                    btn_type.setText("입고");
                break;

            case R.id.change_btn_changeStock:
                break;

            case R.id.change_btn_date:
                DatePickerDialog dialog = new DatePickerDialog(this,onDateSetListener,mYear,(mMonth - 1),mDay);
                dialog.show();
                break;

            case R.id.change_btn_add:
                break;

            case R.id.change_btn_barcode:
                Intent intent = new Intent(getApplicationContext(),ScanCodeActivity.class);
                intent.putExtra("ItemList",prdItems);
                startActivityForResult(intent,0);
                break;
            case R.id.change_btn_save:
                break;

            default:
                break;
        }
    }

    private void setStockData() {
        // 처리량이 조금 느림. 그러나 처리 하나가 더 추가되어야함.
        stockItems.clear();
        stockKeys.clear();
        FirebaseDatabase.getInstance().getReference()
                .child("real").child("stock").child(String.valueOf(storeCode))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot currentSnapshot : snapshot.getChildren()){
                            String currentkey = currentSnapshot.getKey();
                            stockItems.add(currentSnapshot.getValue(Integer.class));
                            stockKeys.add(currentkey);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void setPrdData() {
        prdItems.clear();
//        etcItems.clear();
        FirebaseDatabase.getInstance().getReference()
                .child("real").child("product")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot currentSnapshot : snapshot.getChildren()){
//                            boolean check = false;
                            PrdItem currentItem = currentSnapshot.getValue(PrdItem.class);

                            for (int i = 0 ; i < stockKeys.size() ; i ++){
                                if (currentItem.getName().equals(stockKeys.get(i))){
//                                    check = true;
                                    currentItem.setStock(stockItems.get(i));
                                    prdItems.add(currentItem);
                                }
                            }

//                            if (!check){
//                                etcItems.add(currentItem);
//                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            PrdItem selectedItem = (PrdItem) data.getSerializableExtra("prd_selected");
            btn_search.setText(selectedItem.getName());
        }
    }
}
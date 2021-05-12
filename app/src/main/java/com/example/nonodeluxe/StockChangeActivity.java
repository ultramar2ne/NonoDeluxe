package com.example.nonodeluxe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.nonodeluxe.adapter.InventoryAdapter;
import com.example.nonodeluxe.fragment.NumberPickerDialog;
import com.example.nonodeluxe.fragment.PrdListFragment;
import com.example.nonodeluxe.model.InventoryItem;
import com.example.nonodeluxe.model.PrdItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Locale;

public class StockChangeActivity extends AppCompatActivity implements View.OnClickListener, PrdListFragment.OnFragmentInteractionListener, NumberPicker.OnValueChangeListener {

    private int storeCode = MainActivity.currentStoreCode;

    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private Date mDate = new Date(System.currentTimeMillis());

    private ArrayList<Integer> stockItems = new ArrayList<>();
    private ArrayList<String> stockKeys = new ArrayList<>();
    private ArrayList<PrdItem> prdItems = new ArrayList<>();
    private ArrayList<InventoryItem> inventoryItems = new ArrayList<>();

    private ArrayList<InventoryItem> addItems = new ArrayList<>();
    private ArrayList<String> changedStockItems = new ArrayList<>();
    private ArrayList<String> kKeys = new ArrayList<>();
    private PrdItem selectedItem;

    private NumberPickerDialog numberPickerDialog;

    ProgressDialog progressDialog;
    int mYear, mMonth, mDay;

    Button btn_search;
    ImageButton btn_scanner;
    Button btn_type;
    Button btn_changeStock;
    Button btn_date;
    Button btn_add;
    ImageButton btn_save;

    RecyclerView recyclerView;
    InventoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_change);

        progressDialog = new ProgressDialog(this);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.show();

        btn_search = (Button)findViewById(R.id.change_btn_search);
        btn_type = (Button)findViewById(R.id.change_btn_type);
        btn_changeStock = (Button)findViewById(R.id.change_btn_changeStock);
        btn_date = (Button)findViewById(R.id.change_btn_date);
        btn_add = (Button)findViewById(R.id.change_btn_add);
        btn_scanner = (ImageButton)findViewById(R.id.change_btn_barcode);
        btn_save = (ImageButton)findViewById(R.id.change_btn_save);

        recyclerView = (RecyclerView)findViewById(R.id.change_recycler);

        btn_search.setOnClickListener(this);
        btn_type.setOnClickListener(this);
        btn_changeStock.setOnClickListener(this);
        btn_date.setOnClickListener(this);
        btn_date.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_scanner.setOnClickListener(this);
        btn_save.setOnClickListener(this);

        numberPickerDialog = new NumberPickerDialog();
        numberPickerDialog.setValueChangeListener(this);

        setStockData();
        setPrdData();
        setRecyclerView();

        String ggDate = new SimpleDateFormat("yyyy-MM-dd",Locale.KOREA).format(mDate);

        mYear = Integer.parseInt(new SimpleDateFormat("yyyy", Locale.KOREA).format(mDate));
        mMonth = Integer.parseInt(new SimpleDateFormat("MM",Locale.KOREA).format(mDate));
        mDay = Integer.parseInt(new SimpleDateFormat("dd",Locale.KOREA).format(mDate));

        btn_type.setText("입고");
        btn_date.setText(ggDate);
//        btn_date.setText(mYear + "/" + mMonth + "/" + mDay);

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                mYear = year;
                mMonth = month+1;
                mDay = day;
                btn_date.setText(String.format("%4d-%02d-%02d",mYear,mMonth,mDay));
            }
        };
    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new InventoryAdapter(addItems);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new InventoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_btn_search:
                openFragment(prdItems);
                break;

            case R.id.change_btn_type:
                if (btn_type.getText().equals("입고"))
                    btn_type.setText("출고");
                else
                    btn_type.setText("입고");
                break;

            case R.id.change_btn_changeStock:
                Bundle bundle = new Bundle(6);
                bundle.putString("title", btn_search.getText().toString()); // key , value
                bundle.putString("subtitle", btn_type.getText().toString()); // key , value
                bundle.putInt("maxvalue", 100); // key , value
                bundle.putInt("minvalue", 1); // key , value
                bundle.putInt("step", 1); // key , value
                bundle.putInt("defvalue", 1); // key , value
                numberPickerDialog.setArguments(bundle);
                numberPickerDialog.show(getSupportFragmentManager(),"hello");
                break;

            case R.id.change_btn_date:
                DatePickerDialog dialog = new DatePickerDialog(this,onDateSetListener,mYear,(mMonth - 1),mDay);
                dialog.show();
                break;

            case R.id.change_btn_add:
                int changeStock = Integer.parseInt(btn_changeStock.getText().toString());
                InventoryItem currentItem = new InventoryItem();
                String date = String.format("%4d-%02d-%02d",mYear,mMonth,mDay);
//                String date = String.valueOf(mYear) + "-" + String.valueOf(mMonth) + "-" + String.valueOf(mDay);
                currentItem.setDate(date);
                currentItem.setChange(changeStock);
                currentItem.setName(selectedItem.getName());

                if (!changedStockItems.contains(selectedItem.getName()))
                    changedStockItems.add(selectedItem.getName());

                if (btn_type.getText().toString().equals("입고")){
                    int i = selectedItem.getStock() + changeStock;
                    currentItem.setStock(i);
                    currentItem.setType(1);
                } else {
                    currentItem.setStock(selectedItem.getStock() - changeStock);
                    currentItem.setType(0);
                }

                addItems.add(currentItem);
                adapter.notifyDataSetChanged();
                break;

            case R.id.change_btn_barcode:
                Intent intent = new Intent(getApplicationContext(),ScanCodeActivity.class);
                intent.putExtra("ItemList",prdItems);
                startActivityForResult(intent,0);
                break;
            case R.id.change_btn_save:

                progressDialog.show();

                for (int i = 0 ; i < addItems.size();i++){
                    String name = addItems.get(i).getName();
                    addItems.get(i).setName(null);

                    String key = FirebaseDatabase.getInstance().getReference()
                            .child("real").child("history").child(String.valueOf(storeCode)).child(name).push().getKey();

                    FirebaseDatabase.getInstance().getReference()
                            .child("real").child("history").child(String.valueOf(storeCode)).child(name)
                            .child(key).setValue(addItems.get(i));
                }
                sortInventoryItems();

                progressDialog.dismiss();
                onBackPressed();
                break;

            default:
                break;
        }
    }

    private void sortInventoryItems() {

        for (int i = 0; i < changedStockItems.size();i++){
            final String currentPrdName = changedStockItems.get(i);
            FirebaseDatabase.getInstance().getReference()
                    .child("real").child("history").child(String.valueOf(storeCode)).child(currentPrdName)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            inventoryItems.clear();
                            for (DataSnapshot currentSnapshot : snapshot.getChildren()){
                                InventoryItem inventoryItem = currentSnapshot.getValue(InventoryItem.class);
                                kKeys.add(currentSnapshot.getKey());
                                inventoryItems.add(inventoryItem);
                            }
                            Collections.sort(inventoryItems, new SortByDate());

                            for (int k = 0; k<inventoryItems.size();k++){
                                if (k != 0){
                                    int change = inventoryItems.get(k).getChange();
                                    int stock = inventoryItems.get(k-1).getStock();
                                    if (inventoryItems.get(k).getType() == 1)
                                        inventoryItems.get(k).setStock(stock + change);
                                    else if (inventoryItems.get(k).getType() == 0)
                                        inventoryItems.get(k).setStock(stock - change);
                                }

                                FirebaseDatabase.getInstance().getReference()
                                        .child("real").child("history").child(String.valueOf(storeCode)).child(currentPrdName)
                                        .child(kKeys.get(k)).setValue(inventoryItems.get(k));
                            }
                            FirebaseDatabase.getInstance().getReference()
                                    .child("real").child("stock").child(String.valueOf(storeCode)).child(currentPrdName)
                                    .setValue(inventoryItems.get(inventoryItems.size()-1).getStock());
                            kKeys.clear();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }
        Toast.makeText(getApplicationContext(),"저장되었습니다.",Toast.LENGTH_SHORT).show();

    }

    private void setStockData() {

        btn_scanner.setClickable(false);
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

                        }
                        progressDialog.dismiss();
                        btn_scanner.setClickable(true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    public void openFragment(ArrayList<PrdItem> prdItems){
        PrdListFragment fragment = PrdListFragment.newInstance(prdItems);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right);
        transaction.addToBackStack(null);
        transaction.add(R.id.change_container,fragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            selectedItem = (PrdItem) data.getSerializableExtra("prd_selected");
            btn_search.setText(selectedItem.getName());
        }
    }

    @Override
    public void onFragmentInteraction(PrdItem selectedItem) {
        this.selectedItem = selectedItem;
        btn_search.setText(selectedItem.getName());
        onBackPressed();
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        btn_changeStock.setText(picker.getValue() + 1 + "");
    }

    private class SortByDate implements Comparator<InventoryItem> {
        //날짜를 지정할때 두자리로 저장
        @Override
        public int compare(InventoryItem a, InventoryItem b) {

            return a.getDate().compareTo(b.getDate());
        }
    }
}
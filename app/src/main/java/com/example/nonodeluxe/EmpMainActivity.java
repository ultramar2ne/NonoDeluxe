package com.example.nonodeluxe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nonodeluxe.adapter.ArrayStoreAdapter;
import com.example.nonodeluxe.adapter.StoreAdapter;
import com.example.nonodeluxe.model.EmpItem;
import com.example.nonodeluxe.model.StoreItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class EmpMainActivity extends AppCompatActivity {

    private ArrayStoreAdapter mAdapter;

    private TextView unit_name, emp_name;
    private ArrayList<StoreItem> storeItems = new ArrayList<>();

    EmpItem empItem = new EmpItem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_main);

        unit_name = (TextView)findViewById(R.id.main_unitName);
        emp_name = (TextView)findViewById(R.id.main_empName);

        getUserData();
        setStoreData();
        setRecyclerView();
    }


    private void setStoreData() {
        FirebaseDatabase.getInstance().getReference()
                .child("info").child("store").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                storeItems.clear();
                for (DataSnapshot currentSnapshot : snapshot.getChildren()){
                    String key = currentSnapshot.getKey();
                    StoreItem storeItem = currentSnapshot.getValue(StoreItem.class);
                    storeItems.add(storeItem);
//                    if ((int)snapshot.child("unit_code").getValue() == Preferences.getInt(EmpMainActivity.this,"unit_code")) {
//
//                    }
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        Query sortbyAge = FirebaseDatabase.getInstance().getReference().child("info").child("store").orderByChild("store_code");
//        sortbyAge.addListenerForSingleValueEvent(listener);
    }


    private void getUserData() {
        FirebaseDatabase.getInstance().getReference()
                .child("info").child("employee").child(Preferences.getString(EmpMainActivity.this,"id"))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        empItem = snapshot.getValue(EmpItem.class);
                        unit_name.setText(snapshot.child("unit_name").getValue(String.class));
                        emp_name.setText("담당자: " + snapshot.child("name").getValue(String.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        emp_name.setText(empItem.getName());
    }


    private void setRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(EmpMainActivity.this);
        mAdapter = new ArrayStoreAdapter(storeItems);
        RecyclerView mRecyclerView = findViewById(R.id.recycler_main);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new StoreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String sum = storeItems.get(position).getStore_name();
                Toast.makeText(getApplicationContext(),sum,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("store_name",sum);
                startActivity(intent);
            }
        });
    }
}
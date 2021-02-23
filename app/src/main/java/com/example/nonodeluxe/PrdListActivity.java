package com.example.nonodeluxe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.example.nonodeluxe.adapter.PrdListAdapter;
import com.example.nonodeluxe.model.HistoryItem;
import com.example.nonodeluxe.model.PrdItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PrdListActivity extends AppCompatActivity {

    private PrdListAdapter adapter;
    private ArrayList<PrdItem> prdItems = new ArrayList<>();

    DatabaseReference databaseReal = FirebaseDatabase.getInstance().getReference().child("real");

//    private String currentStore = Preferences.getString(PrdListActivity.this,"currentStoreCode");
    Toolbar toolbar;

    private ArrayList<String> nameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prd_list);

        toolbar = (Toolbar)findViewById(R.id.prdlist_toolbar);
        toolbar.setTitle("제품 목록");

//        setPrdData();
        setStockData();
        setRecyclerView();
    }

    private void setStockData() {

        //thread 처리 필요
        databaseReal.child("history").child("32")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot currentSnapshot : snapshot.getChildren()){
                            final String key = currentSnapshot.getKey();
//                            currentSnapshot.

                            FirebaseDatabase.getInstance().getReference().child("real").child("product")
                                    .addValueEventListener (new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot prd : snapshot.getChildren()){
                                        PrdItem prdItem = prd.getValue(PrdItem.class);
                                        if (prdItem.getName().equals(key)){
//                                            prdItem.setStock(historyItem.getStock());
                                            prdItems.add(prdItem);
                                            break;
                                        }
                                    }
                                    adapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void setPrdData() {

        FirebaseDatabase.getInstance().getReference()
                .child("real").child("product").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                prdItems.clear();
                for (DataSnapshot currentSnapshot : snapshot.getChildren()){
                    String key = currentSnapshot.getKey();
                    final PrdItem prdItem = currentSnapshot.getValue(PrdItem.class);

                    FirebaseDatabase.getInstance().getReference()
                            .child("real").child("history").child("32").child(prdItem.getName())
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                                        HistoryItem historyItem = snapshot1.getValue(HistoryItem.class);
                                        Toast.makeText(getApplicationContext(),historyItem.getStock() + "", Toast.LENGTH_SHORT).show();
                                        prdItem.setStock(historyItem.getStock());
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                    prdItems.add(prdItem);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PrdListActivity.this);
        adapter = new PrdListAdapter(prdItems);
        RecyclerView mRecyclerView = findViewById(R.id.prdlist_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new PrdListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String currentPrdName = prdItems.get(position).getName();
                Toast.makeText(getApplicationContext(),currentPrdName,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),PrdInfoActivity.class);
                intent.putExtra("prd_name",currentPrdName);
                startActivity(intent);
            }
        });
    }
}
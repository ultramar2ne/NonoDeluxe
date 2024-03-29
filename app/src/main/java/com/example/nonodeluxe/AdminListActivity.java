package com.example.nonodeluxe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.nonodeluxe.adapter.PrdListAdapter;
import com.example.nonodeluxe.adapter.StoreListAdapter;
import com.example.nonodeluxe.model.ViewHolderCasePrd;
import com.example.nonodeluxe.model.PrdItem;
import com.example.nonodeluxe.model.StoreItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminListActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

    private ArrayList<PrdItem> prdItems = new ArrayList<>();
    private ArrayList<StoreItem> storeItems = new ArrayList<>();

    RecyclerView recyclerView;
    Toolbar toolbar;

    StoreListAdapter storeListAdapter;
    PrdListAdapter prdListAdapter;

    boolean check;
    int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list);

        recyclerView = (RecyclerView)findViewById(R.id.adminList_recycler);

        toolbar = (Toolbar)findViewById(R.id.adminList_toolbar);

        check = getIntent().getBooleanExtra("check",false);
        mode = getIntent().getIntExtra("mode",0);

        if (check){
            toolbar.setTitle("제품별 목록");
            setPrdRecyclerView();
        } else {
            toolbar.setTitle("매장별 목록");
            setStoreRecyclerView();
        }
        setSupportActionBar(toolbar);
    }

    private void setPrdRecyclerView() {
        setPrdData();

        prdListAdapter = new PrdListAdapter(ViewHolderCasePrd.SIMPLE,prdItems);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(prdListAdapter);

        prdListAdapter.setOnItemClickListener(new PrdListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });

    }

    private void setStoreRecyclerView() {
        setStoreData();

        storeListAdapter = new StoreListAdapter(storeItems);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(storeListAdapter);

        storeListAdapter.setOnItemClickListener(new StoreListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                MainActivity.currentStoreCode = storeItems.get(position).getStore_code();

                if (mode == 1){
                    startActivity(new Intent(getApplicationContext(),StockChangeActivity.class));
                } else if (mode == 2){
                    startActivity(new Intent(getApplicationContext(),PrdListActivity.class));
                }


            }
        });

    }

    private void setPrdData() {
        prdItems.clear();
        FirebaseDatabase.getInstance().getReference()
                .child("real").child("product")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot currentSnapshot : snapshot.getChildren()){
                            boolean check = false;
                            PrdItem currentItem = currentSnapshot.getValue(PrdItem.class);
                            prdItems.add(currentItem);
                        }
                        prdListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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
                }
                storeListAdapter.notifyDataSetChanged();
                storeListAdapter.setStoreItemsFull(storeItems);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        Query sortbyAge = FirebaseDatabase.getInstance().getReference().child("info").child("store").orderByChild("store_code");
//        sortbyAge.addListenerForSingleValueEvent(listener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.simple_adminlist_dark, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_search:
                SearchView searchView = (SearchView) item.getActionView();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        storeListAdapter.getFilter().filter(newText);
                        return false;
                    }
                });
                return true;
            default:
                return true;
        }
    }
}
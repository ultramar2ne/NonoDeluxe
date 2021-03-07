package com.example.nonodeluxe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.nonodeluxe.adapter.PrdListAdapter;
import com.example.nonodeluxe.fragment.HomeFragment;
import com.example.nonodeluxe.fragment.PrdAddFragment;
import com.example.nonodeluxe.model.PrdCase;
import com.example.nonodeluxe.model.PrdItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PrdListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private int REQUEST_CODE = 1;

    private PrdListAdapter adapter;
    private ArrayList<Integer> stockItems = new ArrayList<>();
    private ArrayList<String> stockKeys = new ArrayList<>();
    private ArrayList<PrdItem> prdItems = new ArrayList<>();
    private ArrayList<PrdItem> etcItems = new ArrayList<>();

    private int storeCode = HomeFragment.currentStoreCode;

//    private String currentStore = Preferences.getString(PrdListActivity.this,"currentStoreCode");
    Toolbar toolbar;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prd_list);

        toolbar = (Toolbar)findViewById(R.id.prdList_toolbar);
        toolbar.setTitle("제품 목록");
        setSupportActionBar(toolbar);

        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.prdList_refresh);
        refreshLayout.setOnRefreshListener(this);

        setStockData();
        setPrdData();
        setRecyclerView();
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
        etcItems.clear();
        FirebaseDatabase.getInstance().getReference()
                .child("real").child("product")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot currentSnapshot : snapshot.getChildren()){
                            boolean check = false;
                            PrdItem currentItem = currentSnapshot.getValue(PrdItem.class);

                            for (int i = 0 ; i < stockKeys.size() ; i ++){
                                if (currentItem.getName().equals(stockKeys.get(i))){
                                    check = true;
                                    currentItem.setStock(stockItems.get(i));
                                    prdItems.add(currentItem);
                                }
                            }

                            if (!check){
                                etcItems.add(currentItem);
                            }
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
        adapter = new PrdListAdapter(PrdCase.LIST, prdItems);
        RecyclerView mRecyclerView = findViewById(R.id.prdList_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new PrdListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openSelectedItemInfo(position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_prdlist_toolbar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.toolbar_search:
                return true;
            case R.id.toolbar_scanner:
                Intent intent = new Intent(getApplicationContext(),ScanCodeActivity.class);
                intent.putExtra("ItemList",prdItems);
                startActivity(intent);
                return true;
            case R.id.toolbar_add:
                FrameLayout fragmentContainer = (FrameLayout)findViewById(R.id.prdList_fragment_container);
                PrdAddFragment fragment = PrdAddFragment.newInstance(etcItems,prdItems.size());
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right);
                transaction.addToBackStack(null);
                transaction.add(R.id.prdList_fragment_container, fragment, "HElloWolrD").commit();
                return true;
            default:
                return true;
        }
    }

    public void openSelectedItemInfo(int position){
        String currentPrdName = prdItems.get(position).getName();
        Toast.makeText(getApplicationContext(),currentPrdName,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),PrdInfoActivity.class);
        intent.putExtra("prd_name",currentPrdName);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setStockData();
        setPrdData();
    }

    @Override
    public void onRefresh() {
        setStockData();
        setPrdData();
        refreshLayout.setRefreshing(false);
    }
}
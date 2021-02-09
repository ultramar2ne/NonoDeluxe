package com.example.nonodeluxe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nonodeluxe.adapter.StoreAdapter;
import com.example.nonodeluxe.model.StoreItem;
import com.example.nonodeluxe.viewholder.StoreListViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmpMainActivity extends AppCompatActivity {

    private FirebaseRecyclerAdapter<StoreItem, StoreListViewHolder> adapter;
    private FirebaseRecyclerOptions options;

    private TextView unit_name, emp_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_main);

        unit_name = (TextView)findViewById(R.id.main_unitName);
        emp_name = (TextView)findViewById(R.id.main_empName);

        setUserData();
        setUpRecyclerView();
    }

    private void setUserData() {

        final DatabaseReference userData = FirebaseDatabase.getInstance().getReference()
                .child("info").child("employee").child(Preferences.getString(EmpMainActivity.this,"id"));

        userData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                DataSnapshot dataSnapshot;

                unit_name.setText(snapshot.child("unit_name").getValue(String.class));
                emp_name.setText("담당자: " + snapshot.child("name").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_main);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(EmpMainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        options = new FirebaseRecyclerOptions.Builder<StoreItem>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("info").child("store")
                        .orderByChild("unit_code").equalTo(5001), StoreItem.class)
                .build();

        adapter = new StoreAdapter(options);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
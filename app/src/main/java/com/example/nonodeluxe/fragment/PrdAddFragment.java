package com.example.nonodeluxe.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.adapter.PrdListAdapter;
import com.example.nonodeluxe.model.MyItem;
import com.example.nonodeluxe.model.PrdCase;
import com.example.nonodeluxe.model.PrdItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PrdAddFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    RecyclerView recyclerView_new;
    PrdListAdapter adapter;
    PrdListAdapter adapter_new;

    ArrayList<PrdItem> prdItems = new ArrayList<>();
    ArrayList<PrdItem> addItems = new ArrayList<>();

    EditText edt;
    Button button;


    public PrdAddFragment() {
        // Required empty public constructor
    }

    public static PrdAddFragment newInstance(ArrayList<PrdItem> prdItems){
        PrdAddFragment fragment = new PrdAddFragment();
        Bundle args = new Bundle();
        args.putSerializable("ETC",prdItems);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            prdItems = (ArrayList<PrdItem>) getArguments().getSerializable("ETC");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_prd_add, container, false);
        edt = view.findViewById(R.id.dlg_edt_nubmer);
        button = view.findViewById(R.id.prdAdd_btn_save);
        recyclerView = view.findViewById(R.id.prdAdd_recycler);
        recyclerView_new = view.findViewById(R.id.prdAdd_recycler_new);

        button.setOnClickListener(this);

//        fillData();
        setRecyclerView();


        // Inflate the layout for this fragment
        return view;
    }

    private void fillData() {
        FirebaseDatabase.getInstance().getReference()
                .child("real").child("product")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot currentSnapshot:snapshot.getChildren()){
                            PrdItem prdItem = currentSnapshot.getValue(PrdItem.class);
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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new PrdListAdapter(PrdCase.ADD,prdItems);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        adapter_new = new PrdListAdapter(PrdCase.NEW,addItems);
        recyclerView_new.setLayoutManager(layoutManager1);
        recyclerView_new.setAdapter(adapter_new);

        adapter.setOnItemClickListener(new PrdListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {
                View dlgView = View.inflate(getActivity(),R.layout.dialog_prd_new,null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                String currentPrdName = prdItems.get(position).getName();
                dlg.setTitle(currentPrdName);
                dlg.setView(dlgView);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addItems.add(prdItems.get(position));
//                        addItems.get(addItems.size()).setStock(Integer.parseInt(edt.getText().toString()));

                        adapter_new.notifyDataSetChanged();
                        recyclerView_new.setVisibility(View.VISIBLE);
                    }
                });
                dlg.show();


//                Toast.makeText(getApplicationContext(),currentPrdName,Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getApplicationContext(), PrdInfoActivity.class);
//                intent.putExtra("prd_name",currentPrdName);
//                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == button){
            recyclerView_new.setVisibility(View.GONE);
        }
    }
}
package com.example.nonodeluxe.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.nonodeluxe.MainActivity;
import com.example.nonodeluxe.R;
import com.example.nonodeluxe.adapter.PrdListAdapter;
import com.example.nonodeluxe.model.ViewHolderCasePrd;
import com.example.nonodeluxe.model.PrdItem;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;


public class PrdAddFragment extends Fragment implements View.OnClickListener, PrdListAdapter.OnItemClickListener {

    private int storeCode = MainActivity.currentStoreCode;

    RecyclerView recyclerView;
    RecyclerView recycelrview_showAdd;
    PrdListAdapter adapter;
    PrdListAdapter adapter_showAdd;

    ArrayList<PrdItem> prdItems = new ArrayList<>();
    ArrayList<PrdItem> addItems = new ArrayList<>();
    Boolean[] checked;
//    int[] checked_add;
    int existItem;

    EditText edt;
    ImageButton button;
    Toolbar toolbar;


    public PrdAddFragment() {
        // Required empty public constructor
    }

    public static PrdAddFragment newInstance(ArrayList<PrdItem> prdItems, int size){
        PrdAddFragment fragment = new PrdAddFragment();
        Bundle args = new Bundle();
        args.putSerializable("ETC",prdItems);
        args.putInt("EXIST",size);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            prdItems = (ArrayList<PrdItem>) getArguments().getSerializable("ETC");
            existItem = getArguments().getInt("EXIST");
            checked = new Boolean[prdItems.size()];
//            checked_add = new int[prdItems.size()];
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_prd_add, container, false);
        button = view.findViewById(R.id.prdAdd_btn_save);
        recyclerView = view.findViewById(R.id.prdAdd_recycler);
        recycelrview_showAdd = view.findViewById(R.id.prdAdd_recycler_new);

        toolbar = view.findViewById(R.id.prdAdd_toolbar);
        toolbar.setTitle("제품 추가");

        button.setOnClickListener(this);
        button.setVisibility(View.GONE);

        setRecyclerView();

        // Inflate the layout for this fragment
        return view;
    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new PrdListAdapter(ViewHolderCasePrd.ADD,prdItems,checked);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new PrdListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {

                if (checked[position] == null || !checked[position]){
                    addItems.add(prdItems.get(position));
                    checked[position] = true;
                } else {
                    addItems.remove(prdItems.get(position));
                    checked[position] = false;
                }
                adapter.notifyDataSetChanged();
                adapter_showAdd.notifyDataSetChanged();
                recycelrview_showAdd.setVisibility(View.VISIBLE);
                recycelrview_showAdd.scrollToPosition(adapter_showAdd.getItemCount() - 1);
                saveButtonCheck();


//
//                View dlgView = View.inflate(getActivity(),R.layout.dialog_prd_new,null);
//                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
//                String currentPrdName = prdItems.get(position).getName();
//                edt = (EditText) dlgView.findViewById(R.id.dlg_edt_nubmer);
//                dlg.setTitle(currentPrdName);
//                dlg.setView(dlgView);
//                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String str;
//                        try {
//                            str = edt.getText().toString();
//                            prdItems.get(position).setStock(Integer.parseInt(str));
//                            addItems.add(prdItems.get(position));
//                        } catch (NullPointerException e){
//                            Toast.makeText(getActivity(),"최초수량을 입력 해 주세요.",Toast.LENGTH_SHORT).show();
//                        } catch (Exception e){
//                            Toast.makeText(getActivity(),"기타 에러 : " + e.getMessage(),Toast.LENGTH_SHORT).show();
//                        }
//                        adapter_showAdd.notifyDataSetChanged();
//                        recycelrview_showAdd.setVisibility(View.VISIBLE);
//                    }
//                });
//                dlg.show();
            }
        });

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        adapter_showAdd = new PrdListAdapter(ViewHolderCasePrd.NEW,addItems);
        recycelrview_showAdd.setLayoutManager(layoutManager1);
        recycelrview_showAdd.setAdapter(adapter_showAdd);
        adapter_showAdd.setOnItemClickListener(this);



    }

    private void addStockData() {
        // 처리량이 조금 느림. 그러나 처리 하나가 더 추가되어야함.
        for (int i = 0 ; i < addItems.size() ; i ++){
            HashMap<String, Integer> currentItem = new HashMap<>();
            currentItem.put(addItems.get(i).getName(),addItems.get(i).getStock());
            FirebaseDatabase.getInstance().getReference()
                    .child("real").child("stock").child(String.valueOf(storeCode)).child(addItems.get(i).getName()).setValue(addItems.get(i).getStock());
        }
    }

    @Override
    public void onClick(View v) {
        if (v == button){

            addStockData();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().remove(PrdAddFragment.this).commit();
            fragmentManager.popBackStack();
        }
    }

    @Override
    public void onItemClick(int position) {     // recyclerview adapter

        addItems.remove(position);
        adapter_showAdd.notifyDataSetChanged();
        saveButtonCheck();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void saveButtonCheck(){
        if (addItems.size() == 0){
            button.setVisibility(View.GONE);
        } else {
            button.setVisibility(View.VISIBLE);
        }
    }


}
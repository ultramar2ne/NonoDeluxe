package com.example.nonodeluxe.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.nonodeluxe.MainActivity;
import com.example.nonodeluxe.PrdListActivity;
import com.example.nonodeluxe.Preferences;
import com.example.nonodeluxe.R;
import com.example.nonodeluxe.adapter.MainMenuAdapter;
import com.example.nonodeluxe.model.MainMenuItem;
import com.example.nonodeluxe.model.StoreItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements MainMenuAdapter.OnItemClickListener, View.OnClickListener{

    private RecyclerView recyclerView;
    private MainMenuAdapter mainMenuAdapter;

    public static int currentStoreCode;

    TextView storeName;
    TextView empName;
    Button switcher;

    private ArrayList<MainMenuItem> menuItems;
    private ArrayList<StoreItem> storeItems;
    private ArrayList<String> strings;

    int stringIndex = 0;
    String inputData;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            inputData = getArguments().getString("store_name");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        fillStoreList();
        fillExampleList();

        storeName = view.findViewById(R.id.home_storeName);
        empName = view.findViewById(R.id.main_empName);
        empName.setText("담당자: " +  Preferences.getString(getActivity(),"id"));
//        storeName.setFactory(new ViewSwitcher.ViewFactory() {
//            @Override
//            public View makeView() {
//                TextView textView = new TextView(getContext());
////                textView.setAutoSizeTextTypeUniformWithConfiguration(20,50,TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM,1);
//                textView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
//                textView.setTextSize(0x32);
//                textView.setTextColor(Color.rgb(41,41,41));
//                return textView;
//            }
//        });
        switcher = view.findViewById(R.id.textSwitcher);
        switcher.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.recycler_home);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);

        mainMenuAdapter = new MainMenuAdapter(menuItems);
        mainMenuAdapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mainMenuAdapter);



        return view;
    }

    private void fillStoreList() {
        storeItems = new ArrayList<>();
        strings = new ArrayList<>();
        int unitCode = Preferences.getInt(getActivity(),"unitCode");

        if (unitCode == 5000){
//            Intent intent = getActivity().getParentActivityIntent();
////            String store_name = intent.getStringExtra("store_name");
//            storeName.setText(inputData);
//            switcher.setOnClickListener(null);
        } else {
            FirebaseDatabase.getInstance().getReference()
                    .child("info").child("store").orderByChild("unit_code").equalTo(unitCode).
                    addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            storeItems.clear();
                            for (DataSnapshot currentSnapshot : snapshot.getChildren()){
                                String key = currentSnapshot.getKey();
                                StoreItem storeItem = currentSnapshot.getValue(StoreItem.class);
                                storeItems.add(storeItem);
                                strings.add(storeItem.getStore_name());
                            }
                            Toast.makeText(getContext(),"hello",Toast.LENGTH_SHORT).show();
                            storeName.setText(strings.get(0));
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void fillExampleList() {
        menuItems = new ArrayList<>();

        menuItems.add(new MainMenuItem(R.drawable.ic_circle_blue,"입고"));
        menuItems.add(new MainMenuItem(R.drawable.ic_circle_red,"출고"));
        menuItems.add(new MainMenuItem(R.drawable.ic_circle_green,"수정"));
        menuItems.add(new MainMenuItem(R.drawable.ic_circle_navy,"제품목록"));
//        menuItems.add(new MainMenuItem(R.drawable.ic_circle_blue,"hello"));
    }

    @Override
    public void onItemClick(int position) {
        switch (position){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                Intent intent = new Intent(getActivity(), PrdListActivity.class);
                Preferences.setString(getActivity(),String.valueOf(storeItems.get(stringIndex).getStore_code()),"currentStoreCode");
                currentStoreCode = storeItems.get(stringIndex).getStore_code();
                intent.putExtra("hello","hello");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == switcher){
            if (stringIndex == strings.size() - 1){
                stringIndex = 0;
                storeName.setText(strings.get(stringIndex));
            } else {
                storeName.setText(strings.get(++stringIndex));
            }
        }
    }
}
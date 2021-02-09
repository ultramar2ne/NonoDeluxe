package com.example.nonodeluxe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.model.StoreItem;
import com.example.nonodeluxe.viewholder.StoreListViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class StoreAdapter extends FirebaseRecyclerAdapter<StoreItem, StoreListViewHolder> {
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }

    public StoreAdapter(@NonNull FirebaseRecyclerOptions<StoreItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull StoreListViewHolder viewHolder, int i, @NonNull StoreItem item) {
        viewHolder.store_code.setText(item.getStore_code() + "호점");
        viewHolder.store_name.setText(item.getStore_name());
    }

    @NonNull
    @Override
    public StoreListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store,parent,false);
        StoreListViewHolder evh = new StoreListViewHolder(view,onItemClickListener);
        return evh;
    }
}

package com.example.nonodeluxe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.model.StoreItem;
import com.example.nonodeluxe.viewholder.StoreListViewHolder;;

import java.util.ArrayList;

public class StoreAdapter extends RecyclerView.Adapter<StoreListViewHolder> {
    private OnItemClickListener onItemClickListener;
    private ArrayList<StoreItem> storeItems;

    public StoreAdapter(ArrayList<StoreItem> storeItems) {
        this.storeItems = storeItems;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }

    @NonNull
    @Override
    public StoreListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store, parent, false);
        StoreListViewHolder evh = new StoreListViewHolder(view, onItemClickListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull StoreListViewHolder holder, int position) {
        StoreItem currentItem = storeItems.get(position);
        holder.store_code.setText(currentItem.getStore_code() + "호점");
        holder.store_name.setText(currentItem.getStore_name());
    }

    @Override
    public int getItemCount() {
        return storeItems.size();
    }

}

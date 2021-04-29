package com.example.nonodeluxe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.model.InventoryItem;
import com.example.nonodeluxe.viewholder.InventoryViewHolder;

import java.util.ArrayList;

;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryViewHolder> {
    private OnItemClickListener onItemClickListener;
    private ArrayList<InventoryItem> inventoryItems;

    public InventoryAdapter(ArrayList<InventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }

    @NonNull
    @Override
    public InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        InventoryViewHolder evh = new InventoryViewHolder(view, onItemClickListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryViewHolder holder, int position) {
        InventoryViewHolder viewHolder = (InventoryViewHolder)holder;
        viewHolder.onBind(inventoryItems.get(position));
    }

    @Override
    public int getItemCount() {
        return inventoryItems.size();
    }

}

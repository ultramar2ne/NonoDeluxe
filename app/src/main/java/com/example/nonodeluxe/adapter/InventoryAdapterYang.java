package com.example.nonodeluxe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.model.InventoryItem;
import com.example.nonodeluxe.model.InventoryItemVerYang;
import com.example.nonodeluxe.model.MyItemView;
import com.example.nonodeluxe.model.ViewHolderCaseInventory;
import com.example.nonodeluxe.viewholder.InventoryViewHolder;
import com.example.nonodeluxe.viewholder.InventoryViewHolderWithName;
import com.example.nonodeluxe.viewholder.InventoryViewHolderYang;

import java.util.ArrayList;


public class InventoryAdapterYang extends RecyclerView.Adapter<MyItemView> {
    private OnItemClickListener onItemClickListener;
    private ArrayList<InventoryItemVerYang> inventoryItems;

    public InventoryAdapterYang(ArrayList<InventoryItemVerYang> inventoryItems) {
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
    public MyItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inventory_yang, parent, false);
        return new InventoryViewHolderYang(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyItemView holder, int position) {
        InventoryViewHolderYang viewHolder = (InventoryViewHolderYang)holder;
        viewHolder.onBind(inventoryItems.get(position));
    }

    @Override
    public int getItemCount() {
        return inventoryItems.size();
    }

    public void removeAtPosition(int position){
        if (position < inventoryItems.size()){
            inventoryItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void move(int fromPosition, int toPosition){
        final InventoryItemVerYang current = inventoryItems.get(fromPosition);
        inventoryItems.remove(fromPosition);
        inventoryItems.add(toPosition, current);
        notifyItemMoved(fromPosition,toPosition);
    }

}

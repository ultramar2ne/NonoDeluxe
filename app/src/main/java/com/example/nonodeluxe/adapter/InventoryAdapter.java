package com.example.nonodeluxe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.model.InventoryItem;
import com.example.nonodeluxe.model.MyItemView;
import com.example.nonodeluxe.model.ViewHolderCaseInventory;
import com.example.nonodeluxe.viewholder.InventoryViewHolder;
import com.example.nonodeluxe.viewholder.InventoryViewHolderWithName;

import java.util.ArrayList;


public class InventoryAdapter extends RecyclerView.Adapter<MyItemView> {
    private OnItemClickListener onItemClickListener;
    private ArrayList<InventoryItem> inventoryItems;

    private ViewHolderCaseInventory sel_type;

    public InventoryAdapter(ViewHolderCaseInventory sel_type , ArrayList<InventoryItem> inventoryItems) {
        this.sel_type = sel_type;
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
        View view;

        if (sel_type == ViewHolderCaseInventory.FULL){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inventory, parent, false);
            return new InventoryViewHolder(view, onItemClickListener);
        } else if (sel_type == ViewHolderCaseInventory.NAME){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inventory_with_name, parent, false);
            return new InventoryViewHolderWithName(view, onItemClickListener);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyItemView holder, int position) {
        if (holder instanceof InventoryViewHolder){
            InventoryViewHolder viewHolder = (InventoryViewHolder)holder;
            viewHolder.onBind(inventoryItems.get(position));
        } else if (holder instanceof InventoryViewHolderWithName){
            InventoryViewHolderWithName viewHolder = (InventoryViewHolderWithName)holder;
            viewHolder.onBind(inventoryItems.get(position));
        }
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
        final InventoryItem current = inventoryItems.get(fromPosition);
        inventoryItems.remove(fromPosition);
        inventoryItems.add(toPosition, current);
        notifyItemMoved(fromPosition,toPosition);
    }

}

package com.example.nonodeluxe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.model.HistoryItem;
import com.example.nonodeluxe.model.StoreItem;
import com.example.nonodeluxe.viewholder.HistoryViewHolder;
import com.example.nonodeluxe.viewholder.StoreListViewHolder;

import java.util.ArrayList;
import java.util.Collections;

;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {
    private OnItemClickListener onItemClickListener;
    private ArrayList<HistoryItem> historyItems;

    public HistoryAdapter(ArrayList<HistoryItem> historyItems) {
        this.historyItems = historyItems;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        HistoryViewHolder evh = new HistoryViewHolder(view, onItemClickListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        HistoryItem currentItem = historyItems.get(position);

        holder.history_date.setText(String.valueOf(currentItem.getDate()));
        holder.history_changeStock.setText(String.valueOf(currentItem.getChange()));
        holder.history_stock.setText(String.valueOf(currentItem.getStock()));

        if (currentItem.getType() == 1){
            holder.history_type.setText("입고");
        } else {
            holder.history_type.setText("출고");
        }

        //블라블라
    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

}

package com.example.nonodeluxe.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.adapter.HistoryAdapter;
import com.example.nonodeluxe.adapter.StoreAdapter;

public class HistoryViewHolder extends RecyclerView.ViewHolder {

    public TextView store_code;
    public TextView store_name;

    public HistoryViewHolder(@NonNull final View view, final HistoryAdapter.OnItemClickListener listener) {
        super(view);
        store_code = view.findViewById(R.id.item_storeCode);
        store_name = view.findViewById(R.id.item_storeName);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        listener.onItemClick(position);
                    }
                }
            }
        });
    }
}

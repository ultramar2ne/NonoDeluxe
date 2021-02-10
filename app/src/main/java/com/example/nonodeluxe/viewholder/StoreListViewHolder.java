package com.example.nonodeluxe.viewholder;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.adapter.StoreAdapter;

public class StoreListViewHolder extends RecyclerView.ViewHolder {

    public TextView store_code;
    public TextView store_name;

    public StoreListViewHolder(@NonNull final View view, final StoreAdapter.OnItemClickListener listener) {
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

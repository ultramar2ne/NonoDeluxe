package com.example.nonodeluxe.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.adapter.StoreListAdapter;
import com.example.nonodeluxe.model.MyItem;
import com.example.nonodeluxe.model.StoreItem;

public class StoreListViewHolder extends RecyclerView.ViewHolder {

    public TextView store_code;
    public TextView store_name;

    private StoreItem storeItem;

    public StoreListViewHolder(@NonNull final View view, final StoreListAdapter.OnItemClickListener listener) {
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

    public void onBind(MyItem data){
        storeItem = (StoreItem) data;
        store_name.setText(storeItem.getStore_name());
        store_code.setText(storeItem.getStore_code() + "호점");
    }
}
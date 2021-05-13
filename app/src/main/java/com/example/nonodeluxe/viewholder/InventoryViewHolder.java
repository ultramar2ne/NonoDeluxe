package com.example.nonodeluxe.viewholder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.adapter.InventoryAdapter;
import com.example.nonodeluxe.model.InventoryItem;
import com.example.nonodeluxe.model.MyItem;
import com.example.nonodeluxe.model.MyItemView;

public class InventoryViewHolder extends MyItemView {

    public TextView history_date;
    public TextView history_type;
    public TextView history_changeStock;
    public TextView history_stock;

    private InventoryItem inventoryItem;

    public InventoryViewHolder(@NonNull final View view, final InventoryAdapter.OnItemClickListener listener) {
        super(view);
        history_date = view.findViewById(R.id.inventory_date);
        history_type = view.findViewById(R.id.inventory_txt_change);
        history_changeStock = view.findViewById(R.id.inventory_change);
        history_stock = view.findViewById(R.id.inventory_stock);

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
        inventoryItem = (InventoryItem) data;

        String date = inventoryItem.getDate();
        history_date.setText(date.substring(5,10));

//        if (inventoryItem.getName() != null){
//            history_date.setText(inventoryItem.getName());
//        } else {
//            history_date.setText(date.substring(5,9));
//        }
        history_changeStock.setText(String.valueOf(inventoryItem.getChange()));
        history_stock.setText(String.valueOf(inventoryItem.getStock()));

        if (inventoryItem.getType() == 1){
            history_type.setTextColor(Color.rgb(0,160,0));
            history_type.setText("입고");
        } else {
            history_type.setTextColor(Color.rgb(200,0,0));
            history_type.setText("출고");
        }
    }
}

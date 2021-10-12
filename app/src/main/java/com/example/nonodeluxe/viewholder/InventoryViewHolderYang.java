package com.example.nonodeluxe.viewholder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.adapter.InventoryAdapterYang;
import com.example.nonodeluxe.model.InventoryItem;
import com.example.nonodeluxe.model.InventoryItemVerYang;
import com.example.nonodeluxe.model.MyItem;
import com.example.nonodeluxe.model.MyItemView;

public class InventoryViewHolderYang extends MyItemView {

    public TextView history_date;
    public TextView history_name;
    public TextView history_changeStock;
    public TextView history_stock;

    private InventoryItemVerYang inventoryItem;

    public InventoryViewHolderYang(@NonNull final View view, final InventoryAdapterYang.OnItemClickListener listener) {
        super(view);
        history_date = view.findViewById(R.id.yang_date);
        history_name = view.findViewById(R.id.yang_name);
        history_changeStock = view.findViewById(R.id.yang_change);
        history_stock = view.findViewById(R.id.yang_stock);

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
        inventoryItem = (InventoryItemVerYang) data;

//        String date = inventoryItem.getDate();
//        history_date.setText(date.substring(5,10));

//        if (inventoryItem.getName() != null){
//            history_date.setText(inventoryItem.getName());
//        } else {
//            history_date.setText(date.substring(5,9));
//        }
        history_date.setText(String.valueOf(inventoryItem.getDate()));
        history_name.setText(String.valueOf(inventoryItem.getName()));
        history_changeStock.setText(String.valueOf(inventoryItem.getChange()));
        history_stock.setText("재고" + String.valueOf(inventoryItem.getStock()));

        String change = String.valueOf(inventoryItem.getChange());

        if (inventoryItem.getType() == 1){
            history_changeStock.setTextColor(Color.rgb(0,160,0));
            history_changeStock.setText("입고" + change);
        } else if (inventoryItem.getType() == 2){
            history_changeStock.setTextColor(Color.rgb(200,0,0));
            history_changeStock.setText("출고" + change);
        } else {
            history_changeStock.setTextColor(Color.rgb(0,0,200));
            history_changeStock.setText("출고" + change);
        }
    }
}

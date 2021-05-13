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

public class InventoryViewHolderWithName extends MyItemView {

    public TextView inventory_name;
    public TextView inventory_date;
    public TextView inventory_type;
    public TextView inventory_changeStock;
    public TextView inventory_stock;

    private InventoryItem inventoryItem;

    public InventoryViewHolderWithName(@NonNull final View view, final InventoryAdapter.OnItemClickListener listener) {
        super(view);
        inventory_name = view.findViewById(R.id.inventory_name);
        inventory_date = view.findViewById(R.id.inventory_date);
        inventory_type = view.findViewById(R.id.inventory_txt_change);
        inventory_changeStock = view.findViewById(R.id.inventory_change);
        inventory_stock = view.findViewById(R.id.inventory_stock);

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

        inventory_name.setText(inventoryItem.getName());
        inventory_date.setText(inventoryItem.getDate().substring(5,10));
        inventory_changeStock.setText(String.valueOf(inventoryItem.getChange()));

        if (inventoryItem.getType() == 1){
            inventory_type.setTextColor(Color.rgb(0,160,0));
            inventory_type.setText("입고");
        } else {
            inventory_type.setTextColor(Color.rgb(200,0,0));
            inventory_type.setText("출고");
        }
    }
}

package com.example.nonodeluxe.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nonodeluxe.OnViewHolderItemClickListener;
import com.example.nonodeluxe.R;
import com.example.nonodeluxe.adapter.PrdListAdapter;
import com.example.nonodeluxe.model.MyItem;
import com.example.nonodeluxe.model.MyItemView;
import com.example.nonodeluxe.model.PrdItem;

public class PrdNewViewHolder extends MyItemView {

    public TextView prd_name;
    public TextView prd_stock;
//    public CheckBox checkBox;

    PrdItem prdItem;
    OnViewHolderItemClickListener onViewHolderItemClickListener;

    public PrdNewViewHolder(@NonNull View view, final PrdListAdapter.OnItemClickListener listener) {
        super(view);
        prd_name = view.findViewById(R.id.prdANew_txt_name);
        prd_stock = view.findViewById(R.id.prdANew_txt_stock);

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
        prdItem = (PrdItem) data;
        prd_name.setText(prdItem.getName());
//        prd_stock.setText(String.valueOf(prdItem.getStock()));
//        category.setText(prdItem.getCategory());
    }
}

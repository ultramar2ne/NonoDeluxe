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

public class PrdListViewHolder extends MyItemView {

    public TextView prd_name;
    public TextView category;
    public TextView stock;
    public TextView standard;

    PrdItem data;
    OnViewHolderItemClickListener onViewHolderItemClickListener;

    public PrdListViewHolder(@NonNull final View view, final PrdListAdapter.OnItemClickListener listener) {
        super(view);
        prd_name = view.findViewById(R.id.prd_name);
        category = view.findViewById(R.id.prd_category);
        stock = view.findViewById(R.id.prd_stock);
        standard = view.findViewById(R.id.prd_standard);

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

    public void onBind(MyItem data) {
        this.data = (PrdItem)data;
        prd_name.setText(this.data.getName());
        category.setText(this.data.getCategory());
        stock.setText(String.valueOf(this.data.getStock()));
        standard.setText(this.data.getStandard());
    }

    public void setOnViewHolderItemClickListener(OnViewHolderItemClickListener onViewHolderItemClickListener){
        this.onViewHolderItemClickListener = onViewHolderItemClickListener;
    }
}

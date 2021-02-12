package com.example.nonodeluxe.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.adapter.PrdListAdapter;

public class PrdListViewHolder extends RecyclerView.ViewHolder {

    public TextView prd_name;
    public TextView category;
    public TextView stock;


    public PrdListViewHolder(@NonNull final View view, final PrdListAdapter.OnItemClickListener listener) {
        super(view);
        prd_name = view.findViewById(R.id.prd_name);
        category = view.findViewById(R.id.prd_category);
        stock = view.findViewById(R.id.prd_stock);

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

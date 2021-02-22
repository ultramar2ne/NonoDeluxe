package com.example.nonodeluxe.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.adapter.HistoryAdapter;

public class HistoryViewHolder extends RecyclerView.ViewHolder {

    public TextView history_date;
    public TextView history_type;
    public TextView history_changeStock;
    public TextView history_stock;

    public HistoryViewHolder(@NonNull final View view, final HistoryAdapter.OnItemClickListener listener) {
        super(view);
        history_date = view.findViewById(R.id.history_date);
        history_type = view.findViewById(R.id.history_txt_change);
        history_changeStock = view.findViewById(R.id.history_change);
        history_stock = view.findViewById(R.id.history_stock);

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

package com.example.nonodeluxe.viewholder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.adapter.HistoryAdapter;
import com.example.nonodeluxe.model.HistoryItem;
import com.example.nonodeluxe.model.MyItem;
import com.example.nonodeluxe.model.StoreItem;

public class HistoryViewHolder extends RecyclerView.ViewHolder {

    public TextView history_date;
    public TextView history_type;
    public TextView history_changeStock;
    public TextView history_stock;

    private HistoryItem historyItem;

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

    public void onBind(MyItem data){
        historyItem = (HistoryItem) data;

        String date = String.valueOf(historyItem.getMonth() + "월 " + historyItem.getDay() + "일");

        if (historyItem.getName() != null){
            history_date.setText(historyItem.getName());
        } else {
            history_date.setText(date);
        }
        history_changeStock.setText(String.valueOf(historyItem.getChange()));
        history_stock.setText(String.valueOf(historyItem.getStock()));

        if (historyItem.getType() == 1){
            history_type.setTextColor(Color.rgb(0,160,0));
            history_type.setText("입고");
        } else {
            history_type.setTextColor(Color.rgb(200,0,0));
            history_type.setText("출고");
        }
    }
}

package com.example.nonodeluxe.viewholder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nonodeluxe.OnViewHolderItemClickListener;
import com.example.nonodeluxe.R;
import com.example.nonodeluxe.adapter.PrdListAdapter;
import com.example.nonodeluxe.model.MyItem;
import com.example.nonodeluxe.model.MyItemView;
import com.example.nonodeluxe.model.PrdItem;

public class PrdAddViewHolder extends MyItemView {

    public TextView prd_name;
    public TextView category;
    public CheckBox checkBox;

    PrdItem prdItem;
    OnViewHolderItemClickListener onViewHolderItemClickListener;

    public PrdAddViewHolder(@NonNull View view, final PrdListAdapter.OnItemClickListener listener) {
        super(view);
        prd_name = view.findViewById(R.id.prdChk_name);
        category = view.findViewById(R.id.prdChk_category);
        checkBox = view.findViewById(R.id.prdChk_checkbox);

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

    public void onBind(MyItem data, Boolean checked){
        prdItem = (PrdItem) data;
        prd_name.setText(prdItem.getName());
        category.setText(prdItem.getCategory());
        if (checked == null){
            checkBox.setChecked(false);
        } else {
            checkBox.setChecked(checked);
        }
    }
}

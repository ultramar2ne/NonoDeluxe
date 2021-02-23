package com.example.nonodeluxe.viewholder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.model.MyItem;
import com.example.nonodeluxe.model.MyItemView;
import com.example.nonodeluxe.model.PrdItem;

public class PrdAddViewHolder extends MyItemView {

    public TextView prd_name;
    public TextView category;
    public CheckBox checkBox;

    PrdItem prdItem;

    public PrdAddViewHolder(@NonNull View view) {
        super(view);
        prd_name = view.findViewById(R.id.prdChk_name);
        category = view.findViewById(R.id.prdChk_category);
        checkBox = view.findViewById(R.id.prdChk_checkbox);
    }

    public void onBind(MyItem data){
        prdItem = (PrdItem) data;
        prd_name.setText(prdItem.getName());
        category.setText(prdItem.getCategory());
    }
}

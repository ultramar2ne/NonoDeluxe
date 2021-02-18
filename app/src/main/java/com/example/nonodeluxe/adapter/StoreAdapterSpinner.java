package com.example.nonodeluxe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.model.StoreItem;

import java.util.ArrayList;

public class StoreAdapterSpinner extends ArrayAdapter<StoreItem> {

    public StoreAdapterSpinner(Context context, ArrayList<StoreItem> storeitems ){
        super(context,0,storeitems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_store,parent,false
            );
        }

        TextView store_code = convertView.findViewById(R.id.item_storeCode);
        TextView store_name = convertView.findViewById(R.id.item_storeName);

        StoreItem currentItem = getItem(position);

        if (currentItem != null){
            store_code.setText(currentItem.getStore_code() + "호점");
            store_name.setText(currentItem.getStore_name());
        }
        return convertView;
    }
}

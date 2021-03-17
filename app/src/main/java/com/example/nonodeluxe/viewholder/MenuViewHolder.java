package com.example.nonodeluxe.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.adapter.MainMenuAdapter;
import com.example.nonodeluxe.model.MainMenuItem;
import com.example.nonodeluxe.model.MyItem;
import com.example.nonodeluxe.model.StoreItem;

public class MenuViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView textView;

    private MainMenuItem mainMenuItem;

    public MenuViewHolder(@NonNull View view, final MainMenuAdapter.OnItemClickListener onItemClickListener) {
        super(view);
        imageView = view.findViewById(R.id.item_menuSrc);
        textView = view.findViewById(R.id.item_menuName);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        onItemClickListener.onItemClick(position);
                    }
                }
            }
        });
    }

    public void onBind(MyItem data){
        mainMenuItem = (MainMenuItem)data;
        imageView.setImageResource(mainMenuItem.getImageResource());
        textView.setText(mainMenuItem.getText());
    }
}

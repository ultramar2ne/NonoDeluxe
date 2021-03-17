package com.example.nonodeluxe.adapter;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.model.MainMenuItem;
import com.example.nonodeluxe.viewholder.MenuViewHolder;
import com.example.nonodeluxe.viewholder.PrdListViewHolder;

import java.util.ArrayList;

public class MainMenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {
    private OnItemClickListener onItemClickListener;
    private ArrayList<MainMenuItem> menuItems;

    public MainMenuAdapter(ArrayList<MainMenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(MainMenuAdapter.OnItemClickListener listener){
        onItemClickListener = listener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu,parent, false);
        MenuViewHolder evh = new MenuViewHolder(view, onItemClickListener);

        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuViewHolder viewHolder = (MenuViewHolder)holder;
        viewHolder.onBind(menuItems.get(position));
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }
}

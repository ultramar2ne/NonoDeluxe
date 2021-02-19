package com.example.nonodeluxe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.model.PrdItem;
import com.example.nonodeluxe.viewholder.PrdListViewHolder;

import java.util.ArrayList;
import java.util.List;

public class PrdListAdapter extends RecyclerView.Adapter<PrdListViewHolder> {
    private OnItemClickListener onItemClickListener;
    private ArrayList<PrdItem> prdItems;
    private ArrayList<PrdItem> prdItemsFull;

    public PrdListAdapter(ArrayList<PrdItem> prdItems) {
        this.prdItems = prdItems;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }

    @NonNull
    @Override
    public PrdListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prd, parent, false);
        PrdListViewHolder evh = new PrdListViewHolder(view, onItemClickListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull PrdListViewHolder holder, int position) {
        PrdItem currentItem = prdItems.get(position);

        holder.prd_name.setText(currentItem.getName());
        holder.category.setText(currentItem.getCategory());
        holder.stock.setText(currentItem.getStock());
        holder.standard.setText(currentItem.getStandard());
    }

    @Override
    public int getItemCount() {
        return prdItems.size();
    }

    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<PrdItem> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0){
                filteredList.addAll(prdItemsFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (PrdItem item : prdItemsFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            prdItems.clear();
            prdItems.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };
}

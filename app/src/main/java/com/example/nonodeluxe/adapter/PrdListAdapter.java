package com.example.nonodeluxe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nonodeluxe.R;
import com.example.nonodeluxe.model.MyItem;
import com.example.nonodeluxe.model.MyItemView;
import com.example.nonodeluxe.model.PrdCase;
import com.example.nonodeluxe.model.PrdItem;
import com.example.nonodeluxe.viewholder.PrdAddViewHolder;
import com.example.nonodeluxe.viewholder.PrdListViewHolder;

import java.util.ArrayList;
import java.util.List;

public class PrdListAdapter extends RecyclerView.Adapter<MyItemView> {
    private OnItemClickListener onItemClickListener;
    private ArrayList<PrdItem> prdItems = new ArrayList<>();

    private PrdCase sel_type;

    public PrdListAdapter(PrdCase sel_type, ArrayList<PrdItem> prdItems) {
        this.sel_type = sel_type;
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
    public MyItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if (sel_type == PrdCase.LIST){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prd,parent,false);
            return new PrdListViewHolder(view, onItemClickListener);
        } else if (sel_type == PrdCase.ADD){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prd_checkbox,parent,false);
            return new PrdAddViewHolder(view);
        }
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prd, parent, false);
//        PrdListViewHolder evh = new PrdListViewHolder(view, onItemClickListener);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyItemView holder, int position) {
        if (holder instanceof PrdListViewHolder){
            PrdListViewHolder viewHolder = (PrdListViewHolder)holder;
            viewHolder.onBind(prdItems.get(position));
        } else if (holder instanceof PrdAddViewHolder){
            PrdAddViewHolder viewHolder = (PrdAddViewHolder) holder;
            viewHolder.onBind(prdItems.get(position));
        }
//
//        PrdItem currentItem = prdItems.get(position);
//
//        holder.prd_name.setText(currentItem.getName());
//        holder.category.setText(currentItem.getCategory());
//        holder.stock.setText(String.valueOf(currentItem.getStock()));
//        holder.standard.setText(currentItem.getStandard());
    }

    @Override
    public int getItemCount() {
        return prdItems.size();
    }

//    public Filter getFilter() {
//        return exampleFilter;
//    }

//    private Filter exampleFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence charSequence) {
//            List<PrdItem> filteredList = new ArrayList<>();
//
//            if (charSequence == null || charSequence.length() == 0){
//                filteredList.addAll(prdItemsFull);
//            } else {
//                String filterPattern = charSequence.toString().toLowerCase().trim();
//
//                for (PrdItem item : prdItemsFull) {
//                    if (item.getName().toLowerCase().contains(filterPattern)){
//                        filteredList.add(item);
//                    }
//                }
//            }
//            FilterResults results = new FilterResults();
//            results.values = filteredList;
//
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//            prdItems.clear();
//            prdItems.addAll((List)filterResults.values);
//            notifyDataSetChanged();
//        }
//    };
}

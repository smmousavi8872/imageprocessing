package com.developer.smmmousavi.imageprocessing.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.smmmousavi.imageprocessing.R;
import com.developer.smmmousavi.imageprocessing.ui.viewholder.CbirResultVH;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CbirResultAdapter extends RecyclerView.Adapter<CbirResultVH> {

    private List<String> mItemList;

    public CbirResultAdapter(List<String> itemList) {
        mItemList = itemList;
    }

    public void setItemList(List<String> itemList) {
        mItemList = itemList;
    }

    @NonNull
    @Override
    public CbirResultVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cbir_result, null, false);
        return new CbirResultVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CbirResultVH holder, int position) {
        holder.bind(mItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}

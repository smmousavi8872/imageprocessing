package com.developer.smmmousavi.imageprocessing.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.smmmousavi.imageprocessing.R;
import com.developer.smmmousavi.imageprocessing.model.ArtefactResultModel;
import com.developer.smmmousavi.imageprocessing.ui.viewholder.ArtefactResultVH;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArtefactResultAdapter extends RecyclerView.Adapter<ArtefactResultVH> {

    private List<ArtefactResultModel> mItemList;

    public void setItemList(List<ArtefactResultModel> itemList) {
        this.mItemList = itemList;
    }

    public ArtefactResultAdapter(List<ArtefactResultModel> itemList) {
        this.mItemList = itemList;
    }

    @NonNull
    @Override
    public ArtefactResultVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_result_artefact, null);
        return new ArtefactResultVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtefactResultVH holder, int position) {
        holder.bind(mItemList.get(position));

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}

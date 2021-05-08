package com.filicko.petcare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MedInfoAdapter extends RecyclerView.Adapter<MedInfoAdapter.ViewHolder> {

    Context context;
    ArrayList<MedInfoModel> medInfoList;

    public MedInfoAdapter(Context context, ArrayList<MedInfoModel> medInfoList) {
        this.context = context;
        this.medInfoList = medInfoList;
    }

    @NonNull
    @Override
    public MedInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedInfoAdapter.ViewHolder holder, int position) {
        if(medInfoList != null && medInfoList.size() > 0) {
            MedInfoModel model = medInfoList.get(position);
            holder.medDatumText.setText(model.getDatum());
            holder.medInfoText.setText(model.getInfo());
        }
        else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return medInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView medDatumText;
        TextView medInfoText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            medDatumText = itemView.findViewById(R.id.medDatumText);
            medInfoText = itemView.findViewById(R.id.medInfoText);
        }
    }
}

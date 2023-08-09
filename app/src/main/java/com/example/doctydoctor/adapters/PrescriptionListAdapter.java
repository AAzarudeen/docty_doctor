package com.example.doctydoctor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctydoctor.R;

public class PrescriptionListAdapter extends RecyclerView.Adapter<PrescriptionListAdapter.ListViewHolder> {

    Context context;

    public PrescriptionListAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListViewHolder(LayoutInflater.from(context).inflate(R.layout.appointment_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

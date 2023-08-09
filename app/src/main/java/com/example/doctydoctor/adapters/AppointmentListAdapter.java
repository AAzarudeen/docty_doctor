package com.example.doctydoctor.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctydoctor.OnChangeFragments;
import com.example.doctydoctor.R;
import com.example.doctydoctor.model.AppointmentDetails;

import java.util.ArrayList;

public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.ListAdapterVH> {

    Context context;

    ArrayList<AppointmentDetails> appointmentList;

    OnChangeFragments onCha;
    public AppointmentListAdapter(Context context, ArrayList<AppointmentDetails> appointmentList, OnChangeFragments onCha){
        this.context = context;
        this.appointmentList = appointmentList;
        this.onCha = onCha;
    }

    @NonNull
    @Override
    public ListAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.appointment_list_item,parent,false);
        return new ListAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterVH holder, int position) {
            holder.name.setText(appointmentList.get(position).getName());
            holder.date.setText(appointmentList.get(position).getDate());
            holder.time.setText(appointmentList.get(position).getTime());
            holder.name.setOnClickListener(v->{
                Bundle data = new Bundle();
                data.putString("name",appointmentList.get(position).getName());
                data.putString("date",appointmentList.get(position).getDate());
                data.putString("time",appointmentList.get(position).getTime());
                data.putString("description",appointmentList.get(position).getDescription());

                System.out.println(data.getString("name"));
                System.out.println(data.getString("date"));
                System.out.println(data.getString("time"));
                System.out.println(data.getString("description"));




                onCha.onChange(data);
            });
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public class ListAdapterVH extends RecyclerView.ViewHolder {
        TextView name,date,time;
        public ListAdapterVH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
        }
    }
}
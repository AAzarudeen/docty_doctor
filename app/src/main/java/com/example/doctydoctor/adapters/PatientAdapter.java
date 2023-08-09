package com.example.doctydoctor.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doctydoctor.R;
import com.example.doctydoctor.activity.MainActivity;
import com.example.doctydoctor.fragments.AddAppointmentFragment;
import com.example.doctydoctor.fragments.DashboardFragment;
import com.example.doctydoctor.fragments.PatientProfileFragment;
import com.example.doctydoctor.fragments.ProfileFragment;

import java.util.ArrayList;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {

    ArrayList<PatientDetails> patientDetails;
    Context context;

    public PatientAdapter(Context context, ArrayList<PatientDetails> patientDetails){
        this.context = context;
        this.patientDetails = patientDetails;
    }

    @NonNull
    @Override
    public PatientAdapter.PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_card,parent,false) ;
        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientAdapter.PatientViewHolder holder, int position) {
            holder.name.setText(patientDetails.get(position).getName());
        Glide
                .with(context)
                .load(patientDetails.get(position).getProfile())
                .into(holder.photo);
        holder.itemView.setOnClickListener(v->{
            PatientProfileFragment vj = new PatientProfileFragment();

            Bundle data = new Bundle();

            data.putString("uid",patientDetails.get(position).getuId());

            vj.setArguments(data);

            ((FragmentActivity)context)
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment,vj)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return patientDetails.size();
    }

    public class PatientViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView photo;
        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            photo = itemView.findViewById(R.id.photo);
        }
    }
}

package com.example.doctydoctor.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doctydoctor.R;
import com.example.doctydoctor.model.Utils;
import com.example.doctydoctor.activity.MainActivity;
import com.example.doctydoctor.adapters.PatientAdapter;
import com.example.doctydoctor.adapters.PatientDetails;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    TextView userName;
    ImageView profile;
    Button shareCode;
    RecyclerView patientList;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public DashboardFragment() {

    }

    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        shareCode = view.findViewById(R.id.shareCode);
        userName = view.findViewById(R.id.username);
        profile = view.findViewById(R.id.profile);
        patientList = view.findViewById(R.id.patient_list);

        Glide
                .with(getContext())
                .load(Utils.profile)
                .into(profile);

        userName.setText(Utils.name);

        ArrayList<PatientDetails> pdList = new ArrayList<>();

        MainActivity.db.collection("users")
                        .get()
                                .addOnSuccessListener(task->{
                                    for(DocumentSnapshot ds : task.getDocuments()){
                                        if(ds.get("doctorID") != null) {
                                            if (ds.get("doctorID").toString().equals(MainActivity.auth.getUid())){
                                                MainActivity.storageRef
                                                        .child(String.format("profiles/%s",ds.getId()))
                                                        .getDownloadUrl()
                                                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                            @Override
                                                            public void onSuccess(Uri uri) {
                                                                PatientDetails pd = new PatientDetails(ds.getId(),ds.get("name").toString(),
                                                                        ds.get("phone").toString(),ds.get("gender").toString(),
                                                                        ds.get("blood_type").toString(),
                                                                        ds.get("dob").toString(),ds.get("height").toString(),
                                                                        ds.get("weight").toString(),ds.get("doctorID").toString()
                                                                        ,uri);
                                                                pdList.add(pd);
                                                                patientList.setAdapter(new PatientAdapter(getContext(),pdList));
                                                                patientList.setLayoutManager(new LinearLayoutManager(getContext()));
                                                            }
                                                        }).addOnFailureListener(exception -> {
                                                            Toast.makeText(getContext(), "Error in loading image", Toast.LENGTH_SHORT).show();
                                                        });
                                            }
                                        }
                                    }
                                });

        shareCode.setOnClickListener(v->{
            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            String shareBody = "Here is the share content body";
            intent.setType("text/plain");
            intent.putExtra(android.content.Intent.EXTRA_TEXT, MainActivity.user.getUid());
            /*Fire!*/
            startActivity(Intent.createChooser(intent, "Choose"));
        });
        return view;
    }
}
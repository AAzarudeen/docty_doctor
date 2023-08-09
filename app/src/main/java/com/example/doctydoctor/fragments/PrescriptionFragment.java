package com.example.doctydoctor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctydoctor.AddPrescriptionFragment;
import com.example.doctydoctor.R;
import com.example.doctydoctor.adapters.PrescriptionListAdapter;

public class PrescriptionFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public PrescriptionFragment() {

    }

    public static PrescriptionFragment newInstance(String param1, String param2) {
        PrescriptionFragment fragment = new PrescriptionFragment();
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
        View view = inflater.inflate(R.layout.fragment_prescription, container, false);


        Button add_prescription = view.findViewById(R.id.add_prescription);

        RecyclerView prescriptionList = view.findViewById(R.id.prescription_list);

        prescriptionList.setLayoutManager(new LinearLayoutManager(getContext()));
        prescriptionList.setAdapter(new PrescriptionListAdapter(getContext()));

        add_prescription.setOnClickListener(v-> {
            AddPrescriptionFragment vj = new AddPrescriptionFragment();
        });

        return view;
    }
}
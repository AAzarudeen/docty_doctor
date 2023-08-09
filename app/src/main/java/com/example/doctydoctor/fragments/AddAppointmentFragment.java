package com.example.doctydoctor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.doctydoctor.R;

public class AddAppointmentFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AddAppointmentFragment() {

    }

    public static AddAppointmentFragment newInstance(String param1, String param2) {
        AddAppointmentFragment fragment = new AddAppointmentFragment();
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

        View view = inflater.inflate(R.layout.fragment_add_appointment, container, false);
        RadioGroup modeType = view.findViewById(R.id.type_mode);
        RadioButton online = view.findViewById(R.id.online);
        RadioButton offline = view.findViewById(R.id.offline);
        TextView location = view.findViewById(R.id.location);


        modeType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.online){
                    location.setVisibility(View.GONE);
            }
            if (checkedId == R.id.offline){
                location.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }
}
package com.example.doctydoctor.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doctydoctor.CalActivity;
import com.example.doctydoctor.R;
import com.example.doctydoctor.VideoActivity;

public class ViewAppointmentFragment extends Fragment {

    TextView name,description,date,time;


    Bundle data;

    public ViewAppointmentFragment() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        data = getArguments();
        System.out.println(getArguments());
    }

    public static ViewAppointmentFragment newInstance(String param1, String param2) {

        ViewAppointmentFragment fragment = new ViewAppointmentFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println(getArguments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_appointment, container, false);

        Button join = view.findViewById(R.id.join_meeting);
        name = view.findViewById(R.id.name);
        date = view.findViewById(R.id.date);
        time = view.findViewById(R.id.time);
        description = view.findViewById(R.id.description);

        System.out.println(getArguments());

        System.out.println(getArguments().getString("name"));
        System.out.println(getArguments().getString("date"));
        System.out.println(getArguments().getString("time"));
        System.out.println(getArguments().getString("description"));

        name.setText(getArguments().getString("name"));
        date.setText(getArguments().getString("date"));
        time.setText(getArguments().getString("time"));
        description.setText(getArguments().getString("description"));

        join.setOnClickListener(v->{
//            getActivity()
//                    .getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.main_fragment,new VideoCallFragment())
//                    .commit();
            Intent intent = new Intent(getActivity(), CalActivity.class);
            startActivity(intent);
        });

        return view;
    }
}
package com.example.doctydoctor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctydoctor.OnChangeFragments;
import com.example.doctydoctor.R;
import com.example.doctydoctor.model.AppointmentDetails;
import com.example.doctydoctor.activity.MainActivity;
import com.example.doctydoctor.adapters.AppointmentListAdapter;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class AppointmentFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AppointmentFragment() {

    }

    public static AppointmentFragment newInstance(String param1, String param2) {
        AppointmentFragment fragment = new AppointmentFragment();
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
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);

        OnChangeFragments onCha = (data) ->{
            System.out.println(data.getString("name"));
            System.out.println(data.getString("date"));
            System.out.println(data.getString("time"));
            System.out.println(data.getString("description"));


            ViewAppointmentFragment vj = new ViewAppointmentFragment();
            vj.setArguments(data);
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment,vj)
                .commit();
        };

//        CardView addAppointment = view.findViewById(R.id.add_appointment);
//        CardView viewAppointment = view.findViewById(R.id.view_appointment);

        RecyclerView appointmentList = view.findViewById(R.id.appointment_list);

//        addAppointment.setOnClickListener(v->{
//            getActivity()
//                    .getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.main_fragment,new AddAppointmentFragment())
//                    .commit();
//        });
//
//        viewAppointment.setOnClickListener(v->{
//            getActivity()
//                    .getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.main_fragment,new ViewAppointmentFragment())
//                    .commit();
////            Navigation.findNavController(v).navigate(R.id.action_appointmentFragment_to_viewAppointmentFragment);
//        });

        MainActivity.db.collection("appointment")
                .get()
                .addOnSuccessListener(task->{
                    ArrayList<AppointmentDetails> apList= new ArrayList<>();
                    for (DocumentSnapshot ds : task.getDocuments()){
                        AppointmentDetails ap = new AppointmentDetails(ds.getString("name"),
                        ds.getString("description"),ds.getString("date"),
                                ds.getString("time"));
                        apList.add(ap);
                        appointmentList.setLayoutManager(new LinearLayoutManager(getContext()));
                        appointmentList.setAdapter(new AppointmentListAdapter(getContext(),apList,onCha));
                    }
                }).addOnFailureListener(task->{

                });

        return view;
    }
}
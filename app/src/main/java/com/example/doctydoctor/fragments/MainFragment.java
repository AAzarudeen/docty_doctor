package com.example.doctydoctor.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.doctydoctor.R;
import com.example.doctydoctor.model.Utils;
import com.example.doctydoctor.activity.MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainFragment extends Fragment implements NavigationBarView.OnItemSelectedListener{

    BottomNavigationView bottomNavigationView;

    DashboardFragment dashboardFragment;
    ProfileFragment profileFragment;
    PrescriptionFragment prescriptionFragment;
    AppointmentFragment appointmentFragment;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MainFragment() {

    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        MainActivity.storageRef
                .child(String.format("profiles/%s",MainActivity.user.getUid()))
                .getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                            Utils.profile = uri;
                    }
                }).addOnFailureListener(exception -> {
                    Toast.makeText(getContext(), "Error in loading image", Toast.LENGTH_SHORT).show();
                });

        MainActivity.db
                .collection("doctor")
                .document(MainActivity.user.getUid())
                .get()
                .addOnSuccessListener(task->{
                    Utils.name = task.getString("name");
                    Utils.gender = task.getString("gender");
                    Utils.dob = task.getString("dob");
                    Utils.specialization = task.getString("specialization");
                    Utils.experience = task.getString("experience");
                    Utils.phone = task.getString("phone");
                    Utils.email = task.getString("email");
                    Utils.doctorID = task.getString("doctor");
                    bottomNavigationView.setSelectedItemId(R.id.dashboard);
                });

        dashboardFragment = new DashboardFragment();
        profileFragment = new ProfileFragment();
        prescriptionFragment = new PrescriptionFragment();
        appointmentFragment = new AppointmentFragment();

        bottomNavigationView = view.findViewById(R.id.bottom_nav);
            bottomNavigationView
                    .setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        return view;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        if (Utils.doctorID == null){
//            Toast.makeText(getContext(), "Please connect with your doctor", Toast.LENGTH_SHORT).show();
//            changeFragment(dashboardFragment);
//            return false;
//        }
        switch (item.getItemId()){
            case R.id.dashboard:
                changeFragment(dashboardFragment);
                return true;
            case R.id.profile:
                changeFragment(profileFragment);
                return true;
            case R.id.appointment:
                changeFragment(appointmentFragment);
                return true;
        }
        return false;
    }

    void changeFragment(Fragment fragment){
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment,fragment)
                .commit();
    }
}
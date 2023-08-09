package com.example.doctydoctor.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.doctydoctor.AddPrescriptionFragment;
import com.example.doctydoctor.R;
import com.example.doctydoctor.activity.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class PatientProfileFragment extends Fragment {

    ImageView profile;
    TextView name, phone, height, weight, blood_type,dob;
    RadioGroup gender;
    Uri profileUri;

    Button add_prescription;

    String genderStr = "male";

    ActivityResultLauncher<Intent> startActivityForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            Uri contentUri = data.getData();
//            profile.setImageURI(contentUri);
//            profileUri = data.getData();
            StorageReference riversRef = MainActivity.storageRef.child("prescriptions/"+getArguments().getString("uid"));
            riversRef.putFile(contentUri).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                }
            });
            System.out.println("URI "+contentUri);
        }
    });

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PatientProfileFragment() {
        // Required empty public constructor
    }

    public static PatientProfileFragment newInstance(String param1, String param2) {
        PatientProfileFragment fragment = new PatientProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_patient_profile, container, false);

        profile = view.findViewById(R.id.profile);
        name = view.findViewById(R.id.name);
        phone = view.findViewById(R.id.phone);
        dob = view.findViewById(R.id.dob);
        gender = view.findViewById(R.id.gender);
        weight = view.findViewById(R.id.weight);
        height = view.findViewById(R.id.height);
        blood_type = view.findViewById(R.id.blood_type);
        add_prescription = view.findViewById(R.id.add_prescription);

        add_prescription.setOnClickListener(v->{
//            AddPrescriptionFragment vj = new AddPrescriptionFragment();
//            Bundle data = new Bundle();
//            data.putString("uid",getArguments().getString("uid"));
//            getActivity()
//                    .getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.main_fragment,vj)
//                    .commit();
            Intent data = new Intent(Intent.ACTION_GET_CONTENT);
            data.addCategory(Intent.CATEGORY_OPENABLE);
            data.setType("application/pdf");
            Intent intent = Intent.createChooser(data, "Choose a file");
            startActivityForResult.launch(intent);
        });

        MainActivity.storageRef
                .child(String.format("profiles/%s",getArguments().getString("uid")))
                .getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide
                                .with(getContext())
                                .load(uri)
                                .into(profile);
                    }
                }).addOnFailureListener(exception -> {
                    Toast.makeText(getContext(), "Error in loading image", Toast.LENGTH_SHORT).show();
                });

        MainActivity.db
                .collection("users")
                .document(getArguments().getString("uid"))
                .get()
                .addOnSuccessListener(task->{
                    name.setText(task.getString("name"));
                    dob.setText(task.getString("dob"));
                    weight.setText(task.getString("weight"));
                    height.setText(task.getString("height"));
                    phone.setText(task.getString("phone"));
                    blood_type.setText(task.getString("blood_type"));

                    if (task.getString("gender").equals("male")) {
                        gender.check(R.id.male);
                    }
                    if (task.getString("gender").equals("female")) {
                        gender.check(R.id.female);
                    }
                });

        return view;
    }
}
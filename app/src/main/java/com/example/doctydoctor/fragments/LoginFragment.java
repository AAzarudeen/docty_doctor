package com.example.doctydoctor.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.doctydoctor.OTP;
import com.example.doctydoctor.R;
import com.example.doctydoctor.activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class LoginFragment extends Fragment {

    private EditText edtPhone, edtOTP;

    private Button verifyOTPBtn, generateOTPBtn;

    public static FirebaseAuth mAuth;

    private String verificationId;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    public LoginFragment() {

    }


    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mAuth = FirebaseAuth.getInstance();

        edtPhone = view.findViewById(R.id.phone);
        edtOTP = view.findViewById(R.id.otp);
        verifyOTPBtn = view.findViewById(R.id.verify_otp);
        generateOTPBtn = view.findViewById(R.id.send_otp);

        generateOTPBtn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                Toast.makeText(getContext(), "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
            } else {
                String phone = "+91" + edtPhone.getText().toString();
                sendVerificationCode(phone);
            }
        });

        verifyOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtOTP.getText().toString())) {
                    Toast.makeText(getContext(), "Please enter OTP", Toast.LENGTH_SHORT).show();
                } else {
                    verifyCode(edtOTP.getText().toString());
                }
            }
        });
        return view;
    }

    private void sendVerificationCode(String number) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(getActivity())
                        .setCallbacks(mCallBack)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            final String code = phoneAuthCredential.getSmsCode();

            if (code != null) {

                edtOTP.setText(code);

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

                signInWithCredential(credential);

            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            MainActivity.user = mAuth.getCurrentUser();
                            MainActivity.db
                                    .collection("doctor")
                                    .document(MainActivity.user.getUid())
                                    .get().addOnSuccessListener(ta->{
                                        if (ta.getData() == null){
                                            Navigation.findNavController(verifyOTPBtn).navigate(R.id.action_loginFragment_to_initialDetailsFragment);
                                        }
                                        else{
                                            Navigation.findNavController(verifyOTPBtn).navigate(R.id.action_loginFragment_to_dashboard);
                                        }
                                    });
                            Toast.makeText(getContext(), "Login Success", Toast.LENGTH_SHORT).show();
//                            finish();
                        } else {
                            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void verifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        signInWithCredential(credential);
    }

}
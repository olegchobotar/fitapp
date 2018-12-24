package com.android.fitapp.authentification;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.fitapp.Main;
import com.android.fitapp.R;
import com.android.fitapp.entity.User;
import com.android.fitapp.profile.EditProfile;
import com.android.fitapp.profile.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class ResetPassword extends Fragment {
    private EditText email;
    private Button buttonReset;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        email = view.findViewById(R.id.reset_password_email);
        buttonReset = view.findViewById(R.id.btn_reset_password);
        progressBar = view.findViewById(R.id.reset_progress_bar);
        progressBar.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString();
                if (TextUtils.isEmpty(userEmail)){
                    Toast.makeText(getActivity(), "Please write your valid email address.", Toast.LENGTH_LONG).show();
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()){
                                Toast.makeText(getActivity(), "Password was sent on your email.", Toast.LENGTH_LONG).show();
                                Authorization fragment = new Authorization();
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                            }
                            else {
                                String message = task.getException().getMessage();
                                Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        return view;
    }

}

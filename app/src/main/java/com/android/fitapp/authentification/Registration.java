package com.android.fitapp.authentification;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Registration extends Fragment {

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextCountry;
    private EditText editTextCity;
    private EditText editTextPassword;
    private EditText editTextPhone;

    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    private FirebaseDatabase firebaseDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        editTextName = view.findViewById(R.id.edit_text_name);
        editTextEmail = view.findViewById(R.id.edit_text_email);
        editTextCountry = view.findViewById(R.id.edit_text_country);
        editTextCity = view.findViewById(R.id.edit_text_city);
        editTextPassword = view.findViewById(R.id.edit_text_password);
        editTextPhone = view.findViewById(R.id.edit_text_phone);
        progressBar = view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        view.findViewById(R.id.button_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                ((Main) getActivity()).changeLoginStatus();

            }
        });

        return view;
    }


    private void registerUser() {
        final String name = editTextName.getText().toString().trim();
        final String nickname = editTextName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String country = editTextCountry.getText().toString().trim();
        final String city = editTextCity.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String phone = editTextPhone.getText().toString().trim();

        if (name.isEmpty()) {
            editTextName.setError(getString(R.string.input_error_name));
            editTextName.requestFocus();
            return;
        }
        if (nickname.isEmpty()) {
            editTextName.setError(getString(R.string.input_error_name));
            editTextName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError(getString(R.string.input_error_email));
            editTextEmail.requestFocus();
            return;
        }

        if (country.isEmpty()) {
            editTextCountry.setError(getString(R.string.input_error_country));
            editTextCountry.requestFocus();
            return;
        }

        if (city.isEmpty()) {
            editTextCity.setError(getString(R.string.input_error_city));
            editTextCity.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError(getString(R.string.input_error_email_invalid));
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError(getString(R.string.input_error_password));
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError(getString(R.string.input_error_password_length));
            editTextPassword.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            editTextPhone.setError(getString(R.string.input_error_phone));
            editTextPhone.requestFocus();
            return;
        }

        if (phone.length() != 10) {
            editTextPhone.setError(getString(R.string.input_error_phone_invalid));
            editTextPhone.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            User user = new User(
                                    name,
                                    email,
                                    country,
                                    city,
                                    phone
                            );

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        sendVerificationEmail();
                                        Toast.makeText(getActivity(),  getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                                        mAuth.signOut();
                                        Authorization fragment = new Authorization();
                                        getActivity().getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                                        ((Main)getActivity()).changeLoginStatus();
                                    } else {
                                        Toast.makeText(getActivity(),  task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    private void sendVerificationEmail(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){

                    }else {
                        Toast.makeText(getActivity(), "Couldn`t send verification email", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

}

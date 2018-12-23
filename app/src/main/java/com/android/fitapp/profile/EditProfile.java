package com.android.fitapp.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.fitapp.R;
import com.android.fitapp.entity.User;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class EditProfile extends Fragment {

    private EditText editTextName;
    private EditText editTextTitle;
    private EditText editTextCountry;
    private EditText editTextCity;
    private EditText editTextDescription;
    private EditText editTextPhone;
    private Button updateButton;
    private ProgressBar progressBar;

    private String uid;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private User mUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        editTextName = view.findViewById(R.id.edit_text_name);
        editTextTitle = view.findViewById(R.id.edit_text_title);
        editTextCountry = view.findViewById(R.id.edit_text_country);
        editTextCity = view.findViewById(R.id.edit_text_city);
        editTextDescription = view.findViewById(R.id.edit_text_description);
        editTextPhone = view.findViewById(R.id.edit_text_phone);
        progressBar = view.findViewById(R.id.progressbar);
        updateButton = view.findViewById(R.id.button_update);
        progressBar.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


        loadData();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfo();
            }
        });

        return view;
    }

    private void loadData() {
        if (mAuth.getCurrentUser().getUid() != null) {
            uid = mAuth.getCurrentUser().getUid();
            FirebaseDatabase.getInstance().getReference().child("Users").child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String retrieveUsername = dataSnapshot.child("name").getValue().toString();
                        String retrieveTitle = dataSnapshot.child("title").getValue().toString();
                        String retrieveCountry = dataSnapshot.child("country").getValue().toString();
                        String retrieveCity = dataSnapshot.child("city").getValue().toString();
                        String retrieveDescription = dataSnapshot.child("description").getValue().toString();
                        String retrievePhone = dataSnapshot.child("phone").getValue().toString();

                        editTextName.setText(retrieveUsername);
                        editTextTitle.setText(retrieveTitle);
                        editTextCountry.setText(retrieveCountry);
                        editTextCity.setText(retrieveCity);
                        editTextDescription.setText(retrieveDescription);
                        editTextPhone.setText(retrievePhone);


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void updateInfo() {
        final String name = editTextName.getText().toString().trim();
        final String title = editTextTitle.getText().toString().trim();
        final String country = editTextCountry.getText().toString().trim();
        final String city = editTextCity.getText().toString().trim();
        final String description = editTextDescription.getText().toString().trim();
        final String phone = editTextPhone.getText().toString().trim();

        if (name.isEmpty()) {
            editTextName.setError(getString(R.string.input_error_name));
            editTextName.requestFocus();
            return;
        }
        if (title.isEmpty()) {
            editTextName.setError(getString(R.string.input_error_name));
            editTextName.requestFocus();
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

        if (description.isEmpty()) {
            editTextName.setError(getString(R.string.input_error_name));
            editTextName.requestFocus();
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

       databaseReference.child(getContext().getString(R.string.users)).child(uid).child("name").setValue(name);
       databaseReference.child(getContext().getString(R.string.users)).child(uid).child("title").setValue(title);
       databaseReference.child(getContext().getString(R.string.users)).child(uid).child("country").setValue(country);
       databaseReference.child(getContext().getString(R.string.users)).child(uid).child("city").setValue(city);
       databaseReference.child(getContext().getString(R.string.users)).child(uid).child("description").setValue(description);
       databaseReference.child(getContext().getString(R.string.users)).child(uid).child("phone").setValue(phone);
       progressBar.setVisibility(View.GONE);
       ProfileFragment fragment = new ProfileFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
        Toast.makeText(getActivity(), "The data hes been changed", Toast.LENGTH_LONG).show();


    }

}




package com.android.fitapp.profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.fitapp.R;
import com.android.fitapp.authentification.Authorization;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;


public class ProfileFragment extends Fragment {
    private TextView nameView;
    private TextView titleView;
    private TextView descriptionView;
    private TextView cityView;
    private TextView ratingView;
    private TextView programsView;
    private ImageView userImage;

    private String uid;
    private Uri profImageURI = null;

    ImageButton editProfile;


    private FirebaseAuth mAuth;
    private ValueEventListener eventListener;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;

    private static final int PICK_IMAGE_REQUEST = 234;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            Authorization fragment = new Authorization();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
        }
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        nameView = view.findViewById(R.id.profile_name);
        titleView = view.findViewById(R.id.profile_title);
        descriptionView = view.findViewById(R.id.profile_description);
        cityView = view.findViewById(R.id.profile_city);
        ratingView = view.findViewById(R.id.profile_rating);
        programsView = view.findViewById(R.id.profile_programs);
        userImage = view.findViewById(R.id.profile_image);

        editProfile = view.findViewById(R.id.profile_edit);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    uid = mAuth.getCurrentUser().getUid();
                    databaseReference.addValueEventListener(eventListener);
                } else {
                    Authorization fragment = new Authorization();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                }
            }


        };
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                retrieveUserData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Please check your internet connection", Toast.LENGTH_LONG).show();
            }
        };

        editProfile.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                EditProfile fragment = new EditProfile();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });
        return view;
    }


    private void changePhoto(){
        if (profImageURI != null){
            StorageReference imagePath = storageReference.child("users").child(uid);

            imagePath.putFile(profImageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String imageUri = taskSnapshot.getDownloadUrl().toString();
                    databaseReference.child("Users").child(uid).child("photoUrl").setValue(imageUri) .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "New photo was uploaded", Toast.LENGTH_LONG).show();
                            } else
                                Toast.makeText(getActivity(), task.getException().toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            profImageURI = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), profImageURI);
                userImage.setImageBitmap(bitmap);
                changePhoto();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select an Image"), PICK_IMAGE_REQUEST);
    }

    private void retrieveUserData() {
        databaseReference.child("Users").child(uid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String retrieveUsername = dataSnapshot.child("name").getValue().toString();
                            String retrieveTitle = dataSnapshot.child("title").getValue().toString();
                            String retrieveProfImage = dataSnapshot.child("photoUrl").getValue().toString();
                            String retrieveDescription = dataSnapshot.child("description").getValue().toString();
                            String retrieveCity = dataSnapshot.child("city").getValue().toString();
                            String retrieveCountry = dataSnapshot.child("country").getValue().toString();
                            String retrieveRating = dataSnapshot.child("rating").getValue().toString();
                            String retrieveCountOfPrograms = dataSnapshot.child("countOfPrograms").getValue().toString();

                            if (!retrieveProfImage.isEmpty()) {
                                Picasso.get().load(retrieveProfImage).into(userImage);
                            }

                            nameView.setText(retrieveUsername);
                            titleView.setText(retrieveTitle);
                            descriptionView.setText(retrieveDescription);
                            cityView.setText(retrieveCity + ", " + retrieveCountry);
                            ratingView.setText(retrieveRating);
                            programsView.setText(retrieveCountOfPrograms);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}

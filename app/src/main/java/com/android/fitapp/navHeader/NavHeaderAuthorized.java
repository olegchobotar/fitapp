package com.android.fitapp.navHeader;

import android.content.Context;
import android.graphics.PostProcessor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.fitapp.R;
import com.android.fitapp.entity.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import static android.support.constraint.Constraints.TAG;


public class NavHeaderAuthorized extends Fragment {
    private TextView nameView;
    private TextView emailView;
    private ImageView imageView;

    private Context context;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;

    private String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_header_authorized, container, false);

        nameView = view.findViewById(R.id.username);
        emailView = view.findViewById(R.id.email);
        imageView = view.findViewById(R.id.nav_image);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        checkAuth();

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
        return view;
    }

    private void checkAuth() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    uid = mAuth.getCurrentUser().getUid();
                }
            }
        };
    }

    private void retrieveUserData() {
        checkAuth();
        if (uid != null) {
            FirebaseDatabase.getInstance().getReference().child("Users").child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String retrieveUsername = dataSnapshot.child("name").getValue().toString();
                        String retrieveEmail = dataSnapshot.child("email").getValue().toString();
                        String retrieveProfImage = dataSnapshot.child("photoUrl").getValue().toString();
                        if (!retrieveProfImage.isEmpty()) {
                            Picasso.get().load(retrieveProfImage).into(imageView);
                        }

                        nameView.setText(retrieveUsername);
                        emailView.setText(retrieveEmail);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    /* private void showData(DataSnapshot dataSnapshot) {
         for (DataSnapshot ds : dataSnapshot.getChildren()) {
             User user = new User();
             user.setName(ds.child(uid).getValue(User.class).getName());
             user.setTitle(ds.child(uid).getValue(User.class).getTitle());
             user.setDescription(ds.child(uid).getValue(User.class).getDescription());
             user.setEmail(ds.child(uid).getValue(User.class).getEmail());
             user.setCountry(ds.child(uid).getValue(User.class).getCountry());
             user.setCity(ds.child(uid).getValue(User.class).getCity());
             user.setPhone(ds.child(uid).getValue(User.class).getPhone());
             user.setPhotoUrl(ds.child(uid).getValue(User.class).getPhotoUrl());
             user.setRating(ds.child(uid).getValue(User.class).getRating());
             user.setCountOfPrograms(ds.child(uid).getValue(User.class).getCountOfPrograms());

             nameView.setText(user.getName());
             emailView.setText(user.getEmail());
         }
     }
 */
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        databaseReference.addValueEventListener(eventListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
        databaseReference.removeEventListener(eventListener);
    }

    private void setupFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        if (mAuth.getCurrentUser() != null) {
            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    if (user != null) {
                        // User is signed in
                    } else {
                        // User is signed out
                    }
                }
            };

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = new User();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        // if (ds.getKey().equals(context.getString(R.string.users) )){
                        nameView.setText(user.getName());
                        emailView.setText(user.getEmail());
                        //}
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else Toast.makeText(getActivity(), "Null", Toast.LENGTH_LONG).show();

    }
}

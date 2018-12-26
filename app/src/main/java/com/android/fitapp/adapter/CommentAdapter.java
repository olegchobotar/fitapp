package com.android.fitapp.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.fitapp.Main;
import com.android.fitapp.R;
import com.android.fitapp.entity.Comment;
import com.android.fitapp.entity.User;
import com.android.fitapp.profile.ProfileFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context mContext;
    private List<Comment> mComment;

    private FirebaseUser firebaseUser;

    public CommentAdapter(Context mContext, List<Comment> mComment) {
        this.mContext = mContext;
        this.mComment = mComment;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_item, viewGroup, false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final Comment comment = mComment.get(i);

        if(comment != null){
            if (i % 2 == 0) viewHolder.getView().setBackgroundColor(Main.themeAttributeToColor(R.attr.programFirstRow, mContext, R.color.white));
            else viewHolder.getView().setBackgroundColor(Main.themeAttributeToColor(R.attr.programSecondRow, mContext, R.color.white));
            viewHolder.getCommentMessage().setText(String.valueOf(comment.getComment()));

            getUserInfo(viewHolder, comment.getPublisher());
        }

        /*viewHolder.imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("UserId", comment.());

                ProfileFragment fragment = new ProfileFragment();
                fragment.setArguments(bundle);

                ((Main) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });*/
    }



    @Override
    public int getItemCount() {
        return mComment.size();
    }

    @Getter
    @Setter
    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView imageProfile;
        TextView username;
        TextView commentMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

            imageProfile = itemView.findViewById(R.id.comment_image_profile);
            username = itemView.findViewById(R.id.comment_username);
            commentMessage = itemView.findViewById(R.id.comment_message);
        }

    }

    private void getUserInfo(@NonNull final ViewHolder viewHolder, String publisherId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(publisherId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Glide.with(mContext).load(user.getPhotoUrl()).into(viewHolder.imageProfile);
                viewHolder.getUsername().setText(String.valueOf(user.getName()));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}


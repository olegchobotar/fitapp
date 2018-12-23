package com.android.fitapp.profile;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.fitapp.R;
import com.android.fitapp.entity.User;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;


public class ProfileFragment extends Fragment {
    TextView nameView;
    TextView titleView;
    TextView descriptionView;
    TextView cityView;
    TextView ratingView;
    TextView programsView;



    ImageButton editProfile;

    String url = "http://www.mocky.io/v2/5c1537852e0000630037c664";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());


        User user = restTemplate.getForObject(url, User.class);

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        nameView = view.findViewById(R.id.profile_name);
        titleView = view.findViewById(R.id.profile_title);
        descriptionView = view.findViewById(R.id.profile_description);
        cityView = view.findViewById(R.id.profile_city);
        ratingView = view.findViewById(R.id.profile_rating);
        programsView = view.findViewById(R.id.profile_programs);

        editProfile = view.findViewById(R.id.profile_edit);


        nameView.setText(user.getName());
        titleView.setText(user.getTitle());
        descriptionView.setText(user.getDescription());
        cityView.setText(user.getCity() + ", " + user.getCountry());
        ratingView.setText(String.valueOf(user.getRating()));
        programsView.setText(String.valueOf(user.getCountOfPrograms()));


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfile fragment = new EditProfile();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });
        return view;
    }
}

package com.android.fitapp;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.fitapp.entity.User;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;


public class ProfileFragment extends Fragment {
    TextView nameView;
    TextView descriptionView;
    TextView cityView;

    String url = "http://www.mocky.io/v2/5c14fd543400005e1cb8e990";
    //String url = "https://fit-app-by-a1lexen.herokuapp.com";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());


        User user = restTemplate.getForObject(url, User.class);
       //com.example.oleg.slidemenu.entity.ProgramRow[] response = restTemplate.getForObject(url+"/programs", com.example.oleg.slidemenu.entity.ProgramRow[].class);

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        nameView = view.findViewById(R.id.profile_name);
        descriptionView = view.findViewById(R.id.profile_description);
        cityView = view.findViewById(R.id.profile_city);

        nameView.setText(user.getName());
        descriptionView.setText(user.getPhone());
        cityView.setText(user.getCity() + ", " + user.getCountry());


        /*nameView = view.findViewById(R.id.profile_name);
        descriptionView = view.findViewById(R.id.profile_description);
        cityView = view.findViewById(R.id.profile_city);
        user = new User();


        nameView.setAdapter(new ProgramsAdapter(this.getContext(), programs));
*/
        return view;
    }
}

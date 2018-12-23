package com.android.fitapp.navHeader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.fitapp.Main;
import com.android.fitapp.R;
import com.android.fitapp.authentification.Authorization;
import com.android.fitapp.authentification.Registration;

public class NavHeaderNotAuthorized extends Fragment {
    Button buttonSignUp;
    Button buttonLogIn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_header_not_authorized, container, false);

        buttonSignUp = view.findViewById(R.id.nav_header_sign_up);
        buttonLogIn = view.findViewById(R.id.nav_header_log_in);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registration fragment = new Registration();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                ((Main) getActivity()).onBackPressed();

            }
        });


        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Authorization fragment = new Authorization();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                ((Main) getActivity()).onBackPressed();

            }
        });
        return view;
    }
}

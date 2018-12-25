package com.android.fitapp.programs;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.fitapp.FirstTabFragment;
import com.android.fitapp.R;
import com.android.fitapp.SecondTabFragment;
import com.android.fitapp.adapter.TabAdapter;
import com.android.fitapp.authentification.Authorization;
import com.android.fitapp.elements.CustomViewPager;
import com.google.firebase.auth.FirebaseAuth;

public class CreateProgramFragment extends Fragment {
    View view;
    TabAdapter adapter;
    TabLayout tabLayout;
    CustomViewPager viewPager;
    public static Boolean withExercises = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            Authorization fragment = new Authorization();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            Toast.makeText(getContext(), "You have to log in before using this feature!", Toast.LENGTH_SHORT).show();
        }

        view = inflater.inflate(R.layout.program_constructor_layout, container, false);

        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);

        adapter = new TabAdapter(getFragmentManager());

        adapter.addFragment(new FirstTabFragment(), "General");
        adapter.addFragment(new SecondTabFragment(), "Exercises");

        viewPager.setAdapter(adapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 1) {
                    if (!withExercises) {
                        Toast.makeText(getContext(), "Exercises not allowed!", Toast.LENGTH_SHORT).show();
                        viewPager.setCurrentItem(0);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }


}

package com.android.fitapp;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.android.fitapp.adapter.AddExerciseAdapter;
import com.android.fitapp.entity.Program;
import com.android.fitapp.programs.CreateProgramFragment;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;


public class FirstTabFragment extends Fragment {
    View view;
    Button createBtn;
    String url = "https://fit-app-by-a1lexen.herokuapp.com";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        final RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

        view = inflater.inflate(R.layout.tab_first_fragment, container, false);
        createBtn = view .findViewById(R.id.create_article);

        ((CheckBox)view.findViewById(R.id.with_exercises)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    CreateProgramFragment.withExercises = true;
                else
                    CreateProgramFragment.withExercises = false;
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CreateProgramFragment.withExercises) {
                    ResponseEntity response = restTemplate.postForEntity(url + "/programs", dataToProgram(),ResponseEntity.class);
                }

            }
        });

        return view;
    }

    private Program dataToProgram() {
        ArrayList<String> tags = new ArrayList(Arrays.asList("ABS", "Legs", "Chest", "Arms"));
        String title = ((EditText)view.findViewById(R.id.title)).getText().toString();
        String desc = ((EditText)view.findViewById(R.id.description)).getText().toString();
        String text = ((EditText)view.findViewById(R.id.text)).getText().toString();
        return new Program(title, text, desc, tags, AddExerciseAdapter.edits);
    }
}

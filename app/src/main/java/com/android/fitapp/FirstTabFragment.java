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
import android.widget.Toast;

import com.android.fitapp.adapter.AddExerciseAdapter;
import com.android.fitapp.entity.Program;
import com.android.fitapp.programs.CreateProgramFragment;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
                    Toast.makeText(getContext(), "New program successfully added!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    private Program dataToProgram() {
        List<String> tags = getTags((view.findViewById(R.id.article_tags)));
        String title = ((EditText)view.findViewById(R.id.title)).getText().toString();
        if (title.isEmpty()) {
            ((EditText)view.findViewById(R.id.title)).setError("The field cannot be empty!");
            view.findViewById(R.id.title).requestFocus();
        }

        String desc = ((EditText)view.findViewById(R.id.description)).getText().toString();
        if (desc.isEmpty()) {
            ((EditText)view.findViewById(R.id.description)).setError("The field cannot be empty!");
            view.findViewById(R.id.description).requestFocus();
        }

        String text = ((EditText)view.findViewById(R.id.text)).getText().toString();
        if (text.isEmpty()) {
            ((EditText)view.findViewById(R.id.text)).setError("The field cannot be empty!");
            view.findViewById(R.id.text).requestFocus();
        }

        return new Program(title, text, desc, tags, AddExerciseAdapter.edits);
    }

    private List<String> getTags(View tagsView) {
        String tags_src = ((EditText)tagsView).getText().toString();
        if (tags_src.isEmpty()){
            ((EditText) tagsView).setError("Field cannot be empty!");
            tagsView.requestFocus();
        }
        return Arrays.asList(tags_src.split(","));
    }
}

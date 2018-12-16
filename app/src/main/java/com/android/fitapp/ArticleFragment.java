package com.android.fitapp;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.fitapp.adapter.ExercisesApater;
import com.android.fitapp.entity.ArticleRow;
import com.android.fitapp.entity.Exercise;
import com.android.fitapp.entity.Program;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class ArticleFragment extends Fragment {
    String url = "https://fit-app-by-a1lexen.herokuapp.com";
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

        ArticleRow row = (ArticleRow) getArguments().getSerializable("program");

        Program program = restTemplate.getForObject(url+"/programs/" + row.getId(), Program.class);

        Toast toast = Toast.makeText(this.getContext(),"", Toast.LENGTH_SHORT);
        if (program.getExercises().size() > 0) {
            view = inflater.inflate(R.layout.program_fragment, container, false);
            TextView title = view.findViewById(R.id.program_title),
                    text = view.findViewById(R.id.program_text);
            TableRow tags = view.findViewById(R.id.program_tags);
            ListView listView = view.findViewById(R.id.exercises);

            title.setText(program.getTitle());
            text.setText(program.getText());
            System.out.println(program.getExercises().size());
            for (String str : program.getTags()) {
                View teg = inflater.inflate(R.layout.tag_fragment, tags, false);
                TextView tag_title = teg.findViewById(R.id.tag);
                tag_title.setText(str);
                tags.addView(teg);
            }
            List<Exercise> exercises = new ArrayList<>();
            exercises.addAll(program.getExercises());

            listView.setAdapter(new ExercisesApater(getContext(), exercises));
            toast.setText("It's a program");
        } else {
            view = inflater.inflate(R.layout.article_fragment, container, false);
            TextView title = view.findViewById(R.id.article_title),
                    text = view.findViewById(R.id.article_text);
            TableRow tags = view.findViewById(R.id.article_tags);

            title.setText(program.getTitle());
            text.setText(program.getText());

            for (String str : program.getTags()) {
                View teg = inflater.inflate(R.layout.tag_fragment, tags, false);
                TextView tag_title = teg.findViewById(R.id.tag);
                tag_title.setText(str);
                tags.addView(teg);
            }
            toast.setText("It's an article");
        }

        toast.show();

        return view;
    }
}

package com.android.fitapp.programs;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.fitapp.R;
import com.android.fitapp.adapter.ProgramsAdapter;
import com.android.fitapp.entity.ArticleRow;
import com.android.fitapp.entity.Program;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class ProgramsFragment extends Fragment {
    ListView listView;
    List<ArticleRow> programs;
    String url = "https://fit-app-by-a1lexen.herokuapp.com";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

        Program[] response = restTemplate.getForObject(url+"/programs", Program[].class);

        View view = inflater.inflate(R.layout.fragment_programs, container, false);
        listView = view.findViewById(R.id.programs_list);
        programs = new ArrayList();

        for (Program pr : response) {
            programs.add(new ArticleRow(pr.getId(), pr.getTitle(),
                    pr.getDesc(), pr.getTags()));
        }

        listView.setAdapter(new ProgramsAdapter(this.getContext(), programs));

        return view;
    }
}

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

import com.example.oleg.slidemenu.adapter.ProgramsAdapter;
import com.example.oleg.slidemenu.entity.ProgramRow;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

public class ProgrammsFragment extends Fragment {
    ListView listView;
    List<ProgramRow> programs;
    String url = "https://fit-app-by-a1lexen.herokuapp.com";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

        ProgramRow[] response = restTemplate.getForObject(url+"/programs", ProgramRow[].class);

        View view = inflater.inflate(R.layout.fragment_programs, container, false);
        listView = view.findViewById(R.id.programs_list);
        programs = new ArrayList();

        for (ProgramRow pr : response) {
            programs.add(new ProgramRow(pr.getId(), pr.getTitle(),
                    pr.getDesc(), pr.getTags()));
        }


        listView.setAdapter(new ProgramsAdapter(this.getContext(), programs));

        return view;
    }
}

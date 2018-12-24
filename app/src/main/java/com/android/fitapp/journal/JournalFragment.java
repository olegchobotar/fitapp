package com.android.fitapp.journal;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.fitapp.Main;
import com.android.fitapp.R;
import com.android.fitapp.adapter.JournalAdapter;
import com.android.fitapp.entity.Record;
import com.google.firebase.auth.FirebaseAuth;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class JournalFragment extends Fragment {
    List<Record> records;
    View view;
    RecyclerView recyclerView;
    String url = "https://fit-app-by-a1lexen.herokuapp.com/records";
    Button add_record_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
        records = new ArrayList();
        final String uid;
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Record[] trecord = restTemplate.getForObject(url + "/"+uid , Record[].class);
            for (int i = 0; i < trecord.length; i++) {
                records.add(trecord[i]);
            }
        }

        view = inflater.inflate(R.layout.fragment_journal, container, false);

        add_record_btn = view.findViewById(R.id.new_record_btn);
        add_record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Main) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddRecordFragment()).commit();
            }
        });

        recyclerView = view.findViewById(R.id.records_list);
        recyclerView.setAdapter(new JournalAdapter(records, getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        return view;
    }
}

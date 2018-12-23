package com.android.fitapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.fitapp.adapter.JournalAdapter;
import com.android.fitapp.entity.Record;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class JournalFragment extends Fragment {
    ArrayList<Record> records;
    View view;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        records = new ArrayList();
        records.add(new Record(new Date(), 40,40,75,103,0,180,73));
        records.add(new Record(new Date(), 40,40,75,103,0,175,63));
        records.add(new Record(new Date(), 40,40,75,103,0,175,68));
        view = inflater.inflate(R.layout.fragment_journal, container, false);

        recyclerView = view.findViewById(R.id.records_list);
        recyclerView.setAdapter(new JournalAdapter(records, getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        return view;
    }
}

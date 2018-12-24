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
import android.widget.Button;

import com.android.fitapp.adapter.AddExerciseAdapter;
import com.android.fitapp.entity.Exercise;

import java.util.ArrayList;

public class SecondTabFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    ArrayList<Exercise> list;
    AddExerciseAdapter adapter;
    Button addBtn;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        list = new ArrayList<>();
        view = inflater.inflate(R.layout.tab_second_fragment, container, false);
        recyclerView = view.findViewById(R.id.rv);
        addBtn = view.findViewById(R.id.add_btn);

        list.add(new Exercise());

        adapter = new AddExerciseAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AddExerciseAdapter) recyclerView.getAdapter()).addNewRow();
            }
        });

        return view;
    }
}

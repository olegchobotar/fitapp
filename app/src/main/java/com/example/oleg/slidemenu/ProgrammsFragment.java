package com.example.oleg.slidemenu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.oleg.slidemenu.adapter.ProgramsAdapter;
import com.example.oleg.slidemenu.entity.ProgramRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProgrammsFragment extends Fragment {
    ListView listView;
    List<ProgramRow> programs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_programms, container, false);
        listView = view.findViewById(R.id.programs_list);
        programs = new ArrayList<ProgramRow>();
        programs.add(new ProgramRow("The best exercises for full body.",
                "It'll help u to prepare your body for much harder work. " +
                        "U must to be already strong enough.", Arrays.asList("Legs", "Chest")));
        programs.add(new ProgramRow("The best exercises for full body.",
                "It'll help u to prepare your body for much harder work. " +
                        "U must to be already strong enough.", Arrays.asList("Legs", "Chest")));

        programs.add(new ProgramRow("The best exercises for full body.",
                "It'll help u to prepare your body for much harder work. " +
                        "U must to be already strong enough.", Arrays.asList("Legs", "Chest")));

        programs.add(new ProgramRow("The best exercises for full body.",
                "It'll help u to prepare your body for much harder work. " +
                        "U must to be already strong enough.", Arrays.asList("Legs", "Chest")));

        programs.add(new ProgramRow("The best exercises for full body.",
                "It'll help u to prepare your body for much harder work. " +
                        "U must to be already strong enough.", Arrays.asList("Legs", "Chest")));

        listView.setAdapter(new ProgramsAdapter(this.getContext(), programs));

        return view;
    }
}

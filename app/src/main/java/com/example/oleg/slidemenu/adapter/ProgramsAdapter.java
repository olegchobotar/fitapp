package com.example.oleg.slidemenu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.oleg.slidemenu.R;
import com.example.oleg.slidemenu.entity.ProgramRow;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ProgramsAdapter extends ArrayAdapter<ProgramRow> {

    private Context context;
    private List<ProgramRow> programs;

    public ProgramsAdapter(Context context, List<ProgramRow> programs) {
        super(context, R.layout.program_row_layout, programs);
        this.context = context;
        this.programs = programs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.program_row_layout, parent, false);
        if (position % 2 == 0) {
            view.setBackgroundResource(R.color.grayLight);
        } else {
            view.setBackgroundResource(R.color.coloWhite);
        }
        TextView title = view.findViewById(R.id.title),
                desc = view.findViewById(R.id.desc),
                tegs = view.findViewById(R.id.tegs);
        StringBuilder tegs_string = new StringBuilder();
        for(int i = 0; i < programs.get(position).getTegs().size(); i++){
            if (i != programs.get(position).getTegs().size()-1) {
                tegs_string.append(programs.get(position).getTegs().get(i) +  ", ");
            } else {
                tegs_string.append(programs.get(position).getTegs().get(i));
            }
        }

        title.setText(programs.get(position).getTitle());
        desc.setText(programs.get(position).getDesc());
        tegs.setText(tegs_string.toString());

        return view;
    }
}

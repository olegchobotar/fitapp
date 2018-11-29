package com.example.oleg.slidemenu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.oleg.slidemenu.R;
import com.example.oleg.slidemenu.entity.ProgramRow;

import java.util.List;

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
            view.setBackgroundResource(R.color.colorWhite);
        }
        TextView title = view.findViewById(R.id.title),
                desc = view.findViewById(R.id.desc);
        TableRow tags = view.findViewById(R.id.tags);

        title.setText(programs.get(position).getTitle());
        desc.setText(programs.get(position).getDesc());

        for (String str : programs.get(position).getTags()) {
            View teg = inflater.inflate(R.layout.tag_fragment, tags, false);
            TextView tag_title = teg.findViewById(R.id.tag);
            tag_title.setText(str);
            tags.addView(teg);
        }


        System.out.println();

        return view;
    }
}

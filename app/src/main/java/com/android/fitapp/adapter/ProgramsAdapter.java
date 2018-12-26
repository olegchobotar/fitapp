package com.android.fitapp.adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.fitapp.Main;
import com.android.fitapp.ArticleFragment;
import com.android.fitapp.R;
import com.android.fitapp.entity.ArticleRow;
import com.android.fitapp.programs.CommentsFragment;

import java.util.List;

public class ProgramsAdapter extends ArrayAdapter<ArticleRow> {

    private Context context;
    private List<ArticleRow> programs;

    private ImageView comments;

    public ProgramsAdapter(Context context, List<ArticleRow> programs) {
        super(context, R.layout.program_row_layout, programs);
        this.context = context;
        this.programs = programs;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.program_row_layout, parent, false);

        if (position % 2 == 0) {
            view.setBackgroundColor(Main.themeAttributeToColor(R.attr.programFirstRow, context, R.color.white));
        } else {
            view.setBackgroundColor(Main.themeAttributeToColor(R.attr.programSecondRow, context, R.color.white));
        }
        TextView title = view.findViewById(R.id.title),
                desc = view.findViewById(R.id.desc);
        TableRow tags = view.findViewById(R.id.tags);
        comments = view.findViewById(R.id.program_comment);

        title.setText(programs.get(position).getTitle());
        desc.setText(programs.get(position).getDesc());

        for (String str : programs.get(position).getTags()) {
            View teg = inflater.inflate(R.layout.tag_fragment, tags, false);
            TextView tag_title = teg.findViewById(R.id.tag);
            tag_title.setText(str);
            tags.addView(teg);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("program", programs.get(position));

                ArticleFragment fragment = new ArticleFragment();
                fragment.setArguments(bundle);

                ((Main) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });
        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("program", programs.get(position));

                CommentsFragment fragment = new CommentsFragment();
                fragment.setArguments(bundle);

                ((Main) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });
        return view;
    }
}

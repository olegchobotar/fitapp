package com.android.fitapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.fitapp.R;
import com.android.fitapp.entity.Record;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.ViewHolder> {
    List<Record> records;
    Context context;

    public JournalAdapter(List<Record> records, Context context) {
        this.records = records;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.record_row, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Record record = records.get(i);
        if (record != null) { if (i % 2 == 0) viewHolder.getView().setBackgroundResource(R.color.grayLight);
            else viewHolder.getView().setBackgroundResource(R.color.colorWhite);

            double bmi = record.getWeight() / Math.pow(record.getHeight()/100.0, 2);
            viewHolder.getBMI().setText(String.valueOf(String.format("%.2f", bmi)));
            viewHolder.getLeftArm().setText(String.valueOf(record.getLeftArm()));
            viewHolder.getRightArm().setText(String.valueOf(record.getRightArm()));
            viewHolder.getWaist().setText(String.valueOf(record.getWaist()));
            viewHolder.getChest().setText(String.valueOf(record.getChest()));
            viewHolder.getHeight().setText(String.valueOf(record.getHeight()));
            viewHolder.getWeight().setText(String.valueOf(record.getWeight()));
            viewHolder.getDate().setText(record.getDate());

        }
    }


    @Override
    public int getItemCount() {
        return records.size();
    }

    @Getter
    @Setter
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView leftArm;
        TextView rightArm;
        TextView waist;
        TextView chest;
        TextView BMI;
        TextView height;
        TextView weight;
        TextView date;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            leftArm = itemView.findViewById(R.id.left_arm);
            rightArm = itemView.findViewById(R.id.right_arm);
            waist = itemView.findViewById(R.id.waist);
            chest = itemView.findViewById(R.id.chest);
            BMI = itemView.findViewById(R.id.bmi);
            height = itemView.findViewById(R.id.height);
            weight = itemView.findViewById(R.id.weight);
            date = itemView.findViewById(R.id.record_date);
            view = itemView;
        }
    }
}

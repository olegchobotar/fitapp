package com.android.fitapp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.fitapp.entity.Record;
import com.google.firebase.auth.FirebaseAuth;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProgressFragment extends Fragment {
    View view;
    List<Record> records;
    String url = "https://fit-app-by-a1lexen.herokuapp.com/records";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_progress, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

        GraphView graph = view.findViewById(R.id.graph);

        graph.getLegendRenderer().setVisible(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-10);
        graph.getViewport().setMaxY(190);

        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);

        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space
        graph.getGridLabelRenderer().setNumVerticalLabels(10);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Record[] trecord = restTemplate.getForObject(url + "/"+uid , Record[].class);
            records = new ArrayList();
            for (int i = 0; i < trecord.length; i++) {
                records.add(trecord[i]);
            }
            try {
                graph.getViewport().setXAxisBoundsManual(true);
                graph.getViewport().setMinX(new SimpleDateFormat("dd/MM/yyyy").parse(records.get(0).getDate()).getTime());
                graph.getViewport().setMaxX(new SimpleDateFormat("dd/MM/yyyy").parse(records.get(records.size() - 1).getDate()).getTime());

                LineGraphSeries<DataPoint> series_chest = new LineGraphSeries<>();
                LineGraphSeries<DataPoint> series_waist = new LineGraphSeries<>();
                LineGraphSeries<DataPoint> series_height = new LineGraphSeries<>();
                LineGraphSeries<DataPoint> series_weight = new LineGraphSeries<>();

                series_chest.setColor(Color.MAGENTA);
                series_chest.setDrawDataPoints(true);
                series_chest.setTitle("Chest");

                series_waist.setColor(Color.YELLOW);
                series_waist.setDrawDataPoints(true);
                series_waist.setTitle("Waist");

                series_height.setColor(Color.GREEN);
                series_height.setTitle("Height");
                series_height.setDrawDataPoints(true);

                series_weight.setColor(Color.WHITE);
                series_weight.setTitle("Weight");
                series_weight.setDrawDataPoints(true);

                for (Record r : records) {
                    series_chest.appendData(new DataPoint(new SimpleDateFormat("dd/MM/yyyy").parse(r.getDate()), r.getChest()), true, 100);
                    series_waist.appendData(new DataPoint(new SimpleDateFormat("dd/MM/yyyy").parse(r.getDate()), r.getWaist()), true, 100);
                    series_height.appendData(new DataPoint(new SimpleDateFormat("dd/MM/yyyy").parse(r.getDate()), r.getHeight()), true, 100);
                    series_weight.appendData(new DataPoint(new SimpleDateFormat("dd/MM/yyyy").parse(r.getDate()), r.getWeight()), true, 100);
                }
                graph.addSeries(series_chest);
                graph.addSeries(series_height);
                graph.addSeries(series_waist);
                graph.addSeries(series_weight);
            } catch (ParseException  e) {
                Toast.makeText(getContext(), "Invalid journal date.", Toast.LENGTH_SHORT).show();
            }
        }

        return view;
    }
}

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
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ProgressFragment extends Fragment {
    View view;
    List<Record> records;
    String url = "https://fit-app-by-a1lexen.herokuapp.com/records";
    GraphView graph;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_progress, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

        graph = view.findViewById(R.id.graph);

        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
/*        graph.getGridLabelRenderer().setNumHorizontalLabels(4); // only 4 because of the space
        graph.getGridLabelRenderer().setNumVerticalLabels(5);*/

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Record[] trecord = restTemplate.getForObject(url + "/"+uid , Record[].class);
            records = new ArrayList();
            for (int i = 0; i < trecord.length; i++) {
                records.add(trecord[i]);
            }
            try {
                graph.getViewport().setXAxisBoundsManual(true);
                graph.getViewport().setMinX(new SimpleDateFormat("dd/MM/yyyy").parse(records.get(0).getDate()).getTime() - 84000);
                graph.getViewport().setMaxX(new SimpleDateFormat("dd/MM/yyyy").parse(records.get(records.size() - 1).getDate()).getTime() + 84000);

                LineGraphSeries<DataPoint> series_chest = new LineGraphSeries<>();
                LineGraphSeries<DataPoint> series_waist = new LineGraphSeries<>();
                LineGraphSeries<DataPoint> series_height = new LineGraphSeries<>();
                LineGraphSeries<DataPoint> series_weight = new LineGraphSeries<>();
                LineGraphSeries<DataPoint> series_larm = new LineGraphSeries<>();
                LineGraphSeries<DataPoint> series_rarm = new LineGraphSeries<>();

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

                series_larm.setColor(Color.CYAN);
                series_larm.setTitle("Left arm");
                series_larm.setDrawDataPoints(true);

                series_rarm.setColor(Color.LTGRAY);
                series_rarm.setTitle("Right arm");
                series_rarm.setDrawDataPoints(true);

                for (Record r : records) {
                    series_chest.appendData(new DataPoint(new SimpleDateFormat("dd/MM/yyyy").parse(r.getDate()), r.getChest()), true, 100);
                    series_waist.appendData(new DataPoint(new SimpleDateFormat("dd/MM/yyyy").parse(r.getDate()), r.getWaist()), true, 100);
                    series_height.appendData(new DataPoint(new SimpleDateFormat("dd/MM/yyyy").parse(r.getDate()), r.getHeight()), true, 100);
                    series_weight.appendData(new DataPoint(new SimpleDateFormat("dd/MM/yyyy").parse(r.getDate()), r.getWeight()), true, 100);
                    series_larm.appendData(new DataPoint(new SimpleDateFormat("dd/MM/yyyy").parse(r.getDate()), r.getLeftArm()), true, 100);
                    series_rarm.appendData(new DataPoint(new SimpleDateFormat("dd/MM/yyyy").parse(r.getDate()), r.getRightArm()), true, 100);
                }

                series_chest.setOnDataPointTapListener(new OnDataPointTapListener() {
                    @Override
                    public void onTap(Series series, DataPointInterface dataPoint) {
                        Toast.makeText(getActivity(), "Chest size on " + new SimpleDateFormat("dd/MM/yyyy").format(new Date((long)dataPoint.getX())) + " is " + dataPoint.getY(), Toast.LENGTH_SHORT).show();
                    }
                });
                series_waist.setOnDataPointTapListener(new OnDataPointTapListener() {
                    @Override
                    public void onTap(Series series, DataPointInterface dataPoint) {
                        Toast.makeText(getActivity(), "Waist size on " + new SimpleDateFormat("dd/MM/yyyy").format(new Date((long)dataPoint.getX())) + " is " + dataPoint.getY(), Toast.LENGTH_SHORT).show();
                    }
                });
                series_height.setOnDataPointTapListener(new OnDataPointTapListener() {
                    @Override
                    public void onTap(Series series, DataPointInterface dataPoint) {
                        Toast.makeText(getActivity(), "Height on " + new SimpleDateFormat("dd/MM/yyyy").format(new Date((long)dataPoint.getX())) + " is " + dataPoint.getY(), Toast.LENGTH_SHORT).show();
                    }
                });
                series_weight.setOnDataPointTapListener(new OnDataPointTapListener() {
                    @Override
                    public void onTap(Series series, DataPointInterface dataPoint) {
                        Toast.makeText(getActivity(), "Weight on " + new SimpleDateFormat("dd/MM/yyyy").format(new Date((long)dataPoint.getX())) + " is " + dataPoint.getY(), Toast.LENGTH_SHORT).show();
                    }
                });
                series_larm.setOnDataPointTapListener(new OnDataPointTapListener() {
                    @Override
                    public void onTap(Series series, DataPointInterface dataPoint) {
                        Toast.makeText(getActivity(), "Left arm size on " + new SimpleDateFormat("dd/MM/yyyy").format(new Date((long)dataPoint.getX())) + " is " + dataPoint.getY(), Toast.LENGTH_SHORT).show();
                    }
                });
                series_rarm.setOnDataPointTapListener(new OnDataPointTapListener() {
                    @Override
                    public void onTap(Series series, DataPointInterface dataPoint) {
                        Toast.makeText(getActivity(), "Right amr size on " + new SimpleDateFormat("dd/MM/yyyy").format(new Date((long)dataPoint.getX())) + " is " + dataPoint.getY(), Toast.LENGTH_SHORT).show();
                    }
                });

                graph.addSeries(series_chest);
                graph.addSeries(series_height);
                graph.addSeries(series_waist);
                graph.addSeries(series_weight);
                graph.addSeries(series_larm);
                graph.addSeries(series_rarm);
            } catch (ParseException  e) {
                Toast.makeText(getContext(), "Invalid journal date.", Toast.LENGTH_SHORT).show();
            }
        }

        graph.getLegendRenderer().setVisible(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(190);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);

        return view;
    }
}

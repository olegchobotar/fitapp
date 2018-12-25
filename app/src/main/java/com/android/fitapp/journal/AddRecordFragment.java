package com.android.fitapp.journal;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.fitapp.R;
import com.android.fitapp.entity.Record;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class AddRecordFragment extends Fragment {
    View view;
    TextView chest;
    TextView waist;
    TextView l_arm;
    TextView r_arm;
    TextView height;
    TextView weight;
    Button send_btn;
    String url = "https://fit-app-by-a1lexen.herokuapp.com/records";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

        view = inflater.inflate(R.layout.add_new_info_layout, container, false);

        chest = view.findViewById(R.id.chest_info);
        waist = view.findViewById(R.id.waist_info);
        l_arm = view.findViewById(R.id.left_arm_info);
        r_arm = view.findViewById(R.id.right_arm_info);
        height = view.findViewById(R.id.height_info);
        weight = view.findViewById(R.id.weight_info);
        final List<TextView> fields = Arrays.asList(chest, waist, l_arm, r_arm, height, weight);
        
        send_btn = view.findViewById(R.id.send_record_btn);

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = true;

                for (TextView input : fields) {
                    if (input.getText().toString().length() < 1) {
                        Toast.makeText(getContext(), "Please, fill the fields.", Toast.LENGTH_SHORT).show();
                        System.out.println("Please fill the fields!");
                        valid = false;
                        break;
                    }
                }
                if (valid) {
                    final String uid;
                    if (FirebaseAuth.getInstance().getCurrentUser() != null){
                        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        Record record = new Record(new Long(0),
                                DateFormat.format("dd/MM/yyyy", new Date()).toString(),
                                Double.parseDouble(l_arm.getText().toString()),
                                Double.parseDouble(r_arm.getText().toString()),
                                Double.parseDouble(waist.getText().toString()),
                                Double.parseDouble(chest.getText().toString()),
                                0.0,
                                Double.parseDouble(height.getText().toString()),
                                Double.parseDouble(weight.getText().toString()),
                                uid);
                        System.out.println(record.getDate());
                        ResponseEntity response = restTemplate.postForEntity(url, record, ResponseEntity.class);
                        Toast.makeText(getContext(), "The record added successfully!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        return view;
    }
}

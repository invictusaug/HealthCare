package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorDetailsActivity extends AppCompatActivity {

    private final String[][] doctor_details1 =
            {
                    {"Doctors Name : Ajit Saste", "Hospital Address : Pimpri", "Experience : 5 years", "Mobile Number : 9898989898", "600"},
                    {"Doctors Name : Prasad Pawar", "Hospital Address : Nigdi", "Experience : 15 years", "Mobile Number : 9898989897", "300"},
                    {"Doctors Name : Swapnil Kale", "Hospital Address : Pune", "Experience : 8 years", "Mobile Number : 9898989896", "500"},
                    {"Doctors Name : Deepak Deshmuk", "Hospital Address : Chinchwad", "Experience : 6 years", "Mobile Number : 9898989895", "800"},
                    {"Doctors Name : Ashok Panda", "Hospital Address : Katraj", "Experience : 7 years", "Mobile Number : 9898989894", "700"}
            };
    private final String[][] doctor_details2 =
            {
                    {"Doctors Name : Ajit Saste", "Hospital Address : Pimpri", "Experience : 13 years", "Mobile Number : 9898989898", "600"},
                    {"Doctors Name : Ajit Saste", "Hospital Address : Pimpri", "Experience : 7 years", "Mobile Number : 9898989898", "900"},
                    {"Doctors Name : Ajit Saste", "Hospital Address : Pimpri", "Experience : 9 years", "Mobile Number : 9898989898", "300"},
                    {"Doctors Name : Ajit Saste", "Hospital Address : Pimpri", "Experience : 10 years", "Mobile Number : 9898989898", "500"},
                    {"Doctors Name : Ajit Saste", "Hospital Address : Pimpri", "Experience : 5 years", "Mobile Number : 9898989898", "800"}
            };
    private final String[][] doctor_details3 =
            {
                    {"Doctors Name : Ajit Saste", "Hospital Address : Pimpri", "Experience : 5 years", "Mobile Number : 9898989898", "600"},
                    {"Doctors Name : Ajit Saste", "Hospital Address : Pimpri", "Experience : 14 years", "Mobile Number : 9898989898", "900"},
                    {"Doctors Name : Ajit Saste", "Hospital Address : Pimpri", "Experience : 9 years", "Mobile Number : 9898989898", "300"},
                    {"Doctors Name : Ajit Saste", "Hospital Address : Pimpri", "Experience : 6 years", "Mobile Number : 9898989898", "500"},
                    {"Doctors Name : Ajit Saste", "Hospital Address : Pimpri", "Experience : 7 years", "Mobile Number : 9898989898", "800"}
            };
    private final String[][] doctor_details4 =
            {
                    {"Doctors Name : Ajit Saste", "Hospital Address : Pimpri", "Experience : 5 years", "Mobile Number : 9898989898", "600"},
                    {"Doctors Name : Ajit Saste", "Hospital Address : Pimpri", "Experience : 6 years", "Mobile Number : 9898989898", "900"},
                    {"Doctors Name : Ajit Saste", "Hospital Address : Pimpri", "Experience : 7 years", "Mobile Number : 9898989898", "300"},
                    {"Doctors Name : Ajit Saste", "Hospital Address : Pimpri", "Experience : 8 years", "Mobile Number : 9898989898", "500"},
                    {"Doctors Name : Ajit Saste", "Hospital Address : Pimpri", "Experience : 9 years", "Mobile Number : 9898989898", "800"}
            };
    private final String[][] doctor_details5 =
            {
                    {"Doctors Name : Ajit Saste", "Hospital Address : Pimpri", "Experience : 1 years", "Mobile Number : 9898989898", "600"},
                    {"Doctors Name : Ajit Saste", "Hospital Address : Pimpri", "Experience : 2 years", "Mobile Number : 9898989898", "900"},
                    {"Doctors Name : Ajit Saste", "Hospital Address : Pimpri", "Experience : 3 years", "Mobile Number : 9898989898", "300"},
                    {"Doctors Name : Ajit Saste", "Hospital Address : Pimpri", "Experience : 4 years", "Mobile Number : 9898989898", "500"},
                    {"Doctors Name : Ajit Saste", "Hospital Address : Pimpri", "Experience : 5 years", "Mobile Number : 9898989898", "800"}
            };
    TextView tv;
    Button btn;
    String[][] doctor_details = {};
    ArrayList list;
    SimpleAdapter sa;
    HashMap<String,String> item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tv = findViewById(R.id.textViewBMCartTitle);
        btn = findViewById(R.id.buttonBMDAddToCart);
        Intent it = getIntent();
        String title = it.getStringExtra("title");
        tv.setText(title);
        assert title != null;
        if(title.compareTo("Family Physician")==0) {
            doctor_details = doctor_details1;
        } else if(title.compareTo("Dietician")==0) {
            doctor_details = doctor_details2;
        } else if(title.compareTo("Dentist")==0) {
            doctor_details = doctor_details3;
        } else if(title.compareTo("Surgeon")==0) {
            doctor_details = doctor_details4;
        } else {
            doctor_details = doctor_details5;
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorDetailsActivity.this, FindDoctorActivity.class));
            }
        });
        list = new ArrayList();
        for (String[] doctorDetail : doctor_details) {
            item = new HashMap<String, String>();
            item.put("line1", doctorDetail[0]);
            item.put("line2", doctorDetail[1]);
            item.put("line3", doctorDetail[2]);
            item.put("line4", doctorDetail[3]);
            item.put("line5", "Cons Fees:" + doctorDetail[4] + "/-");
            list.add(item);
        }
        sa = new SimpleAdapter(this, list, R.layout.multi_lines, new String[]{"line1","line2","line3","line4","line5"}, new int[]{R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e,});
        ListView lst = findViewById(R.id.listViewBMCart);
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(DoctorDetailsActivity.this, BookAppointmentActivity.class);
                it.putExtra("text1", title);
                it.putExtra("text2", doctor_details[position][0]);
                it.putExtra("text3", doctor_details[position][1]);
                it.putExtra("text4", doctor_details[position][3]);
                it.putExtra("text5", doctor_details[position][4]);
                startActivity(it);
            }
        });
    }
}
package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class LabTestActivity extends AppCompatActivity {

    private final String[][] packages =
            {
                    {"Package 1 : Full Body Checkup", "", "", "", "999"},
                    {"Package 2 : Blood Glucose Fasting", "", "", "","899"},
                    {"Package 3 : COVID-19 Antibody - IgG", "", "", "","799"},
                    {"Package 4 : Thyroid Check", "", "", "","699"},
                    {"Package 5 : Immunity Check", "", "", "","599"}
            };
    private final String[] package_details = {
            "Blood Glucose Fasting\n" + " Complete Hemogram\n" + "HbA1c\n" + " Iron Studies\n" + "Kidney Function Test\n" +
                    "LDH Lactate Dehydrogenase, Serum\n" + "Lipid Profile\n" + "Liver Function Test", "COVID-19 Antibody - IgG",
            "Thyroid Profile-Total (T3, T4 & TSH Ultra-sensitive)", "Complete Hemogram\n" +
            "CRP (C Reactive Protein) Quantitative, Serum\n" +
            " Iron Studies\n" + "Kidney Function Test\n" + "Vitamin D Total-25 Hydroxy\n" + "Liver Function Test\n" + "Lipid Profile"
    };
    HashMap<String, String> item;
    ArrayList list;
    SimpleAdapter sa;
    Button btnGoToCart, btnBack;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lab_test);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnGoToCart = findViewById(R.id.buttonBMDAddToCart);
        btnBack = findViewById(R.id.buttonBMDBack);
        listView = findViewById(R.id.listViewBMCart);

        btnBack.setOnClickListener(v -> startActivity(new Intent(LabTestActivity.this, HomeActivity.class)));
        list = new ArrayList();
        for (String[] aPackage : packages) {
            item = new HashMap<>();
            item.put("line1", aPackage[0]);
            item.put("line2", aPackage[1]);
            item.put("line3", aPackage[2]);
            item.put("line4", aPackage[3]);
            item.put("line5", "Total Cost:" + aPackage[4] + "/-");
            list.add(item);
        }
        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        listView.setAdapter(sa);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent it = new Intent(LabTestActivity.this, LabTestDetailsActivity.class);
            it.putExtra("text1",packages[position][0]);
            it.putExtra("text2", package_details[position]);
            it.putExtra("text3", packages[position][4]);
            startActivity(it);
        });
        btnGoToCart.setOnClickListener(v -> startActivity(new Intent(LabTestActivity.this,CartLabActivity.class)));
    }
}
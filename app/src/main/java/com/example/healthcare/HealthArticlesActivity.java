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
import java.util.stream.IntStream;

public class HealthArticlesActivity extends AppCompatActivity {

    private final String[][] health_articles =
            {
                    {"Walking Daily", "", "", "", "Click for more Details"},
                    {"Home Care of COVID-19", "", "", "", "Click for more Details"},
                    {"Stop Smoking", "", "", "", "Click for more Details"},
                    {"Menstrual Cramps", "", "", "", "Click for more Details"},
                    {"Healthy Gut", "", "", "", "Click for more Details"},
            };
    private final int[] images = {
            R.drawable.health1,
            R.drawable.health2,
            R.drawable.health3,
            R.drawable.health4,
            R.drawable.health5,
    };
    HashMap<String, String> stringStringHashMap;
    ArrayList arrayList;
    SimpleAdapter simpleAdapter;
    Button btnBack;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_health_articles);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.listViewHA);
        btnBack = findViewById(R.id.buttonHABack);
        btnBack.setOnClickListener(v -> startActivity(new Intent(HealthArticlesActivity.this, HomeActivity.class)));
        arrayList = new ArrayList<>();
        IntStream.range(0, health_articles.length).forEachOrdered(i -> {
            stringStringHashMap = new HashMap<>();
            stringStringHashMap.put("line1", health_articles[i][0]);
            stringStringHashMap.put("line2", health_articles[i][1]);
            stringStringHashMap.put("line3", health_articles[i][2]);
            stringStringHashMap.put("line4", health_articles[i][3]);
            stringStringHashMap.put("line5", health_articles[i][4]);
            arrayList.add(stringStringHashMap);
        });
        simpleAdapter = new SimpleAdapter(this, arrayList,
                R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent it = new Intent(HealthArticlesActivity.this, HealthArticlesDetailsActivity.class);
            it.putExtra("text1",health_articles[position][0]);
            it.putExtra("text2", images[position]);
            startActivity(it);
        });
    }
}
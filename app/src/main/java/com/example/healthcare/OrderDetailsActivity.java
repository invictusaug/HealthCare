package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class OrderDetailsActivity extends AppCompatActivity {

    private String[][] order_details = {};
    HashMap<String, String> stringStringHashMap;
    ArrayList arrayList;
    SimpleAdapter simpleAdapter;
    ListView listView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        button = findViewById(R.id.buttonBMDAddToCart);
        listView = findViewById(R.id.listViewBMCart);
        button.setOnClickListener(v -> startActivity(new Intent(OrderDetailsActivity.this, HomeActivity.class)));
        ArrayList dbData;
        try (Database database = new Database(getApplicationContext(), "healthcare", null, 1)) {
            SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
            String username = sharedPreferences.getString("username", "");
            dbData = database.getOrderData(username);
        }
        order_details = new String[dbData.size()][];
        IntStream.range(0, order_details.length).forEachOrdered(i -> {
            order_details[i] = new String[5];
            String arrData = dbData.get(i).toString();
            String[] strData = arrData.split(Pattern.quote("$"));
            order_details[i][0] = strData[0];
            order_details[i][1] = strData[1];
            if (strData[7].compareTo("medicine") == 0) {
                order_details[i][3] = "Del:" + strData[4];
            } else {
                order_details[i][3] = "Del:" + strData[4] + " " + strData[5];
            }
            order_details[i][2] = "Rs." + strData[6];
            order_details[i][4] = strData[7];
        });
        arrayList = new ArrayList();
        IntStream.range(0, order_details.length).forEachOrdered(i -> {
            stringStringHashMap = new HashMap<>();
            stringStringHashMap.put("line1", order_details[i][0]);
            stringStringHashMap.put("line2", order_details[i][1]);
            stringStringHashMap.put("line3", order_details[i][2]);
            stringStringHashMap.put("line4", order_details[i][3]);
            stringStringHashMap.put("line5", order_details[i][4]);
            arrayList.add(stringStringHashMap);
        });
        simpleAdapter = new SimpleAdapter(this, arrayList, R.layout.multi_lines,new String[] {"line1","line2","line3","line4","line5"},
                new int[] {R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e});
        listView.setAdapter(simpleAdapter);
    }
}
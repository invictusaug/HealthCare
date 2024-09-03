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

public class BuyMedicineActivity extends AppCompatActivity {

    private final String[][] packages =
            {
                    {"Uprise-D3 1000IU Capsule", "", "", "", "50"},
                    {"HealthVit Chromium Picolinate 200mcg Capsule", "", "", "", "305"},
                    {"Vitamin B Complex Capsules", "", "", "", "", "448"},
                    {"Inlife Vitamin E Wheat Germ Oil Capsule", "", "", "", "539"},
                    {"Dolo 650 Tablet", "", "", "", "30"},
                    {"Crocin 650 Advance Tablet", "", "", "", "50"},
                    {"Strpsils Medicated Lozenges for Sore Throat", "", "", "", "40"},
                    {"Tata 1mg Calcium + Vitamin D3", "", "", "", "30"},
                    {"Feronia -XT Tablet", "", "", "", "130"},
            };
    private final String[] package_details = {
            "Building and keeping the bones & teeth strong\n" +
                    "Reducing Fatigue/stress and muscular pains\n" +
                    "Boosting immunity and increasing resistance against infection",
            "Chromium is an essential trace mineral that plays an important role in helping insulin regulation",
            "Provides relief from vitamin B deficiencies\n" +
                    "Helps in formation of red blood cells\n" +
                    "Maintains healthy nervous system",
            "It promotes health as well as skin benefit\n" +
                    "It helps reduce skin blemish and pigmentation\n" +
                    "It acts as the safeguard the skin from the harsh UVA and UVB sun rays.",
            "Dolo 650 Tablet helps relieve pain and fever by blocking the release of certain chemical mess",
            "Helps relieve fever and bring down high temperature\n" +
                    "Suitable for people with heart condition or high blood pressure",
            "Relieves the symptoms of a bacterial throat infection and soothes the recovery process\n" +
                    "Provides a warm and comforting feeling during sore throat",
            "Reduces the risk of calcium deficiency, rickets and Osteoporosis\n" +
                    "Promotes mobility and flexibility of joints",
            "Helps to reduce the iron deficiency due to chronic blood loss or low intake of iron"
    };
    HashMap<String, String> stringStringHashMap;
    ArrayList arrayList;
    SimpleAdapter simpleAdapter;
    ListView listView;
    Button btnBack, btnGoToCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buy_medicine);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        listView = findViewById(R.id.listViewBMCart);
        btnBack = findViewById(R.id.buttonBMDBack);
        btnGoToCart = findViewById(R.id.buttonBMDAddToCart);
        btnGoToCart.setOnClickListener(v -> startActivity(new Intent(BuyMedicineActivity.this, CartBuyMedicineActivity.class)));
        btnBack.setOnClickListener(v -> startActivity(new Intent(BuyMedicineActivity.this, HomeActivity.class)));
        arrayList = new ArrayList();
        for (String[] aPackage : packages) {
            stringStringHashMap = new HashMap<>();
            stringStringHashMap.put("line1", aPackage[0]);
            stringStringHashMap.put("line2", aPackage[1]);
            stringStringHashMap.put("line3", aPackage[2]);
            stringStringHashMap.put("line4", aPackage[3]);
            stringStringHashMap.put("line5", "Total Cost:" + aPackage[4] + "/-");
            arrayList.add(stringStringHashMap);
        }
        simpleAdapter = new SimpleAdapter(this, arrayList,
                R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent it = new Intent(BuyMedicineActivity.this, BuyMedicineDetailsActivity.class);
            it.putExtra("text1",packages[position][0]);
            it.putExtra("text2", package_details[position]);
            it.putExtra("text3", packages[position][4]);
            startActivity(it);
        });
    }
}
package com.example.healthcare;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.stream.IntStream;

public class CartBuyMedicineActivity extends AppCompatActivity {

    HashMap<String,String> item;
    ArrayList list;
    SimpleAdapter sa;
    TextView tvTotalCost;
    ListView listView;
    private DatePickerDialog datePickerDialog;
    private Button dateButton, btnCheckout, btnBack;
    private String[][] packages = {};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart_buy_medicine);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dateButton = findViewById(R.id.buttonBMCartDatePicker);
        btnCheckout = findViewById(R.id.buttonBMCartCheckout);
        btnBack = findViewById(R.id.buttonBMCartBack);
        listView = findViewById(R.id.listViewBMCart);
        tvTotalCost = findViewById(R.id.textViewBMCartTotalPrice);

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        float totalAmount;
        ArrayList dbData;
        try (Database database = new Database(getApplicationContext(), "healthcare", null, 1)) {
            totalAmount = 0;
            dbData = database.getCartData(username, "medicine");
        }
        Toast.makeText(getApplicationContext(), ""+dbData, Toast.LENGTH_LONG).show();
        packages = new String[dbData.size()][];
        IntStream.range(0, packages.length).forEachOrdered(i -> packages[i] = new String[5]);
        int i = 0;
        while (i < dbData.size()) {
            String arrData = dbData.get(i).toString();
            String[] strData = arrData.split(java.util.regex.Pattern.quote("$"));
            packages[i][0] = strData[0];
            packages[i][4] = "Cost : " + strData[1] + "/-";
            totalAmount += Float.parseFloat(strData[1]);
            i++;
        }
        tvTotalCost = findViewById(R.id.textViewBMCartTotalPrice);
        tvTotalCost.setText(String.format("Total Cost : %s", totalAmount));

        list = new ArrayList();
        Arrays.stream(packages).forEachOrdered(aPackage -> {
            item = new HashMap<>();
            item.put("line1", aPackage[0]);
            item.put("line2", aPackage[1]);
            item.put("line3", aPackage[2]);
            item.put("line4", aPackage[3]);
            item.put("line5", aPackage[4]);
            list.add(item);
        });
        sa = new SimpleAdapter(this, list, R.layout.multi_lines, new String[] {"line1","line2","line3","line4","line5"},
                new int[] {R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        listView.setAdapter(sa);
        btnBack.setOnClickListener(v -> startActivity(new Intent(CartBuyMedicineActivity.this, BuyMedicineActivity.class)));
        btnCheckout.setOnClickListener(v -> {
            Intent intent = new Intent(CartBuyMedicineActivity.this, BuyMedicineBookActivity.class);
            intent.putExtra("price", tvTotalCost.getText());
            intent.putExtra("date", dateButton.getText());
            startActivity(intent);
        });
        initDatePicker();
        dateButton.setOnClickListener(v -> datePickerDialog.show());
    }
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            month += 1;
            dateButton.setText(String.format("%d/%d/%d", dayOfMonth, month, year));
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis()+86400000);
    }
}
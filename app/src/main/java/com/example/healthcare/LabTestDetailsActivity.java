package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LabTestDetailsActivity extends AppCompatActivity {

    TextView tvPackageName, tvTotalCost;
    EditText edDetails;
    Button btnAddToCart, btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lab_test_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvPackageName = findViewById(R.id.textViewBMDPackageName);
        tvTotalCost = findViewById(R.id.textViewBMDTotalCost);
        edDetails  =findViewById(R.id.listViewBMCart);
        btnAddToCart = findViewById(R.id.buttonBMDAddToCart);
        btnBack = findViewById(R.id.buttonBMDBack);

        edDetails.setKeyListener(null);

        Intent intent = getIntent();
        tvPackageName.setText(intent.getStringExtra("text1"));
        edDetails.setText(intent.getStringExtra("text2"));
        tvTotalCost.setText(String.format("Total Cost : %s/-", intent.getStringExtra("text3")));

        btnBack.setOnClickListener(v -> startActivity(new Intent(LabTestDetailsActivity.this, LabTestActivity.class)));
        btnAddToCart.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
            String username = sharedPreferences.getString("username","");
            String product = tvPackageName.getText().toString();
            float price = Float.parseFloat(intent.getStringExtra("text3"));
            try (Database database = new Database(getApplicationContext(), "healthcare", null, 1)) {
                if (database.checkCart(username, product)) {
                    Toast.makeText(getApplicationContext(), "Product Already Added", Toast.LENGTH_SHORT).show();
                } else {
                    database.addCart(username, product, price, "lab");
                    Toast.makeText(getApplicationContext(), "Record Inserted to Cart", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LabTestDetailsActivity.this, LabTestActivity.class));
                }
            }
        });
    }
}
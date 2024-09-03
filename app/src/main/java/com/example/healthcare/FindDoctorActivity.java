package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FindDoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_find_doctor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        CardView exit = findViewById(R.id.cardFDBack);
        exit.setOnClickListener(v -> startActivity(new Intent(FindDoctorActivity.this, HomeActivity.class)));
        CardView familyphysician = findViewById(R.id.cardFDFamilyPhysician);
        familyphysician.setOnClickListener(v -> {
            Intent it = new Intent(FindDoctorActivity.this, DoctorDetailsActivity.class);
            it.putExtra("title", "Family Physicians");
            startActivity(it);
        });
        CardView dietician = findViewById(R.id.cardFDDietecian);
        dietician.setOnClickListener(v -> {
            Intent it = new Intent(FindDoctorActivity.this, DoctorDetailsActivity.class);
            it.putExtra("title", "Dietician");
            startActivity(it);
        });
        CardView dentist = findViewById(R.id.cardFDDentist);
        dentist.setOnClickListener(v -> {
            Intent it = new Intent(FindDoctorActivity.this, DoctorDetailsActivity.class);
            it.putExtra("title", "Dentist");
            startActivity(it);
        });
        CardView surgeon = findViewById(R.id.cardFDSurgeon);
        surgeon.setOnClickListener(v -> {
            Intent it = new Intent(FindDoctorActivity.this, DoctorDetailsActivity.class);
            it.putExtra("title", "Surgeon");
            startActivity(it);
        });
        CardView cardiologists = findViewById(R.id.cardFDCardiologists);
        cardiologists.setOnClickListener(v -> {
            Intent it = new Intent(FindDoctorActivity.this, DoctorDetailsActivity.class);
            it.putExtra("title", "Cardiologists");
            startActivity(it);
        });
    }
}
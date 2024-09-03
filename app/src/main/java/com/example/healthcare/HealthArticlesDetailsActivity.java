package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HealthArticlesDetailsActivity extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_health_articles_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        button = findViewById(R.id.buttonHAD);
        textView = findViewById(R.id.textViewHADTitle);
        imageView = findViewById(R.id.imageViewHAD);
        Intent intent = getIntent();
        textView.setText(intent.getStringExtra("text1"));
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            int resId = bundle.getInt("text2");
            imageView.setImageResource(resId);
        }
        button.setOnClickListener(v -> startActivity(new Intent(HealthArticlesDetailsActivity.this,HealthArticlesActivity.class)));
    }
}
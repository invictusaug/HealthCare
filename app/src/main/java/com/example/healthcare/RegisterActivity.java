package com.example.healthcare;

import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {

    EditText edUsername, edEmail, edPassword, edConfirm;
    Button btn;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edUsername = findViewById(R.id.editTextLTBFullName);
        edPassword = findViewById(R.id.editTextLTBPinCode);
        edEmail = findViewById(R.id.editTextLTBAddress);
        edConfirm = findViewById(R.id.editTextLTBContactNumber);
        btn = findViewById(R.id.buttonBMCartCheckout);
        tv = findViewById(R.id.textViewExistingUser);

        tv.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));

        btn.setOnClickListener(v -> {
            String username = edUsername.getText().toString();
            String email = edEmail.getText().toString();
            String password = edPassword.getText().toString();
            String confirm = edConfirm.getText().toString();
            Database db = new Database(getApplicationContext(), "healthcare", null, 1);
            if(username.isEmpty() || email.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please fill all the details", Toast.LENGTH_SHORT).show();
            } else {
                if(password.compareTo(confirm)==0) {
                    if(isValid(password)) {
                        db.register(username, email, password);
                        Toast.makeText(getApplicationContext(), "Record Inserted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Password is invalid", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Password ans Cofirm password doesn't match", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public static boolean isValid(String passwordhere) {
        int f1 = 0, f2 = 0, f3 = 0;
        if(passwordhere.isEmpty()) {
            return false;
        } else {
            for(int p = 0; p < passwordhere.length(); p++) {
                if(Character.isLetter(passwordhere.charAt(p))) {
                    f1=1;
                }
            }
            for(int r = 0; r < passwordhere.length(); r++) {
                if(Character.isDigit(passwordhere.charAt(r))) {
                    f2 = 1;
                }
            }
            for(int s = 0; s < passwordhere.length(); s++) {
                char c = passwordhere.charAt(s);
                if(c >= 33 && c <= 46 || c == 64) {
                    f3 = 1;
                }
            }
            return f1 == 1 && f2 == 1 && f3 == 1;
        }
    }

}
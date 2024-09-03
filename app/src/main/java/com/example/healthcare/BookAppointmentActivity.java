package com.example.healthcare;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import java.util.Calendar;

public class BookAppointmentActivity extends AppCompatActivity {

    EditText ed1, ed2, ed3, ed4;
    TextView tv;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateButton;
    private Button timeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_appointment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tv = findViewById(R.id.textViewAppTitle);
        ed1 = findViewById(R.id.editTextAppFullName);
        ed2 = findViewById(R.id.editTextLTBAddress);
        ed3 = findViewById(R.id.editTextLTBPinCode);
        ed4 = findViewById(R.id.editTextLTBContactNumber);
        dateButton = findViewById(R.id.buttonBMCartDatePicker);
        timeButton = findViewById(R.id.buttonCartTimePicker);
        Button btnBook = findViewById(R.id.buttonBMCartCheckout);
        Button btnBack = findViewById(R.id.buttonBMCartBack);

        ed1.setKeyListener(null);
        ed2.setKeyListener(null);
        ed3.setKeyListener(null);
        ed4.setKeyListener(null);

        Intent it = getIntent();
        String title = it.getStringExtra("text1");
        String fullname = it.getStringExtra("text2");
        String address = it.getStringExtra("text3");
        String contact = it.getStringExtra("text4");
        String fees = it.getStringExtra("text5");

        tv.setText(title);
        ed1.setText(fullname);
        ed2.setText(address);
        ed3.setText(contact);
        ed4.setText("Cons Fees:"+fees+"/-");

        initDatePicker();

        dateButton.setOnClickListener(v -> datePickerDialog.show());
        initTimePicker();
        timeButton.setOnClickListener(v -> timePickerDialog.show());
        btnBack.setOnClickListener(v -> startActivity(new Intent(BookAppointmentActivity.this, FindDoctorActivity.class)));
        btnBook.setOnClickListener(v -> {
            Database database = new Database(getApplicationContext(), "healthcare", null, 1);
            SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
            String username = sharedPreferences.getString("username","");
            if(database.checkAppointmentExists(username, title + " => " + fullname, address, contact, dateButton.getText().toString(),timeButton.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Appointment already booked", Toast.LENGTH_LONG).show();
            } else {
                database.addOrder(username, title + " => " + fullname, address, contact, 0,dateButton.getText().toString(),timeButton.getText().toString(),Float.parseFloat(fees), "appointment");
                Toast.makeText(getApplicationContext(), "Appointment is done successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(BookAppointmentActivity.this, HomeActivity.class));
            }
        });
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
    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> timeButton.setText(String.format("%d:%d", hourOfDay, minute));
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);
        int style = AlertDialog.THEME_HOLO_DARK;
        timePickerDialog = new TimePickerDialog(this, style, timeSetListener, hours, minutes, true);
    }
}
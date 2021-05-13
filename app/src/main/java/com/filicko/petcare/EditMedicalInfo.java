package com.filicko.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.sql.Date;
import java.util.Calendar;

public class EditMedicalInfo extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    TextView datum;
    TextView info;
    int position;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medical_info);
        databaseHelper = new DatabaseHelper(this);
        datum = findViewById(R.id.textMedicalInfoDate);
        info = findViewById(R.id.textMedicalInfo);
    }
    public void saveMedicalInfo(View view) {
        position = getIntent().getIntExtra("position", 1);
        String text = info.getText().toString();
        String date = datum.getText().toString();
        databaseHelper.addMedicalInfoToDatabase(position, text, date);
        startActivity(new Intent(this, petActivity.class).putExtra("position", position));


    }
    public void pickDate(View view) {
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                System.out.println("tusom");
                System.out.println(year + "." + month + "." + dayOfMonth);
                datum.setText(dayOfMonth + "." + month + "." + year);
            }
        };
    }
}
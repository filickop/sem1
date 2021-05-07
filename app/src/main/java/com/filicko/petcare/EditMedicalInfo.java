package com.filicko.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.sql.Date;

public class EditMedicalInfo extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    TextView datum;
    TextView info;
    int position;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medical_info);
        databaseHelper = new DatabaseHelper(this);
        datum = findViewById(R.id.textMedicalInfoDate);
        info = findViewById(R.id.textMedicalInfo);
        datePicker = new DatePicker();
    }
    public void saveMedicalInfo(View view) {
        position = getIntent().getIntExtra("position", 1);
        String text = null;
        String date = null;
        databaseHelper.addMedicalInfoToDatabase(position + 1, text, date);
        startActivity(new Intent(this, petActivity.class).putExtra("position", position));

    }
    public void pickDate(View view) {
        datePicker.pickDate(this);
        int[] time = datePicker.getDate();
        System.out.println("datumdva" + time[0] + "." + time[1] + "." + time[2]);
        datum.setText(time[0] + "." + time[1] + "." + time[2]);
    }

}
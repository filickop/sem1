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
        String text = info.getText().toString();
        String date = datum.getText().toString();
        databaseHelper.addMedicalInfoToDatabase(position, text, date);
        startActivity(new Intent(this, petActivity.class).putExtra("position", position));


    }
    public void pickDate(View view) {
        System.out.println(datePicker);
        datePicker.pickDate(this, ()->{
            int[] time = datePicker.getDate();
            datum.setText(time[2] + "." + time[1] + "." + time[0]);
        });

    }

}
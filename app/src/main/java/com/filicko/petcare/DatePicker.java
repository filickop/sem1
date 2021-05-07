package com.filicko.petcare;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import java.util.Calendar;
import java.util.Date;

public class DatePicker {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    int year;
    int month;
    int day;
    public void pickDate(Context context) {
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                System.out.println("tusom");
                System.out.println(year + "." + month + "." + dayOfMonth);
                setDate(year,month + 1, dayOfMonth);
            }
        };
    }

    public void setDate(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }
    public int[] getDate() {
        return new int[] {year, month, day};
    }
}

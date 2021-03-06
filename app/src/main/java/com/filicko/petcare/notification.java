package com.filicko.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.Calendar;

public class notification extends AppCompatActivity {
    TextView budik1;
    TextView budik2;
    TextView budik3;
    TextView budik4;
    TextView budik5;
    TextView budik6;

    boolean budik1Clicked;
    boolean budik2Clicked;
    boolean budik3Clicked;
    boolean budik4Clicked;
    boolean budik5Clicked;
    boolean budik6Clicked;

    Switch switch1;
    Switch switch2;
    Switch switch3;
    Switch switch4;
    Switch switch5;
    Switch switch6;

    DatabaseHelper databaseHelper;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        generujUpozornenie();
        position = getIntent().getIntExtra("position", position);
        databaseHelper = new DatabaseHelper(this);
        budik1Clicked = false;
        budik2Clicked = false;
        budik3Clicked = false;
        budik4Clicked = false;
        budik5Clicked = false;
        budik6Clicked = false;
        budik1 = findViewById(R.id.nastavRanoVoda);
        budik2 = findViewById(R.id.nastavObedVoda);
        budik3 = findViewById(R.id.nastavVecerVoda);
        budik4 = findViewById(R.id.nastavJedloRano);
        budik5 = findViewById(R.id.nastavJedloObed);
        budik6 = findViewById(R.id.nastavJedloVecer);

        switch1 = findViewById(R.id.vodaSwitchRano);
        switch2 = findViewById(R.id.vodaSwitchObed);
        switch3 = findViewById(R.id.vodaSwitchVecer);
        switch4 = findViewById(R.id.jedloSwitchRano);
        switch5 = findViewById(R.id.jedloSwitchObed);
        switch6 = findViewById(R.id.jedloSwitchVecer);

        budik1.setText(databaseHelper.getCas(position, 1));
        budik2.setText(databaseHelper.getCas(position, 2));
        budik3.setText(databaseHelper.getCas(position, 3));
        budik4.setText(databaseHelper.getCas(position, 4));
        budik5.setText(databaseHelper.getCas(position, 5));
        budik6.setText(databaseHelper.getCas(position, 6));
        switch1.setChecked(databaseHelper.getChecked(position, 1));
        switch2.setChecked(databaseHelper.getChecked(position, 2));
        switch3.setChecked(databaseHelper.getChecked(position, 3));
        switch4.setChecked(databaseHelper.getChecked(position, 4));
        switch5.setChecked(databaseHelper.getChecked(position, 5));
        switch6.setChecked(databaseHelper.getChecked(position, 6));


        budik1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                budik1Clicked = true;
                pickTime(v);
            }
        });
        budik2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                budik2Clicked = true;
                pickTime(v);
            }
        });
        budik3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                budik3Clicked = true;
                pickTime(v);
            }
        });
        budik4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                budik4Clicked = true;
                pickTime(v);
            }
        });
        budik5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                budik5Clicked = true;
                pickTime(v);
            }
        });
        budik6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                budik6Clicked = true;
                pickTime(v);
            }
        });
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!budik1.getText().equals("Nastav ??as")) {
                    if (isChecked) {
                        System.out.println("je checked");
                        databaseHelper.addBudikToDatabase(position + 1, 1, databaseHelper.getCas(position, 1), 1);
                    } else {
                        System.out.println("nie je checked");
                        databaseHelper.addBudikToDatabase(position + 1, 1, databaseHelper.getCas(position, 1), 0);
                    }
                } else {
                    switch1.setChecked(false);
                }
            }
        });
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!budik2.getText().equals("Nastav ??as")) {
                    if (isChecked) {
                        databaseHelper.addBudikToDatabase(position + 1, 2, databaseHelper.getCas(position, 2), 1);
                    } else {
                        databaseHelper.addBudikToDatabase(position + 1, 2, databaseHelper.getCas(position, 2), 0);
                    }
                } else {
                    switch2.setChecked(false);
                }
            }
        });
        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!budik3.getText().equals("Nastav ??as")) {
                    if (isChecked) {
                        System.out.println("je checked");
                        databaseHelper.addBudikToDatabase(position + 1, 3, databaseHelper.getCas(position, 3), 1);
                    } else {
                        System.out.println("nie je checked");
                        databaseHelper.addBudikToDatabase(position + 1, 3, databaseHelper.getCas(position, 3), 0);
                    }
                } else {
                    switch3.setChecked(false);
                }
            }
        });
        switch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!budik4.getText().equals("Nastav ??as")) {
                    if (isChecked) {
                        System.out.println("je checked");
                        databaseHelper.addBudikToDatabase(position + 1, 4, databaseHelper.getCas(position, 4), 1);
                    } else {
                        System.out.println("nie je checked");
                        databaseHelper.addBudikToDatabase(position + 1, 4, databaseHelper.getCas(position, 4), 0);
                    }
                } else {
                    switch4.setChecked(false);
                }
            }
        });
        switch5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!budik5.getText().equals("Nastav ??as")) {
                    if (isChecked) {
                        System.out.println("je checked");
                        databaseHelper.addBudikToDatabase(position + 1, 5, databaseHelper.getCas(position, 5), 1);
                    } else {
                        System.out.println("nie je checked");
                        databaseHelper.addBudikToDatabase(position + 1, 5, databaseHelper.getCas(position, 5), 0);
                    }
                } else {
                    switch5.setChecked(false);
                }
            }
        });
        switch6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!budik6.getText().equals("Nastav ??as")) {
                    if (isChecked) {
                        System.out.println("je checked");
                        databaseHelper.addBudikToDatabase(position + 1, 6, databaseHelper.getCas(position, 6), 1);
                    } else {
                        System.out.println("nie je checked");
                        databaseHelper.addBudikToDatabase(position + 1, 6, databaseHelper.getCas(position, 6), 0);
                    }
                } else {
                    switch6.setChecked(false);
                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, petActivity.class).putExtra("position", position));
    }

    /**
     * metoda na nastavenie notifikacie
     */
    public void generujUpozornenie () {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.HOUR_OF_DAY, 7);
        calendar.set(calendar.MINUTE, 42);
        calendar.set(Calendar.SECOND, 00);

        Intent intent = new Intent(this, Upozornenia.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager alarmManagerTank = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManagerTank.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60000, pendingIntent);
        notifChannel();
    }

    /**
     * metoda na vytvorenie notifikacneho kanalu
     */
    public void notifChannel () {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Moja notifikacia";
            String description = "tu napisem info ";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("upozornenie", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * spusti dialog na vybratie casu
     * @param view potrebny na funkcnost tejto metody
     */
    public void pickTime (View view){

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                System.out.println(hourOfDay + ":" + minute);
                String cas;
                if (hourOfDay < 10) {
                    cas = "0" + hourOfDay + ":";
                } else {
                    cas = hourOfDay + ":";
                }
                if (minute < 10) {
                    cas = cas + "0" + minute;
                } else {
                    cas = cas + minute;
                }
                if (budik1Clicked) {
                    budik1.setText(cas);
                    budik1Clicked = false;
                    databaseHelper.addBudikToDatabase(position + 1, 1, cas, 0);
                } else if (budik2Clicked) {
                    budik2.setText(cas);
                    budik2Clicked = false;
                    databaseHelper.addBudikToDatabase(position + 1, 2, cas, 0);
                } else if (budik3Clicked) {
                    budik3.setText(cas);
                    budik3Clicked = false;
                    databaseHelper.addBudikToDatabase(position + 1, 3, cas, 0);
                } else if (budik4Clicked) {
                    budik4.setText(cas);
                    budik4Clicked = false;
                    databaseHelper.addBudikToDatabase(position + 1, 4, cas, 0);
                } else if (budik5Clicked) {
                    budik5.setText(cas);
                    budik5Clicked = false;
                    databaseHelper.addBudikToDatabase(position + 1, 5, cas, 0);
                } else if (budik6Clicked) {
                    budik6.setText(cas);
                    budik6Clicked = false;
                    databaseHelper.addBudikToDatabase(position + 1, 6, cas, 0);
                }
            }
        }, hour, minute, android.text.format.DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }
}

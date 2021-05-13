package com.filicko.petcare;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Upozornenia extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = "";
        String text = "";
        DatabaseHelper db = new DatabaseHelper(context);
        Cursor cur = db.getCur("reminders");
        Cursor pet = db.getCur("basicInfo");

        while (cur.moveToNext()) {
            pet.moveToFirst();
            do {
                if(cur.getInt(1) == pet.getInt(0)) {
                    title = pet.getString(1);
                    break;
                }
            } while (pet.moveToNext());

            if(cur.getInt(4) == 1 && db.compareTimes(cur.getString(3))) {
                switch (cur.getInt(2)) {
                    case 1:
                        text = "si prosí vodu ráno";
                        break;
                    case 2:
                        text = "si prosí vodu na obed";
                        break;
                    case 3:
                        text = "si prosí vodu večer";
                        break;
                    case 4:
                        text = "si prosí jedlo ráno";
                        break;
                    case 5:
                        text = "si prosí jedlo na obed";
                        break;
                    case 6:
                        text = "si prosí jedlo večer";
                        break;
                }
                upozornenie(context, text , title, cur.getInt(0));
            }
        }
    }
    public void upozornenie(Context context, String string, String meno, int id){

        NotificationCompat.Builder ncb = new NotificationCompat.Builder(context, "upozornenie")
                .setSmallIcon(R.drawable.ic_launcher_background).setContentTitle(meno).setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(string)).setAutoCancel(true);

        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        nm.notify(id, ncb.build());
    }
}

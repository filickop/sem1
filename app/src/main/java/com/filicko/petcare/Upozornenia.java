package com.filicko.petcare;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Upozornenia extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DatabaseHelper db = new DatabaseHelper(context);
        upozornenie(context,"pes", "malypes", 8);

    }
    public void upozornenie(Context context, String string, String meno, int id){
        NotificationCompat.Builder ncb = new NotificationCompat.Builder(context, "upozornenie")
                .setSmallIcon(R.drawable.ic_launcher_background).setContentTitle(meno).setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(string)).setAutoCancel(true);

        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        nm.notify(id, ncb.build());
    }
}

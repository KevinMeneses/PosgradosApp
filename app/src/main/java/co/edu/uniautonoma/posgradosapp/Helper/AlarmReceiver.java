package co.edu.uniautonoma.posgradosapp.Helper;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import co.edu.uniautonoma.posgradosapp.R;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent snoozeIntent = new Intent(context, AlarmReceiver.class);
        snoozeIntent.setAction("descartar");
        PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(context, 0, snoozeIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NotificationCompat.CATEGORY_ALARM);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        if(intent.getAction().equals("alarma")) {

            builder .setSmallIcon(R.drawable.alerta)
                    .setContentTitle("Recordatorio")
                    .setContentText("Tienes clase en 30 minutos")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("Tienes clase en 30 minutos"))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setSound(Uri.parse(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI))
                    .addAction(R.drawable.alerta, "descartar", snoozePendingIntent);

            notificationManager.notify(1, builder.build());
        }

        if(intent.getAction().equals("descartar")) {
            notificationManager.cancelAll();
        }

    }
}

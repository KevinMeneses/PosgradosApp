package co.edu.uniautonoma.posgradosapp.presentation.helper

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import co.edu.uniautonoma.posgradosapp.presentation.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val snoozeIntent = Intent(context, AlarmReceiver::class.java)
        snoozeIntent.action = "descartar"
        val snoozePendingIntent = PendingIntent.getBroadcast(context, 0, snoozeIntent, 0)
        val builder = NotificationCompat.Builder(context, NotificationCompat.CATEGORY_ALARM)
        val notificationManager = NotificationManagerCompat.from(context)
        if (intent.action == "alarma") {
            builder.setSmallIcon(R.drawable.alerta)
                    .setContentTitle("Recordatorio")
                    .setContentText("Tienes clase en 30 minutos")
                    .setStyle(NotificationCompat.BigTextStyle()
                            .bigText("Tienes clase en 30 minutos"))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setSound(Uri.parse(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI))
                    .addAction(R.drawable.alerta, "descartar", snoozePendingIntent)
            notificationManager.notify(1, builder.build())
        }
        if (intent.action == "descartar") {
            notificationManager.cancelAll()
        }
    }
}
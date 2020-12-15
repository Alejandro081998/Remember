package com.frgp.remember.ServicioAlarmas;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import com.frgp.remember.Base.Notificaciones.NotificacionesBD;
import com.frgp.remember.Base.Rutinas.RutinasBD;
import com.frgp.remember.Entidades.Rutinas;

public class Alarma extends BroadcastReceiver
{
    private static final String CHANNEL_ID = "1234";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        RutinasBD rutinasBD = new RutinasBD(context,"VerificarRutinas");
        rutinasBD.execute();

        Log.d("ALARMARUTINAS", "PASA: ");
        wl.release();
    }



    public void setAlarm(Context context)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, Alarma.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 1234, i, PendingIntent.FLAG_UPDATE_CURRENT);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 30*1000*1 , pi); // Millisec * Second * Minute
    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, Alarma.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 1234, intent, PendingIntent.FLAG_NO_CREATE);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}

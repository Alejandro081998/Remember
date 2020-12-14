package com.frgp.remember.ServicioAlarmas;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.frgp.remember.ServicioNotificaciones.Alarm;
import com.frgp.remember.Session.Session;

public class AlarmaAutoStart extends BroadcastReceiver
{
    Alarma alarm = new Alarma();
    private Session session;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        session = new Session();
        session.setCt(context);
        session.cargar_session();

        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()))
        {
            if(!session.getUsuario().equals(""))
                alarm.setAlarm(context);
        }
    }
}

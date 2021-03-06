package com.frgp.remember.ServicioNotificaciones;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.frgp.remember.Session.Session;

public class AutoStart extends BroadcastReceiver
{
    Alarm alarm = new Alarm();
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

package com.frgp.remember.ServicioNotificaciones;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.frgp.remember.Session.Session;

public class Servicio extends Service
{
    Alarm alarm = new Alarm();
    private Session session;
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        session = new Session();
        session.setCt(this);
        session.cargar_session();

        if(!session.getUsuario().equals("")) {
            alarm.setAlarm(this);
        }else
            stopSelf();

            return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        alarm.setAlarm(this);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}

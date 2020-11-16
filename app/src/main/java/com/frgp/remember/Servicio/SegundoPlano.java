package com.frgp.remember.Servicio;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.frgp.remember.Base.Data.DatosBD;
import com.frgp.remember.Base.Notificaciones.NotificacionesBD;
import com.frgp.remember.Entidades.Estados;
import com.frgp.remember.Entidades.Notificaciones;
import com.frgp.remember.Session.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SegundoPlano extends Service {

    private static ArrayList<Notificaciones> listaNotificaciones = new ArrayList<Notificaciones>();
    private Estados estado1;
    private Notificaciones not;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("msg", "Servicio creado");
    }

    @Override
    public int onStartCommand(Intent intencion, int flags, int idArranque) {
        Log.d("msg_servicio", "Servicio reiniciado");

        Session ses = new Session();
        ses.setCt(this);
        ses.cargar_session();

        if(ses.getContrasena().length() != 0) {
            NotificacionesBD not = new NotificacionesBD(this, "VerificarNotificaciones");
            not.execute();
        }



        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
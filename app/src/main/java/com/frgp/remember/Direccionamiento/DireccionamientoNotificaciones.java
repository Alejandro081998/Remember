package com.frgp.remember.Direccionamiento;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.frgp.remember.Base.Notificaciones.NotificacionesBD;
import com.frgp.remember.Base.Rutinas.RutinasBD;
import com.frgp.remember.Entidades.Avisos;
import com.frgp.remember.Entidades.Notificaciones;
import com.frgp.remember.Entidades.Rutinas;
import com.frgp.remember.IniciarSesion.iniciar_sesion;
import com.frgp.remember.Principal.MainActivity;
import com.frgp.remember.R;
import com.frgp.remember.Session.Session;
import com.frgp.remember.ui.Rutinas.Raiz.RutinasFragment;
import com.frgp.remember.ui.Vinculaciones.Listado.ListadoVinculacionesFragment;
import com.frgp.remember.ui.Vinculaciones.ListadoProfesional.VinculacionesProfesionalFragment;
import com.frgp.remember.ui.Vinculaciones.Pendientes.VinculacionesPendientesFragment;

import java.sql.Time;

public class DireccionamientoNotificaciones extends AppCompatActivity {

    private String apartado = "";
    private Session session;
    private String fecha = "",hora = "";
    private int id_noti;
    public static final String TAG = "LOG";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_notif);


        Log.d(TAG, "onCreate:");

        apartado = getIntent().getStringExtra("apartado");
        id_noti = getIntent().getIntExtra("noti",-1);
        fecha = getIntent().getStringExtra("dia_rutina");
        hora = getIntent().getStringExtra("horaRutina");
        session = new Session();
        session.setCt(this);
        session.cargar_session();

        if (!session.getUsuario().equals("")) {

            FragmentManager fragmentManager = ((AppCompatActivity) this).getSupportFragmentManager();;
            Intent intent = new Intent(this, MainActivity.class);

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.cancel(id_noti);

            if(!apartado.equals("RutinasAlarma")) {
                Notificaciones not = new Notificaciones();
                not.setIdNotificacion(id_noti);

                NotificacionesBD notbd = new NotificacionesBD(this, not, "Leida");
                notbd.execute();
            }else{
                Avisos avisos = new Avisos();
                Rutinas rutinas = new Rutinas();

                rutinas.setId_rutina(id_noti);
                avisos.setId_rutina(rutinas);

                RutinasBD rutinasBD = new RutinasBD(this,avisos, fecha, hora,"AvisoAlarma");
                rutinasBD.execute();
            }

            switch (apartado) {

                case "Vinculaciones Pendientes":
                    //fragmentManager.beginTransaction().replace(R.id.content_main, new VinculacionesPendientesFragment()).commit();


                    intent.putExtra("apartado","Vinculaciones Pendientes");
                    startActivity(intent);
                    break;

                case "Vinculaciones Existentes":

                    if (session.getTipo_rol().equals("Paciente")) {
                        intent.putExtra("apartado","ListadoVinculaciones");
                        startActivity(intent);
                        //fragmentManager.beginTransaction().replace(R.id.content_main, new ListadoVinculacionesFragment()).commit();
                    } else {
                        intent.putExtra("apartado","ListadoProfesional");
                        startActivity(intent);
                        //fragmentManager.beginTransaction().replace(R.id.content_main, new VinculacionesProfesionalFragment()).commit();
                    }
                    break;

                case "Rutinas Existentes":
                    intent.putExtra("apartado","Rutinas");
                    startActivity(intent);
                    //fragmentManager.beginTransaction().replace(R.id.content_main, new RutinasFragment()).commit();
                    break;

                case "RutinasAlarma":
                    String a = "";
                    intent.putExtra("apartado","Rutinas");
                    startActivity(intent);
                    break;

                default:
                    intent.putExtra("apartado","");
                    startActivity(intent);
                    //fragmentManager.beginTransaction().replace(R.id.content_main, new RutinasFragment()).commit();
                    break;
            }

        } else {
            Intent intent = new Intent(this, iniciar_sesion.class);
            startActivity(intent);
        }


    }


}

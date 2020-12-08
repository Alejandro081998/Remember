package com.frgp.remember.Base.Notificaciones;

//import frgp.utn.edu.ar.consultas_mysql.adapter.ClienteAdapter;
//import frgp.utn.edu.ar.consultas_mysql.entidad.Cliente;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.frgp.remember.Adaptadores.AdaptadorNotificaciones;
import com.frgp.remember.Base.Data.DatosBD;
import com.frgp.remember.Entidades.Apartados;
import com.frgp.remember.Entidades.Estados;
import com.frgp.remember.Entidades.Notificaciones;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.R;
import com.frgp.remember.Session.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificacionesBD extends AsyncTask<String, Void, String> {


    private Usuarios usu;
    private Usuarios usu1;
    private Notificaciones not;
    private Apartados apart;
    private Estados estado1,estado2;



    private String que_hacer;
    private Context context;
    private ProgressDialog dialog;
    private String mensaje_devuelto;
    private Session ses;

    private String tipo_rutina;
    private boolean insertamos;
    private boolean no_hay_notificaciones;
    private SearchView buscar_notificaciones;

    private ListView list_notificaciones;
    private TextView txt_no_hay_notificaciones;

    private static ArrayList<Notificaciones> listaNotificaciones = new ArrayList<Notificaciones>();

    public NotificacionesBD(Context ct, String que, TextView no, ListView lv, SearchView sv){

        no_hay_notificaciones = true;
        this.context = ct;
        ses = new Session();
        ses.setCt(ct);
        ses.cargar_session();
        this.txt_no_hay_notificaciones = no;
        this.list_notificaciones = lv;
        dialog = new ProgressDialog(ct);
        this.que_hacer = que;
        this.buscar_notificaciones = sv;

    }

    public NotificacionesBD(Context ct, Apartados ap, Usuarios u, Notificaciones nt, String que){

        this.usu = new Usuarios();
        this.usu = u;
        this.apart = new Apartados();
        this.apart = ap;
        this.not = new Notificaciones();
        this.not = nt;
        this.context = ct;
        ses = new Session();
        ses.setCt(ct);
        ses.cargar_session();
        estado1 = new Estados();
        estado2 = new Estados();
        this.que_hacer = que;

    }

    public NotificacionesBD(Context ct, Notificaciones nt, String que){

        this.not = new Notificaciones();
        this.not = nt;
        this.context = ct;
        estado1 = new Estados();
        this.que_hacer = que;
    }

    public NotificacionesBD(Context ct, String que){

        this.not = new Notificaciones();
        this.context = ct;
        ses = new Session();
        ses.setCt(ct);
        ses.cargar_session();
        estado1 = new Estados();
        this.que_hacer = que;
    }


    @Override
    protected String doInBackground(String... urls) {
        String response = "";
        mensaje_devuelto = "";
        PreparedStatement ps = null;
        PreparedStatement ps_aux = null;
        //String estado = "";


        if (que_hacer.equals("NuevaNotificacion")) {

            insertamos = true;

            try {
                Log.d("NUEVANOTI", "OK");

                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                //ps = con.prepareStatement("INSERT INTO usuarios(id, nombre,stock,alertastock,idCategoria) VALUES(?,?,?,?,?)");

                ResultSet rs;
                Statement st = con.createStatement();

                rs = st.executeQuery("SELECT idestado from estados where estado='Pendiente'");

                if (rs.next())
                    estado1.setId_estado(rs.getInt(1));
                else {
                    mensaje_devuelto = "No se encuentra estado!";
                    insertamos = false;
                }

                rs = st.executeQuery("SELECT idApartado from apartados where descripcion='" + apart.getDescripcion() + "'");

                if (rs.next())
                    apart.setIdApartado(rs.getInt(1));
                else {
                    mensaje_devuelto = "No se encuentra apartado!";
                    insertamos = false;
                }


                if (insertamos) {


                    Log.d("INSERTONOTI", "OK");

                    ps = con.prepareStatement("INSERT INTO notificaciones (idRemitente,idDestinatario,hora," +
                            "Descripcion,idApartado,Estado,Envio) VALUES (?,?,DATE_SUB(NOW(),INTERVAL 3 HOUR),?,?,?,?)");

                    ps.setInt(1, ses.getId_usuario());
                    ps.setInt(2, usu.getId_usuario());
                    ps.setString(3, not.getDescripcion());
                    ps.setInt(4, apart.getIdApartado());
                    ps.setInt(5, estado1.getId_estado());
                    ps.setInt(6, estado1.getId_estado());

                    int notificacion = ps.executeUpdate();

                    if (notificacion > 0) {

                        Log.d("GUARDONOTIFICACION", "OK");

                    } else
                        Log.d("GUARDONOTIFICACION", "NO");

                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al guardar NOTIFICACION!";
            }
        }

        if (que_hacer.equals("CargarNotificaciones")) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                //ps = con.prepareStatement("INSERT INTO usuarios(id, nombre,stock,alertastock,idCategoria) VALUES(?,?,?,?,?)");

                ResultSet rs;
                Statement st = con.createStatement();
                ResultSet rs_;
                Statement st_ = con.createStatement();

                rs = st.executeQuery("SELECT * FROM notificaciones where idDestinatario=" + ses.getId_usuario()
                + " and Estado=2");

                listaNotificaciones.clear();

                while (rs.next()) {

                    not = new Notificaciones();
                    apart = new Apartados();
                    estado1 = new Estados();
                    estado2 = new Estados();
                    usu = new Usuarios();
                    usu1 = new Usuarios();

                    rs_ = st_.executeQuery("SELECT Descripcion from apartados where idApartado=" + rs.getInt("idApartado"));

                    if (rs_.next())
                        apart.setDescripcion(rs_.getString("Descripcion"));

                    Log.d("APARTADO:", "" + apart.getDescripcion());

                    not.setIdNotificacion(rs.getInt("idNotificacion"));
                    usu.setId_usuario(rs.getInt("idRemitente"));
                    not.setIdRemitente(usu);
                    usu1.setId_usuario(rs.getInt("idDestinatario"));
                    not.setIdDestinatario(usu1);
                    not.setHora(rs.getTimestamp("Hora"));
                    not.setDescripcion(rs.getString("Descripcion"));
                    apart.setIdApartado(rs.getInt("idApartado"));
                    not.setIdApartado(apart);
                    estado1.setId_estado(rs.getInt("Estado"));
                    not.setEstado(estado1);
                    estado2.setId_estado(rs.getInt("Envio"));
                    not.setEnvio(estado2);


                    listaNotificaciones.add(not);
                    no_hay_notificaciones = false;
                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al buscar notificaciones!";
            }


        }

        if (que_hacer.equals("Leida") || que_hacer.equals("Recibida")) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                //ps = con.prepareStatement("INSERT INTO usuarios(id, nombre,stock,alertastock,idCategoria) VALUES(?,?,?,?,?)");

                Statement st = con.createStatement();
                ResultSet rs;


                if (que_hacer.equals("Leida")) {

                    ps = con.prepareStatement("UPDATE notificaciones SET Estado=?" +
                            " where idNotificacion=?");

                    rs = st.executeQuery("SELECT * FROM estados where estado ='Leida'");

                    if (rs.next())
                        estado1.setId_estado(rs.getInt("idestado"));
                }

                if (que_hacer.equals("Recibida")) {

                    ps = con.prepareStatement("UPDATE notificaciones SET Envio=?" +
                            " where idNotificacion=?");

                    rs = st.executeQuery("SELECT * FROM estados where estado ='Recibida'");

                    if (rs.next())
                        estado1.setId_estado(rs.getInt("idestado"));
                }

                ps.setInt(1, estado1.getId_estado());
                ps.setInt(2, not.getIdNotificacion());


                int filas = ps.executeUpdate();

                if (filas > 0)
                    Log.d("NOTIFICACION_MODIFICADA", "OK ID:" + not.getIdNotificacion());
                else
                    Log.d("NOTIFICACION_MODIFICADA", "NO");


                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al modificar NOTIFICACION!";
            }
        }

        if (que_hacer.equals("VerificarNotificaciones")) {

            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                //ps = con.prepareStatement("INSERT INTO usuarios(id, nombre,stock,alertastock,idCategoria) VALUES(?,?,?,?,?)");

                ResultSet rs;
                Statement st = con.createStatement();
                ResultSet rs_;
                Statement st_ = con.createStatement();


                rs = st.executeQuery("SELECT * FROM estados where estado ='Pendiente'");

                if (rs.next())
                    estado1.setId_estado(rs.getInt("idestado"));

                rs = st.executeQuery("SELECT * FROM notificaciones where Envio=" + estado1.getId_estado() + " and" +
                        " idDestinatario=" + ses.getId_usuario());

                listaNotificaciones.clear();

                while (rs.next()) {

                    Log.d("HAYNOTI:" ,"SI HAY");
                    not = new Notificaciones();
                    not.setDescripcion(rs.getString("Descripcion"));
                    not.setIdNotificacion(rs.getInt("idNotificacion"));

                    listaNotificaciones.add(not);


                    rs_ = st_.executeQuery("SELECT * FROM estados where estado ='Recibida'");

                    if (rs_.next())
                        estado1.setId_estado(rs_.getInt("idestado"));

                        ps = con.prepareStatement("UPDATE notificaciones SET Envio=?" +
                                " where idNotificacion=" + not.getIdNotificacion());

                        ps.setInt(1,estado1.getId_estado());


                        int filas = ps.executeUpdate();


                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al buscar notificaciones!";

            }

        }

        return response;
    }




    @Override
    protected void onPreExecute() {
        if(que_hacer.equals("CargarNotificaciones")) {
            dialog.setMessage("Procesando...");
            dialog.show();
        }
    }

    @Override
    protected void onPostExecute(String response) {

        if(que_hacer.equals("CargarNotificaciones")) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            final AdaptadorNotificaciones adapter = new AdaptadorNotificaciones(context,listaNotificaciones);
            list_notificaciones.setAdapter(adapter);


            buscar_notificaciones.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    adapter.getFilter().filter(s);
                    return true;
                }
            });



            if (no_hay_notificaciones) {
                txt_no_hay_notificaciones.setVisibility(View.VISIBLE);
                list_notificaciones.setVisibility(View.GONE);
            } else {
                txt_no_hay_notificaciones.setVisibility(View.GONE);
                list_notificaciones.setVisibility(View.VISIBLE);
            }

        }

        if(que_hacer.equals("VerificarNotificaciones")){


            for(Notificaciones not: listaNotificaciones){

                CreateNotificationChannel();
                CreateNotification(not.getDescripcion(),not.getIdNotificacion());

            }

            NotificacionesBD nt = new NotificacionesBD(context,"VerificarNotificaciones");
            nt.execute();


        }


    }


    private void CreateNotificationChannel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            String CHANNEL_ID = "NOTIFICACION";

            CharSequence name = "Notificacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) (context).getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void CreateNotification(String desc, int notif){

        String CHANNEL_ID = "NOTIFICACION";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_alerta);
        builder.setContentTitle("Rememberme");
        builder.setContentText(desc);
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA,1000,1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        //builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(notif,builder.build());

    }

}

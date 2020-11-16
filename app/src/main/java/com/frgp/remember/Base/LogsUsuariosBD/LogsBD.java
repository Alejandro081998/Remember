package com.frgp.remember.Base.LogsUsuariosBD;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.frgp.remember.Adaptadores.AdaptadorNotificaciones;
import com.frgp.remember.Base.Data.DatosBD;
import com.frgp.remember.Entidades.Apartados;
import com.frgp.remember.Entidades.Estados;
import com.frgp.remember.Entidades.LogsUsuarios;
import com.frgp.remember.Entidades.Notificaciones;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.Session.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class LogsBD extends AsyncTask<String, Void, String> {


    private Usuarios usu;
    private LogsUsuarios userlog;
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



    public LogsBD(Context ct, LogsUsuarios usulog, String que){

        this.context = ct;
        ses = new Session();
        ses.setCt(ct);
        ses.cargar_session();
        estado1 = new Estados();
        estado2 = new Estados();
        this.userlog = new LogsUsuarios();
        this.userlog = usulog;
        this.que_hacer = que;

    }

    @Override
    protected String doInBackground(String... urls) {
        String response = "";
        mensaje_devuelto = "";
        PreparedStatement ps = null;
        PreparedStatement ps_aux = null;
        //String estado = "";


        if(que_hacer.equals("NuevoLog")) {

            insertamos = true;

            try {
                Log.d("NUEVANOTI","OK");

                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                //ps = con.prepareStatement("INSERT INTO usuarios(id, nombre,stock,alertastock,idCategoria) VALUES(?,?,?,?,?)");

                ResultSet rs;
                Statement st = con.createStatement();


                if(insertamos){


                    Log.d("INSERTONOTI","OK");

                    ps = con.prepareStatement("INSERT INTO logs_usuarios (idusuario, fecha_hora_log, log_descripcion)" +
                            " VALUES (?,DATE_SUB(NOW(),INTERVAL 3 HOUR),?)");

                    ps.setInt(1,ses.getId_usuario());
                    ps.setString(2,userlog.getLogdesc());


                    int notificacion = ps.executeUpdate();

                    if(notificacion > 0){

                        Log.d("GUARDOLOG","OK");

                    }else
                        Log.d("GUARDOLOG","NO");

                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al guardar Log!";
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


    }

}

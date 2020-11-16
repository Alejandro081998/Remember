package com.frgp.remember.Base.Recuperaciones;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.frgp.remember.Base.Data.DatosBD;
import com.frgp.remember.Entidades.Recuperaciones;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.IniciarSesion.iniciar_sesion;
import com.frgp.remember.Session.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class RecuperacionesBD  extends AsyncTask<String, Void, String> {

    private Usuarios user;
    private Recuperaciones recu;
    private String que_hacer, telefono, usuario, contrasena;
    private Context context;
    private ProgressDialog dialog;
    private String mensaje_devuelto;
    private Session ses;
    private EditText tel;
    private TextView id_recu;
    private boolean tiene_telefono;
    private String us, con;


    public RecuperacionesBD(Recuperaciones recu, Context ct, String que) {
        ses = new Session();
        ses.setCt(ct);
        ses.cargar_session();
        this.recu = new Recuperaciones();
        this.recu = recu;
        this.user = new Usuarios();
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
        this.tiene_telefono = false;
    }

    public RecuperacionesBD(Context ct, String que, EditText tel, TextView id_recu) {
        ses = new Session();
        ses.setCt(ct);
        ses.cargar_session();
        this.recu = new Recuperaciones();
        this.recu = recu;
        this.user = new Usuarios();
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
        this.tel = tel;
        this.id_recu = id_recu;
        this.tiene_telefono = false;
    }

    public RecuperacionesBD(Context ct, String que, String tel, String usuario, String contrasena) {

        this.recu = new Recuperaciones();
        this.recu = recu;
        this.user = new Usuarios();
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
        this.telefono = tel;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }


    @Override
    protected String doInBackground(String... urls) {
        String response = "";
        String hacemos = "";
        mensaje_devuelto = "";
        PreparedStatement ps;

        if (que_hacer.equals("Guardar")) {

            boolean insertamos = true;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);

                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery("SELECT * FROM recuperaciones where idusuario=" + ses.getId_usuario());

                if (rs.next()) {
                    hacemos = "Modificar";
                } else {
                    hacemos = "Insertar";
                }

                rs = st.executeQuery("SELECT * FROM recuperaciones where idusuario<>" + ses.getId_usuario() + " and" +
                        " telefono='" + recu.getTelefono() + "'");

                if (rs.next())
                    insertamos = false;


                if (insertamos) {

                    if (hacemos.equals("Insertar")) {

                        ps = con.prepareStatement("INSERT INTO recuperaciones(idusuario, telefono)" +
                                " VALUES (?,?)");

                        ps.setInt(1, ses.getId_usuario());
                        ps.setString(2, recu.getTelefono());

                    } else {

                        ps = con.prepareStatement("Update recuperaciones set telefono=? where idrecuperacion=" + recu.getId_recuperacion());
                        ps.setString(1, recu.getTelefono());

                    }


                    int filas = ps.executeUpdate();

                    if (filas > 0) {
                        mensaje_devuelto = "Telefono registrado!";
                    } else
                        mensaje_devuelto = "Error al registrar telefono!";
                } else
                    mensaje_devuelto = "El telefono ya se encuentra registrado en otro usuario";


                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al registrar telefono!!";
            }
        }

        if (que_hacer.equals("Eliminar")) {


            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                ps = con.prepareStatement("Delete from recuperaciones where idRecuperacion=?");

                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery("SELECT * FROM recuperaciones where idusuario=" + ses.getId_usuario());

                if (rs.next()) {
                    ps.setInt(1, recu.getId_recuperacion());

                    int filas = ps.executeUpdate();

                    if (filas > 0) {
                        mensaje_devuelto = "Telefono eliminado!";
                    } else
                        mensaje_devuelto = "Error al eliminar telefono!";
                } else {
                    mensaje_devuelto = "No tienes un telefono para eliminar";
                }


                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al eliminar telefono!!";
            }
        }

        if (que_hacer.equals("TraerTelefono")) {

            String result2;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery("SELECT * FROM  recuperaciones where idusuario=" + ses.getId_usuario());

                if (rs.next()) {

                    recu.setId_recuperacion(rs.getInt("idrecuperacion"));
                    recu.setTelefono(rs.getString("telefono"));
                    tiene_telefono = true;

                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                result2 = "Conexion no exitosa";
            }


        }

        if (que_hacer.equals("EnviarMensaje")) {

            boolean insertamos = true;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);

                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery("SELECT u.usuario,u.contraseña from usuarios u inner join recuperaciones r " +
                        "on u.idusuario = r.idusuario where r.telefono='" + telefono + "'");

                if (rs.next()) {
                    this.us = rs.getString("usuario");
                    this.con = rs.getString("contraseña");
                    tiene_telefono = true;
                }
                else{
                    mensaje_devuelto = "Telefono no registrado";

                }


                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al registrar telefono!!";
            }
        }

        return response;


    }


    @Override
    protected void onPreExecute() {
        dialog.setMessage("Procesando...");
        dialog.show();
    }

    @Override
    protected void onPostExecute(String response) {

        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        if (que_hacer.equals("EnviarMensaje")) {
            contrasena = this.con;
            usuario = this.us;

            if(tiene_telefono)
            ((iniciar_sesion) context).enviar_datos(telefono, contrasena, usuario);
            else
                Toast.makeText(context, mensaje_devuelto, Toast.LENGTH_SHORT).show();

        }

            if (que_hacer.equals("Guardar") || que_hacer.equals("Eliminar")) {
                Toast.makeText(context, mensaje_devuelto, Toast.LENGTH_SHORT).show();
            }


            if (que_hacer.equals("TraerTelefono")) {

                if (tiene_telefono) {

                    tel.setText(recu.getTelefono());
                    id_recu.setText("" + recu.getId_recuperacion());

                } else {

                    tel.setText("");
                    id_recu.setText("");


                }

            }
        }

    }


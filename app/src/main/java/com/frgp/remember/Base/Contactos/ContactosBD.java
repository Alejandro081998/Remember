package com.frgp.remember.Base.Contactos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.frgp.remember.Adaptadores.AdaptadorContactos;
import com.frgp.remember.Base.Data.DatosBD;
import com.frgp.remember.Entidades.Contactos;
import com.frgp.remember.Entidades.Estados;
import com.frgp.remember.Entidades.Familiares;
import com.frgp.remember.Entidades.Pacientes;
import com.frgp.remember.Entidades.Profesionales;
import com.frgp.remember.Entidades.TipoRol;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.Principal.MainActivity;
import com.frgp.remember.R;
import com.frgp.remember.Session.Session;
import com.frgp.remember.ui.ListadoContactos.ListadoContactosFragment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

public class ContactosBD extends AsyncTask<String, Void, String> {


    private Contactos cont;
    private Pacientes pac;
    private String que_hacer;
    private Context context;
    private ProgressDialog dialog;
    private String mensaje_devuelto;
    private Session ses;
    private ListView lcontactos;
    private TextView no_hay;
    private Boolean no_hay_cont;
    private SearchView buscar;

    private static ArrayList<Contactos> listaContactos = new ArrayList<Contactos>();

    public ContactosBD(Contactos contac, Context ct, String que) {
        ses = new Session();
        ses.setCt(ct);
        ses.cargar_session();
        pac = new Pacientes();
        cont = new Contactos();
        this.cont = contac;
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
    }

    public ContactosBD(Context ct, String que, ListView lv, TextView tx, SearchView bus) {
        listaContactos.clear();
        ses = new Session();
        ses.setCt(ct);
        ses.cargar_session();
        //cont = new Contactos();
        pac = new Pacientes();
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
        lcontactos = lv;
        no_hay = tx;
        no_hay_cont = true;
        this.buscar = bus;
    }





    @Override
    protected String doInBackground(String... urls) {
        String response = "";
        mensaje_devuelto = "";
        PreparedStatement ps;

        if (que_hacer.equals("Insertar")) {

            boolean insertamos = true;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                ps = con.prepareStatement("INSERT INTO contactos (idpaciente, nombrecontacto, numero, " +
                        "estado) VALUES (?,?,?,?)");

                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery("SELECT idpaciente FROM pacientes p inner join usuarios u on p.idusuario = u.idusuario" +
                        " where u.idusuario=" + ses.getId_usuario());

                if (rs.next()) {
                    pac.setId_paciente(rs.getInt("idpaciente"));
                    cont.setId_paciente(pac);
                } else {

                    mensaje_devuelto = "Error buscando idpaciente!";
                }

                ps.setInt(1, cont.getId_paciente().getId_paciente());
                ps.setString(2, cont.getNombre_contacto());
                ps.setString(3, cont.getNumero());
                ps.setBoolean(4, cont.getEstado());


                rs = st.executeQuery("SELECT nombreContacto FROM contactos where numero ='" + cont.getNumero() + "' and idPaciente="
                        + cont.getId_paciente().getId_paciente());

                if (rs.next()) {

                    insertamos = false;
                    mensaje_devuelto = "El numero ya esta registrado en tu lista bajo el nombre de "+rs.getString(1)+"!";

                }

                rs = st.executeQuery("SELECT Count(idpaciente) FROM contactos where idpaciente =" + cont.getId_paciente().getId_paciente());

                if (rs.next()) {

                    if (rs.getInt(1) >= 10) {
                        insertamos = false;
                        mensaje_devuelto = "Alcanzaste el maximo de contactos en tu cuenta(10)!";
                    }

                }


                if (insertamos) {

                    int filas = ps.executeUpdate();

                    if (filas > 0) {
                        mensaje_devuelto = "Contacto registrado!";
                    } else
                        mensaje_devuelto = "Error al registrar contacto!";

                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al registrar contacto!!";
            }
        }

        if (que_hacer.equals("Modificar")) {

            boolean insertamos = true;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                ps = con.prepareStatement("UPDATE contactos set nombreContacto=?, numero=? where idContacto=?");

                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery("SELECT idpaciente FROM pacientes p inner join usuarios u on p.idusuario = u.idusuario" +
                        " where u.idusuario=" + ses.getId_usuario());

                if (rs.next()) {
                    pac.setId_paciente(rs.getInt("idpaciente"));
                    cont.setId_paciente(pac);
                } else {

                    mensaje_devuelto = "Error buscando idpaciente!";
                }

                ps.setString(1, cont.getNombre_contacto());
                ps.setString(2, cont.getNumero());
                ps.setInt(3, cont.getId_contacto());


                rs = st.executeQuery("SELECT nombreContacto FROM contactos where numero ='" + cont.getNumero() + "' and idPaciente="
                        + cont.getId_paciente().getId_paciente() + " and idContacto <>"+ cont.getId_contacto());

                if (rs.next()) {

                    insertamos = false;
                    mensaje_devuelto = "El numero ya esta registrado en tu lista bajo el nombre de "+rs.getString(1)+"!";

                }

                if (insertamos) {

                    int filas = ps.executeUpdate();

                    if (filas > 0) {
                        mensaje_devuelto = "Contacto actualizado!";
                    } else
                        mensaje_devuelto = "Error al actualizar contacto!";

                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al actualizar contacto!!";
            }
        }

        if (que_hacer.equals("Eliminar")) {


            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                ps = con.prepareStatement("Delete from contactos where idContacto=?");


                ps.setInt(1, cont.getId_contacto());


                    int filas = ps.executeUpdate();

                    if (filas > 0) {
                        mensaje_devuelto = "Contacto eliminado!";
                    } else
                        mensaje_devuelto = "Error al eliminar contacto!";


                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al eliminar contacto!!";
            }
        }

        if (que_hacer.equals("Listar")) {

            String result2;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery("SELECT p.idpaciente FROM  pacientes p inner join usuarios u on p.idusuario = u.idusuario" +
                        " where u.idusuario=" + ses.getId_usuario());

                if(rs.next()){

                    pac.setId_paciente(rs.getInt(1));

                }

                rs = st.executeQuery("SELECT * FROM contactos where idpaciente=" + pac.getId_paciente());

                result2 = " ";

                while (rs.next()) {
                    no_hay_cont = false;
                    cont = new Contactos();
                    cont.setId_contacto(rs.getInt("idContacto"));
                    cont.setNombre_contacto(rs.getString("nombreContacto"));
                    cont.setNumero(rs.getString("numero"));
                    cont.setEstado(rs.getBoolean("estado"));
                    listaContactos.add(cont);

            }


                response = "Conexion exitosa";
                con.close();
        } catch(Exception e){
            e.printStackTrace();
            result2 = "Conexion no exitosa";
        }


        }

        return response;


    }


    @Override
    protected void onPreExecute() {
        dialog.show();
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Override
    protected void onPostExecute(String response) {

        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        if(que_hacer.equals("Insertar"))
        {
            Toast.makeText(context,mensaje_devuelto,Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, new ListadoContactosFragment()).commit();
        }

        if(que_hacer.equals("Modificar") || que_hacer.equals("Eliminar")){
            Toast.makeText(context,mensaje_devuelto,Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, new ListadoContactosFragment()).commit();

        }


        if(que_hacer.equals("Listar")) {
            final AdaptadorContactos adapter = new AdaptadorContactos(context, listaContactos);
            lcontactos.setAdapter(adapter);

            buscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

            if(no_hay_cont) {
                no_hay.setVisibility(View.VISIBLE);
                lcontactos.setVisibility(View.GONE);
            }
            else {
                no_hay.setVisibility(View.GONE);
                lcontactos.setVisibility(View.VISIBLE);
            }
        }

    }


}

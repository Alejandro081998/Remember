package com.frgp.remember.Base.ListaNegra;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.frgp.remember.Adaptadores.AdaptadorListaNegra;
import com.frgp.remember.Base.Data.DatosBD;
import com.frgp.remember.Base.LogsUsuariosBD.LogsBD;
import com.frgp.remember.Entidades.Estados;
import com.frgp.remember.Entidades.ListaNegra;
import com.frgp.remember.Entidades.LogsUsuarios;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.Entidades.Vinculaciones;
import com.frgp.remember.R;
import com.frgp.remember.Session.Session;
import com.frgp.remember.ui.ListaNegra.ListaNegraFragment;
import com.frgp.remember.ui.Perfil.PerfilDetalle.PerfilDetalleFragment;
import com.mysql.jdbc.log.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ListaNegraBD extends AsyncTask<String, Void, String> {


    private Usuarios user,user2;
    private Estados est;
    private Vinculaciones vin;
    private ListaNegra list;
    private String user_destinat;
    private String que_hacer;
    private Context context;
    private ProgressDialog dialog;
    private String mensaje_devuelto;
    private Session ses;
    private ListView lnegra;
    private TextView no_hay;
    private Boolean no_hay_lista;
    private SearchView buscar;



    private static ArrayList<ListaNegra> listaNegra = new ArrayList<ListaNegra>();

    public ListaNegraBD(ListaNegra list, Usuarios us, Context ct, String que) {
        ses = new Session();
        ses.setCt(ct);
        ses.cargar_session();
        this.user = new Usuarios();
        this.user = us;
        this.list = new ListaNegra();
        this.list = list;
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
        this.vin = new Vinculaciones();
        this.est = new Estados();
    }

    public ListaNegraBD(Context ct, String que, ListView lv, TextView tx, SearchView bus) {

        listaNegra.clear();
        ses = new Session();
        ses.setCt(ct);
        ses.cargar_session();
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
        lnegra = lv;
        no_hay = tx;
        no_hay_lista = true;
        this.buscar = bus;
        this.vin = new Vinculaciones();
        this.est = new Estados();
    }





    @Override
    protected String doInBackground(String... urls) {
        String response = "";
        mensaje_devuelto = "";
        PreparedStatement ps;

        if (que_hacer.equals("Bloquear")) {

            boolean insertamos = true;
            boolean vinculado = false;
            boolean pendiente = false;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);

                ps = con.prepareStatement("INSERT INTO listanegra (idUsuario, idUserBloqueado) VALUES (?,?)");

                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery("SELECT * FROM  listanegra where idUsuario=" + ses.getId_usuario() + " and idUserBloqueado=" +
                         user.getId_usuario());

                if(rs.next()){

                    insertamos = false;

                }

                if(insertamos) {

                    ps.setInt(1, ses.getId_usuario());
                    ps.setInt(2, user.getId_usuario());


                    int filas = ps.executeUpdate();

                    if (filas > 0) {
                        mensaje_devuelto = "Usuario bloqueado!";


                        rs = st.executeQuery("SELECT usuario from usuarios where idusuario=" + user.getId_usuario());

                        if (rs.next()) {
                            user_destinat = rs.getString(1);
                        } else
                            mensaje_devuelto = "Error al buscar usuario para log";


                        rs = st.executeQuery("SELECT idestado from estados where estado='Activa'");

                        if(rs.next())
                            est.setId_estado(rs.getInt("idestado"));
                        else
                            mensaje_devuelto = "Error al buscar estado";

                        rs = st.executeQuery("SELECT * from vinculaciones where idSupervisor=" + user.getId_usuario() + " and idPaciente=" +
                                ses.getId_usuario() + " and estadoVinculacion=" + est.getId_estado());
                        if(rs.next()) {
                            //mensaje_devuelto = "Ya te encuentras vinculado a este usuario!";
                            vinculado = true;
                            vin.setId_vinculacion(rs.getInt("idVinculacion"));
                        }
                        else
                            rs = st.executeQuery("SELECT * from vinculaciones where idPaciente=" + user.getId_usuario() + " and idSupervisor=" +
                                    ses.getId_usuario() + " and estadoVinculacion=" + est.getId_estado());
                        if(rs.next()){
                            //mensaje_devuelto = "Ya te encuentras vinculado a este usuario!";
                            vinculado = true;
                            vin.setId_vinculacion(rs.getInt("idVinculacion"));
                        }


                        rs = st.executeQuery("SELECT idestado from estados where estado='Pendiente'");

                        if(rs.next())
                            est.setId_estado(rs.getInt("idestado"));
                        else
                            mensaje_devuelto = "Error al buscar estado";

                        rs = st.executeQuery("SELECT * from vinculaciones where idSupervisor=" + user.getId_usuario() + " and" +
                                " idPaciente=" + ses.getId_usuario() + " and estadoVinculacion=" + est.getId_estado());

                        if(rs.next()){
                            pendiente = true;
                            vin.setId_vinculacion(rs.getInt("idVinculacion"));
                        }
                        else {
                            rs = st.executeQuery("SELECT * from vinculaciones where idSupervisor=" + ses.getId_usuario() + " and" +
                                    " idPaciente=" + user.getId_usuario() + " and estadoVinculacion=" + est.getId_estado());

                            if(rs.next()){
                                pendiente = true;
                                vin.setId_vinculacion(rs.getInt("idVinculacion"));
                            }

                        }

                        if(pendiente){

                            ps = con.prepareStatement("UPDATE vinculaciones SET estadoVinculacion=?,estadoSolicitud=?" +
                                    " where idVinculacion=?");

                            rs = st.executeQuery("SELECT * FROM estados where estado ='Rechazada'");

                            if(rs.next())
                                est.setId_estado(rs.getInt("idestado"));

                            ps.setInt(1, est.getId_estado());
                            ps.setInt(2, est.getId_estado());
                            ps.setInt(3, vin.getId_vinculacion());

                            int filas_ = ps.executeUpdate();

                        }


                        if(vinculado){

                            ps = con.prepareStatement("UPDATE vinculaciones SET estadoVinculacion=?" +
                                    " where idVinculacion=?");

                            rs = st.executeQuery("SELECT * FROM estados where estado ='Suspendida'");

                            if(rs.next())
                                est.setId_estado(rs.getInt("idestado"));

                                ps.setInt(1, est.getId_estado());
                                ps.setInt(2, vin.getId_vinculacion());

                                int filas_ = ps.executeUpdate();
                        }




                    } else
                        mensaje_devuelto = "Error al bloquear usuario!";

                }
                else
                    mensaje_devuelto = "El usuario ya se encuentra bloqueado";

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al bloquear usuario!";
            }
        }

        if (que_hacer.equals("Desbloquear") || que_hacer.equals("DesbloquearId")) {

            boolean insertamos = true;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);


                if(que_hacer.equals("Desbloquear")) {

                    ps = con.prepareStatement("Delete from listanegra where idBloqueo=?");

                    ps.setInt(1, list.getId_bloqueo());

                }else{

                    ps = con.prepareStatement("Delete from listanegra where idUsuario=? and idUserBloqueado=?");

                    ps.setInt(1, ses.getId_usuario());
                    ps.setInt(2,user.getId_usuario());

                }

                Statement st = con.createStatement();
                ResultSet rs;

                rs = st.executeQuery("SELECT * FROM  listanegra where idUsuario=" + ses.getId_usuario() + " and idUserBloqueado=" +
                        user.getId_usuario());

                if(!rs.next()){

                    insertamos = false;

                }



                if(insertamos) {
                    int filas = ps.executeUpdate();

                    if (filas > 0) {
                        mensaje_devuelto = "Usuario desbloqueado!";


                        rs = st.executeQuery("SELECT usuario from usuarios where idusuario=" + user.getId_usuario());

                        if (rs.next()) {
                            user_destinat = rs.getString(1);
                        } else
                            mensaje_devuelto = "Error al buscar usuario para log";


                    } else
                        mensaje_devuelto = "Error al desbloquear usuario!";

                }
                else{

                    mensaje_devuelto = "No te encuentras bloqueado a este usuario!";

                }


                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al desbloquear usuario!";
            }
        }

        if (que_hacer.equals("Listar")) {

            String result2;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                Statement st = con.createStatement();
                ResultSet rs;

                Statement st_ = con.createStatement();
                ResultSet rs_;

                rs = st.executeQuery("SELECT * FROM  listanegra where idUsuario=" + ses.getId_usuario());


                while(rs.next()){

                    no_hay_lista = false;

                    list = new ListaNegra();
                    user = new Usuarios();
                    user2 = new Usuarios();

                    rs_ = st_.executeQuery("SELECT nombre,apellido FROM usuarios where idUsuario=" + rs.getInt("idUserBloqueado"));

                    if(rs_.next()){

                        user2.setNombre(rs_.getString("nombre"));
                        user2.setApellido(rs_.getString("apellido"));
                    }

                    list.setId_bloqueo(rs.getInt("idBloqueo"));
                    user.setId_usuario(rs.getInt("idUsuario"));
                    user2.setId_usuario(rs.getInt("idUserBloqueado"));
                    list.setId_usuario(user);
                    list.setId_user_bloqueado(user2);

                    listaNegra.add(list);


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

        if(que_hacer.equals("Bloquear") || que_hacer.equals("Desbloquear") || que_hacer.equals("DesbloquearId"))
        {


            Fragment currentFragment_ = ((AppCompatActivity)context).getSupportFragmentManager().findFragmentById(R.id.content_main);

            if(currentFragment_ instanceof PerfilDetalleFragment){

                Bundle datosAEnviar = new Bundle();
                datosAEnviar.putInt("usuario_perfil", user.getId_usuario());

                Fragment fragmento = new PerfilDetalleFragment();
                fragmento.setArguments(datosAEnviar);
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main, fragmento).commit();

                if(ses.getTipo_rol().equals("Paciente")){

                    if(que_hacer.equals("Bloquear")) {

                        LogsUsuarios log = new LogsUsuarios();
                        log.setLogdesc("" + ses.getUsuario() + " ha bloqueado al usuario '" + user_destinat + "'");
                        LogsBD userlog = new LogsBD(context, log, "NuevoLog");
                        userlog.execute();
                    }

                    if(que_hacer.equals("Desbloquear") || que_hacer.equals("DesbloquearId") ) {

                        LogsUsuarios log = new LogsUsuarios();
                        log.setLogdesc("" + ses.getUsuario() + " ha desbloqueado al usuario '" + user_destinat + "'");
                        LogsBD userlog = new LogsBD(context, log, "NuevoLog");
                        userlog.execute();
                    }

                }

                Toast.makeText(context, mensaje_devuelto, Toast.LENGTH_SHORT).show();
            }
            else {

                if(ses.getTipo_rol().equals("Paciente")) {

                    if (que_hacer.equals("Desbloquear") || que_hacer.equals("DesbloquearId")) {

                        LogsUsuarios log = new LogsUsuarios();
                        log.setLogdesc("" + ses.getUsuario() + " ha desbloqueado al usuario '" + user_destinat + "'");
                        LogsBD userlog = new LogsBD(context, log, "NuevoLog");
                        userlog.execute();
                    }
                }

                Toast.makeText(context, mensaje_devuelto, Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main, new ListaNegraFragment()).commit();
            }
        }

        if(que_hacer.equals("Listar")) {
            final AdaptadorListaNegra adapter = new AdaptadorListaNegra(context, listaNegra);
            lnegra.setAdapter(adapter);

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

            if(no_hay_lista) {
                no_hay.setVisibility(View.VISIBLE);
                lnegra.setVisibility(View.GONE);
            }
            else {
                no_hay.setVisibility(View.GONE);
                lnegra.setVisibility(View.VISIBLE);
            }
        }

    }


}
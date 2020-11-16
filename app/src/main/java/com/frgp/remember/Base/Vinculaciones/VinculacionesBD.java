package com.frgp.remember.Base.Vinculaciones;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.frgp.remember.Adaptadores.AdaptadorNuevaVinculacion;
import com.frgp.remember.Adaptadores.AdaptadorUsuariosVinculaciones;
import com.frgp.remember.Base.Data.DatosBD;
import com.frgp.remember.Base.LogsUsuariosBD.LogsBD;
import com.frgp.remember.Base.Notificaciones.NotificacionesBD;
import com.frgp.remember.Entidades.Apartados;
import com.frgp.remember.Entidades.Estados;
import com.frgp.remember.Entidades.Familiares;
import com.frgp.remember.Entidades.LogsUsuarios;
import com.frgp.remember.Entidades.Notificaciones;
import com.frgp.remember.Entidades.Pacientes;
import com.frgp.remember.Entidades.Profesionales;
import com.frgp.remember.Entidades.TipoRol;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.Entidades.Vinculaciones;
import com.frgp.remember.Principal.MainActivity;
import com.frgp.remember.R;
import com.frgp.remember.Session.Session;
import com.frgp.remember.ui.ListadoContactos.ListadoContactosFragment;
import com.frgp.remember.ui.Perfil.PerfilDetalle.PerfilDetalleFragment;
import com.frgp.remember.ui.Seguimiento.Detalle.DetallePerfilFragment;
import com.frgp.remember.ui.Vinculaciones.Listado.ListadoVinculacionesFragment;
import com.frgp.remember.ui.Vinculaciones.ListadoProfesional.VinculacionesProfesionalFragment;
import com.frgp.remember.ui.Vinculaciones.NuevaVinculacion.VinculacionesFragment;
import com.frgp.remember.ui.Vinculaciones.Pendientes.VinculacionesPendientesFragment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

//import frgp.utn.edu.ar.consultas_mysql.adapter.ClienteAdapter;
//import frgp.utn.edu.ar.consultas_mysql.entidad.Cliente;


public class VinculacionesBD extends AsyncTask<String, Void, String> {


    private Vinculaciones vin;
    private Apartados apart;
    private Estados est_solicitud;
    private Estados est_vinculacion;
    private TipoRol tipo;
    private Notificaciones noti;
    private Usuarios usu, user_destinatario;
    private String que_hacer;
    private Context context;
    private String user_destinat;
    private ProgressDialog dialog;
    private String mensaje_devuelto;
    private Session ses;
    private TipoRol tipo_envia;
    private ListView list_pendientes;
    private TextView no_hay;
    private boolean no_hay_pendiente;
    private boolean no_hay_medicos;
    private boolean no_hay_familiares;
    private boolean no_hay_pacientes;
    private ListView list_pacientes;
    private ListView list_medicos;
    private ListView list_familiares;
    private TextView no_hay_familiares_text;
    private TextView no_hay_pacientes_text;
    private TextView no_hay_medicos_text;
    private SearchView busqueda_vinculado;

    private static ArrayList<Vinculaciones> listaPendientes = new ArrayList<Vinculaciones>();
    private static ArrayList<Vinculaciones> listaFamiliares = new ArrayList<Vinculaciones>();
    private static ArrayList<Vinculaciones> listaPacientes = new ArrayList<Vinculaciones>();
    private static ArrayList<Vinculaciones> listaMedicos = new ArrayList<Vinculaciones>();

    public VinculacionesBD(Usuarios us, Context ct, String que) {

        usu = new Usuarios();
        tipo_envia = new TipoRol();
        this.user_destinatario = new Usuarios();
        this.noti = new Notificaciones();
        this.apart = new Apartados();
        ses = new Session();
        est_solicitud = new Estados();
        est_vinculacion = new Estados();
        ses.setCt(ct);
        ses.cargar_session();
        this.usu = us;
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
        this.no_hay_pendiente = true;
        this.vin = new Vinculaciones();
    }

    public VinculacionesBD(Context ct, String que, ListView lv, TextView tx) {

        usu = new Usuarios();
        tipo_envia = new TipoRol();
        ses = new Session();
        est_solicitud = new Estados();
        est_vinculacion = new Estados();
        ses.setCt(ct);
        ses.cargar_session();
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
        this.no_hay_pendiente = true;
        this.no_hay = tx;
        this.list_pendientes = lv;
        this.tipo = new TipoRol();
    }


    public VinculacionesBD(Vinculaciones v, Context ct, String que) {

        ses = new Session();
        est_solicitud = new Estados();
        est_vinculacion = new Estados();
        ses.setCt(ct);
        ses.cargar_session();
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
        this.vin = v;
        this.user_destinatario = new Usuarios();
        this.noti = new Notificaciones();
        this.apart = new Apartados();
    }

    public VinculacionesBD(Context ct, String que, ListView lv_pac, ListView lv_med, ListView lv_fami,
                           TextView pac,TextView med,TextView fami, SearchView busqueda_vinculado) {
        usu = new Usuarios();
        ses = new Session();
        ses.setCt(ct);
        ses.cargar_session();
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
        no_hay_pacientes = true;
        no_hay_familiares = true;
        no_hay_medicos = true;
        this.tipo = new TipoRol();
        this.list_familiares = lv_fami;
        this.list_pacientes = lv_pac;
        this.list_medicos = lv_med;
        this.no_hay_familiares_text = fami;
        this.no_hay_medicos_text = med;
        this.no_hay_pacientes_text = pac;
        this.busqueda_vinculado = busqueda_vinculado;
    }







    @Override
    protected String doInBackground(String... urls) {
        String response = "";
        mensaje_devuelto = "";
        PreparedStatement ps;
        PreparedStatement ps_aux = null;
        //String estado = "";


        if(que_hacer.equals("Insertar")) {

            boolean insertamos = true;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                //ps = con.prepareStatement("INSERT INTO usuarios(id, nombre,stock,alertastock,idCategoria) VALUES(?,?,?,?,?)");

                ResultSet rs;
                Statement st = con.createStatement();

                rs = st.executeQuery("SELECT t.idtiporol,t.rol from usuarios u inner join tiporol t on t.idtiporol = u.tiporol where " +
                                "u.idusuario=" + usu.getId_usuario());

                if(rs.next())
                {
                    tipo_envia.setId_rol(rs.getInt("idtiporol"));
                    tipo_envia.setRol(rs.getString("rol"));

                }
                else{

                    mensaje_devuelto = "Error al buscar rol";
                }


                rs = st.executeQuery("SELECT idestado from estados where estado='Activa'");

                if(rs.next())
                    est_vinculacion.setId_estado(rs.getInt("idestado"));
                else
                    mensaje_devuelto = "Error al buscar estado";

                rs = st.executeQuery("SELECT * from vinculaciones where idSupervisor=" + usu.getId_usuario() + " and idPaciente=" +
                        ses.getId_usuario() + " and estadoVinculacion=" + est_vinculacion.getId_estado());
                if(rs.next()) {
                    mensaje_devuelto = "Ya te encuentras vinculado a este usuario!";
                    insertamos = false;
                }
                else
                    rs = st.executeQuery("SELECT * from vinculaciones where idPaciente=" + usu.getId_usuario() + " and idSupervisor=" +
                            ses.getId_usuario() + " and estadoVinculacion=" + est_vinculacion.getId_estado());
                if(rs.next()){
                    mensaje_devuelto = "Ya te encuentras vinculado a este usuario!";
                    insertamos = false;
                }

                rs = st.executeQuery("SELECT * FROM  listaNegra where idUsuario=" + ses.getId_usuario() + " and idUserBloqueado=" +
                        usu.getId_usuario());

                if(rs.next()){
                    mensaje_devuelto = "No puedes vincularte ya que has bloqueado a este usuario!";
                    insertamos = false;

                }
                else{

                    rs = st.executeQuery("SELECT * FROM  listaNegra where idUsuario=" + usu.getId_usuario() + " and idUserBloqueado=" +
                            ses.getId_usuario());

                    if(rs.next()){
                        mensaje_devuelto = "No puedes vincularte porque que el usuario te ha bloqueado!";
                        insertamos = false;

                    }
                }




                rs = st.executeQuery("SELECT idestado from estados where estado='Enviada'");

                if(rs.next())
                    est_solicitud.setId_estado(rs.getInt("idestado"));
                else
                    mensaje_devuelto = "Error al buscar estado";


                rs = st.executeQuery("SELECT * from vinculaciones where idSupervisor=" + usu.getId_usuario() + " and idPaciente=" +
                                ses.getId_usuario() + " and estadoSolicitud=" + est_solicitud.getId_estado());
                if(rs.next()) {
                    mensaje_devuelto = "Ya existe solicitud pendiente de aprobacion con este usuario!";
                    insertamos = false;
                }
                else
                    rs = st.executeQuery("SELECT * from vinculaciones where idPaciente=" + usu.getId_usuario() + " and idSupervisor=" +
                            ses.getId_usuario() + " and estadoSolicitud=" + est_solicitud.getId_estado());
                if(rs.next()){
                    mensaje_devuelto = "Ya existe solicitud pendiente de aprobacion con este usuario!";
                    insertamos = false;
                }


                if(insertamos) {

                    rs = st.executeQuery("SELECT idestado from estados where estado='Pendiente'");

                    if(rs.next())
                        est_vinculacion.setId_estado(rs.getInt("idestado"));
                    else
                        mensaje_devuelto = "Error al buscar estado";



                    ps = con.prepareStatement("INSERT INTO vinculaciones (idSupervisor, idPaciente, estadoSolicitud, " +
                            "estadoVinculacion,creadoPor) VALUES (?,?,?,?,?)");


                    ps.setInt(3, est_solicitud.getId_estado());
                    ps.setInt(4, est_vinculacion.getId_estado());
                    ps.setInt(5,ses.getId_usuario());

                    if (tipo_envia.getRol().equals("Paciente")) {
                        ps.setInt(2, usu.getId_usuario());
                        ps.setInt(1, ses.getId_usuario());
                    } else {
                        ps.setInt(1, usu.getId_usuario());
                        ps.setInt(2, ses.getId_usuario());
                    }

                    int filas = ps.executeUpdate();

                    if (filas > 0) {
                        mensaje_devuelto = "Solicitud enviada con exito, debes aguardar aprobacion!";


                        if(ses.getTipo_rol().equals("Paciente")){

                            rs = st.executeQuery("select idVinculacion from vinculaciones where idpaciente=" + ses.getId_usuario() +
                                    " order by idvinculacion desc limit 1");

                            if(rs.next())
                            {
                                vin.setId_vinculacion(rs.getInt(1));
                            }

                            rs = st.executeQuery("SELECT * FROM vinculaciones where idVinculacion=" + vin.getId_vinculacion());

                            if (rs.next()) {

                                if (rs.getInt("idSupervisor") == ses.getId_usuario())
                                    user_destinatario.setId_usuario(rs.getInt("idPaciente"));
                                else if (rs.getInt("idSupervisor") != ses.getId_usuario())
                                    user_destinatario.setId_usuario(rs.getInt("idSupervisor"));

                            }


                            rs = st.executeQuery("SELECT usuario from usuarios where idusuario=" + user_destinatario.getId_usuario());

                            if (rs.next()) {
                                user_destinat = rs.getString(1);
                            } else
                                mensaje_devuelto = "Error al buscar usuario para log";


                        }


                        apart.setDescripcion("Vinculaciones Pendientes");
                        user_destinatario.setId_usuario(usu.getId_usuario());

                        rs = st.executeQuery("SELECT nombre,apellido from usuarios where idusuario=" + ses.getId_usuario());

                        if(rs.next()) {
                            noti.setDescripcion("" + rs.getString("nombre") + " " + rs.getString("apellido") + " te envió una solicitud " +
                                    "de vinculación");
                        }
                        else
                            mensaje_devuelto = "Error al buscar usuario para notificacion";




                    }
                    else
                        mensaje_devuelto = "Error al enviar la solicitud, intenta luego!";

                }
                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al enviar solicitud!";
            }
        }

        if(que_hacer.equals("Pendientes")) {

            boolean insertamos = true;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                //ps = con.prepareStatement("INSERT INTO usuarios(id, nombre,stock,alertastock,idCategoria) VALUES(?,?,?,?,?)");

                ResultSet rs;
                Statement st = con.createStatement();
                ResultSet rs_;



                if (ses.getTipo_rol().equals("Paciente")) {

                    rs = st.executeQuery("SELECT * from estados where estado = 'Enviada'");

                    if (rs.next())
                        rs = st.executeQuery("SELECT u.nombre,u.apellido,v.idVinculacion,v.idSupervisor,t.rol,u.idusuario from usuarios u inner join vinculaciones v on" +
                                " v.idsupervisor = u.idusuario inner join tiporol t on t.idtiporol = u.tiporol" +
                                " where v.creadoPor<>" + ses.getId_usuario() + " and v.estadoSolicitud=" + rs.getInt("idestado") + " and" +
                                " idPaciente=" + ses.getId_usuario());

                    listaPendientes.clear();



                    while (rs.next()) {

                        usu = new Usuarios();
                        vin = new Vinculaciones();
                        tipo = new TipoRol();

                        //if(rs_.next())
                            tipo.setRol(rs.getString(5));

                            Log.d("TIPO",rs.getString(5));

                        no_hay_pendiente = false;

                        usu.setId_usuario(rs.getInt(6));
                        usu.setTipo(tipo);
                        usu.setNombre(rs.getString(1));
                        usu.setApellido(rs.getString(2));
                        vin.setId_vinculacion(rs.getInt(3));
                        vin.setId_supervisor(usu);

                        listaPendientes.add(vin);
                        //listaUsuarios.add(user);

                    }


                } else {

                    rs = st.executeQuery("SELECT * from estados where estado = 'Enviada'");

                    if (rs.next())
                        rs = st.executeQuery("SELECT u.nombre,u.apellido,v.idVinculacion,u.idusuario from usuarios u inner join vinculaciones v on v.idPaciente = u.idusuario" +
                                " where v.creadoPor<>" + ses.getId_usuario() + " and v.estadoSolicitud=" + rs.getInt("idestado") + " and" +
                                " idSupervisor=" + ses.getId_usuario());

                    listaPendientes.clear();

                    while (rs.next()) {
                        no_hay_pendiente = false;
                        usu = new Usuarios();
                        vin = new Vinculaciones();
                        usu.setId_usuario(rs.getInt(4));
                        usu.setNombre(rs.getString(1));
                        usu.setApellido(rs.getString(2));
                        vin.setId_vinculacion(rs.getInt(3));
                        vin.setId_paciente(usu);

                        listaPendientes.add(vin);
                        //listaUsuarios.add(usuarios_);

                    }

                }
                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al buscar vinculaciones pendientes!";
            }
        }

        if(que_hacer.equals("Aceptar") || que_hacer.equals("Rechazar")){

            boolean insertamos = true;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                //ps = con.prepareStatement("INSERT INTO usuarios(id, nombre,stock,alertastock,idCategoria) VALUES(?,?,?,?,?)");
                ps = con.prepareStatement("UPDATE vinculaciones SET estadoSolicitud=?, estadoVinculacion=?" +
                        " where idVinculacion=?");

                Statement st = con.createStatement();
                ResultSet rs;


                rs = st.executeQuery("SELECT * FROM vinculaciones where idVinculacion=" + vin.getId_vinculacion());

                if (rs.next()) {

                    if (rs.getInt("idSupervisor") == ses.getId_usuario())
                        user_destinatario.setId_usuario(rs.getInt("idPaciente"));
                    else if (rs.getInt("idSupervisor") != ses.getId_usuario())
                        user_destinatario.setId_usuario(rs.getInt("idSupervisor"));

                }


                rs = st.executeQuery("SELECT usuario from usuarios where idusuario=" + user_destinatario.getId_usuario());

                if (rs.next()) {
                    user_destinat = rs.getString(1);
                } else
                    mensaje_devuelto = "Error al buscar usuario para log";



                if(que_hacer.equals("Aceptar")){

                    rs = st.executeQuery("SELECT * FROM estados where estado ='Aceptada'");

                    if(rs.next())
                        est_solicitud.setId_estado(rs.getInt("idestado"));

                    rs = st.executeQuery("SELECT * FROM estados where estado ='Activa'");

                    if(rs.next())
                        est_vinculacion.setId_estado(rs.getInt("idestado"));


                }else{

                    rs = st.executeQuery("SELECT * FROM estados where estado ='Rechazada'");

                    if(rs.next()) {
                        est_solicitud.setId_estado(rs.getInt("idestado"));
                        est_vinculacion.setId_estado(rs.getInt("idestado"));
                    }
                }

                ps.setInt(1,est_solicitud.getId_estado());
                ps.setInt(2,est_vinculacion.getId_estado());
                ps.setInt(3,vin.getId_vinculacion());


                if(ses.getTipo_rol().equals("Paciente")){

                    rs = st.executeQuery("SELECT * FROM estados where estado ='Activa'");

                    if(rs.next())
                        est_solicitud.setId_estado(rs.getInt("idestado"));

                    if(que_hacer.equals("Aceptar")) {

                        if(rs.next())
                            rs = st.executeQuery("SELECT count(*) FROM vinculaciones where idPaciente =" + rs.getInt("idPaciente") + " and" +
                                    " estadoVinculacion=" + est_vinculacion.getId_estado());

                        if (rs.next())
                            if (rs.getInt(1) == 10) {
                                insertamos = false;
                                mensaje_devuelto = "El paciente alcanzo su maximo de vinculaciones(10)!";
                            }
                    }

                }else{

                    rs = st.executeQuery("SELECT * FROM estados where estado ='Activa'");

                    if(rs.next())
                        est_solicitud.setId_estado(rs.getInt("idestado"));

                    rs = st.executeQuery("SELECT * FROM vinculaciones where idVinculacion =" + vin.getId_vinculacion());


                    if(que_hacer.equals("Aceptar")) {

                    if(rs.next())
                    rs = st.executeQuery("SELECT count(*) FROM vinculaciones where idPaciente =" + rs.getInt("idPaciente") + " and" +
                            " estadoVinculacion=" + est_vinculacion.getId_estado());

                        if (rs.next())
                            if (rs.getInt(1) == 10) {
                                insertamos = false;
                                mensaje_devuelto = "El paciente alcanzo su maximo de vinculaciones(10)!";
                            }
                    }

                }




                if(insertamos){


                    int filas = ps.executeUpdate();

                    if(filas > 0) {
                        if (que_hacer.equals("Aceptar")) {
                            mensaje_devuelto = "Solicitud aceptada con exito!";


                            apart.setDescripcion("Vinculaciones Existentes");

                            rs = st.executeQuery("SELECT * FROM vinculaciones where idVinculacion=" + vin.getId_vinculacion());

                            if(rs.next()) {

                                if(rs.getInt("idSupervisor")== ses.getId_usuario())
                                    user_destinatario.setId_usuario(rs.getInt("idPaciente"));
                                else if(rs.getInt("idSupervisor") != ses.getId_usuario())
                                    user_destinatario.setId_usuario(rs.getInt("idSupervisor"));

                            }


                            rs = st.executeQuery("SELECT nombre,apellido from usuarios where idusuario=" + ses.getId_usuario());

                            if(rs.next()) {
                                noti.setDescripcion("" + rs.getString("nombre") + " " + rs.getString("apellido") + " ha aceptado tu solicitud " +
                                        "de vinculación");
                            }
                            else
                                mensaje_devuelto = "Error al buscar usuario para notificacion";


                        }
                        else {
                            mensaje_devuelto = "Solicitud rechazada!";


                            apart.setDescripcion("Vinculaciones Existentes");

                            rs = st.executeQuery("SELECT * FROM vinculaciones where idVinculacion=" + vin.getId_vinculacion());

                            if(rs.next()) {

                                if(rs.getInt("idSupervisor")== ses.getId_usuario())
                                    user_destinatario.setId_usuario(rs.getInt("idPaciente"));
                                else if(rs.getInt("idSupervisor") != ses.getId_usuario())
                                    user_destinatario.setId_usuario(rs.getInt("idSupervisor"));

                            }


                            rs = st.executeQuery("SELECT nombre,apellido from usuarios where idusuario=" + ses.getId_usuario());

                            if(rs.next()) {
                                noti.setDescripcion("" + rs.getString("nombre") + " " + rs.getString("apellido") + " ha rechazado tu solicitud " +
                                        "de vinculación");
                            }
                            else
                                mensaje_devuelto = "Error al buscar usuario para notificacion";


                        }
                    }
                    else
                        mensaje_devuelto = "Error al registrar la solicitud!";

                    }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al registrar vinculacion!";
            }
        }

        if(que_hacer.equals("Desvincularse") || que_hacer.equals("DesvincularseId")){

            boolean insertamos = false;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                //ps = con.prepareStatement("INSERT INTO usuarios(id, nombre,stock,alertastock,idCategoria) VALUES(?,?,?,?,?)");
                ps = con.prepareStatement("UPDATE vinculaciones SET estadoVinculacion=?" +
                        " where idVinculacion=?");

                Statement st = con.createStatement();
                ResultSet rs;


                    rs = st.executeQuery("SELECT * FROM estados where estado ='Suspendida'");

                    if(rs.next())
                        est_vinculacion.setId_estado(rs.getInt("idestado"));


                    if(que_hacer.equals("Desvincularse")) {

                        ps.setInt(1, est_vinculacion.getId_estado());
                        ps.setInt(2, vin.getId_vinculacion());
                        insertamos = true;
                    }
                    else{

                        rs = st.executeQuery("SELECT idestado from estados where estado='Activa'");

                        if(rs.next())
                            est_vinculacion.setId_estado(rs.getInt("idestado"));
                        else
                            mensaje_devuelto = "Error al buscar estado";

                        rs = st.executeQuery("SELECT * from vinculaciones where idSupervisor=" + usu.getId_usuario() + " and idPaciente=" +
                                ses.getId_usuario() + " and estadoVinculacion=" + est_vinculacion.getId_estado());
                        if(rs.next()) {
                            //mensaje_devuelto = "Ya te encuentras vinculado a este usuario!";
                            insertamos = true;
                            vin.setId_vinculacion(rs.getInt("idVinculacion"));
                        }
                        else
                            rs = st.executeQuery("SELECT * from vinculaciones where idPaciente=" + usu.getId_usuario() + " and idSupervisor=" +
                                    ses.getId_usuario() + " and estadoVinculacion=" + est_vinculacion.getId_estado());
                        if(rs.next()){
                            //mensaje_devuelto = "Ya te encuentras vinculado a este usuario!";
                            insertamos = true;
                            vin.setId_vinculacion(rs.getInt("idVinculacion"));
                        }

                        if(insertamos){

                            rs = st.executeQuery("SELECT * FROM estados where estado ='Suspendida'");

                            if(rs.next())
                                est_vinculacion.setId_estado(rs.getInt("idestado"));

                            ps.setInt(1, est_vinculacion.getId_estado());
                            ps.setInt(2, vin.getId_vinculacion());

                        }


                    }

                if(insertamos) {

                    int filas = ps.executeUpdate();

                    if (filas > 0) {
                        mensaje_devuelto = "Desvinculacion exitosa!";


                        apart.setDescripcion("Vinculaciones Existentes");

                        rs = st.executeQuery("SELECT * FROM vinculaciones where idVinculacion=" + vin.getId_vinculacion());

                        if (rs.next()) {

                            if (rs.getInt("idSupervisor") == ses.getId_usuario())
                                user_destinatario.setId_usuario(rs.getInt("idPaciente"));
                            else if (rs.getInt("idSupervisor") != ses.getId_usuario())
                                user_destinatario.setId_usuario(rs.getInt("idSupervisor"));

                        }


                        rs = st.executeQuery("SELECT usuario from usuarios where idusuario=" + user_destinatario.getId_usuario());

                        if (rs.next()) {
                            user_destinat = rs.getString(1);
                        } else
                            mensaje_devuelto = "Error al buscar usuario para log";



                        rs = st.executeQuery("SELECT nombre,apellido from usuarios where idusuario=" + ses.getId_usuario());

                        if (rs.next()) {
                            noti.setDescripcion("" + rs.getString("nombre") + " " + rs.getString("apellido") + " se ha desvinculado de ti");
                        } else
                            mensaje_devuelto = "Error al buscar usuario para notificacion";


                    } else
                        mensaje_devuelto = "Error al desvincularse!";

                }
                else{
                    mensaje_devuelto = "No te encuentras vinculado a este usuario!";
                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al desvincularse!";
            }
        }

        if(que_hacer.equals("ListadoIndividual")) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                //ps = con.prepareStatement("INSERT INTO usuarios(id, nombre,stock,alertastock,idCategoria) VALUES(?,?,?,?,?)");

                ResultSet rs;
                Statement st = con.createStatement();


                if (ses.getTipo_rol().equals("Paciente")) {

                    int estado = 0;
                    int tiporol=0;

                    rs = st.executeQuery("SELECT * from estados where estado = 'Activa'");

                    if(rs.next())
                    estado = rs.getInt("idestado");

                    rs = st.executeQuery("SELECT * from tiporol where rol = 'Supervisor'");

                    if(rs.next())
                    tiporol = rs.getInt("idtiporol");

                        rs = st.executeQuery("SELECT u.nombre,u.apellido,v.idVinculacion,v.idSupervisor,t.rol,u.idusuario from usuarios u"+
                                " inner join vinculaciones v on v.idSupervisor = u.idusuario inner join tiporol t on t.idtiporol = u.tiporol" +
                                " where v.estadoVinculacion="+ estado + " and v.idPaciente=" + ses.getId_usuario() + " and u.tiporol="+tiporol);

                    listaMedicos.clear();

                    while (rs.next()) {
                        Log.d("PROFESIONAL","Hay profesional");
                        usu = new Usuarios();
                        vin = new Vinculaciones();
                        tipo = new TipoRol();

                        tipo.setRol(rs.getString(5));

                        no_hay_medicos = false;

                        usu.setTipo(tipo);
                        usu.setNombre(rs.getString(1));
                        usu.setApellido(rs.getString(2));
                        vin.setId_vinculacion(rs.getInt(3));
                        usu.setId_usuario(rs.getInt(6));
                        vin.setId_supervisor(usu);


                        listaMedicos.add(vin);
                        //listaUsuarios.add(user);

                    }


                    rs = st.executeQuery("SELECT * from tiporol where rol = 'Familiar'");

                    if(rs.next())
                    tiporol = rs.getInt("idtiporol");

                        rs = st.executeQuery("SELECT u.nombre,u.apellido,v.idVinculacion,v.idSupervisor,t.rol,u.idusuario from usuarios u"+
                                " inner join vinculaciones v on v.idSupervisor = u.idusuario inner join tiporol t on t.idtiporol = u.tiporol" +
                                " where v.estadoVinculacion="+ estado + " and v.idPaciente=" + ses.getId_usuario() + " and u.tiporol="+tiporol);

                    listaFamiliares.clear();

                    while (rs.next()) {
                        usu = new Usuarios();
                        vin = new Vinculaciones();
                        tipo = new TipoRol();

                        tipo.setRol(rs.getString(5));

                        no_hay_familiares = false;

                        usu.setTipo(tipo);
                        usu.setNombre(rs.getString(1));
                        usu.setApellido(rs.getString(2));
                        usu.setId_usuario(rs.getInt(6));
                        vin.setId_vinculacion(rs.getInt(3));
                        vin.setId_supervisor(usu);

                        listaFamiliares.add(vin);
                        //listaUsuarios.add(user);

                    }


                } else {

                    int estado = 0;
                    int tiporol = 0;

                    rs = st.executeQuery("SELECT * from estados where estado = 'Activa'");

                    if(rs.next())
                    estado = rs.getInt("idestado");

                    rs = st.executeQuery("SELECT * from tiporol where rol = 'Paciente'");

                    if(rs.next())
                    tiporol = rs.getInt("idtiporol");

                        rs = st.executeQuery("SELECT u.nombre,u.apellido,v.idVinculacion,u.idusuario,t.rol from usuarios u"+
                                " inner join vinculaciones v on v.idPaciente = u.idusuario inner join tiporol t on t.idtiporol = u.tiporol" +
                                " where v.estadoVinculacion="+ estado + " and v.idSupervisor=" + ses.getId_usuario() + " and" +
                                " u.tiporol=" + tiporol);

                    listaPacientes.clear();

                    while (rs.next()) {
                        no_hay_pacientes = false;
                        usu = new Usuarios();
                        vin = new Vinculaciones();
                        tipo = new TipoRol();
                        tipo.setRol(rs.getString(5));
                        usu.setTipo(tipo);
                        usu.setId_usuario(rs.getInt(4));
                        usu.setNombre(rs.getString(1));
                        usu.setApellido(rs.getString(2));
                        vin.setId_vinculacion(rs.getInt(3));
                        vin.setId_paciente(usu);

                        listaPacientes.add(vin);
                        //listaUsuarios.add(usuarios_);

                    }

                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al buscar vinculaciones individuales!";
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


        if(que_hacer.equals("Insertar")) {
            Toast.makeText(context, mensaje_devuelto, Toast.LENGTH_SHORT).show();

            NotificacionesBD not = new NotificacionesBD(context,apart,user_destinatario,noti,"NuevaNotificacion");
            not.execute();

            if(ses.getTipo_rol().equals("Paciente")){

                    LogsUsuarios log = new LogsUsuarios();
                    log.setLogdesc("" + ses.getUsuario() + " envio una solicitud de vinculacion al usuario '" + user_destinat + "'");
                    LogsBD userlog = new LogsBD(context, log, "NuevoLog");
                    userlog.execute();
            }

        }

        if(que_hacer.equals("Pendientes")) {
            final AdaptadorUsuariosVinculaciones adapter = new AdaptadorUsuariosVinculaciones(context, listaPendientes);
            list_pendientes.setAdapter(adapter);
            if(no_hay_pendiente) {
                no_hay.setVisibility(View.VISIBLE);
                list_pendientes.setVisibility(View.GONE);
            }
            else {
                no_hay.setVisibility(View.GONE);
                list_pendientes.setVisibility(View.VISIBLE);
            }
        }

        if(que_hacer.equals("ListadoIndividual")) {


            if (ses.getTipo_rol().equals("Paciente")) {

                final AdaptadorUsuariosVinculaciones adapter = new AdaptadorUsuariosVinculaciones(context, listaFamiliares);
                list_familiares.setAdapter(adapter);
                final AdaptadorUsuariosVinculaciones adapter_ = new AdaptadorUsuariosVinculaciones(context, listaMedicos);
                list_medicos.setAdapter(adapter_);

                if (no_hay_medicos) {
                    no_hay_medicos_text.setVisibility(View.VISIBLE);
                    list_medicos.setVisibility(View.GONE);
                } else {
                    no_hay_medicos_text.setVisibility(View.GONE);
                    list_medicos.setVisibility(View.VISIBLE);
                }

                if (no_hay_familiares) {
                    no_hay_familiares_text.setVisibility(View.VISIBLE);
                    list_familiares.setVisibility(View.GONE);
                } else {
                    no_hay_familiares_text.setVisibility(View.GONE);
                    list_familiares.setVisibility(View.VISIBLE);
                }

            }

            if (ses.getTipo_rol().equals("Familiar") || ses.getTipo_rol().equals("Supervisor")) {

                final AdaptadorUsuariosVinculaciones adapter = new AdaptadorUsuariosVinculaciones(context, listaPacientes);
                list_pacientes.setAdapter(adapter);

                busqueda_vinculado.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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


                if (no_hay_pacientes) {
                    no_hay_pacientes_text.setVisibility(View.VISIBLE);
                    list_pacientes.setVisibility(View.GONE);
                } else {
                    no_hay_pacientes_text.setVisibility(View.GONE);
                    list_pacientes.setVisibility(View.VISIBLE);
                }
            }

        }

        if(que_hacer.equals("Aceptar") || que_hacer.equals("Rechazar")) {
            Toast.makeText(context,mensaje_devuelto,Toast.LENGTH_SHORT).show();

            NotificacionesBD not = new NotificacionesBD(context,apart,user_destinatario,noti,"NuevaNotificacion");
            not.execute();

            FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, new VinculacionesPendientesFragment()).commit();


            if(ses.getTipo_rol().equals("Paciente")){

                if(que_hacer.equals("Aceptar")) {

                    LogsUsuarios log = new LogsUsuarios();
                    log.setLogdesc("" + ses.getUsuario() + " se ha vinculado al usuario '" + user_destinat + "'");
                    LogsBD userlog = new LogsBD(context, log, "NuevoLog");
                    userlog.execute();
                }
                else{
                    LogsUsuarios log = new LogsUsuarios();
                    log.setLogdesc(""+ses.getUsuario()+" rechazo la vinculacion de el usuario '" + user_destinat + "'");
                    LogsBD userlog = new LogsBD(context,log,"NuevoLog");
                    userlog.execute();

                }

            }else{

                LogsUsuarios log = new LogsUsuarios();
                log.setLogdesc("" + user_destinat + " se ha vinculado al usuario '" + ses.getUsuario() + "'");
                LogsBD userlog = new LogsBD(context, log, "NuevoLog");
                userlog.execute();

            }

        }

        if(que_hacer.equals("Desvincularse")) {
            Toast.makeText(context,mensaje_devuelto,Toast.LENGTH_SHORT).show();

            if(ses.getTipo_rol().equals("Paciente")) {
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main, new ListadoVinculacionesFragment()).commit();

                LogsUsuarios log = new LogsUsuarios();
                log.setLogdesc(""+ses.getUsuario()+" se ha desvinculado de el usuario '" + user_destinat + "'");
                LogsBD userlog = new LogsBD(context,log,"NuevoLog");
                userlog.execute();

            }else{
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main, new VinculacionesProfesionalFragment()).commit();
            }

            NotificacionesBD not = new NotificacionesBD(context,apart,user_destinatario,noti,"NuevaNotificacion");
            not.execute();

        }

        if(que_hacer.equals("DesvincularseId")){

            Toast.makeText(context,mensaje_devuelto,Toast.LENGTH_SHORT).show();

            Bundle datosAEnviar = new Bundle();
            datosAEnviar.putInt("usuario_perfil", usu.getId_usuario());

            Fragment fragmento = new PerfilDetalleFragment();
            fragmento.setArguments(datosAEnviar);
            FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragmento).commit();

            LogsUsuarios log = new LogsUsuarios();
            log.setLogdesc(""+ses.getUsuario()+" se ha desvinculado de el usuario '" + user_destinat + "'");
            LogsBD userlog = new LogsBD(context,log,"NuevoLog");
            userlog.execute();

        }




    }


}


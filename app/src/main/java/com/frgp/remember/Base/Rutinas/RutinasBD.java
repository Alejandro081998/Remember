package com.frgp.remember.Base.Rutinas;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.frgp.remember.Adaptadores.AdaptadorRutinasFamiliares;
import com.frgp.remember.Adaptadores.AdaptadorRutinasPacientes;
import com.frgp.remember.Adaptadores.AdaptadorRutinasProfesionales;
import com.frgp.remember.Base.Data.DatosBD;
import com.frgp.remember.Base.LogsUsuariosBD.LogsBD;
import com.frgp.remember.Base.Notificaciones.NotificacionesBD;
import com.frgp.remember.Direccionamiento.DireccionamientoNotificaciones;
import com.frgp.remember.Entidades.Apartados;
import com.frgp.remember.Entidades.Avisos;
import com.frgp.remember.Entidades.Dias;
import com.frgp.remember.Entidades.DiasXRutinas;
import com.frgp.remember.Entidades.Estados;
import com.frgp.remember.Entidades.LogsUsuarios;
import com.frgp.remember.Entidades.Notificaciones;
import com.frgp.remember.Entidades.Rutinas;
import com.frgp.remember.Entidades.TipoRutinas;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.R;
import com.frgp.remember.Session.Session;
import com.frgp.remember.ui.Rutinas.Raiz.RutinasFragment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

import static android.content.Context.NOTIFICATION_SERVICE;

//import frgp.utn.edu.ar.consultas_mysql.adapter.ClienteAdapter;
//import frgp.utn.edu.ar.consultas_mysql.entidad.Cliente;


public class RutinasBD extends AsyncTask<String, Void, String> {


    private Rutinas rut;
    private Avisos avi;
    private Apartados apart;
    private TipoRutinas tipo;
    private ArrayList<DiasXRutinas> dias_rutinas;
    private ArrayList<DiasXRutinas> no_dias_rutinas;
    private Estados estado;
    private Estados estado_1;
    private Estados estado_2;
    private Estados est;
    private Usuarios usu, user_destinatario;
    private Notificaciones noti;
    private String que_hacer;
    private Context context;
    private ProgressDialog dialog;
    private String mensaje_devuelto;
    private Session ses;
    private ArrayList<String> dias_seleccionados;
    private ArrayList<String> dias_noseleccionados;
    private String tipo_rutina;
    private TipoRutinas tipo_rut;
    private boolean inserto;
    private boolean insertamos;
    private boolean no_hay_pacientes;
    private boolean no_hay_familiares;
    private boolean no_hay_medicos;
    private PendingIntent pendingIntent;
    private ListView list_familiares;
    private ListView list_pacientes;
    private ListView list_medicos;
    private TextView no_hay_familiares_text;
    private TextView no_hay_medicos_text;
    private TextView no_hay_pacientes_text;
    private TextView titulo;
    private Spinner tipo_rutinas;
    private TimePicker picker;
    private String Tipo_seteado, day;
    private static ArrayList<String> diasSpinner = new ArrayList<String>();
    private Set<String> lista_dias_set;
    private String[] lista_dias;
    private Spinner spdias;
    private String fechaHoy;
    private Dias dias;
    private String fecha,hora;


    //private static ArrayList<DiasXRutinas> listaPendientes = new ArrayList<DiasXRutinas>();
    private static  ArrayList<String> listaDias = new ArrayList<String>();
    private static ArrayList<CheckBox> listaChecks = new ArrayList<CheckBox>();
    private static ArrayList<Rutinas> listaFamiliares = new ArrayList<Rutinas>();
    private static ArrayList<Rutinas> listaPacientes = new ArrayList<Rutinas>();
    private static ArrayList<Rutinas> listaMedicos = new ArrayList<Rutinas>();
    private static ArrayList<String> datosSpinner = new ArrayList<String>();
    private static ArrayList<Rutinas> listaRutinasUsuario = new ArrayList<Rutinas>();
    private static ArrayList<Rutinas> listaRutinasUsuarioAuxiliar = new ArrayList<Rutinas>();
    private static ArrayList<Avisos> listaAvisosHoy = new ArrayList<Avisos>();


    public RutinasBD(Rutinas rt, ArrayList<String> diassel, String tiporut, Context ct, String que, Usuarios user) {

        this.usu = new Usuarios();
        inserto = true;
        tipo_rut = new TipoRutinas();
        ses = new Session();
        estado = new Estados();
        estado_1 = new Estados();
        estado_2 = new Estados();
        rut = new Rutinas();
        dias_rutinas = new ArrayList<DiasXRutinas>();
        dias_seleccionados = new ArrayList<String>();
        dias_rutinas.clear();
        dias_seleccionados.clear();
        ses.setCt(ct);
        ses.cargar_session();
        this.usu = user;
        dias_seleccionados = diassel;
        this.tipo_rutina = tiporut;
        this.rut = rt;
        this.context = ct;
        this.que_hacer = que;
        insertamos = true;
        dialog = new ProgressDialog(ct);
        this.apart = new Apartados();
        this.noti = new Notificaciones();
        this.user_destinatario = new Usuarios();
    }

    public RutinasBD(Rutinas rt, ArrayList<String> diassel,ArrayList<String> nodiassel, String tiporut, Context ct, String que, Usuarios user) {

        this.usu = new Usuarios();
        this.usu = user;
        inserto = true;
        tipo_rut = new TipoRutinas();
        ses = new Session();
        estado = new Estados();
        estado_1 = new Estados();
        estado_2 = new Estados();
        rut = new Rutinas();
        dias_rutinas = new ArrayList<DiasXRutinas>();
        no_dias_rutinas = new ArrayList<DiasXRutinas>();
        dias_seleccionados = new ArrayList<String>();
        dias_noseleccionados = new ArrayList<String>();
        dias_rutinas.clear();
        dias_seleccionados.clear();
        no_dias_rutinas.clear();
        dias_noseleccionados = nodiassel;
        ses.setCt(ct);
        ses.cargar_session();
        dias_seleccionados = diassel;
        this.tipo_rutina = tiporut;
        this.rut = rt;
        this.context = ct;
        this.que_hacer = que;
        insertamos = true;
        dialog = new ProgressDialog(ct);
        this.apart = new Apartados();
        this.noti = new Notificaciones();
        this.user_destinatario = new Usuarios();
    }

    public RutinasBD(Context ct, String que, ListView lv_pac, ListView lv_med, ListView lv_fami,
                     TextView pac,TextView med,TextView fami,Usuarios u) {
        usu = new Usuarios();
        ses = new Session();
        tipo = new TipoRutinas();
        ses.setCt(ct);
        ses.cargar_session();
        usu = u;
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
        no_hay_pacientes = true;
        no_hay_familiares = true;
        no_hay_medicos = true;
        estado = new Estados();
        estado_1 = new Estados();
        estado_2 = new Estados();
        rut = new Rutinas();
        this.list_familiares = lv_fami;
        this.list_pacientes = lv_pac;
        this.list_medicos = lv_med;
        this.no_hay_familiares_text = fami;
        this.no_hay_medicos_text = med;
        this.no_hay_pacientes_text = pac;
    }

    public RutinasBD(Context ct, Rutinas r, String que, Spinner tipo, TextView titu, ArrayList<CheckBox> chk, TimePicker time){

        tipo_rut = new TipoRutinas();
        this.que_hacer = que;
        this.context = ct;
        this.rut = new Rutinas();
        this.rut = r;
        dialog = new ProgressDialog(ct);
        this.tipo_rutinas = tipo;
        this.titulo = titu;
        this.listaChecks = chk;
        this.picker = time;
        this.est = new Estados();
        this.apart = new Apartados();
        this.noti = new Notificaciones();
        this.user_destinatario = new Usuarios();
    }

    public RutinasBD(Context context, String que, Spinner spn){
    diasSpinner.clear();
    this.context = context;
    this.que_hacer = que;
    this.spdias = spn;
    dialog = new ProgressDialog(context);
}


    public RutinasBD(Context ct, String que, ListView lv_pac, ListView lv_med, ListView lv_fami,
                     TextView pac,TextView med,TextView fami,Usuarios u, String dayIn) {
        usu = new Usuarios();
        ses = new Session();
        tipo = new TipoRutinas();
        ses.setCt(ct);
        ses.cargar_session();
        usu = u;
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
        no_hay_pacientes = true;
        no_hay_familiares = true;
        no_hay_medicos = true;
        estado = new Estados();
        estado_1 = new Estados();
        estado_2 = new Estados();
        rut = new Rutinas();
        this.list_familiares = lv_fami;
        this.list_pacientes = lv_pac;
        this.list_medicos = lv_med;
        this.no_hay_familiares_text = fami;
        this.no_hay_medicos_text = med;
        this.no_hay_pacientes_text = pac;
        this.day = dayIn;
    }

    public RutinasBD(Context ct, Rutinas r, String que,Usuarios user){

        this.usu = new Usuarios();
        this.usu = user;
        this.que_hacer = que;
        this.context = ct;
        this.rut = new Rutinas();
        this.rut = r;
        dialog = new ProgressDialog(ct);
        this.estado = new Estados();
        this.ses = new Session();
        this.ses.setCt(ct);
        this.ses.cargar_session();
        this.apart = new Apartados();
        this.noti = new Notificaciones();
        this.user_destinatario = new Usuarios();

    }

    public RutinasBD(Context ct, Avisos a, String fecha, String hora, String que){

        this.avi = new Avisos();
        this.avi = a;
        this.que_hacer = que;
        this.context = ct;
        dialog = new ProgressDialog(ct);
        this.ses = new Session();
        this.ses.setCt(ct);
        this.ses.cargar_session();
        this.fecha = fecha;
        this.hora = hora;

    }

    public RutinasBD(Context ct, String que){
        this.context = ct;
        ses = new Session();
        ses.setCt(ct);
        ses.cargar_session();
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
    }


    @Override
    protected String doInBackground(String... urls) {
        String response = "";
        mensaje_devuelto = "";
        PreparedStatement ps;
        PreparedStatement ps_aux = null;
        //String estado = "";

        if (que_hacer.equals("AvisoAlarma")) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                //ps = con.prepareStatement("INSERT INTO usuarios(id, nombre,stock,alertastock,idCategoria) VALUES(?,?,?,?,?)");

                Statement st = con.createStatement();
                ResultSet rs;

                Calendar fechaActual = Calendar.getInstance();

                fechaActual.set(fechaActual.get(Calendar.YEAR),fechaActual.get(Calendar.MONTH),
                        fechaActual.get(Calendar.DATE),fechaActual.get(Calendar.HOUR_OF_DAY),
                        fechaActual.get(Calendar.MINUTE), fechaActual.get(Calendar.SECOND));

                ps = con.prepareStatement("INSERT INTO Avisos (idRutina, Dia, horaRutina) Values(?,?,?)");

                ps.setInt(1, avi.getId_rutina().getId_rutina());
                ps.setString(2, fecha);
                ps.setString(3,hora);

                int filas = ps.executeUpdate();

                if (filas > 0)
                    Log.d("Aviso INSERTADO", "OK ID:" + avi.getId_rutina().getId_rutina());
                else
                    Log.d("Aviso INSERTADO", "NO");


                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al modificar Aviso!";
            }
        }

        if(que_hacer.equals("CargarSpinner")) {

            boolean insertamos = true;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);

                ResultSet rs;
                Statement st = con.createStatement();

                rs = st.executeQuery("Select count(*) from dias");

                while(rs.next()){
                    lista_dias = new String[rs.getInt(1)];
                }

                rs = st.executeQuery("SELECT * from dias");

                int i = 0;
                diasSpinner.add("Todos");
                while(rs.next()){

                    diasSpinner.add(rs.getString("Descripcion"));

                }
                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al buscar los dias de la semana!";
            }
        }

        if(que_hacer.equals("RutinaPaciente")) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                //ps = con.prepareStatement("INSERT INTO usuarios(id, nombre,stock,alertastock,idCategoria) VALUES(?,?,?,?,?)");

                ResultSet rs;
                ResultSet rs_;
                ResultSet _rs;
                Statement st = con.createStatement();
                Statement st_ = con.createStatement();
                Statement _st = con.createStatement();


                rs = st.executeQuery("SELECT idTipoRutina from tiporutinas where Descripcion='"+tipo_rutina+"'");

                if(rs.next()) {
                    tipo_rut.setId_tipo_rutina(rs.getInt(1));
                    rut.setTipo_rutina(tipo_rut);
                }
                else {
                    mensaje_devuelto = "No se encuentra tipo rutina!";
                    insertamos = false;
                }

                rs = st.executeQuery("SELECT idestado from estados where estado='Activa'");

                if(rs.next())
                    estado_1.setId_estado(rs.getInt(1));
                else {
                    mensaje_devuelto = "No se encuentra estado!";
                    insertamos = false;
                }

                rs = st.executeQuery("SELECT idestado from estados where estado='Suspendida'");

                if(rs.next())
                    estado_2.setId_estado(rs.getInt(1));
                else {
                    mensaje_devuelto = "No se encuentra estado!";
                    insertamos = false;
                }


                rs = st.executeQuery("SELECT idestado from estados where estado='Activo'");

                if(rs.next())
                    estado.setId_estado(rs.getInt(1));
                else {
                    mensaje_devuelto = "No se encuentra estado!";
                    insertamos = false;
                }

                rs = st.executeQuery("SELECT * from dias where Estado=" + estado.getId_estado());

                while(rs.next()){

                    for(String dia: dias_seleccionados){

                        if(dia.equals(rs.getString("Descripcion"))){

                            Dias dias = new Dias();
                            DiasXRutinas diasXRutinas = new DiasXRutinas();
                            dias.setId_dia(rs.getInt("idDia"));
                            diasXRutinas.setId_dia(dias);
                            diasXRutinas.setEstado(estado_1);
                            dias_rutinas.add(diasXRutinas);

                        }

                    }

                }


                if(ses.getTipo_rol().equals("Paciente")) {

                    rs = st.executeQuery("SELECT * from rutinas where idpaciente=" + ses.getId_usuario()
                            + " and (Estado=" + estado_1.getId_estado() + " or Estado=" + estado_2.getId_estado() + ") and Hora='" + rut.getHora().getHours() + ":"
                            + rut.getHora().getMinutes() + ":00'");

                }else{

                    rs = st.executeQuery("SELECT * from rutinas where idpaciente=" + usu.getId_usuario()
                            + " and (Estado=" + estado_1.getId_estado() + " or Estado=" + estado_2.getId_estado() + ") and Hora='" + rut.getHora().getHours() + ":"
                            + rut.getHora().getMinutes() + ":00'");

                }



                while(rs.next()){

                    rs_ = st_.executeQuery("SELECT idDia from diasrutina where idRutina=" + rs.getInt("idrutina") + " and estado="+estado.getId_estado());
                    while(rs_.next()){

                        for(DiasXRutinas dia: dias_rutinas){

                            if(rs_.getInt(1) == dia.getId_dia().getId_dia()){

                                _rs = _st.executeQuery("SELECT Descripcion from dias where idDia=" + dia.getId_dia().getId_dia());

                                if(_rs.next()){
                                    insertamos = false;
                                    mensaje_devuelto = "Ya existe una rutina un dia "+ _rs.getString(1) + " a las " + rs.getTime("Hora") ;
                                }

                            }


                        }

                    }

                }

                if(insertamos){


                    ps = con.prepareStatement("INSERT INTO rutinas (idpaciente, idcreador, tiporutina, " +
                            "Hora,Descripcion,Estado) VALUES (?,?,?,?,?,?)");

                    ps.setInt(1,usu.getId_usuario());
                    ps.setInt(2,ses.getId_usuario());
                    ps.setInt(3,rut.getTipo_rutina().getId_tipo_rutina());
                    ps.setTime(4,rut.getHora());
                    ps.setString(5,rut.getDescripcion());
                    ps.setInt(6,estado_1.getId_estado());

                    int rutina = ps.executeUpdate();

                    if(rutina > 0){

                        Log.d("ENTRO A DIAS RUTINAS","DIAS RUTINAS");

                        if(ses.getTipo_rol().equals("Paciente")) {

                            rs_ = st_.executeQuery("select idrutina from rutinas where idpaciente=" + ses.getId_usuario() +
                                    " order by idrutina desc limit 1");
                        }
                        else{
                            rs_ = st_.executeQuery("select idrutina from rutinas where idpaciente=" + usu.getId_usuario() +
                                    " order by idrutina desc limit 1");


                            apart.setDescripcion("Rutinas Existentes");
                            user_destinatario.setId_usuario(usu.getId_usuario());

                            rs = st.executeQuery("SELECT nombre,apellido from usuarios where idusuario=" + ses.getId_usuario());

                            if(rs.next()) {
                                noti.setDescripcion("" + rs.getString("nombre") + " " + rs.getString("apellido") + " te creó una nueva rutina llamada '" + rut.getDescripcion() + "'");
                            }
                            else
                                mensaje_devuelto = "Error al buscar usuario para notificacion";


                        }

                        if(rs_.next()){

                            Log.d("ENCONTRO RUTINA", "LA ENCONTRO");

                            for(DiasXRutinas dias: dias_rutinas){


                                Rutinas rr = new Rutinas();
                                rr.setId_rutina(rs_.getInt(1));
                                dias.setId_rutina(rr);

                                ps = con.prepareStatement("INSERT INTO diasrutina (idDia, idRutina, Estado)" +
                                        " VALUES (?,?,?)");

                                ps.setInt(1,dias.getId_dia().getId_dia());
                                ps.setInt(2,dias.getId_rutina().getId_rutina());
                                ps.setInt(3,estado.getId_estado());

                                int insertar_dias = ps.executeUpdate();

                                if(insertar_dias == 0){

                                    inserto = false;
                                }

                            }

                        }

                    }else
                        inserto = false;

                    if(inserto)
                        mensaje_devuelto = "Rutina guardada con exito";
                    else
                        mensaje_devuelto = "Error al guardar rutina!";

                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al guardar rutina!";
            }
        }

        if(que_hacer.equals("ModificarRutina")) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                //ps = con.prepareStatement("INSERT INTO usuarios(id, nombre,stock,alertastock,idCategoria) VALUES(?,?,?,?,?)");

                ResultSet rs;
                ResultSet rs_;
                ResultSet _rs;
                Statement st = con.createStatement();
                Statement st_ = con.createStatement();
                Statement _st = con.createStatement();
                ResultSet _rs_;
                Statement _st_ = con.createStatement();

                rs = st.executeQuery("SELECT idTipoRutina from tiporutinas where Descripcion='"+tipo_rutina+"'");

                if(rs.next()) {
                    tipo_rut.setId_tipo_rutina(rs.getInt(1));
                    rut.setTipo_rutina(tipo_rut);
                }
                else {
                    mensaje_devuelto = "No se encuentra tipo rutina!";
                    insertamos = false;
                }


                rs = st.executeQuery("SELECT idestado from estados where estado='Activa'");

                if(rs.next())
                    estado_1.setId_estado(rs.getInt(1));
                else {
                    mensaje_devuelto = "No se encuentra estado!";
                    insertamos = false;
                }

                rs = st.executeQuery("SELECT idestado from estados where estado='Suspendida'");

                if(rs.next())
                    estado_2.setId_estado(rs.getInt(1));
                else {
                    mensaje_devuelto = "No se encuentra estado!";
                    insertamos = false;
                }


                rs = st.executeQuery("SELECT idestado from estados where estado='Activo'");

                if(rs.next())
                    estado.setId_estado(rs.getInt(1));
                else {
                    mensaje_devuelto = "No se encuentra estado!";
                    insertamos = false;
                }


                rs = st.executeQuery("SELECT * from dias where Estado=" + estado.getId_estado());

                while(rs.next()){

                    for(String dia: dias_seleccionados){

                        if(dia.equals(rs.getString("Descripcion"))){

                            Dias dias = new Dias();
                            DiasXRutinas diasXRutinas = new DiasXRutinas();
                            dias.setId_dia(rs.getInt("idDia"));
                            diasXRutinas.setId_dia(dias);
                            diasXRutinas.setEstado(estado_1);
                            dias_rutinas.add(diasXRutinas);

                        }


                    }

                }

                rs = st.executeQuery("SELECT * from dias where Estado=" + estado.getId_estado());

                while(rs.next()) {

                    for (String dia : dias_noseleccionados) {

                        if (dia.equals(rs.getString("Descripcion"))) {

                            Dias dias = new Dias();
                            DiasXRutinas diasXRutinas = new DiasXRutinas();
                            dias.setId_dia(rs.getInt("idDia"));
                            diasXRutinas.setId_dia(dias);
                            diasXRutinas.setEstado(estado_1);
                            no_dias_rutinas.add(diasXRutinas);

                        }
                    }
                }

                rs = st.executeQuery("SELECT * from rutinas where idpaciente="+usu.getId_usuario()
                        + " and (Estado=" + estado_1.getId_estado() + " or Estado=" + estado_2.getId_estado() +") and Hora='" + rut.getHora().getHours() + ":"
                        + rut.getHora().getMinutes() + ":00' and idRutina<>" + rut.getId_rutina());

                while(rs.next()){

                    rs_ = st_.executeQuery("SELECT idDia from diasrutina where idRutina=" + rs.getInt("idrutina") + " and estado="+estado.getId_estado());
                    while(rs_.next()){

                        for(DiasXRutinas dia: dias_rutinas){

                            if(rs_.getInt(1) == dia.getId_dia().getId_dia()){

                                _rs = _st.executeQuery("SELECT Descripcion from dias where idDia=" + dia.getId_dia().getId_dia());

                                if(_rs.next()){
                                    insertamos = false;
                                    mensaje_devuelto = "Ya existe una rutina un dia "+ _rs.getString(1) + " a las " + rs.getTime("Hora") ;
                                }

                            }


                        }

                    }

                }

                if(insertamos){


                    ps = con.prepareStatement("Update rutinas set tiporutina=?,hora=?,Descripcion=? where idrutina=?");

                    ps.setInt(1,rut.getTipo_rutina().getId_tipo_rutina());
                    ps.setTime(2,rut.getHora());
                    ps.setString(3,rut.getDescripcion());
                    ps.setInt(4,rut.getId_rutina());

                    int rutina = ps.executeUpdate();

                    if(rutina > 0){


                        if(!ses.getTipo_rol().equals("Paciente")) {

                            apart.setDescripcion("Rutinas Existentes");
                            user_destinatario.setId_usuario(usu.getId_usuario());

                            rs = st.executeQuery("SELECT nombre,apellido from usuarios where idusuario=" + ses.getId_usuario());

                            if (rs.next()) {
                                noti.setDescripcion("" + rs.getString("nombre") + " " + rs.getString("apellido") + " ha modificado la rutina '"+ rut.getDescripcion() + "'");                            } else
                                mensaje_devuelto = "Error al buscar usuario para notificacion";


                        }


                        Log.d("ENTRO A DIAS RUTINAS","DIAS RUTINAS");

                        //rs_ = st_.executeQuery("select idrutina from rutinas where idpaciente="+ses.getId_usuario() +
                        //      " order by idrutina desc limit 1");

                        for(DiasXRutinas dias: dias_rutinas) {

                            rs_ = st_.executeQuery("select iddia from dias where estado=" + estado.getId_estado());

                            while(rs_.next()) {

                                if (dias.getId_dia().getId_dia() == rs_.getInt(1)) {

                                    rs = st.executeQuery("select * from diasrutina where iddia=" + dias.getId_dia().getId_dia() + " and idrutina" +
                                            "=" + rut.getId_rutina());

                                    if (rs.next()) {

                                        if (rs.getInt("Estado") != estado.getId_estado()) {

                                            Log.d("EDITARRUTINA", "SE MARCO EL DIA, EL MISMO EXISTIA PERO ESTABA INACTIVO.");

                                            ps = con.prepareStatement("UPDATE diasrutina set Estado=? where idrutina=? and idDia=?");

                                            ps.setInt(3, dias.getId_dia().getId_dia());
                                            ps.setInt(2, rut.getId_rutina());
                                            ps.setInt(1, estado.getId_estado());


                                            int insertar_dias = ps.executeUpdate();

                                            if(insertar_dias == 0)
                                                inserto = false;

                                        }
                                    }
                                        else {

                                        Log.d("EDITARRUTINA", "SE MARCO EL DIA, EL MISMO NO EXISTIA");

                                            ps = con.prepareStatement("Insert into diasrutina (Estado, idrutina, iddia) values(?,?,?)");

                                            ps.setInt(3, dias.getId_dia().getId_dia());
                                            ps.setInt(2, rut.getId_rutina());
                                            ps.setInt(1, estado.getId_estado());


                                        int insertar_dias = ps.executeUpdate();

                                        if(insertar_dias == 0)
                                            inserto = false;

                                        }
                                }

                            }


                        }



                        for(DiasXRutinas dias: no_dias_rutinas) {

                            rs_ = st_.executeQuery("select iddia from dias where estado=" + estado.getId_estado());

                            while(rs_.next()) {

                                if (dias.getId_dia().getId_dia() == rs_.getInt(1)) {

                                    rs = st.executeQuery("select * from diasrutina where iddia=" + dias.getId_dia().getId_dia() + " and idrutina" +
                                            "=" + rut.getId_rutina());

                                    if (rs.next()) {

                                        if (rs.getInt("Estado") == estado.getId_estado()) {

                                            Log.d("EDITARRUTINA", "EL DIA NO SE MARCO, EL MISMO ESTABA ACTIVO");

                                            _rs_ = _st_.executeQuery("SELECT idestado from estados where estado='Inactivo'");

                                            if (_rs_.next())
                                                estado.setId_estado(_rs_.getInt(1));
                                            else {
                                                mensaje_devuelto = "No se encuentra estado!";
                                                insertamos = false;
                                            }

                                            ps = con.prepareStatement("UPDATE diasrutina set Estado=? where idrutina=? and idDia=?");

                                            ps.setInt(3, dias.getId_dia().getId_dia());
                                            ps.setInt(2, rut.getId_rutina());
                                            ps.setInt(1, estado.getId_estado());


                                            int insertar_dias = ps.executeUpdate();

                                            if(insertar_dias == 0){
                                                inserto = false;

                                        }
                                    }

                                }


                            }

                            }


                        }






                      /*  rs_ = st_.executeQuery("select iddia from dias where estado=" + estado.getId_estado());


                        while(rs_.next()){

                            for(DiasXRutinas dias: dias_rutinas) {

                                if (dias.getId_dia().getId_dia() == rs_.getInt(1)) {

                                    rs = st.executeQuery("select * from diasrutina where iddia=" + dias.getId_dia().getId_dia() + " and idrutina" +
                                            "=" + rut.getId_rutina());

                                    if (rs.next()) {

                                        if (rs.getInt("Estado") != estado.getId_estado()) {

                                            Log.d("ESTABAOFF", "PERO EXISTIA PREVIAMENTE");

                                            ps = con.prepareStatement("UPDATE diasrutina set Estado=? where idrutina=? and idDia=?");

                                            ps.setInt(3, dias.getId_dia().getId_dia());
                                            ps.setInt(2, rut.getId_rutina());
                                            ps.setInt(1, estado.getId_estado());

                                        }

                                    } else {

                                        Log.d("ESTABAOFF", "PERO NO EXISTIA PREVIAMENTE");

                                        ps = con.prepareStatement("Insert into diasrutina (Estado, idrutina, iddia) values(?,?,?)");

                                        ps.setInt(3, dias.getId_dia().getId_dia());
                                        ps.setInt(2, rut.getId_rutina());
                                        ps.setInt(1, estado.getId_estado());

                                    }
                                } else {

                                    rs = st.executeQuery("select * from diasrutina where iddia=" + rs_.getInt(1) + " and idrutina" +
                                            "=" + rut.getId_rutina());

                                    Log.d("NOESTA", "EN LA LISTA DE ELEGIDOS");

                                    if (rs.next()) {

                                        if (rs.getInt("Estado") == estado.getId_estado()) {

                                            Log.d("DESELECCIONE DIA", "PASO");

                                            _rs_ = _st_.executeQuery("SELECT idestado from estados where estado='Inactivo'");

                                            if (_rs_.next())
                                                estado.setId_estado(_rs_.getInt(1));
                                            else {
                                                mensaje_devuelto = "No se encuentra estado!";
                                                insertamos = false;
                                            }

                                            ps = con.prepareStatement("UPDATE diasrutina set Estado=? where idrutina=? and idDia=?");

                                            ps.setInt(3, dias.getId_dia().getId_dia());
                                            ps.setInt(2, rut.getId_rutina());
                                            ps.setInt(1, estado.getId_estado());

                                        }
                                    }


                                }
                            }

                            int insertar_dias = ps.executeUpdate();

                            if(insertar_dias == 0){
                                inserto = false;
                            }

                        }*/

                    }else
                        inserto = false;

                    if(inserto)
                        mensaje_devuelto = "Rutina guardada con exito";
                    else
                        mensaje_devuelto = "Error al guardar rutina!";

                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al guardar rutina!";
            }
        }

        if(que_hacer.equals("CargarRutinas") || que_hacer.equals("CargarRutinasxDia") ) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                //ps = con.prepareStatement("INSERT INTO usuarios(id, nombre,stock,alertastock,idCategoria) VALUES(?,?,?,?,?)");

                ResultSet rs;
                Statement st = con.createStatement();
                ResultSet rs_;
                Statement st_ = con.createStatement();


                rs = st.executeQuery("SELECT * from estados where estado = 'Activa'");

                if(rs.next())
                    estado_1.setId_estado(rs.getInt("idestado"));

                rs = st.executeQuery("SELECT * from estados where estado = 'Suspendida'");

                if(rs.next())
                    estado_2.setId_estado(rs.getInt("idestado"));

                if(que_hacer.equals("CargarRutinas"))
                    rs = st.executeQuery("SELECT tr.Descripcion as 'desc',r.Descripcion,r.Hora,r.idCreador,r.idRutina,r.Estado,u.nombre, u.apellido, r.idPaciente" +
                            " from rutinas r inner join usuarios u on u.idusuario = r.idcreador inner join tiporutinas tr on r.tiporutina=" +
                            " tr.idTipoRutina " +
                            " where idPaciente=" + usu.getId_usuario() + " and (r.Estado=" + estado_1.getId_estado() + " or r.Estado=" +
                            estado_2.getId_estado() + ")");
                 else if(day.equals("Todos"))
                    rs = st.executeQuery("SELECT DISTINCT tr.Descripcion as 'desc',r.Descripcion,r.Hora,r.idCreador,r.idRutina,r.Estado,u.nombre, u.apellido, r.idPaciente" +
                            " from rutinas r inner join usuarios u on u.idusuario = r.idcreador inner join tiporutinas tr on r.tiporutina=" +
                            " tr.idTipoRutina INNER JOIN diasrutina dr ON r.idrutina = dr.idRutina INNER JOIN dias d ON dr.idDia = d.idDia " +
                            " where idPaciente=" + usu.getId_usuario() + " and (r.Estado=" + estado_1.getId_estado() + " or r.Estado=" +
                            estado_2.getId_estado() + ")");
                else
                    rs = st.executeQuery("SELECT tr.Descripcion as 'desc',r.Descripcion,r.Hora,r.idCreador,r.idRutina,r.Estado,u.nombre, u.apellido, r.idPaciente" +
                            " from rutinas r inner join usuarios u on u.idusuario = r.idcreador inner join tiporutinas tr on r.tiporutina=" +
                            " tr.idTipoRutina INNER JOIN diasrutina dr ON r.idrutina = dr.idRutina INNER JOIN dias d ON dr.idDia = d.idDia " +
                            " where idPaciente=" + usu.getId_usuario() + " and (r.Estado=" + estado_1.getId_estado() + " or r.Estado=" +
                            estado_2.getId_estado() + ") AND d.Descripcion ='" + day + "'");

                    listaMedicos.clear();
                    listaFamiliares.clear();
                    listaPacientes.clear();

                    while (rs.next()) {

                        rut = new Rutinas();
                        est = new Estados();
                        usu = new Usuarios();
                        Usuarios usu_ = new Usuarios();
                        tipo = new TipoRutinas();
                        usu_.setNombre(rs.getString("nombre"));
                        usu_.setApellido(rs.getString("apellido"));
                        rut.setDescripcion(rs.getString("Descripcion"));
                        rut.setHora(rs.getTime("Hora"));
                        usu.setId_usuario(rs.getInt("idPaciente"));
                        rut.setId_paciente(usu);
                        usu_.setId_usuario(rs.getInt("idCreador"));
                        rut.setId_creador(usu_);
                        rut.setId_rutina(rs.getInt("idRutina"));
                        est.setId_estado(rs.getInt("Estado"));
                        tipo.setDescripcion(rs.getString(1));
                        rut.setTipo_rutina(tipo);
                        rut.setEstados(est);


                        rs_ = st_.executeQuery("SELECT t.rol from tiporol t inner join usuarios u on t.idtiporol = u.tiporol where" +
                                " idusuario="+rs.getInt("idCreador"));

                        if(rs_.next()){

                            if(rs_.getString(1).equals("Paciente")) {
                                listaPacientes.add(rut);
                                no_hay_pacientes = false;
                            }
                            if(rs_.getString(1).equals("Supervisor")) {
                                listaMedicos.add(rut);
                                no_hay_medicos = false;
                            }
                            if(rs_.getString(1).equals("Familiar")) {
                                listaFamiliares.add(rut);
                                no_hay_familiares = false;
                            }
                        }

                    }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al buscar rutinas paciente!";
            }


        }

        if(que_hacer.equals("VerificarRutinas")) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                //ps = con.prepareStatement("INSERT INTO usuarios(id, nombre,stock,alertastock,idCategoria) VALUES(?,?,?,?,?)");

                ResultSet rs;
                Statement st = con.createStatement();

                Calendar fechaActual = Calendar.getInstance();

                fechaActual.set(fechaActual.get(Calendar.YEAR),fechaActual.get(Calendar.MONTH),
                        fechaActual.get(Calendar.DATE),fechaActual.get(Calendar.HOUR_OF_DAY),
                        fechaActual.get(Calendar.MINUTE),fechaActual.get(Calendar.SECOND));

                String Dia = "";

                Log.d("USUARIORUTINAS: ", "" + ses.getId_usuario());

                String[] strDays = new String[]{
                        "Domingo",
                        "Lunes",
                        "Martes",
                        "Miercoles",
                        "Jueves",
                        "Viernes",
                        "Sabado"};

                Dia = strDays[fechaActual.get(Calendar.DAY_OF_WEEK) - 1];

                rs = st.executeQuery("SELECT r.idrutina,r.Descripcion, r.idpaciente, r.Hora," +
                        " dd.Descripcion, e.estado FROM `rutinas` r inner join diasrutina d ON r.idrutina = "+
                                "d.idRutina inner join dias dd on d.idDia = dd.idDia inner join estados e on r.Estado"+
                                "= e.idestado where e.estado = 'Activa' and dd.Descripcion = '" + Dia + "' and" +
                        " r.idpaciente =" + ses.getId_usuario());

                listaRutinasUsuario.clear();
                listaAvisosHoy.clear();
                listaRutinasUsuarioAuxiliar.clear();

                while(rs.next()){
                    Log.d("RUTINAS: ", "USUARIO TIENE RUTINAS EL " + Dia);
                    rut = new Rutinas();
                    rut.setDescripcion(rs.getString(2));
                    rut.setId_rutina(rs.getInt(1));
                    rut.setHora(rs.getTime(4));
                    listaRutinasUsuario.add(rut);
                }

                fechaHoy = "" + fechaActual.get(Calendar.YEAR) + "-" + (fechaActual.get(Calendar.MONTH) + 1) +
                "-" + fechaActual.get(Calendar.DATE);

                Log.d("FECHA HOY:", " " + fechaHoy);

                rs = st.executeQuery("SELECT * from Avisos where Dia = '" + fechaHoy + "'");

                while (rs.next()){
                    Log.d("AVISOS: ", "HAY AVISOS EL " + fechaHoy);
                    rut = new Rutinas();
                    avi = new Avisos();
                    rut.setId_rutina(rs.getInt("idRutina"));
                    avi.setId_rutina(rut);
                    avi.setHoraRutina(rs.getTime("horaRutina"));
                    listaAvisosHoy.add(avi);
                }

                listaRutinasUsuarioAuxiliar.addAll(listaRutinasUsuario);


                for(Avisos av: listaAvisosHoy){
                    for(Rutinas rut: listaRutinasUsuarioAuxiliar){
                        if(av.getId_rutina().getId_rutina() == rut.getId_rutina()
                        && av.getHoraRutina().equals(rut.getHora())){
                            listaRutinasUsuario.remove(rut);
                            Log.d("AVISOS HOY:", " Borre la rutina " + rut.getId_rutina() + " porque " +
                                    "ya se notifico");
                        }
                    }
                }

                listaRutinasUsuarioAuxiliar.clear();
                listaRutinasUsuarioAuxiliar.addAll(listaRutinasUsuario);

                for(Rutinas rut: listaRutinasUsuarioAuxiliar){

                    Calendar fechaSeteada = Calendar.getInstance();
                    fechaSeteada.set(fechaActual.get(Calendar.YEAR),fechaActual.get(Calendar.MONTH),
                            fechaActual.get(Calendar.DATE),rut.getHora().getHours(),rut.getHora().getMinutes(),
                            rut.getHora().getSeconds());

                    if(fechaSeteada.after(fechaActual)) {
                        listaRutinasUsuario.remove(rut);
                        Log.d("HORA TODAVIA NO: ", " Todavia no es hora de la rutina " + rut.getId_rutina());
                    }
                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al buscar rutinas paciente!";
            }


        }

        if(que_hacer.equals("CargarRutinaEditar")) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                //ps = con.prepareStatement("INSERT INTO usuarios(id, nombre,stock,alertastock,idCategoria) VALUES(?,?,?,?,?)");

                ResultSet rs;
                Statement st = con.createStatement();
                ResultSet rs_;
                Statement st_ = con.createStatement();

                datosSpinner.clear();
                listaDias.clear();


                rs = st.executeQuery("SELECT idestado from estados where estado='Activo'");

                if(rs.next())
                    est.setId_estado(rs.getInt("idestado"));
                else
                    mensaje_devuelto = "Error al buscar estado";


                rs = st.executeQuery("SELECT * from tiporutinas where Estado=" +est.getId_estado());

                while(rs.next()) {

                    datosSpinner.add(rs.getString("Descripcion"));
                }



                rs = st.executeQuery("SELECT r.descripcion, r.hora, t.descripcion from rutinas r inner join " +
                        " tiporutinas t on r.tiporutina = t.idtiporutina where idrutina =" + rut.getId_rutina());

                if(rs.next()){

                    tipo_rut.setDescripcion(rs.getString(3));
                    rut.setDescripcion(rs.getString(1));
                    rut.setHora(rs.getTime(2));
                    rut.setTipo_rutina(tipo_rut);

                }

                rs = st.executeQuery("SELECT d.descripcion from dias d inner join diasrutina dr on d.iddia = dr.iddia" +
                        " where dr.idRutina =" + rut.getId_rutina() + " and dr.estado=" + est.getId_estado());

                while (rs.next()) {

                    listaDias.add(rs.getString(1));
                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al buscar rutinas paciente!";
            }


        }

        if(que_hacer.equals("Ignorar") || que_hacer.equals("Activar")){

            inserto = true;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                //ps = con.prepareStatement("INSERT INTO usuarios(id, nombre,stock,alertastock,idCategoria) VALUES(?,?,?,?,?)");
                ps = con.prepareStatement("UPDATE rutinas SET Estado=?" +
                        " where idRutina=?");

                Statement st = con.createStatement();
                ResultSet rs;


                if(que_hacer.equals("Ignorar")) {
                    rs = st.executeQuery("SELECT * FROM estados where estado ='Suspendida'");

                    if (rs.next())
                        estado.setId_estado(rs.getInt("idestado"));
                }

                if(que_hacer.equals("Activar")) {
                    rs = st.executeQuery("SELECT * FROM estados where estado ='Activa'");

                    if (rs.next())
                        estado.setId_estado(rs.getInt("idestado"));
                }


                ps.setInt(1,estado.getId_estado());
                ps.setInt(2,rut.getId_rutina());


                int filas = ps.executeUpdate();

                if(filas > 0) {
                    mensaje_devuelto = "Modificacion exitosa!";


                    rs = st.executeQuery("SELECT * FROM rutinas where idRutina=" + rut.getId_rutina());
                    if (rs.next())
                        rut.setDescripcion(rs.getString("Descripcion"));


                    if(!ses.getTipo_rol().equals("Paciente")) {

                        apart.setDescripcion("Rutinas Existentes");

                        rs = st.executeQuery("SELECT * FROM rutinas where idRutina=" + rut.getId_rutina());

                        if (rs.next()) {

                            if (rs.getInt("idCreador") == ses.getId_usuario())
                                user_destinatario.setId_usuario(rs.getInt("idpaciente"));
                            else if (rs.getInt("idCreador") != ses.getId_usuario())
                                user_destinatario.setId_usuario(rs.getInt("idcreador"));

                            rut.setDescripcion(rs.getString("Descripcion"));

                        }


                        rs = st.executeQuery("SELECT nombre,apellido from usuarios where idusuario=" + ses.getId_usuario());

                        if(que_hacer.equals("Activar")) {

                            if (rs.next()) {
                                noti.setDescripcion("" + rs.getString("nombre") + " " + rs.getString("apellido") + " ha activado la rutina '" + rut.getDescripcion() + "'");
                            } else
                                mensaje_devuelto = "Error al buscar usuario para notificacion";
                        }
                        else{

                            if (rs.next()) {
                                noti.setDescripcion("" + rs.getString("nombre") + " " + rs.getString("apellido") + " ha desactivado la rutina '" + rut.getDescripcion() + "'");
                            } else
                                mensaje_devuelto = "Error al buscar usuario para notificacion";

                        }

                    }


                }
                else {
                    mensaje_devuelto = "Error al modificar rutina!";
                    inserto = false;
                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al modificar rutina!";
            }
        }

        if(que_hacer.equals("Eliminar")){

            inserto = true;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                //ps = con.prepareStatement("INSERT INTO usuarios(id, nombre,stock,alertastock,idCategoria) VALUES(?,?,?,?,?)");
                ps = con.prepareStatement("UPDATE rutinas SET Estado=?" +
                        " where idRutina=?");

                Statement st = con.createStatement();
                ResultSet rs;


                if(que_hacer.equals("Eliminar")) {
                    rs = st.executeQuery("SELECT * FROM estados where estado ='Inactivo'");

                    if (rs.next())
                        estado.setId_estado(rs.getInt("idestado"));
                }


                ps.setInt(1,estado.getId_estado());
                ps.setInt(2,rut.getId_rutina());


                int filas = ps.executeUpdate();

                if(filas > 0) {
                    mensaje_devuelto = "Rutina eliminada!";


                    rs = st.executeQuery("SELECT * FROM rutinas where idRutina=" + rut.getId_rutina());
                    if (rs.next())
                        rut.setDescripcion(rs.getString("Descripcion"));


                    if(!ses.getTipo_rol().equals("Paciente")) {

                        apart.setDescripcion("Rutinas Existentes");

                        rs = st.executeQuery("SELECT * FROM rutinas where idRutina=" + rut.getId_rutina());

                        if (rs.next()) {

                            if (rs.getInt("idCreador") == ses.getId_usuario())
                                user_destinatario.setId_usuario(rs.getInt("idpaciente"));
                            else if (rs.getInt("idCreador") != ses.getId_usuario())
                                user_destinatario.setId_usuario(rs.getInt("idcreador"));

                            rut.setDescripcion(rs.getString("Descripcion"));

                        }


                        rs = st.executeQuery("SELECT nombre,apellido from usuarios where idusuario=" + ses.getId_usuario());

                            if (rs.next()) {
                                noti.setDescripcion("" + rs.getString("nombre") + " " + rs.getString("apellido") + " ha eliminado la rutina '" + rut.getDescripcion() + "'");
                            } else
                                mensaje_devuelto = "Error al buscar usuario para notificacion";

                    }



                }
                else {
                    mensaje_devuelto = "Error al eliminar rutina!";
                    inserto = false;
                }

                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al modificar rutina!";
            }
        }

        return response;

    }


    @Override
    protected void onPreExecute() {

        if(!que_hacer.equals("VerificarRutinas") && !que_hacer.equals("AvisoAlarma")) {
            dialog.show();
            dialog.setContentView(R.layout.progress_dialog);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onPostExecute(String response) {

        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        if(que_hacer.equals("RutinaPaciente")) {
            Toast.makeText(context, mensaje_devuelto, Toast.LENGTH_SHORT).show();
            if(insertamos) {
                if (ses.getTipo_rol().equals("Paciente")) {
                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_main, new RutinasFragment()).commit();
                        LogsUsuarios log = new LogsUsuarios();
                        log.setLogdesc(""+ses.getUsuario()+" ha creado la rutina '" + rut.getDescripcion() +"'");
                        LogsBD userlog = new LogsBD(context,log,"NuevoLog");
                        userlog.execute();
                }else{

                    Bundle datosAEnviar = new Bundle();
                    datosAEnviar.putInt("PacienteRutinas", usu.getId_usuario());

                    Fragment fragmento = new RutinasFragment();
                    fragmento.setArguments(datosAEnviar);
                    FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_main, fragmento).commit();

                    NotificacionesBD not = new NotificacionesBD(context,apart,user_destinatario,noti,"NuevaNotificacion");
                    not.execute();

                }
            }

        }

        if(que_hacer.equals("ModificarRutina")) {
            Toast.makeText(context, mensaje_devuelto, Toast.LENGTH_SHORT).show();
            if(insertamos) {

                if (ses.getTipo_rol().equals("Paciente")) {
                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_main, new RutinasFragment()).commit();
                    LogsUsuarios log = new LogsUsuarios();
                    log.setLogdesc(""+ses.getUsuario()+" ha modificado la rutina '" + rut.getDescripcion() +"'");
                    LogsBD userlog = new LogsBD(context,log,"NuevoLog");
                    userlog.execute();
                }else{

                    Bundle datosAEnviar = new Bundle();
                    datosAEnviar.putInt("PacienteRutinas", usu.getId_usuario());

                    Log.d("USUARIOMODIFICADO", "" + usu.getId_usuario());


                    Fragment fragmento = new RutinasFragment();
                    fragmento.setArguments(datosAEnviar);
                    FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_main, fragmento).commit();

                    NotificacionesBD not = new NotificacionesBD(context,apart,user_destinatario,noti,"NuevaNotificacion");
                    not.execute();

                }
            }

        }

        if(que_hacer.equals("CargarRutinaEditar")) {

            titulo.setText(rut.getDescripcion());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                picker.setMinute(rut.getHora().getMinutes());
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                picker.setHour(rut.getHora().getHours());
            }
            tipo_rutinas.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, datosSpinner));


            for(String st : listaDias){

                if(st.equals("Lunes"))
                    listaChecks.get(0).setChecked(true);

                if(st.equals("Martes"))
                    listaChecks.get(1).setChecked(true);

                if(st.equals("Miercoles"))
                    listaChecks.get(2).setChecked(true);

                if(st.equals("Jueves"))
                    listaChecks.get(3).setChecked(true);

                if(st.equals("Viernes"))
                    listaChecks.get(4).setChecked(true);

                if(st.equals("Sabado"))
                    listaChecks.get(5).setChecked(true);

                if(st.equals("Domingo"))
                    listaChecks.get(6).setChecked(true);


            }

            int i = 0;

            for(String st : datosSpinner){

                if(st.equals(rut.getTipo_rutina().getDescripcion())){
                    tipo_rutinas.setSelection(i);
                }

                i++;

            }



        }

        if(que_hacer.equals("CargarRutinas") || que_hacer.equals("CargarRutinasxDia") ) {


            AdaptadorRutinasFamiliares adapter_fami = new AdaptadorRutinasFamiliares(context,listaFamiliares);
            list_familiares.setAdapter(adapter_fami);

            AdaptadorRutinasPacientes adapter_paci = new AdaptadorRutinasPacientes(context,listaPacientes);
            list_pacientes.setAdapter(adapter_paci);

            AdaptadorRutinasProfesionales adapter_profe = new AdaptadorRutinasProfesionales(context,listaMedicos);
            list_medicos.setAdapter(adapter_profe);

            if (no_hay_pacientes) {
                no_hay_pacientes_text.setVisibility(View.VISIBLE);
                list_pacientes.setVisibility(View.GONE);
            } else {
                no_hay_pacientes_text.setVisibility(View.GONE);
                list_pacientes.setVisibility(View.VISIBLE);
            }

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

        if(que_hacer.equals("Ignorar") || que_hacer.equals("Activar")) {
            Toast.makeText(context,mensaje_devuelto,Toast.LENGTH_SHORT).show();
            if(inserto) {

                if(ses.getTipo_rol().equals("Paciente")) {

                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_main, new RutinasFragment()).commit();

                    if(que_hacer.equals("Ignorar")){

                        LogsUsuarios log = new LogsUsuarios();
                        log.setLogdesc(""+ses.getUsuario()+" ha desactivado la rutina '" + rut.getDescripcion() +"'");
                        LogsBD userlog = new LogsBD(context,log,"NuevoLog");
                        userlog.execute();

                    }else{

                        LogsUsuarios log = new LogsUsuarios();
                        log.setLogdesc(""+ses.getUsuario()+" ha activado la rutina '" + rut.getDescripcion() +"'");
                        LogsBD userlog = new LogsBD(context,log,"NuevoLog");
                        userlog.execute();

                    }

                }
                else{

                    Bundle datosAEnviar = new Bundle();
                    datosAEnviar.putInt("PacienteRutinas", usu.getId_usuario());

                    Fragment fragmento = new RutinasFragment();
                    fragmento.setArguments(datosAEnviar);
                    FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_main, fragmento).commit();

                    NotificacionesBD not = new NotificacionesBD(context,apart,user_destinatario,noti,"NuevaNotificacion");
                    not.execute();

                }
            }
        }

        if(que_hacer.equals("Eliminar")) {
            Toast.makeText(context,mensaje_devuelto,Toast.LENGTH_SHORT).show();
            if(inserto) {
                if(ses.getTipo_rol().equals("Paciente")) {

                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_main, new RutinasFragment()).commit();

                    LogsUsuarios log = new LogsUsuarios();
                    log.setLogdesc(""+ses.getUsuario()+" ha eliminado la rutina '" + rut.getDescripcion() +"'");
                    LogsBD userlog = new LogsBD(context,log,"NuevoLog");
                    userlog.execute();

                }
                else{

                    Bundle datosAEnviar = new Bundle();
                    datosAEnviar.putInt("PacienteRutinas", usu.getId_usuario());

                    Fragment fragmento = new RutinasFragment();
                    fragmento.setArguments(datosAEnviar);
                    FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_main, fragmento).commit();

                    NotificacionesBD not = new NotificacionesBD(context,apart,user_destinatario,noti,"NuevaNotificacion");
                    not.execute();


                }
            }
        }

         if(que_hacer.equals("CargarSpinner")) {

            spdias.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, diasSpinner));
            spdias.setSelection(0);
        }

        if(que_hacer.equals("VerificarRutinas")){

            for(Rutinas rut: listaRutinasUsuario){
                Log.d("ES HORA: ", "ES HORA DE LA RUTINA: " + rut.getId_rutina());
                setPendingIntent(rut.getId_rutina(),"RutinasAlarma",rut.getHora());
                CreateNotificationChannel();
                CreateNotification(rut.getDescripcion() + " - " + fechaHoy,rut.getId_rutina(),rut.getHora());
            }
        }

        }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setPendingIntent(int notif, String apartado, Time horaRutina) {

        String hora = horaRutina.getHours() + ":" + horaRutina.getMinutes() + ":00";

        Intent notificationIntent = new Intent(context, DireccionamientoNotificaciones.class);
        notificationIntent.putExtra("apartado",apartado);
        notificationIntent.putExtra("noti",notif);
        notificationIntent.putExtra("dia_rutina",fechaHoy);
        notificationIntent.putExtra("horaRutina",hora);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(DireccionamientoNotificaciones.class);
        stackBuilder.addNextIntent(notificationIntent);
        pendingIntent = stackBuilder.getPendingIntent(notif, PendingIntent.FLAG_CANCEL_CURRENT);

    }

    private void CreateNotificationChannel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            String CHANNEL_ID = "NOTIFICACION_ALARMAS";

            CharSequence name = "Notificacion_ALARMAS";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) (context).getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void CreateNotification(String desc, int notif, Time time){

        //notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        //notificationIntent.putExtra("apartado",not.getIdApartado().getDescripcion());


        String CHANNEL_ID = "NOTIFICACION_ALARMAS";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_alarm);
        builder.setContentTitle("RememberMe");

        String hora_ = "";
        String minutos_ = "";

        if(time.getHours() < 10 ){
            hora_ = "0" + time.getHours();
        }else{
            hora_ = "" + time.getHours();
        }

        if(time.getMinutes() < 10 ){
            minutos_ = "0" + time.getMinutes();
        }else{
            minutos_ = "" + time.getMinutes();
        }

        builder.setContentText(desc + " - "  + hora_ + ":" + minutos_);
        builder.setColor(Color.RED);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA,1000,1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(notif,builder.build());

    }


    }




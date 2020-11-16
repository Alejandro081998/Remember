package com.frgp.remember.Base.Usuarios;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.frgp.remember.Adaptadores.AdaptadorLogs;
import com.frgp.remember.Adaptadores.AdaptadorNuevaVinculacion;
import com.frgp.remember.Adaptadores.AdaptadorSeguimiento;
import com.frgp.remember.Base.Data.DatosBD;
import com.frgp.remember.Base.LogsUsuariosBD.LogsBD;
import com.frgp.remember.Entidades.Estados;
import com.frgp.remember.Entidades.Familiares;
import com.frgp.remember.Entidades.LogsUsuarios;
import com.frgp.remember.Entidades.Pacientes;
import com.frgp.remember.Entidades.Profesionales;
import com.frgp.remember.Entidades.TipoRol;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.IniciarSesion.iniciar_sesion;
import com.frgp.remember.Principal.MainActivity;
import com.frgp.remember.R;
import com.frgp.remember.Session.Session;
import com.frgp.remember.ui.Perfil.Raiz.PerfilFragment;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

//import frgp.utn.edu.ar.consultas_mysql.adapter.ClienteAdapter;
//import frgp.utn.edu.ar.consultas_mysql.entidad.Cliente;


public class UsuariosBD extends AsyncTask<String, Void, String> {


    private Usuarios user;
    private TipoRol tipo;
    private Estados esta;
    private String que_hacer;
    private Context context;
    private ProgressDialog dialog;
    private String mensaje_devuelto;
    private String tipo_rol;
    private String estado;
    private String matricula;
    private boolean logueo;
    private Pacientes pac;
    private Session ses;
    private TipoRol rol;
    private Boolean modifico;
    private boolean no_hay_usuarios;
    private ListView listUsuarios;
    private SearchView buscar;
    private TextView no_hay;
    private boolean familiar;
    private boolean medico,te_bloqueo;
    private CheckBox chkf;
    private CheckBox chkm;
    private ListView lista_seguimiento;
    private Bitmap image;

    private TextView perfil_nombre, perfil_usuario, perfil_rol, perfil_desde;
    private ImageView perfil_imagen;
    private Switch perfil_switch_vinculacion, perfil_switch_bloqueado;
    private Boolean perfil_vinculado, perfil_bloqueado;
    private LogsUsuarios userlog;

    private TextView no_hay_seguimiento;
    private SearchView buscar_seguimiento;
    private boolean no_hay_seg;

    private ImageView imgseguimiento;

    private static ArrayList<Usuarios> listaUsuarios = new ArrayList<Usuarios>();
    private static ArrayList<Bitmap> listaImagenes = new ArrayList<Bitmap>();
    private static ArrayList<LogsUsuarios> Listalogs = new ArrayList<LogsUsuarios>();

    public UsuariosBD(Usuarios us, Context ct, String que, String tiporol, String estados, String mat) {

        user = new Usuarios();
        this.user = us;
        this.context = ct;
        this.que_hacer = que;
        this.tipo_rol = tiporol;
        this.estado = estados;
        this.matricula = mat;
        dialog = new ProgressDialog(ct);
        this.ses = new Session();
    }

    public UsuariosBD(Usuarios us, Pacientes pa, Context ct, String que) {

        user = new Usuarios();
        pac = new Pacientes();
        this.user = us;
        this.pac = pa;
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
    }

    public UsuariosBD(ImageView img,Usuarios us, Context ct, String que) {

        this.imgseguimiento = img;
        user = new Usuarios();
        this.user = us;
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
    }

    public UsuariosBD(Context ct, String que, ListView lv, SearchView sv, TextView no, boolean f, boolean m, CheckBox chkf, CheckBox chkm) {

        this.ses = new Session();
        this.rol = new TipoRol();
        user = new Usuarios();
        pac = new Pacientes();
        this.listUsuarios = lv;
        this.buscar = sv;
        no_hay = no;
        this.chkf = chkf;
        this.chkm = chkm;
        this.medico = m;
        this.familiar = f;
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
        no_hay_usuarios = true;
    }

    public UsuariosBD(Context ct, String que){

        this.ses = new Session();
        dialog = new ProgressDialog(ct);
        this.context = ct;
        ses.setCt(ct);
        ses.cargar_session();
        this.que_hacer = que;

    }

    public UsuariosBD(Context ct, String que,ListView lv){

        this.ses = new Session();
        dialog = new ProgressDialog(ct);
        this.context = ct;
        ses.setCt(ct);
        ses.cargar_session();
        this.que_hacer = que;
        this.lista_seguimiento = lv;
    }

    public UsuariosBD(Context ct, String que,ListView lv,TextView no_hay_seguimiento, SearchView bus){

        this.ses = new Session();
        dialog = new ProgressDialog(ct);
        this.context = ct;
        ses.setCt(ct);
        ses.cargar_session();
        this.que_hacer = que;
        this.lista_seguimiento = lv;
        this.no_hay_seguimiento = no_hay_seguimiento;
        this.buscar_seguimiento = bus;
        this.no_hay_seg = true;
    }

    public UsuariosBD(Context ct, String que, Usuarios usu, TextView nom, TextView usua, ImageView img, TextView rol,
                      TextView desde, Switch vin, Switch bloque){

        this.user = new Usuarios();
        this.user = usu;
        this.perfil_nombre = nom;
        this.perfil_usuario = usua;
        this.perfil_imagen = img;
        this.perfil_rol = rol;
        this.perfil_desde = desde;
        this.perfil_switch_vinculacion = vin;
        this.perfil_switch_bloqueado = bloque;
        this.perfil_bloqueado = false;
        this.perfil_vinculado = false;
        this.esta = new Estados();
        this.ses = new Session();
        dialog = new ProgressDialog(ct);
        this.context = ct;
        ses.setCt(ct);
        ses.cargar_session();
        this.que_hacer = que;
        this.te_bloqueo = false;
    }

    public UsuariosBD(Context ct, String que,ListView lv,Usuarios usu){

        this.ses = new Session();
        dialog = new ProgressDialog(ct);
        this.context = ct;
        ses.setCt(ct);
        ses.cargar_session();
        this.que_hacer = que;
        this.lista_seguimiento = lv;
        this.user = new Usuarios();
        this.user=usu;

    }



    @Override
    protected String doInBackground(String... urls) {
        String response = "";
        mensaje_devuelto = "";
        PreparedStatement ps;
        PreparedStatement ps_aux = null;
        //String estado = "";
        logueo = false;


        if(que_hacer.equals("Insertar")) {

            boolean insertamos = true;
            modifico = false;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                //ps = con.prepareStatement("INSERT INTO usuarios(id, nombre,stock,alertastock,idCategoria) VALUES(?,?,?,?,?)");
                ps = con.prepareStatement("INSERT INTO usuarios (usuario, contraseña, nombre, " +
                        "apellido, dni, sexo, email, fecha_nacimiento, fecha_alta, tiporol, " +
                        "idestado, image) VALUES (?,?,?,?,?,?,?,?,CURRENT_DATE(),?,?,NULL)");

                Statement st = con.createStatement();
                ResultSet rs;
                ResultSet rs_;

                Log.d("ROL",""+ tipo_rol);

                rs = st.executeQuery("SELECT * FROM tiporol where rol='" + tipo_rol + "'");


                if(rs.next()){
                    tipo = new TipoRol();
                    tipo.setId_rol(rs.getInt("idtiporol"));
                    tipo.setRol(rs.getString("rol"));
                    user.setTipo(tipo);

                }

                rs = st.executeQuery("SELECT * FROM estados where estado='" + this.estado + "'");


                if(rs.next()){
                    esta = new Estados();
                    esta.setId_estado(rs.getInt("idestado"));
                    esta.setEstado(rs.getString("estado"));
                    user.setEstado(esta);

                }


                ps.setString(1, user.getUsuario());
                ps.setString(2, user.getContrasena());
                ps.setString(3, user.getNombre());
                ps.setString(4, user.getApellido());
                ps.setString(5, user.getDni());
                ps.setString(6, String.valueOf(user.getSexo()));
                ps.setString(7, user.getMail());
                ps.setDate(8, user.getFecha_nacimiento());
                //ps.setDate(9, user.getFecha_alta());
                ps.setInt(9, user.getTipo().getId_rol());
                ps.setInt(10, user.getEstado().getId_estado());


                rs = st.executeQuery("SELECT idusuario FROM usuarios where email ='" + user.getMail()+"'");

                if (rs.next()) {

                    insertamos = false;
                    mensaje_devuelto = "Mail ya registrado!";

                }

                rs = st.executeQuery("SELECT idusuario FROM usuarios where usuario ='" + user.getUsuario()+"'");

                if (rs.next()) {

                    insertamos = false;
                    mensaje_devuelto = "Usuario ya registrado!";

                }

                rs = st.executeQuery("SELECT idusuario FROM usuarios where dni ='" + user.getDni()+"'");

                if (rs.next()) {

                    insertamos = false;
                    mensaje_devuelto = "Dni ya registrado!";

                }


                if(insertamos){

                int filas = ps.executeUpdate();


                rs = st.executeQuery("SELECT * from usuarios where email ='" + user.getMail() + "' and" +
                        " usuario='"+user.getUsuario()+"'");

                if (rs.next()) {

                    user.setId_usuario(rs.getInt("idusuario"));

                }


                switch(tipo_rol){

                    case "Paciente":
                        Pacientes pac = new Pacientes();
                        pac.setId_usuario(user);
                        ps_aux = con.prepareStatement("INSERT INTO pacientes (idusuario, factors, peso, " +
                                "altura, medicocabecera) VALUES (?,NULL,NULL,NULL,NULL)");
                        ps_aux.setInt(1,pac.getId_usuario().getId_usuario());
                        break;

                    case "Supervisor":
                        Profesionales prof = new Profesionales();
                        prof.setId_usuario(user);
                        ps_aux = con.prepareStatement("INSERT INTO profesionales (idusuario, nromatricula)" +
                                " VALUES (?,?)");
                        ps_aux.setInt(1,prof.getId_usuario().getId_usuario());
                        ps_aux.setString(2, matricula);
                        break;

                    case "Familiar":
                        Familiares fam = new Familiares();
                        fam.setId_usuario(user);
                        ps_aux = con.prepareStatement("INSERT INTO familiares (idusuario, parentesco)" +
                                " VALUES (?,NULL)");
                        ps_aux.setInt(1,fam.getId_usuario().getId_usuario());
                        break;

                }

                    int filas_ = ps_aux.executeUpdate();


                    if (filas > 0 && filas_>0) {
                        mensaje_devuelto = "Usuario registrado!";
                        modifico = true;
                        if (tipo_rol.equals("Supervisor"))
                            mensaje_devuelto += ", debes aguardar confirmacion";
                    }
                    else
                        mensaje_devuelto = "Error al registrar usuario!";

                }
                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al registrar usuario!!";
            }
        }

        if(que_hacer.equals("Loguin")) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);

                Statement st = con.createStatement();
                ResultSet rs;
                ResultSet rs_;


                rs = st.executeQuery("SELECT e.estado FROM usuarios u inner join estados e" +
                        " on e.idestado = u.idestado where u.usuario='" + user.getUsuario() + "'");

                if(rs.next()){
                    estado = rs.getString(1);
                }
                else
                    mensaje_devuelto = "Usuario incorrecto";

                if(estado.equals("Activo")) {


                    rs = st.executeQuery("SELECT * FROM usuarios where usuario='" + user.getUsuario() + "'" +
                            " and contraseña='" + user.getContrasena() + "'");

                    int id_user;

                    if (rs.next()) {
                        logueo = true;
                        mensaje_devuelto = "Bienvenido/a: " + user.getUsuario();
                        ses.setId_usuario(rs.getInt("idusuario"));
                        ses.setContrasena(rs.getString("contraseña"));
                        ses.setUsuario(rs.getString("usuario"));
                        ses.setCt(context);

                        rs_ = st.executeQuery("SELECT * FROM tiporol t inner join usuarios u on t.idtiporol = u.tiporol" +
                                " where usuario='" + user.getUsuario() + "'");

                        if(rs_.next()){
                            ses.setTipo_rol(rs_.getString("rol"));

                        }
                        else{
                            mensaje_devuelto = "Error en tiporol!";
                        }

                        ses.nueva_session();
                    } else
                        mensaje_devuelto = "Contraseña incorrecta!";

                }else if(estado.equals("Pendiente"))
                    mensaje_devuelto = "Tu usuario esta pendiente de ingreso!";
                else if(estado.equals("Suspendido"))
                    mensaje_devuelto = "Tu usuario se encuentra suspendido!";

                response = "ok";

                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al loguin usuario!!";
            }
        }

        if(que_hacer.equals("Modificar")) {

            boolean insertamos = true;
            modifico = false;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                //ps = con.prepareStatement("INSERT INTO usuarios(id, nombre,stock,alertastock,idCategoria) VALUES(?,?,?,?,?)");
                ps = con.prepareStatement("UPDATE usuarios SET nombre=?, apellido=?, fecha_nacimiento=?, usuario=?," +
                        " contraseña=?, sexo=? where idusuario=?");

                Statement st = con.createStatement();
                ResultSet rs;
                ResultSet rs_;

                ps.setString(1, user.getNombre());
                ps.setString(2, user.getApellido());
                ps.setDate(3, user.getFecha_nacimiento());
                ps.setString(4, user.getUsuario());
                ps.setString(5, user.getContrasena());
                ps.setString(6, String.valueOf(user.getSexo()));
                ps.setInt(7, user.getId_usuario());


                rs = st.executeQuery("SELECT usuario FROM usuarios where idusuario <>" + user.getId_usuario()+ " and usuario='"+
                        user.getUsuario()+"'");

                if (rs.next()) {

                    insertamos = false;
                    mensaje_devuelto = "Usuario en uso!";

                }


                if(insertamos){

                    int filas = ps.executeUpdate();
                    int filas_;

                    Session ses = new Session();
                    ses.setCt(context);
                    ses.cargar_session();

                    if(ses.getTipo_rol().equals("Paciente")){

                        ps_aux = con.prepareStatement("UPDATE pacientes SET factors=?, peso=?, altura=?, medicocabecera=? " +
                                "where idusuario=?");

                        if(!pac.getFactorS().equals("Seleccionar"))
                            ps_aux.setString(1,pac.getFactorS());
                        else
                            ps_aux.setString(1,null);

                        if(pac.getPeso()!=0)
                            ps_aux.setFloat(2,pac.getPeso());
                        else
                            ps_aux.setNull(2, Types.FLOAT);

                        if(pac.getAltura()!=0)
                            ps_aux.setFloat(3,pac.getAltura());
                        else
                            ps_aux.setNull(3, Types.FLOAT);

                        if(!pac.getMedico_cabecera().isEmpty())
                            ps_aux.setString(4,pac.getMedico_cabecera());
                        else
                            ps_aux.setString(4,null);

                        ps_aux.setInt(5,pac.getId_usuario().getId_usuario());

                        filas_ = ps_aux.executeUpdate();

                        if (filas > 0 && filas_>0) {
                            mensaje_devuelto = "Datos modificados!";
                            modifico = true;
                        }
                        else
                            mensaje_devuelto = "Error al modificar los datos!";


                    }
                    else{

                        if (filas > 0) {
                            mensaje_devuelto = "Datos modificados!";
                            modifico = true;
                        }
                        else
                            mensaje_devuelto = "Error al modificar los datos!";

                    }

                }
                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al modificar usuario!!";
            }
        }

        if (que_hacer.equals("ListarUsuariosVinculaciones")) {

            String result2;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                Statement st = con.createStatement();
                ResultSet rs = null;

                listaUsuarios.clear();


                ses.setCt(context);
                ses.cargar_session();

                if(ses.getTipo_rol().equals("Paciente")) {
                    if(familiar && medico) {
                        rs = st.executeQuery("SELECT u.idusuario,u.nombre,u.apellido,u.usuario,u.tiporol,t.rol" +
                                " FROM usuarios u inner join tiporol t on u.tiporol = t.idtiporol where" +
                                " t.rol <>'" + ses.getTipo_rol() + "'");
                        Log.d("CONSULTA", "FAMILIARES Y PROFESIONALES");
                    }
                    else if(!familiar && medico) {
                        rs = st.executeQuery("SELECT u.idusuario,u.nombre,u.apellido,u.usuario,u.tiporol,t.rol" +
                                " FROM usuarios u inner join tiporol t on u.tiporol = t.idtiporol where" +
                                " t.rol ='Supervisor'");
                        Log.d("CONSULTA", "PROFESIONALES");
                    }
                    else {
                        rs = st.executeQuery("SELECT u.idusuario,u.nombre,u.apellido,u.usuario,u.tiporol,t.rol" +
                                " FROM usuarios u inner join tiporol t on u.tiporol = t.idtiporol where" +
                                " t.rol ='Familiar'");
                        Log.d("CONSULTA", "FAMILIARES");
                    }

                }
                    else
                    rs = st.executeQuery("SELECT u.idusuario,u.nombre,u.apellido,u.usuario,u.tiporol,t.rol" +
                            " FROM usuarios u inner join tiporol t on u.tiporol = t.idtiporol where" +
                            " t.rol ='Paciente'");


                while (rs.next()) {
                    user = new Usuarios();
                    tipo = new TipoRol();
                    no_hay_usuarios = false;
                    user.setNombre(rs.getString("nombre"));
                    user.setApellido(rs.getString("apellido"));
                    user.setId_usuario(rs.getInt("idusuario"));
                    user.setUsuario(rs.getString("usuario"));
                    tipo.setId_rol(rs.getInt("tiporol"));
                    tipo.setRol(rs.getString("rol"));
                    user.setTipo(tipo);
                    listaUsuarios.add(user);

                }


                response = "Conexion exitosa";
                con.close();
            } catch(Exception e){
                e.printStackTrace();
                result2 = "Conexion no exitosa";
            }


        }

        if (que_hacer.equals("ObtenerFotoDetSeguimiento")) {

            String result2;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                Statement st = con.createStatement();
                ResultSet rs = null;





                String add = "http://conscientious-calcu.000webhostapp.com/getImage.php?id="+user.getId_usuario();
                URL url = null;
                //Bitmap image = null;
                try {
                    url = new URL(add);
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                rs = st.executeQuery("SELECT image from usuarios where idusuario =" + user.getId_usuario());

                if (rs.next()) {

                    if(rs.getBlob(1)==null)
                        image = BitmapFactory.decodeResource(context.getResources(), R.mipmap.nodisponible);
                }


                response = "Conexion exitosa";
                con.close();
            } catch(Exception e){
                e.printStackTrace();
                result2 = "Conexion no exitosa";
            }


        }

        if (que_hacer.equals("Perfil")) {

            String result2;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);

                Statement st = con.createStatement();
                ResultSet rs = null;

                Statement st_ = con.createStatement();
                ResultSet rs_ = null;

                rs = st.executeQuery("SELECT * FROM  listaNegra where idUsuario=" + user.getId_usuario() + " and idUserBloqueado=" +
                        ses.getId_usuario());

                if(rs.next()){
                    mensaje_devuelto = "No puedes visualizar el perfil porque el usuario te ha bloqueado";
                    te_bloqueo = true;

                }


                rs = st.executeQuery("SELECT * from usuarios where idusuario=" + user.getId_usuario());


                if (rs.next()) {


                    rs_ = st_.executeQuery("SELECT rol from tiporol where idtiporol=" + rs.getInt("tiporol"));

                    if(rs_.next()){

                        this.tipo = new TipoRol();
                        tipo.setRol(rs_.getString(1));

                    }

                    rs_ = st_.executeQuery("SELECT idestado from estados where estado='Activa'");

                    if(rs_.next())
                        esta.setId_estado(rs_.getInt("idestado"));
                    else
                        mensaje_devuelto = "Error al buscar estado";

                    rs_ = st_.executeQuery("SELECT * from vinculaciones where idSupervisor=" + user.getId_usuario() + " and idPaciente=" +
                            ses.getId_usuario() + " and estadoVinculacion=" + esta.getId_estado());
                    if(rs_.next()) {
                        perfil_vinculado = true;
                    }
                    else
                        rs_ = st_.executeQuery("SELECT * from vinculaciones where idPaciente=" + user.getId_usuario() + " and idSupervisor=" +
                                ses.getId_usuario() + " and estadoVinculacion=" + esta.getId_estado());
                    if(rs_.next()){
                        perfil_vinculado = true;
                    }

                    rs_ = st_.executeQuery("SELECT * from listaNegra where idusuario=" +
                            ses.getId_usuario() + " and idUserBloqueado=" + user.getId_usuario());

                    if(rs_.next())
                        perfil_bloqueado = true;


                    user.setNombre(rs.getString("nombre"));
                    user.setApellido(rs.getString("apellido"));
                    user.setUsuario(rs.getString("usuario"));
                    user.setFecha_alta(rs.getDate("fecha_alta"));


                    String add = "http://conscientious-calcu.000webhostapp.com/getImage.php?id="+rs.getInt("idusuario");
                    URL url = null;
                    //Bitmap image = null;
                    try {
                        url = new URL(add);
                        image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(rs.getBlob("image")==null){

                        image = BitmapFactory.decodeResource(context.getResources(), R.mipmap.nodisponible);
                    }

                }


                response = "Conexion exitosa";
                con.close();
            } catch(Exception e){
                e.printStackTrace();
                result2 = "Conexion no exitosa";
            }


        }

        if (que_hacer.equals("Listar_logs")) {

            String result2;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                Statement st = con.createStatement();
                ResultSet rs = null;

                Listalogs.clear();

                rs = st.executeQuery("SELECT * FROM logs_usuarios WHERE idusuario = " + user.getId_usuario());


                while (rs.next()) {
                    userlog = new LogsUsuarios();
                    userlog.setId_usuario(rs.getInt("idusuario"));
                    userlog.setLogdesc(rs.getString("log_descripcion"));
                    userlog.setFecha_hora_log(rs.getTimestamp("fecha_hora_log"));


                    Listalogs.add(userlog);

                }


                response = "Conexion exitosa";
                con.close();
            } catch(Exception e){
                e.printStackTrace();
                result2 = "Conexion no exitosa";
            }


        }

        if (que_hacer.equals("ListarSeguimiento")) {

            String result2;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                Statement st = con.createStatement();
                ResultSet rs = null;

                listaUsuarios.clear();
                listaImagenes.clear();
                ses.setCt(context);
                ses.cargar_session();


                rs = st.executeQuery("SELECT usr.* \n" +
                        "FROM usuarios usr \n" +
                        "LEFT JOIN vinculaciones vnc \n" +
                        "ON usr.idusuario = vnc.idPaciente \n" +
                        "WHERE vnc.estadoVinculacion = 7 \n" +
                        "AND vnc.estadoSolicitud = 5 \n" +
                        "AND vnc.idSupervisor = " + ses.getId_usuario());


                while (rs.next()) {
                    no_hay_seg = false;
                    user = new Usuarios();
                    //no_hay_usuarios = false;
                    user.setNombre(rs.getString("nombre"));
                    user.setId_usuario(rs.getInt("idusuario"));
                    user.setApellido(rs.getString("apellido"));
                    user.setUsuario(rs.getString("usuario"));
                    user.setImagen(rs.getBlob("image"));


                    String add = "http://conscientious-calcu.000webhostapp.com/getImage.php?id="+rs.getInt("idusuario");
                    URL url = null;
                    //Bitmap image = null;
                    try {
                        url = new URL(add);
                        image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(rs.getBlob("image")==null){

                        image = BitmapFactory.decodeResource(context.getResources(), R.mipmap.nodisponible);
                    }

                    listaImagenes.add(image);
                    listaUsuarios.add(user);

                }


                response = "Conexion exitosa";
                con.close();
            } catch(Exception e){
                e.printStackTrace();
                result2 = "Conexion no exitosa";
            }


        }

        if(que_hacer.equals("EliminarFoto")) {

            modifico = false;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                //ps = con.prepareStatement("INSERT INTO usuarios(id, nombre,stock,alertastock,idCategoria) VALUES(?,?,?,?,?)");
                ps = con.prepareStatement("UPDATE usuarios SET image=NULL where idusuario=?");

                ps.setInt(1, ses.getId_usuario());

                    int filas = ps.executeUpdate();

                        if (filas > 0) {
                            mensaje_devuelto = "Imagen eliminada!";
                            modifico = true;
                        }
                        else
                            mensaje_devuelto = "Error al eliminar imagen!";


                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al eliminar imagen!";
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
            if(modifico) {
                Intent intent = new Intent(context, iniciar_sesion.class);
                context.startActivity(intent);
            }
        }

        if(que_hacer.equals("Perfil")) {

            if(!te_bloqueo) {

                perfil_nombre.setText(user.getNombre() + " " + user.getApellido());
                perfil_usuario.setText("Usuario: " + user.getUsuario());
                perfil_imagen.setImageBitmap(image);
                perfil_rol.setText(tipo.getRol().toUpperCase());
                perfil_desde.setText("En Remember me desde: " + user.getFecha_alta());

                if (perfil_vinculado)
                    perfil_switch_vinculacion.setChecked(true);
                else
                    perfil_switch_vinculacion.setChecked(false);

                if (perfil_bloqueado)
                    perfil_switch_bloqueado.setChecked(true);
                else
                    perfil_switch_bloqueado.setChecked(false);
            }
            else{

                Toast.makeText(context, mensaje_devuelto, Toast.LENGTH_SHORT).show();
                perfil_nombre.setText("Usuario no encontrado");
                perfil_usuario.setText("Usuario no encontrado");
                perfil_rol.setText("Usuario no encontrado");
                perfil_desde.setText("Usuario no encontrado");


                if (perfil_vinculado)
                    perfil_switch_vinculacion.setChecked(true);
                else
                    perfil_switch_vinculacion.setChecked(false);

                if (perfil_bloqueado)
                    perfil_switch_bloqueado.setChecked(true);
                else
                    perfil_switch_bloqueado.setChecked(false);


                image = BitmapFactory.decodeResource(context.getResources(), R.mipmap.nodisponible);
                perfil_imagen.setImageBitmap(image);

            }
        }

        if(que_hacer.equals("Modificar")) {
            Toast.makeText(context, mensaje_devuelto, Toast.LENGTH_SHORT).show();
            if(modifico){
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main, new PerfilFragment()).commit();
            }
        }

        if(que_hacer.equals("EliminarFoto")) {
            Toast.makeText(context, mensaje_devuelto, Toast.LENGTH_SHORT).show();
            if(modifico){
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main, new PerfilFragment()).commit();
            }
        }

        if(que_hacer.equals("Loguin")){
            if(logueo){
                    if(ses.getTipo_rol().equals("Paciente")){
                        LogsUsuarios log = new LogsUsuarios();
                        log.setLogdesc(""+ses.getUsuario()+" ha iniciado sesion.");
                        LogsBD userlog = new LogsBD(context,log,"NuevoLog");
                        userlog.execute();
                    }
                Toast.makeText(context,mensaje_devuelto,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
            else
                Toast.makeText(context,mensaje_devuelto,Toast.LENGTH_LONG).show();

            }

        if(que_hacer.equals("ObtenerFotoDetSeguimiento"))
        {
            imgseguimiento.setImageBitmap(image);

        }

        if(que_hacer.equals("Listar_logs")) {

            final AdaptadorLogs adapter = new AdaptadorLogs(context, Listalogs);
            lista_seguimiento.setAdapter(adapter);

        }

        if(que_hacer.equals("ListarUsuariosVinculaciones")) {
            final AdaptadorNuevaVinculacion adapter = new AdaptadorNuevaVinculacion(context, listaUsuarios);
            listUsuarios.setAdapter(adapter);

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

            if(ses.getTipo_rol().equals("Paciente")){
                chkm.setVisibility(View.VISIBLE);
                chkf.setVisibility(View.VISIBLE);
            }else{
                chkm.setVisibility(View.GONE);
                chkf.setVisibility(View.GONE);
            }

            if(no_hay_usuarios) {
                chkm.setVisibility(View.GONE);
                chkf.setVisibility(View.GONE);
                no_hay.setVisibility(View.VISIBLE);
                listUsuarios.setVisibility(View.GONE);
            }
            else if(ses.getTipo_rol().equals("Paciente")){
                chkm.setVisibility(View.VISIBLE);
                chkf.setVisibility(View.VISIBLE);
                no_hay.setVisibility(View.GONE);
                listUsuarios.setVisibility(View.VISIBLE);
            }else{
                chkm.setVisibility(View.GONE);
                chkf.setVisibility(View.GONE);
                no_hay.setVisibility(View.GONE);
                listUsuarios.setVisibility(View.VISIBLE);
            }

        }

        if(que_hacer.equals("ListarSeguimiento")) {

            final AdaptadorSeguimiento adapter = new AdaptadorSeguimiento(context, listaUsuarios, listaImagenes);
            lista_seguimiento.setAdapter(adapter);

            buscar_seguimiento.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

            if (no_hay_seg) {
                no_hay_seguimiento.setVisibility(View.VISIBLE);
                lista_seguimiento.setVisibility(View.GONE);
            } else {
                no_hay_seguimiento.setVisibility(View.GONE);
                lista_seguimiento.setVisibility(View.VISIBLE);
            }
        }

        }

    }


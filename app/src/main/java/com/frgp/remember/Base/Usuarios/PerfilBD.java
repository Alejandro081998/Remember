package com.frgp.remember.Base.Usuarios;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.frgp.remember.Adaptadores.AdaptadorContactos;
import com.frgp.remember.Adaptadores.AdaptadorUsuariosVinculaciones;
import com.frgp.remember.Base.Data.DatosBD;
import com.frgp.remember.Entidades.Contactos;
import com.frgp.remember.Entidades.Estados;
import com.frgp.remember.Entidades.Familiares;
import com.frgp.remember.Entidades.Pacientes;
import com.frgp.remember.Entidades.Profesionales;
import com.frgp.remember.Entidades.TipoRol;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.Entidades.Vinculaciones;
import com.frgp.remember.Principal.MainActivity;
import com.frgp.remember.R;
import com.frgp.remember.Session.Session;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

//import frgp.utn.edu.ar.consultas_mysql.adapter.ClienteAdapter;
//import frgp.utn.edu.ar.consultas_mysql.entidad.Cliente;


public class PerfilBD extends AsyncTask<String, Void, String> {


    private Usuarios user;
    private Usuarios usuarios_;
    private Pacientes pacien;
    private TipoRol tipo;
    private Vinculaciones vin;
    private Context context;
    private ProgressDialog dialog;
    private String mensaje_devuelto;
    private EditText mail;
    private EditText nombre;
    private EditText apellido;
    private EditText fechanacimiento;
    private RadioButton fem;
    private RadioButton mas;
    private EditText usuario;
    private EditText contrasena;
    private EditText repito;
    private EditText dni;
    private String que_hacer;
    private boolean logueo;
    private EditText peso;
    private EditText altura;
    private Spinner factor;
    private EditText medico;
    private TextView usu_home;
    private Session ses;
    private Boolean no_hay_vincu;
    private TextView no_hay;
    private ListView list_vinculaciones;
    private ImageButton imagen_perfil;
    private Bitmap image = null;
    private boolean imagen_nula;

    private static ArrayList<Usuarios> listaUsuarios = new ArrayList<Usuarios>();
    private static ArrayList<Vinculaciones> listaVinculaciones = new ArrayList<Vinculaciones>();

    public PerfilBD(Context ct, String que, EditText nom, EditText ape, EditText dni, EditText fechanac, RadioButton fem,
                    RadioButton mas, EditText mail,
                    EditText usu, EditText contra, EditText repito, EditText pes, EditText alt, Spinner fac, EditText med,
                    TextView no, ListView list, ImageButton img, TextView usu_mostrar) {

        listaVinculaciones.clear();
        //listaUsuarios.clear();
        user = new Usuarios();
        //usuarios_ = new Usuarios();
        pacien = new Pacientes();
        //vin = new Vinculaciones();
        ses = new Session();
        this.context = ct;
        this.nombre = nom;
        this.apellido = ape;
        this.fechanacimiento = fechanac;
        this.fem = fem;
        this.mas = mas;
        this.mail = mail;
        this.usuario = usu;
        this.contrasena = contra;
        this.repito = repito;
        this.que_hacer = que;
        this.dni = dni;
        this.peso = pes;
        this.altura = alt;
        this.factor = fac;
        this.medico = med;
        this.no_hay = no;
        this.list_vinculaciones = list;
        this.no_hay_vincu = true;
        this.imagen_perfil = img;
        this.usu_home = usu_mostrar;
        dialog = new ProgressDialog(ct);
    }

    @Override
    protected String doInBackground(String... urls) {
        String response = "";
        mensaje_devuelto = "";
        //PreparedStatement ps;
        //PreparedStatement ps_aux = null;
        int user_;
        //logueo = false;

        Log.d("ENTRO", "ENTRO MAS ARRIBA A CARGAR PERFIL");


        if (que_hacer.equals("Cargar")) {

            imagen_nula = false;

            Log.d("ENTRO", "ENTRO A CARGAR PERFIL");

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);

                Statement st = con.createStatement();
                ResultSet rs;
                ResultSet rs_ = null;
                ResultSet rss;


                Log.d("ENTRO", "ENTRO A REALIZAR CONSULTA PERFIL");


                ses.setCt(context);
                ses.cargar_session();
                user_ = ses.getId_usuario();


                rs = st.executeQuery("SELECT * from usuarios where idusuario =" + user_);

                if (rs.next()) {
                    user.setId_usuario(rs.getInt("idusuario"));
                    user.setNombre(rs.getString("nombre"));
                    user.setApellido(rs.getString("apellido"));
                    user.setDni(rs.getString("dni"));
                    user.setSexo(rs.getString("sexo").charAt(0));
                    user.setFecha_nacimiento(rs.getDate("fecha_nacimiento"));
                    user.setMail(rs.getString("email"));
                    user.setUsuario(rs.getString("usuario"));
                    user.setContrasena(rs.getString("contraseña"));
                    Log.d("ENCUENTRO", "Encontro al usuario");

                    if (ses.getTipo_rol().equals("Paciente")) {

                        pacien.setId_usuario(user);
                        rs = st.executeQuery("SELECT * from pacientes where idusuario =" + pacien.getId_usuario().getId_usuario());

                        if (rs.next()) {

                            pacien.setId_paciente(rs.getInt("idpaciente"));
                            pacien.setPeso(rs.getFloat("peso"));
                            pacien.setAltura(rs.getFloat("altura"));
                            pacien.setFactorS(rs.getString("factors"));
                            pacien.setMedico_cabecera(rs.getString("medicocabecera"));

                        } else
                            mensaje_devuelto = "Error al cargar datos paciente";
                    }

                    if (ses.getTipo_rol().equals("Paciente")) {

                        rs = st.executeQuery("SELECT * from estados where estado = 'Activa'");

                        if (rs.next())
                            rs = st.executeQuery("SELECT u.nombre,u.apellido,v.idVinculacion,v.idSupervisor,t.rol from usuarios u"+
                                    " inner join vinculaciones v on v.idSupervisor = u.idusuario inner join tiporol t on t.idtiporol = u.tiporol" +
                                    " where v.estadoVinculacion="+ rs.getInt("idestado") + " and v.idPaciente=" + ses.getId_usuario());

                        listaVinculaciones.clear();

                        while (rs.next()) {
                            usuarios_ = new Usuarios();
                            vin = new Vinculaciones();
                            tipo = new TipoRol();

                            tipo.setRol(rs.getString(5));


                            Log.d("ENTRO","VINCULACION PACIENTE");
                            no_hay_vincu = false;

                            usuarios_.setTipo(tipo);
                            usuarios_.setNombre(rs.getString(1));
                            usuarios_.setApellido(rs.getString(2));
                            vin.setId_vinculacion(rs.getInt(3));
                            vin.setId_supervisor(usuarios_);

                            listaVinculaciones.add(vin);
                            //listaUsuarios.add(user);

                        }


                    } else {

                        rs = st.executeQuery("SELECT * from estados where estado = 'Activa'");

                        if (rs.next())
                            rs = st.executeQuery("SELECT u.nombre,u.apellido,v.idVinculacion from usuarios u"+
                                    " inner join vinculaciones v on v.idPaciente = u.idusuario" +
                                    " where v.estadoVinculacion="+ rs.getInt("idestado") + " and v.idSupervisor=" + ses.getId_usuario());

                        listaVinculaciones.clear();

                        while (rs.next()) {
                            no_hay_vincu = false;
                            usuarios_ = new Usuarios();
                            vin = new Vinculaciones();
                            usuarios_.setNombre(rs.getString(1));
                            usuarios_.setApellido(rs.getString(2));
                            vin.setId_vinculacion(rs.getInt(3));
                            vin.setId_paciente(usuarios_);

                            listaVinculaciones.add(vin);
                            //listaUsuarios.add(usuarios_);

                        }

                    }

                    //String id = urls[0];
                    String add = "http://conscientious-calcu.000webhostapp.com/getImage.php?id="+ses.getId_usuario();
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

                    rs = st.executeQuery("SELECT image from usuarios where idusuario =" + user_);

                    if (rs.next()) {

                        if(rs.getBlob(1)==null)
                            image = BitmapFactory.decodeResource(context.getResources(), R.mipmap.nodisponible);
                    }


                } else
                    mensaje_devuelto = "Error al cargar perfil!";

                response = "ok";

                //user.setNombre("Alejandro");

                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al cargar perfil!";
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


        if (que_hacer.equals("Cargar")) {
            nombre.setText(user.getNombre());
            apellido.setText(user.getApellido());
            dni.setText(user.getDni());
            imagen_perfil.setImageBitmap(image);
            if (user.getSexo() == 'f') {
                fem.setChecked(true);
                mas.setChecked(false);
            } else {
                fem.setChecked(false);
                mas.setChecked(true);
            }


            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String nacimiento = sdf.format(user.getFecha_nacimiento());

            fechanacimiento.setText(nacimiento);
            usuario.setText(user.getUsuario());
            contrasena.setText(user.getContrasena());
            repito.setText(user.getContrasena());
            mail.setText(user.getMail());
            usu_home.setText(user.getUsuario());

            ses.setCt(context);
            ses.cargar_session();

            if (ses.getTipo_rol().equals("Paciente")) {

                if (pacien.getPeso() == 0)
                    peso.setText("");
                else
                    peso.setText("" + pacien.getPeso());
                if (pacien.getAltura() == 0)
                    altura.setText("");
                else
                    altura.setText("" + pacien.getAltura());
                if (pacien.getFactorS() == null)
                    pacien.setFactorS("Seleccionar");
                int seleccion = -1;
                switch (pacien.getFactorS()) {

                    case "Seleccionar":
                        seleccion = 0;
                        break;

                    case "A+":
                        seleccion = 1;
                        break;

                    case "A-":
                        seleccion = 2;
                        break;

                    case "B+":
                        seleccion = 3;
                        break;

                    case "B-":
                        seleccion = 4;
                        break;

                    case "AB+":
                        seleccion = 5;
                        break;

                    case "AB-":
                        seleccion = 6;
                        break;

                    case "0+":
                        seleccion = 7;
                        break;

                    case "0-":
                        seleccion = 8;
                        break;


                }
                factor.setSelection(seleccion);
                if (pacien.getMedico_cabecera() == null)
                    medico.setText("");
                else
                    medico.setText(pacien.getMedico_cabecera());

            }

            final AdaptadorUsuariosVinculaciones adapter = new AdaptadorUsuariosVinculaciones(context, listaVinculaciones);
            list_vinculaciones.setAdapter(adapter);
            if(no_hay_vincu) {
                no_hay.setVisibility(View.VISIBLE);
                list_vinculaciones.setVisibility(View.GONE);
            }
            else {
                no_hay.setVisibility(View.GONE);
                list_vinculaciones.setVisibility(View.VISIBLE);
            }


        }


    }


}
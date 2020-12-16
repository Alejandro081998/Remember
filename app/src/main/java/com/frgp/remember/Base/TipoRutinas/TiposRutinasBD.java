package com.frgp.remember.Base.TipoRutinas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.frgp.remember.Adaptadores.AdaptadorNuevaVinculacion;
import com.frgp.remember.Adaptadores.AdaptadorUsuariosVinculaciones;
import com.frgp.remember.Base.Data.DatosBD;
import com.frgp.remember.Entidades.Estados;
import com.frgp.remember.Entidades.Familiares;
import com.frgp.remember.Entidades.Pacientes;
import com.frgp.remember.Entidades.Profesionales;
import com.frgp.remember.Entidades.TipoRol;
import com.frgp.remember.Entidades.TipoRutinas;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.Entidades.Vinculaciones;
import com.frgp.remember.Principal.MainActivity;
import com.frgp.remember.R;
import com.frgp.remember.Session.Session;
import com.frgp.remember.ui.ListadoContactos.ListadoContactosFragment;
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


public class TiposRutinasBD extends AsyncTask<String, Void, String> {


    private TipoRutinas tipo;
    private Spinner spinner;
    private Context context;
    private String que_hacer;
    private ProgressDialog dialog;
    private Session ses;
    private String mensaje_devuelto;
    private Estados est;

    private static ArrayList<TipoRutinas> listaRutinas = new ArrayList<TipoRutinas>();
    private static ArrayList<String> datosSpinner = new ArrayList<String>();


    public TiposRutinasBD(Context ct, Spinner sp,String que) {

        datosSpinner.clear();
        ses = new Session();
        ses.setCt(ct);
        ses.cargar_session();
        this.est = new Estados();
        this.spinner = sp;
        this.context = ct;
        this.que_hacer = que;
        dialog = new ProgressDialog(ct);
    }


    @Override
    protected String doInBackground(String... urls) {
        String response = "";
        //mensaje_devuelto = "";
        PreparedStatement ps;
        PreparedStatement ps_aux = null;
        //String estado = "";


        if(que_hacer.equals("TraerTipos")) {

            boolean insertamos = true;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(DatosBD.urlMySQL, DatosBD.user, DatosBD.pass);
                //ps = con.prepareStatement("INSERT INTO usuarios(id, nombre,stock,alertastock,idCategoria) VALUES(?,?,?,?,?)");

                ResultSet rs;
                Statement st = con.createStatement();

                rs = st.executeQuery("SELECT idestado from estados where estado='Activo'");

                if(rs.next())
                    est.setId_estado(rs.getInt("idestado"));
                else
                    mensaje_devuelto = "Error al buscar estado";


                rs = st.executeQuery("SELECT * from tiporutinas where Estado=" +est.getId_estado());

                while(rs.next()){

                    datosSpinner.add(rs.getString("Descripcion"));

                }
                response = "Conexion exitosa";
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                //result2 = "Conexion no exitosa";
                mensaje_devuelto = "Error al buscar tipos de rutina!";
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


        if(que_hacer.equals("TraerTipos")) {

            spinner.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, datosSpinner));
        }


    }


}


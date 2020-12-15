package com.frgp.remember.Principal;

import android.content.Intent;
import android.os.Bundle;

import com.frgp.remember.Base.Notificaciones.NotificacionesBD;
import com.frgp.remember.Base.Rutinas.RutinasBD;
import com.frgp.remember.Base.Usuarios.UsuariosBD;
import com.frgp.remember.Dialogos.DialogoRecuperaciones;
import com.frgp.remember.Dialogos.EditarContacto;
import com.frgp.remember.Dialogos.NuevoContacto;
import com.frgp.remember.Entidades.Contactos;
import com.frgp.remember.Entidades.ListaNegra;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.R;
import com.frgp.remember.IniciarSesion.iniciar_sesion;
import com.frgp.remember.Servicio.SegundoPlano;
import com.frgp.remember.ServicioAlarmas.Alarma;
import com.frgp.remember.ServicioNotificaciones.Alarm;
import com.frgp.remember.Session.Session;
import com.frgp.remember.ui.Ayuda.AyudaFragment;
import com.frgp.remember.ui.ListaNegra.ListaNegraFragment;
import com.frgp.remember.ui.Notificaciones.Actividades.Calendario.CalendarioFragment;
import com.frgp.remember.ui.Notificaciones.Actividades.Hora.HoraFragment;
import com.frgp.remember.ui.Notificaciones.Actividades.Parentesco.ParentescoFragment;
import com.frgp.remember.ui.Notificaciones.Actividades.Refranero.RefraneroFragment;
import com.frgp.remember.ui.Notificaciones.Historico.NotificacionesHistoricoFragment;
import com.frgp.remember.ui.Notificaciones.Raiz.NotificacionesFragment;
import com.frgp.remember.ui.ListadoContactos.ListadoContactosFragment;
import com.frgp.remember.ui.Perfil.PerfilDetalle.PerfilDetalleFragment;
import com.frgp.remember.ui.Perfil.Raiz.PerfilFragment;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.frgp.remember.ui.Rutinas.Editar.EditarRutinaFragment;
import com.frgp.remember.ui.Rutinas.NuevaRutina.NuevaRutinaFragment;
import com.frgp.remember.ui.Rutinas.Raiz.RutinasFragment;
import com.frgp.remember.ui.Rutinas.Supervisores.SeleccionPacienteRutinaFragment;
import com.frgp.remember.ui.Seguimiento.Detalle.DetallePerfilFragment;
import com.frgp.remember.ui.Seguimiento.Raiz.SeguimientoFragment;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.frgp.remember.ui.Vinculaciones.Listado.ListadoVinculacionesFragment;
import com.frgp.remember.ui.Vinculaciones.ListadoProfesional.VinculacionesProfesionalFragment;
import com.frgp.remember.ui.Vinculaciones.NuevaVinculacion.VinculacionesFragment;
import com.frgp.remember.ui.Vinculaciones.Pendientes.VinculacionesPendientesFragment;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String APARTADO = "";
    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView nav_seguimiento;
    private TextView txt_nombre;
    private ImageView img;
    private Alarma rutinas;
    private Alarm notificaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Session ses = new Session();
        ses.setCt(this);
        ses.cargar_session();

        if(ses.getUsuario().equals("")){
            Intent intent = new Intent(this,iniciar_sesion.class);
            startActivity(intent);
        }

        //toolbar.setSubtitle(R.string.vista_parqueos);
        //setTitle(R.string.app_name);

        /*FloatingActionButton fab = findViewById(R.id.fab);
          fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        Bundle parametros = this.getIntent().getExtras();
        if (parametros != null) {
            APARTADO = getIntent().getExtras().getString("apartado");
        }


        Fragment fragment;

        switch (APARTADO){

            case "Vinculaciones Pendientes":
                 fragment = new VinculacionesPendientesFragment();
                break;

            case "ListadoVinculaciones":
                fragment = new ListadoVinculacionesFragment();
                break;

            case "ListadoProfesional":
                fragment = new VinculacionesProfesionalFragment();
                break;

            case "Rutinas":
                fragment = new RutinasFragment();
                break;

            case "":
                fragment = new PerfilFragment();
                break;

            default:
                 fragment = new PerfilFragment();
                break;

        }



        getSupportFragmentManager().beginTransaction().add(R.id.content_main, fragment).commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        txt_nombre = (TextView) hView.findViewById(R.id.txt_nombre_nav);
        img = (ImageView) hView.findViewById(R.id.img_cabecera);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
        navigationView.setItemIconTintList(null);

        rutinas = new Alarma();
        notificaciones = new Alarm();

        rutinas.setAlarm(getApplicationContext());
        notificaciones.setAlarm(getApplicationContext());



        /*RutinasBD rutinasBD = new RutinasBD(this,"VerificarRutinas");
        rutinasBD.execute();

        NotificacionesBD nt = new NotificacionesBD(this,"VerificarNotificaciones");
        nt.execute();*/

        txt_nombre.setText("Hola " + ses.getUsuario());

        Usuarios user = new Usuarios();
        user.setId_usuario(ses.getId_usuario());

        UsuariosBD us = new UsuariosBD(img,user,this,null,null,null,"ObtenerFotoDetSeguimiento");
        us.execute();


        if(!ses.getTipo_rol().equals("Paciente")){

            navigationView.getMenu().setGroupVisible(R.id.seguimiento,true);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //RECUPERA EL ITEM QUE SE ESTA PRESIONANDO
        int id = item.getItemId();

        if (id == R.id.lista_negra) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, new ListaNegraFragment()).commit();
        }

        return super.onOptionsItemSelected(item);

    }

    public void showFlotanteContacto() {

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_nuevo_contacto);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //      .setAction("Action", null).show();
                abrirDialogoContacto();
            }
        });

        fab.show(true);

    }

    public void showVinculaciones() {

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_vinculaciones);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main, new VinculacionesPendientesFragment()).commit();
            }
        });

        fab.show(true);

    }

    public void showMenu() {

        FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.menu_action);

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab_redireccion_vinculaciones);

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //      .setAction("Action", null).show();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main, new ListadoVinculacionesFragment()).commit();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_redireccion_contactos);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //      .setAction("Action", null).show();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main, new ListadoContactosFragment()).commit();
            }
        });


        menu.showMenu(true);

    }



    public void abrirDialogoContacto() {

        NuevoContacto dialog = new NuevoContacto();
        dialog.show(getSupportFragmentManager(), "");

    }

    public void abrirDialogoContacto(Contactos c) {

        EditarContacto dialog = new EditarContacto(c);
        dialog.show(getSupportFragmentManager(), "");

    }

    public void hideMenu() {
        FloatingActionMenu menu = (FloatingActionMenu) findViewById(R.id.menu_action);

        menu.hideMenu(true);

    }


    public void hideFlotanteContacto() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_nuevo_contacto);
        fab.hide(true);

    }

    public void hideVinculaciones() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_vinculaciones);
        fab.hide(true);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        Log.d("Navigation", "Item selected : " + menuItem.getItemId());

        FragmentManager fragmentManager = getSupportFragmentManager();

        Session ses = new Session();
        ses.setCt(this);
        ses.cargar_session();

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        if (id == R.id.nav_perfil) {
            fragmentManager.beginTransaction().replace(R.id.content_main, new PerfilFragment()).commit();
            //toolbar.setSubtitle(R.string.vista_parqueos);
        } else if (id == R.id.nav_rutinas) {
            if(ses.getTipo_rol().equals("Paciente"))
                fragmentManager.beginTransaction().replace(R.id.content_main, new RutinasFragment()).commit();
            else
                fragmentManager.beginTransaction().replace(R.id.content_main, new VinculacionesProfesionalFragment()).commit();
            //toolbar.setSubtitle(R.string.vista_micuenta);
        } else if (id == R.id.nav_notificaciones) {
            //NotificacionesFragment entre = new NotificacionesFragment();
            fragmentManager.beginTransaction().replace(R.id.content_main, new NotificacionesFragment()).commit();
            //toolbar.setSubtitle(R.string.vista_micuenta);
        } else if (id == R.id.nav_cerrar) {
            ses.setCt(this);
            ses.cerrar_session();
            notificaciones.cancelAlarm(getApplicationContext());
            rutinas.cancelAlarm(getApplicationContext());
            Intent intent = new Intent(this, iniciar_sesion.class);
            startActivity(intent);
        } else if (id == R.id.nav_seguimiento) {
            fragmentManager.beginTransaction().replace(R.id.content_main, new SeguimientoFragment()).commit();
        } else if (id == R.id.nav_ayuda) {
            DialogoRecuperaciones dialog = new DialogoRecuperaciones();
            dialog.show(getSupportFragmentManager(), "");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

            }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
        public void onBackPressed() {

        Session ses = new Session();
        ses.setCt(this);
        ses.cargar_session();

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_main);
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(currentFragment instanceof ListadoContactosFragment){
            fragmentManager.beginTransaction().replace(R.id.content_main, new PerfilFragment()).commit();
        }
        else if(currentFragment instanceof CalendarioFragment){
            fragmentManager.beginTransaction().replace(R.id.content_main, new NotificacionesFragment()).commit();
        }
        else if(currentFragment instanceof HoraFragment){
            fragmentManager.beginTransaction().replace(R.id.content_main, new NotificacionesFragment()).commit();
        }
        else if(currentFragment instanceof ParentescoFragment){
            fragmentManager.beginTransaction().replace(R.id.content_main, new NotificacionesFragment()).commit();
        }
        else if(currentFragment instanceof RefraneroFragment){
            fragmentManager.beginTransaction().replace(R.id.content_main, new NotificacionesFragment()).commit();
        }
        else if(currentFragment instanceof ListadoVinculacionesFragment){
            fragmentManager.beginTransaction().replace(R.id.content_main, new PerfilFragment()).commit();
        }
        else if(currentFragment instanceof VinculacionesFragment){
            fragmentManager.beginTransaction().replace(R.id.content_main, new PerfilFragment()).commit();
        }
        else if(currentFragment instanceof DetallePerfilFragment){
            fragmentManager.beginTransaction().replace(R.id.content_main, new SeguimientoFragment()).commit();
        }
        else if(currentFragment instanceof VinculacionesPendientesFragment){
            fragmentManager.beginTransaction().replace(R.id.content_main, new VinculacionesFragment()).commit();
        }
        else if(currentFragment instanceof VinculacionesProfesionalFragment){
            fragmentManager.beginTransaction().replace(R.id.content_main, new PerfilFragment()).commit();
        }
        else if(currentFragment instanceof PerfilDetalleFragment){
            if(ses.getTipo_rol().equals("Paciente"))
            fragmentManager.beginTransaction().replace(R.id.content_main, new ListadoVinculacionesFragment()).commit();
            else
                fragmentManager.beginTransaction().replace(R.id.content_main, new VinculacionesProfesionalFragment()).commit();
        }
        else if(currentFragment instanceof ListaNegraFragment){
            if(ses.getTipo_rol().equals("Paciente"))
                fragmentManager.beginTransaction().replace(R.id.content_main, new ListadoVinculacionesFragment()).commit();
            else
                fragmentManager.beginTransaction().replace(R.id.content_main, new VinculacionesProfesionalFragment()).commit();
        }
        else if(currentFragment instanceof NuevaRutinaFragment){
            if(ses.getTipo_rol().equals("Paciente"))
                fragmentManager.beginTransaction().replace(R.id.content_main, new RutinasFragment()).commit();
            else
                fragmentManager.beginTransaction().replace(R.id.content_main, new VinculacionesProfesionalFragment()).commit();
        }

        else if(currentFragment instanceof EditarRutinaFragment){
            if(ses.getTipo_rol().equals("Paciente"))
                fragmentManager.beginTransaction().replace(R.id.content_main, new RutinasFragment()).commit();
            else
                fragmentManager.beginTransaction().replace(R.id.content_main, new VinculacionesProfesionalFragment()).commit();
        }

        else if(currentFragment instanceof RutinasFragment){
            if(!ses.getTipo_rol().equals("Paciente"))
                fragmentManager.beginTransaction().replace(R.id.content_main, new VinculacionesProfesionalFragment()).commit();
        }
        else if(currentFragment instanceof NotificacionesHistoricoFragment){
            fragmentManager.beginTransaction().replace(R.id.content_main, new NotificacionesFragment()).commit();
        }
        else{
            Toast.makeText(this, "Para salir cierra la sesion desde el menu!", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}

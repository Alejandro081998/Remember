package com.frgp.remember.IniciarSesion;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.frgp.remember.Base.Notificaciones.NotificacionesBD;
import com.frgp.remember.Base.Usuarios.UsuariosBD;
import com.frgp.remember.Dialogos.DialogoRecuperaciones;
import com.frgp.remember.Dialogos.DialogoRecuperoMensaje;
import com.frgp.remember.Entidades.Notificaciones;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.Principal.MainActivity;
import com.frgp.remember.Registrarse.registrarse;
import com.frgp.remember.RecuperarAcceso.recuperar_acceso;
import com.frgp.remember.R;
import com.frgp.remember.Servicio.SegundoPlano;
import com.frgp.remember.Session.Session;

public class iniciar_sesion extends AppCompatActivity {

    private CheckBox rememberme;
    private EditText txt_usuario;
    private EditText txt_contrasena;
    private Usuarios user;
    private String recuerdo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iniciar_sesion);

        txt_contrasena = (EditText) findViewById(R.id.txt_contrasena_login);
        txt_usuario = (EditText) findViewById(R.id.txt_usuario_login);
        rememberme = (CheckBox) findViewById(R.id.chkRecordar);

        SharedPreferences preferences = getSharedPreferences("recordar", Context.MODE_PRIVATE);
        txt_usuario.setText(preferences.getString("usuario", ""));
        txt_contrasena.setText(preferences.getString("contrasena", ""));
        recuerdo = preferences.getString("recuerdo", "no");

        if (recuerdo.equals("si"))
            rememberme.setChecked(true);
        else
            rememberme.setChecked(false);

        Session ses = new Session();
        ses.setCt(this);
        ses.cargar_session();

        //startService(new Intent(this, SegundoPlano.class));


       /* if(ses.getContrasena().length() == 0){

            stopService(new Intent(this, SegundoPlano.class));

        }*/


        if(ses.getContrasena().length() != 0) {

            Usuarios user = new Usuarios();

            user.setUsuario(ses.getUsuario());
            user.setContrasena(ses.getContrasena());

            Log.d("USUARIOLOGUEADO:", ses.getUsuario());
            Log.d("CONTRASEÑALOGUEADO:" , ses.getContrasena());

            //UsuariosBD userbd = new UsuariosBD(user, this, "Loguin", "", "", "");
            //userbd.execute();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);


        }




        if (ActivityCompat.checkSelfPermission(iniciar_sesion.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (iniciar_sesion.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(iniciar_sesion.this, new String[]{
                    Manifest.permission.SEND_SMS,
            }, 1000);
        else {
        }



    }

    public void redireccion_registrarse(View view) {
        Intent intent = new Intent(this, registrarse.class);
        startActivity(intent);

    }

    public void redireccion_recuperar(View view) {
        DialogoRecuperoMensaje dialog = new DialogoRecuperoMensaje();
        dialog.show(getSupportFragmentManager(), "");

    }

    public boolean enviar_datos(String numero, String contrasena, String usuario) {

        return recuperar_contrasena(numero, "Tu datos de acceso son: \n" +
                " Usuario: " + usuario + "\n" +
                " Contraseña: " + contrasena);


    }

    private boolean recuperar_contrasena(String numero, String mensaje) {

        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(numero, null, mensaje, null, null);
            Toast.makeText(this, "Mensaje enviado!", Toast.LENGTH_SHORT).show();
            return true;
        } catch (Exception e) {
            Toast.makeText(this, "Error al enviar!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        }


    }


    public void iniciar_sesion(View view){


        String usuario = txt_usuario.getText().toString();
        String contrasena = txt_contrasena.getText().toString();



        if(!usuario.isEmpty() && !contrasena.isEmpty()) {

            user = new Usuarios();
            user.setUsuario(usuario);
            user.setContrasena(contrasena);


            if (rememberme.isChecked()) {
                SharedPreferences preferencias = getSharedPreferences("recordar", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("usuario", usuario);
                editor.putString("contrasena", contrasena);
                editor.putString("recuerdo", "si");
                editor.apply();
            } else {
                SharedPreferences preferencias = getSharedPreferences("recordar", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("correo", "");
                editor.putString("contrasena", "");
                editor.putString("recuerdo", "no");
                editor.apply();
            }

            UsuariosBD userbd = new UsuariosBD(user, this, "Loguin", "", "", "");
            userbd.execute();

        }
        else
            Toast.makeText(this,"Complete todos los campos!",Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ExitActivity.exitApplication(this);
    }
}

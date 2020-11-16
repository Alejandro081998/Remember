package com.frgp.remember.Registrarse;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.frgp.remember.Base.Usuarios.UsuariosBD;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.Principal.MainActivity;
import com.frgp.remember.R;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class registrar_datos extends AppCompatActivity {

    private EditText matricula;
    private String matricula_mostrar;
    private String rol_registro;
    private EditText nombres;
    private EditText apellidos;
    private EditText fecha_nacimiento;
    private RadioButton radio_femenino;
    private RadioButton radio_masculino;
    private EditText dni;
    private EditText mail;
    private EditText usuario;
    private EditText contrasena;
    private EditText confirmar_contrasena;
    private Button btn_registro;
    private int dia;
    private int mes;
    private int año;
    private TextView txt_matricula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_datos);

        matricula = (EditText) findViewById(R.id.txt_matricula);
        nombres = (EditText) findViewById(R.id.txt_nombre);
        apellidos = (EditText) findViewById(R.id.txt_apellido);
        fecha_nacimiento = (EditText) findViewById(R.id.txt_fechanacimiento);
        radio_femenino = (RadioButton) findViewById(R.id.radio_femenino);
        radio_masculino = (RadioButton) findViewById(R.id.radio_masculino);
        dni = (EditText) findViewById(R.id.txt_dni);
        mail = (EditText) findViewById(R.id.txt_mail);
        usuario = (EditText) findViewById(R.id.txt_usuario);
        contrasena = (EditText) findViewById(R.id.txt_contrasena);
        confirmar_contrasena = (EditText) findViewById(R.id.txt_repito);
        btn_registro = (Button) findViewById(R.id.btn_registro);
        txt_matricula = (TextView) findViewById(R.id.txt_matricula_numero);


        Bundle extras = getIntent().getExtras();
        matricula_mostrar = (extras.getString("Matricula"));
        rol_registro = (extras.getString("Rol"));

        if(matricula_mostrar.equals("si")) {
            matricula.setVisibility(View.VISIBLE);
            txt_matricula.setVisibility(View.VISIBLE);
        }
        else {
            matricula.setVisibility(View.GONE);
            txt_matricula.setVisibility(View.GONE);
        }

        btn_registro.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                try {
                    registro();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private boolean validarFecha(String dia, String mes, String ano) {


        for (int i = 0; i <= 3; i++) {

            if (i <= 1) {
                if (Character.isDigit(dia.charAt(i)) == false) {
                    Toast.makeText(this, "Formato incorrecto de fecha!", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (Character.isDigit(mes.charAt(i)) == false) {
                    Toast.makeText(this, "Formato incorrecto de fecha!", Toast.LENGTH_LONG).show();
                    return false;
                }

                if (Character.isDigit(ano.charAt(i)) == false) {
                    Toast.makeText(this, "Formato incorrecto de fecha!", Toast.LENGTH_LONG).show();
                    return false;
                }

                if (i >= 2) {
                    if (Character.isDigit(ano.charAt(i)) == false) {
                        Toast.makeText(this, "Formato incorrecto de fecha!", Toast.LENGTH_LONG).show();
                        return false;
                    }
                }


            }
        }


        int dia_ = Integer.parseInt(dia);
        int mes_ = Integer.parseInt(mes);
        int ano_ = Integer.parseInt(ano);

        if (dia.length() > 2 || dia.length() < 2) {
            Toast.makeText(this, "Formato incorrecto de fecha!", Toast.LENGTH_LONG).show();
            return false;
        } else if (dia_ < 1 || dia_ > 31) {
            Toast.makeText(this, "Fecha incorrecta!", Toast.LENGTH_LONG).show();
            return false;
        }

        if (mes.length() > 2 || mes.length() < 2) {
            Toast.makeText(this, "Formato incorrecto de fecha!", Toast.LENGTH_LONG).show();
            return false;
        } else if (mes_ < 1 || mes_ > 12) {
            Toast.makeText(this, "Fecha incorrecta!", Toast.LENGTH_LONG).show();
            return false;
        }

        if (ano.length() > 4 || ano.length() < 4) {
            Toast.makeText(this, "Formato incorrecto de fecha!", Toast.LENGTH_LONG).show();
            return false;
        } else if (ano_ < 1900 || ano_ > 2019) {
            Toast.makeText(this, "Fecha incorrecta!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;


    }

    private void fecha_sistema(){

        java.util.Date fecha = new Date();
        dia = fecha.getDay();
        mes = fecha.getMonth();
        año = fecha.getYear();

    }

    private boolean comparar_fechas(String fecha){

        DateTimeFormatter fmt = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        }
        LocalDate fechaNac = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            fechaNac = LocalDate.parse(fecha, fmt);
        }
        LocalDate ahora = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ahora = LocalDate.now();
        }

        Period periodo = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            periodo = Period.between(fechaNac, ahora);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if(periodo.getYears()>=18)
                return true;
            else
                return false;
        }

        return true;
    }


    public void imprimir_mensaje(Context ct, String mensaje){

        Toast.makeText(ct,mensaje,Toast.LENGTH_SHORT).show();

        nombres.setText("");
        /*apellidos.setText("");
        fecha_nacimiento.setText("");
        dni.setText("");
        mail.setText("");
        usuario.setText("");
        contrasena.setText("");
        confirmar_contrasena.setText("");*/


    }

    private void registro() throws ParseException {

        String nombres_ = nombres.getText().toString();
        String apellidos_ = apellidos.getText().toString();
        String fecha_nacimiento_ = fecha_nacimiento.getText().toString();
        String dni_ = dni.getText().toString();
        String mail_ = mail.getText().toString();
        String usuario_ = usuario.getText().toString();
        String contrasena_ = contrasena.getText().toString();
        String confirmar_ = confirmar_contrasena.getText().toString();
        String matricula_ = matricula.getText().toString();

        boolean validacion = true;

        if(matricula_mostrar.equals("si")){

            if(!nombres_.isEmpty() && !apellidos_.isEmpty() && !fecha_nacimiento_.isEmpty() && !dni_.isEmpty()
            && !mail_.isEmpty() && !usuario_.isEmpty() && !contrasena_.isEmpty() && !confirmar_.isEmpty() &&
             !matricula_.isEmpty()){

                if(nombres_.length()< 3){
                    Toast.makeText(this,"El nombre debe contar con al menos 3 caracteres!",Toast.LENGTH_SHORT).show();
                    validacion = false;
                }

                if(nombres_.length()> 43){
                    Toast.makeText(this,"El nombre es muy largo!",Toast.LENGTH_SHORT).show();
                    validacion = false;
                }

                if(apellidos_.length()< 3){
                    Toast.makeText(this,"El apellido debe contar con al menos 3 caracteres!",Toast.LENGTH_SHORT).show();
                    validacion = false;
                }

                if(apellidos_.length()> 43){
                    Toast.makeText(this,"El apellido es muy largo!",Toast.LENGTH_SHORT).show();
                    validacion = false;
                }

                if(dni_.length()<= 6){
                    Toast.makeText(this,"Dni incorrecto!",Toast.LENGTH_SHORT).show();
                    validacion = false;
                }

                if(dni_.length()> 8){
                    Toast.makeText(this,"Dni Incorrecto!",Toast.LENGTH_SHORT).show();
                    validacion = false;
                }

                if (!validarEmail(mail_)) {
                    Toast.makeText(this, "Correo no valido!", Toast.LENGTH_SHORT).show();
                    validacion = false;
                }

                if(usuario_.length()< 3){
                    Toast.makeText(this,"El usuario debe contar con al menos 3 caracteres!",Toast.LENGTH_SHORT).show();
                    validacion = false;
                }

                if(usuario_.length()> 10){
                    Toast.makeText(this,"El usuario es muy largo!",Toast.LENGTH_SHORT).show();
                    validacion = false;
                }

                if(contrasena_.length()!= 8){
                    Toast.makeText(this,"La contraseña debe contar con 8 caracteres!",Toast.LENGTH_SHORT).show();
                    validacion = false;
                }

                if(confirmar_.length()!= 8){
                    Toast.makeText(this,"La contraseña debe contar con 8 caracteres!",Toast.LENGTH_SHORT).show();
                    validacion = false;
                }

                if(!confirmar_.equals(contrasena_)){
                    Toast.makeText(this,"Las contraseñas no coinciden!",Toast.LENGTH_SHORT).show();
                    validacion = false;
                }

                if(matricula_.length() < 6){
                    Toast.makeText(this,"La matricula debe contar con al menos 6 caracteres!",Toast.LENGTH_SHORT).show();
                    validacion = false;
                }

                if(matricula_.length() > 15){
                    Toast.makeText(this,"La matricula es muy larga!",Toast.LENGTH_SHORT).show();
                    validacion = false;
                }

                if (fecha_nacimiento_.length() == 10) {
                    String dia = fecha_nacimiento_.substring(0, 2);
                    String mes = fecha_nacimiento_.substring(3, 5);
                    String ano = fecha_nacimiento_.substring(6, 10);
                    if(validarFecha(dia, mes, ano) ==false) {
                        validacion = false;
                        Toast.makeText(this, "Formato incorrecto de fecha!", Toast.LENGTH_LONG).show();
                    }
                    else if (!comparar_fechas(fecha_nacimiento_)) {
                        Toast.makeText(this, "Debes ser mayor de edad!", Toast.LENGTH_LONG).show();
                        validacion = false;
                    }
                } else {
                    Toast.makeText(this, "Formato incorrecto de fecha!", Toast.LENGTH_LONG).show();
                    validacion = false;
                }


                    if(!radio_masculino.isChecked() && !radio_femenino.isChecked()){
                        Toast.makeText(this, "Selecciona tu sexo!", Toast.LENGTH_LONG).show();
                        validacion = false;
                    }


                    if(validacion){

                        char sex;

                        if(radio_femenino.isChecked())
                            sex = 'f';
                        else
                            sex = 'm';

                        java.util.Date fechanac=new SimpleDateFormat("dd/MM/yyyy").parse(fecha_nacimiento_);

                        fecha_sistema();

                        String fechaalta = ""+dia + "/" + ""+mes + "/" + año;

                        java.util.Date fechaaltasistema=new SimpleDateFormat("dd/MM/yyyy").parse(fechaalta);

                        java.sql.Date sqlfechanac = new java.sql.Date(fechanac.getTime());
                        java.sql.Date sqlfechaalta = new java.sql.Date(fechaaltasistema.getTime());

                        Usuarios user;

                        user = new Usuarios();

                        user.setUsuario(usuario_);
                        user.setContrasena(contrasena_);
                        user.setNombre(nombres_);
                        user.setApellido(apellidos_);
                        user.setDni(dni_);
                        user.setSexo(sex);
                        user.setMail(mail_);
                        user.setFecha_nacimiento(sqlfechanac);
                        user.setFecha_alta(sqlfechaalta);

                        UsuariosBD userbd = new UsuariosBD(user,this,"Insertar",rol_registro,"Pendiente",matricula_);
                        userbd.execute();

                    }


            }else{

                Toast.makeText(this,"Complete todos los campos!",Toast.LENGTH_SHORT).show();

            }


        }else{

            if(!nombres_.isEmpty() && !apellidos_.isEmpty() && !fecha_nacimiento_.isEmpty() && !dni_.isEmpty()
                    && !mail_.isEmpty() && !usuario_.isEmpty() && !contrasena_.isEmpty() && !confirmar_.isEmpty()){


                if(nombres_.length()< 3){
                    Toast.makeText(this,"El nombre debe contar con al menos 3 caracteres!",Toast.LENGTH_SHORT).show();
                    validacion = false;
                }

                if(nombres_.length()> 43){
                    Toast.makeText(this,"El nombre es muy largo!",Toast.LENGTH_SHORT).show();
                    validacion = false;
                }

                if(apellidos_.length()< 3){
                    Toast.makeText(this,"El apellido debe contar con al menos 3 caracteres!",Toast.LENGTH_SHORT).show();
                    validacion = false;
                }

                if(apellidos_.length()> 43){
                    Toast.makeText(this,"El apellido es muy largo!",Toast.LENGTH_SHORT).show();
                    validacion = false;
                }

                if(dni_.length()<= 6){
                    Toast.makeText(this,"Dni incorrecto!",Toast.LENGTH_SHORT).show();
                    validacion = false;
                }

                if(dni_.length()> 8){
                    Toast.makeText(this,"Dni incorrecto!",Toast.LENGTH_SHORT).show();
                    validacion = false;
                }

                if (!validarEmail(mail_)) {
                    Toast.makeText(this, "Correo no valido!", Toast.LENGTH_SHORT).show();
                    validacion = false;
                }

                if(usuario_.length()< 3){
                    Toast.makeText(this,"El usuario debe contar con al menos 3 caracteres!",Toast.LENGTH_SHORT).show();
                    validacion = false;
                }

                if(usuario_.length()> 10){
                    Toast.makeText(this,"El usuario es muy largo!",Toast.LENGTH_SHORT).show();
                    validacion = false;
                }

                if(contrasena_.length()!= 8){
                    Toast.makeText(this,"La contraseña debe contar con 8 caracteres!",Toast.LENGTH_SHORT).show();
                    validacion = false;
                }

                if(confirmar_.length()!= 8){
                    Toast.makeText(this,"La contraseña debe contar con 8 caracteres!",Toast.LENGTH_SHORT).show();
                    validacion = false;
                }

                if(!confirmar_.equals(contrasena_)){
                    Toast.makeText(this,"Las contraseñas no coinciden!",Toast.LENGTH_SHORT).show();
                    validacion = false;
                }

                if (fecha_nacimiento_.length() == 10) {
                    String dia = fecha_nacimiento_.substring(0, 2);
                    String mes = fecha_nacimiento_.substring(3, 5);
                    String ano = fecha_nacimiento_.substring(6, 10);
                    if(validarFecha(dia, mes, ano) ==false) {
                        validacion = false;
                        Toast.makeText(this, "Formato incorrecto de fecha!", Toast.LENGTH_LONG).show();
                    }
                    else if (!comparar_fechas(fecha_nacimiento_)) {
                            Toast.makeText(this, "Debes ser mayor de edad!", Toast.LENGTH_LONG).show();
                            validacion = false;
                        }
                } else {
                    Toast.makeText(this, "Formato incorrecto de fecha!", Toast.LENGTH_LONG).show();
                    validacion = false;
                }


                if(!radio_masculino.isChecked() && !radio_femenino.isChecked()){
                    Toast.makeText(this, "Selecciona tu sexo!", Toast.LENGTH_LONG).show();
                    validacion = false;
                }


                if(validacion){

                    char sex;

                    if(radio_femenino.isChecked())
                        sex = 'f';
                    else
                        sex = 'm';

                    java.util.Date fechanac=new SimpleDateFormat("dd/MM/yyyy").parse(fecha_nacimiento_);

                    fecha_sistema();

                    String fechaalta = ""+dia + "/" + ""+mes + "/" + año;

                    java.util.Date fechaaltasistema=new SimpleDateFormat("dd/MM/yyyy").parse(fechaalta);

                    java.sql.Date sqlfechanac = new java.sql.Date(fechanac.getTime());
                    java.sql.Date sqlfechaalta = new java.sql.Date(fechaaltasistema.getTime());

                    Usuarios user;

                    user = new Usuarios();

                    user.setUsuario(usuario_);
                    user.setContrasena(contrasena_);
                    user.setNombre(nombres_);
                    user.setApellido(apellidos_);
                    user.setDni(dni_);
                    user.setSexo(sex);
                    user.setMail(mail_);
                    user.setFecha_nacimiento(sqlfechanac);
                    user.setFecha_alta(sqlfechaalta);

                    UsuariosBD userbd = new UsuariosBD(user,this,"Insertar",rol_registro,"Activo","");
                    userbd.execute();

                }



            }else{

                Toast.makeText(this,"Complete todos los campos!",Toast.LENGTH_SHORT).show();

            }


        }

    }

}

package com.frgp.remember.ui.Perfil.Raiz;

import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.frgp.remember.Base.Usuarios.PerfilBD;
import com.frgp.remember.Base.Usuarios.UsuariosBD;
import com.frgp.remember.Dialogos.DialogoEditarFoto;
import com.frgp.remember.Entidades.Pacientes;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.Principal.MainActivity;
import com.frgp.remember.R;
import com.frgp.remember.Session.Session;
import com.frgp.remember.ui.Vinculaciones.Listado.ListadoVinculacionesFragment;
import com.frgp.remember.ui.Vinculaciones.ListadoProfesional.VinculacionesProfesionalFragment;
import com.frgp.remember.ui.Vinculaciones.NuevaVinculacion.VinculacionesFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class PerfilFragment extends Fragment {

    private PerfilViewModel perfilViewModel;
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
    private Usuarios user;
    private TableLayout apartado_paciente;
    private Button btn_actualizar;
    private Pacientes pac;
    private EditText peso;
    private EditText altura;
    private Spinner factor;
    private EditText medico;
    private TableLayout apartado_ingreso;
    private Button btn_ficha;
    private Button btn_ingresos;
    private ImageButton btn_foto;
    private TextView no_hay_vin;
    private ListView lvinculaciones;
    private Button nueva_vinculacion;
    private ImageButton btn_imagen;
    private TextView usu_mostrar;
    private ImageButton btn_editar;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        perfilViewModel =
                ViewModelProviders.of(this).get(PerfilViewModel.class);
        View root = inflater.inflate(R.layout.fragment_perfil, container, false);

        mail = (EditText) root.findViewById(R.id.txt_perfil_mail);
        nombre = (EditText) root.findViewById(R.id.txt_perfil_nombre);
        apellido = (EditText) root.findViewById(R.id.txt_perfil_apellido);
        fechanacimiento = (EditText) root.findViewById(R.id.txt_perfil_fechanacimiento);
        fem = (RadioButton) root.findViewById(R.id.radiofem);
        mas = (RadioButton) root.findViewById(R.id.radiomasc);
        usuario = (EditText) root.findViewById(R.id.txt_perfil_usuario);
        contrasena = (EditText) root.findViewById(R.id.txt_perfil_contrasena);
        repito = (EditText) root.findViewById(R.id.txt_perfil_repito);
        dni = (EditText) root.findViewById(R.id.txt_perfil_dni);
        apartado_paciente = (TableLayout) root.findViewById(R.id.cuadro_paciente);
        btn_actualizar = (Button) root.findViewById(R.id.btn_actualizar);
        peso = (EditText) root.findViewById(R.id.txt_perfil_peso);
        altura = (EditText) root.findViewById(R.id.txt_perfil_altura);
        factor = (Spinner) root.findViewById(R.id.spinner_factores);
        medico = (EditText) root.findViewById(R.id.txt_perfil_medico);
        apartado_ingreso = (TableLayout) root.findViewById(R.id.cuadro_ingreso);
        btn_ficha = (Button) root.findViewById(R.id.btn_ficha);
        btn_ingresos = (Button) root.findViewById(R.id.btn_ingresos);
        no_hay_vin = (TextView) root.findViewById(R.id.no_hay_vinculaciones);
        lvinculaciones = (ListView) root.findViewById(R.id.list_vinculaciones);
        nueva_vinculacion = (Button) root.findViewById(R.id.btn_nueva_vinculacion);
        btn_foto = (ImageButton) root.findViewById(R.id.editar_foto);
        user = new Usuarios();
        pac = new Pacientes();
        usu_mostrar = (TextView) root.findViewById(R.id.txt_usuario_home);
        btn_editar = (ImageButton) root.findViewById(R.id.btn_editar_perfil);


        ((MainActivity) getActivity()).hideFlotanteContacto();
        ((MainActivity) getActivity()) .hideVinculaciones();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.factores, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        factor = (Spinner) root.findViewById(R.id.spinner_factores);
        factor.setAdapter(adapter);
        factor.setEnabled(false);


        PerfilBD perfil = new PerfilBD(getContext(),"Cargar",nombre,apellido,dni,fechanacimiento,fem,mas,mail,usuario,contrasena,
                repito,peso,altura,factor,medico,no_hay_vin,lvinculaciones,btn_foto,usu_mostrar);
        perfil.execute();

        final Session ses = new Session();
        ses.setCt(getContext());
        ses.cargar_session();

        apartado_paciente.setVisibility(View.GONE);
        apartado_ingreso.setVisibility(View.GONE);
        btn_ingresos.setVisibility(View.VISIBLE);

        if(ses.getTipo_rol().equals("Paciente")) {
            btn_ficha.setVisibility(View.VISIBLE);
            //((MainActivity) getActivity()).showFlotanteRedireccionContacto();
            ((MainActivity) getActivity()).showMenu();
        }
        else {
            btn_ficha.setVisibility(View.GONE);
            //((MainActivity) getActivity()).hideFlotanteRedireccionContacto();
            ((MainActivity) getActivity()).hideMenu();
        }


        //final TextView textView = root.findViewById(R.id.text_home);
        perfilViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        btn_editar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                nombre.setEnabled(true);
                apellido.setEnabled(true);
                dni.setEnabled(true);
                fem.setEnabled(true);
                mas.setEnabled(true);
                fechanacimiento.setEnabled(true);
                contrasena.setEnabled(true);
                repito.setEnabled(true);
                peso.setEnabled(true);
                altura.setEnabled(true);
                factor.setEnabled(true);
                medico.setEnabled(true);
                usuario.setEnabled(true);
            }
            });

        btn_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogoEditarFoto dialog = new DialogoEditarFoto();
                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                dialog.show(fragmentManager, "");
            }
        });

        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               if(validaciones()){

                   java.sql.Date sqlfechanac = null;

                   user.setId_usuario(ses.getId_usuario());
                   user.setNombre(nombre.getText().toString());
                   user.setApellido(apellido.getText().toString());
                   try {
                       java.util.Date fechanac=new SimpleDateFormat("dd/MM/yyyy").parse(fechanacimiento.getText().toString());
                       sqlfechanac = new java.sql.Date(fechanac.getTime());
                   } catch (ParseException e) {
                       e.printStackTrace();
                   }

                   user.setFecha_nacimiento(sqlfechanac);
                   user.setUsuario(usuario.getText().toString());
                   user.setContrasena(contrasena.getText().toString());

                   if(fem.isChecked())
                       user.setSexo('f');
                   else if(mas.isChecked())
                       user.setSexo('m');

                   pac.setId_usuario(user);
                   if(peso.getText().toString().isEmpty())
                   pac.setPeso((float) 0);
                       else
                   pac.setPeso(Float.parseFloat(peso.getText().toString()));

                   if(altura.getText().toString().isEmpty())
                       pac.setAltura((float) 0);
                   else
                   pac.setAltura(Float.parseFloat(altura.getText().toString()));

                   pac.setFactorS(factor.getSelectedItem().toString());
                   pac.setMedico_cabecera(medico.getText().toString());

                   UsuariosBD usu = new UsuariosBD(user,pac,getContext(),"Modificar");
                   usu.execute();


               }
            }
        });

        btn_ficha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(apartado_paciente.getVisibility() == View.VISIBLE){
                    apartado_paciente.setVisibility(View.GONE);
                    btn_ficha.setText(R.string.Ficha_medica_mas);
                }else {
                    apartado_paciente.setVisibility(View.VISIBLE);
                    btn_ficha.setText(R.string.Ficha_medica_menos);
                    btn_ingresos.setText(R.string.Datos_ingreso_mas);
                    apartado_ingreso.setVisibility(View.GONE);
                }

            }
        });

        btn_ingresos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(apartado_ingreso.getVisibility() == View.VISIBLE){
                    apartado_ingreso.setVisibility(View.GONE);
                    btn_ingresos.setText(R.string.Datos_ingreso_mas);
                }else {
                    apartado_ingreso.setVisibility(View.VISIBLE);
                    btn_ingresos.setText(R.string.Datos_ingreso_menos);
                    apartado_paciente.setVisibility(View.GONE);
                    btn_ficha.setText(R.string.Ficha_medica_mas);
                }

            }
        });

        nueva_vinculacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main, new VinculacionesFragment()).commit();
            }
        });

        lvinculaciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(ses.getTipo_rol().equals("Paciente")) {
                    FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_main, new ListadoVinculacionesFragment()).commit();
                }
                else{
                    FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_main, new VinculacionesProfesionalFragment()).commit();
                }
            }
        });

        return root;
    }


    public boolean validaciones(){

        if(!nombre.getText().toString().isEmpty() && !apellido.getText().toString().isEmpty() && !fechanacimiento.getText().toString().isEmpty()
         && !usuario.getText().toString().isEmpty() && !contrasena.getText().toString().isEmpty() && !repito.getText().toString().isEmpty())
        {
            if(nombre.getText().toString().length()< 3){
                Toast.makeText(getContext(),"El nombre debe contar con al menos 3 caracteres!",Toast.LENGTH_SHORT).show();
                return false;
            }

            if(nombre.getText().toString().length()> 43){
                Toast.makeText(getContext(),"El nombre es muy largo!",Toast.LENGTH_SHORT).show();
                return false;
            }

            if(apellido.getText().toString().length()< 3){
                Toast.makeText(getContext(),"El apellido debe contar con al menos 3 caracteres!",Toast.LENGTH_SHORT).show();
                return false;
            }

            if(apellido.getText().toString().length()> 43){
                Toast.makeText(getContext(),"El apellido es muy largo!",Toast.LENGTH_SHORT).show();
                return false;
            }

            if(usuario.getText().toString().length()< 3){
                Toast.makeText(getContext(),"El usuario debe contar con al menos 3 caracteres!",Toast.LENGTH_SHORT).show();
                return false;
            }

            if(usuario.getText().toString().length()> 10){
                Toast.makeText(getContext(),"El usuario es muy largo!",Toast.LENGTH_SHORT).show();
                return false;
            }

            if(contrasena.getText().toString().length()!= 8){
                Toast.makeText(getContext(),"La contraseña debe contar con 8 caracteres!",Toast.LENGTH_SHORT).show();
                return false;
            }

            if(repito.getText().toString().length()!= 8){
                Toast.makeText(getContext(),"La contraseña debe contar con 8 caracteres!",Toast.LENGTH_SHORT).show();
                return false;
            }

            if(!repito.getText().toString().equals(contrasena.getText().toString())){
                Toast.makeText(getContext(),"Las contraseñas no coinciden!",Toast.LENGTH_SHORT).show();
                return false;
            }

            if (fechanacimiento.getText().toString().length() == 10) {
                String dia = fechanacimiento.getText().toString().substring(0, 2);
                String mes = fechanacimiento.getText().toString().substring(3, 5);
                String ano = fechanacimiento.getText().toString().substring(6, 10);
                if(validarFecha(dia, mes, ano) ==false) {
                    Toast.makeText(getContext(), "Formato incorrecto de fecha!", Toast.LENGTH_SHORT).show();
                    return false;
                }
                else if (!comparar_fechas(fechanacimiento.getText().toString())) {
                    Toast.makeText(getContext(), "Debes ser mayor de edad!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                Toast.makeText(getContext(), "Formato incorrecto de fecha!", Toast.LENGTH_SHORT).show();
                return false;
            }

            if(!fem.isChecked() && !mas.isChecked()){
                Toast.makeText(getContext(), "Selecciona tu sexo!", Toast.LENGTH_SHORT).show();
                return false;
            }

            Session ses = new Session();
            ses.setCt(getContext());
            ses.cargar_session();

            if(ses.getTipo_rol().equals("Paciente")){


                if(peso.getText().toString().length()>0){

                    if(Float.parseFloat(peso.getText().toString())<=0 || Float.parseFloat(peso.getText().toString())>300) {
                        Toast.makeText(getContext(), "Rango de peso erroneo!", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    }
                }

                if(altura.getText().toString().length()>0){

                    if(Float.parseFloat(altura.getText().toString())<=0 || Float.parseFloat(altura.getText().toString())>2.5) {
                        Toast.makeText(getContext(), "Rango de altura erroneo!", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }


                if(medico.getText().toString().length()>0){

                    if(medico.getText().toString().length()<3){
                        Toast.makeText(getContext(), "El nombre del medico debe contener al menos 3 caracteres!", Toast.LENGTH_SHORT).show();
                    return false;
                }

                    if(medico.getText().toString().length()>43){
                        Toast.makeText(getContext(), "El nombre del medico es muy largo!", Toast.LENGTH_SHORT).show();
                        return false;
                    }


            }

        }
        else{
            Toast.makeText(getContext(), "Complete todos los campos!", Toast.LENGTH_SHORT).show();
            return false;

        }

        return true;

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

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private boolean validarFecha(String dia, String mes, String ano) {


        for (int i = 0; i <= 3; i++) {

            if (i <= 1) {
                if (Character.isDigit(dia.charAt(i)) == false) {
                    Toast.makeText(getContext(), "Formato incorrecto de fecha!", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (Character.isDigit(mes.charAt(i)) == false) {
                    Toast.makeText(getContext(), "Formato incorrecto de fecha!", Toast.LENGTH_SHORT).show();
                    return false;
                }

                if (Character.isDigit(ano.charAt(i)) == false) {
                    Toast.makeText(getContext(), "Formato incorrecto de fecha!", Toast.LENGTH_SHORT).show();
                    return false;
                }

                if (i >= 2) {
                    if (Character.isDigit(ano.charAt(i)) == false) {
                        Toast.makeText(getContext(), "Formato incorrecto de fecha!", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }


            }
        }


        int dia_ = Integer.parseInt(dia);
        int mes_ = Integer.parseInt(mes);
        int ano_ = Integer.parseInt(ano);

        if (dia.length() > 2 || dia.length() < 2) {
            Toast.makeText(getContext(), "Formato incorrecto de fecha!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (dia_ < 1 || dia_ > 31) {
            Toast.makeText(getContext(), "Fecha incorrecta!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mes.length() > 2 || mes.length() < 2) {
            Toast.makeText(getContext(), "Formato incorrecto de fecha!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mes_ < 1 || mes_ > 12) {
            Toast.makeText(getContext(), "Fecha incorrecta!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (ano.length() > 4 || ano.length() < 4) {
            Toast.makeText(getContext(), "Formato incorrecto de fecha!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (ano_ < 1900 || ano_ > 2019) {
            Toast.makeText(getContext(), "Fecha incorrecta!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;


    }

}
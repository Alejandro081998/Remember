package com.frgp.remember.ui.Rutinas.Editar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.frgp.remember.Base.Rutinas.RutinasBD;
import com.frgp.remember.Entidades.Rutinas;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.Principal.MainActivity;
import com.frgp.remember.R;
import com.frgp.remember.Session.Session;

import java.sql.Time;
import java.util.ArrayList;

public class EditarRutinaFragment extends Fragment {

    private EditarRutinaViewModel editarRutinaViewModel;
    private Session ses;
    private Spinner tipo_rutina;
    private TimePicker picker;
    private TextView titulo;
    private CheckBox lunes;
    private CheckBox martes;
    private CheckBox miercoles;
    private CheckBox jueves;
    private CheckBox viernes;
    private CheckBox sabado;
    private CheckBox domingo;
    private ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();
    private Button btn_eliminar;
    private Button btn_guardar;
    private int creador, paciente;
    private String rutina_tipo;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        editarRutinaViewModel =
                ViewModelProviders.of(this).get(EditarRutinaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_editar_rutina, container, false);
        ((MainActivity) getActivity()).hideFlotanteContacto();
        ((MainActivity) getActivity()).hideMenu();
        ((MainActivity) getActivity()).hideVinculaciones();

        ses = new Session();
        ses.setCt(getContext());
        ses.cargar_session();

        tipo_rutina = (Spinner) root.findViewById(R.id.tipos_rutina_editar);
        picker = (TimePicker) root.findViewById(R.id.hora_editar);
        titulo = (TextView) root.findViewById(R.id.txt_titulo_rutina_editar);
        lunes = (CheckBox) root.findViewById(R.id.chkLunes_e);
        martes = (CheckBox) root.findViewById(R.id.chkMartes_e);
        miercoles = (CheckBox) root.findViewById(R.id.chkMiercoles_e);
        jueves = (CheckBox) root.findViewById(R.id.chkJueves_e);
        viernes = (CheckBox) root.findViewById(R.id.chkViernes_e);
        sabado = (CheckBox) root.findViewById(R.id.chkSabado_e);
        domingo = (CheckBox) root.findViewById(R.id.chkDomingo_e);
        btn_eliminar = (Button) root.findViewById(R.id.btn_eliminar_rutina);
        btn_guardar = (Button) root.findViewById(R.id.btn_guardar_rutina);

        checkBoxes.add(lunes);
        checkBoxes.add(martes);
        checkBoxes.add(miercoles);
        checkBoxes.add(jueves);
        checkBoxes.add(viernes);
        checkBoxes.add(sabado);
        checkBoxes.add(domingo);

        final Bundle datosRecuperados = getArguments();

        if (datosRecuperados != null) {

            Rutinas rut_ = new Rutinas();
            rut_.setId_rutina(datosRecuperados.getInt("Rutina"));
            rutina_tipo = datosRecuperados.getString("TipoRutina");
            creador = datosRecuperados.getInt("Creador");
            paciente = datosRecuperados.getInt("Paciente");
            RutinasBD rut = new RutinasBD(getContext(),rut_,"CargarRutinaEditar",tipo_rutina,titulo,checkBoxes,picker);
            rut.execute();
        }

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean paso = true;
                boolean puedo = true;
                String mensaje_devuelto = null;

                if (!lunes.isChecked() && !martes.isChecked() && !miercoles.isChecked() && !jueves.isChecked() && !viernes.isChecked()
                        && !sabado.isChecked() && !domingo.isChecked()) {
                    Toast.makeText(getContext(), "Selecciona algun dia!", Toast.LENGTH_SHORT).show();
                    paso = false;
                }

                if (titulo.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Ingresa una descripcion!", Toast.LENGTH_SHORT).show();
                    paso = false;
                }

                if (paso) {

                    ArrayList<String> dias = new ArrayList<String>();
                    ArrayList<String> nodias = new ArrayList<String>();
                    Rutinas rut = new Rutinas();
                    Session ses = new Session();
                    ses.setCt(getContext());
                    ses.cargar_session();

                    if (datosRecuperados != null)
                        rut.setId_rutina(datosRecuperados.getInt("Rutina"));

                    rut.setDescripcion(titulo.getText().toString());

                    if (lunes.isChecked())
                        dias.add(lunes.getText().toString());
                    else
                        nodias.add(lunes.getText().toString());

                    if (martes.isChecked())
                        dias.add(martes.getText().toString());
                    else
                        nodias.add(martes.getText().toString());

                    if (miercoles.isChecked())
                        dias.add(miercoles.getText().toString());
                    else
                        nodias.add(miercoles.getText().toString());

                    if (jueves.isChecked())
                        dias.add(jueves.getText().toString());
                    else
                        nodias.add(jueves.getText().toString());

                    if (viernes.isChecked())
                        dias.add(viernes.getText().toString());
                    else
                        nodias.add(viernes.getText().toString());

                    if (sabado.isChecked())
                        dias.add(sabado.getText().toString());
                    else
                        nodias.add(sabado.getText().toString());

                    if (domingo.isChecked())
                        dias.add(domingo.getText().toString());
                    else
                        nodias.add(domingo.getText().toString());

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        String str = "" + picker.getHour() + ":" + picker.getMinute() + ":00";
                        Log.d("HORA", str);
                        Time tiempo = null;
                        Time tiempook = tiempo.valueOf(str);
                        rut.setHora(tiempook);
                    }


                    if (ses.getTipo_rol().equals("Paciente")) {

                        if (ses.getId_usuario() != creador) {
                            mensaje_devuelto = "No puedes modificar esta rutina, ya que no eres el creador!";
                            paso = false;
                        }

                        if (rutina_tipo.equals("Profesional")) {
                            mensaje_devuelto = "Este tipo de rutinas solo puede modificarlas un profesional!";
                            paso = false;
                        }

                        if (tipo_rutina.getSelectedItem().toString().equals("Profesional")) {
                            mensaje_devuelto = "No puedes seleccionar es tipo de rutina!";
                            paso = false;
                        }


                    } else if (ses.getTipo_rol().equals("Familiar")) {

                        if (rutina_tipo.equals("Profesional")) {
                            mensaje_devuelto = "Este tipo de rutinas solo puede modificarlas un profesional!";
                            paso = false;
                        }

                        if (tipo_rutina.getSelectedItem().toString().equals("Profesional")) {
                            mensaje_devuelto = "No puedes seleccionar es tipo de rutina!";
                            paso = false;
                        }


                    } else if (ses.getTipo_rol().equals("Supervisor")) {

                        if (ses.getId_usuario() != creador) {
                            mensaje_devuelto = "No puedes modificar esta rutina, ya que no eres el creador!";
                            paso = false;
                        }

                    }


                    if (paso) {

                        if (ses.getTipo_rol().equals("Paciente")) {

                            Usuarios user = new Usuarios();
                            user.setId_usuario(ses.getId_usuario());
                            RutinasBD ruti = new RutinasBD(rut, dias, nodias, tipo_rutina.getSelectedItem().toString(), getContext(), "ModificarRutina", user);
                            ruti.execute();
                        } else {

                                Usuarios user = new Usuarios();
                                user.setId_usuario(paciente);
                                RutinasBD ruti = new RutinasBD(rut, dias, nodias, tipo_rutina.getSelectedItem().toString(), getContext(), "ModificarRutina", user);
                                ruti.execute();

                        }
                    } else
                        Toast.makeText(getContext(), mensaje_devuelto, Toast.LENGTH_LONG).show();


                }

            }
        });




        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean paso = true;
                String mensaje_devuelto = null;

                Session ses = new Session();
                ses.setCt(getContext());
                ses.cargar_session();



                if(ses.getTipo_rol().equals("Paciente")){

                    if(ses.getId_usuario() != creador) {
                        mensaje_devuelto = "No puedes eliminar esta rutina, ya que no eres el creador!";
                        paso = false;
                    }


                }else if(ses.getTipo_rol().equals("Familiar")){

                    if(rutina_tipo.equals("Profesional")){
                        mensaje_devuelto = "Este tipo de rutinas solo puede eliminarlas un profesional!";
                        paso = false;
                    }


                }else if (ses.getTipo_rol().equals("Supervisor")){

                    if(ses.getId_usuario() != creador) {
                        mensaje_devuelto = "No puedes eliminar esta rutina, ya que no eres el creador!";
                        paso = false;
                    }

                }


                if(paso) {


                    if (ses.getTipo_rol().equals("Paciente")) {

                        Usuarios user = new Usuarios();
                        user.setId_usuario(ses.getId_usuario());
                        Rutinas rut_ = new Rutinas();
                        rut_.setId_rutina(datosRecuperados.getInt("Rutina"));
                        RutinasBD rut = new RutinasBD(getContext(), rut_, "Eliminar",user);
                        rut.execute();
                    } else {

                        Usuarios user = new Usuarios();
                        user.setId_usuario(paciente);
                        Rutinas rut_ = new Rutinas();
                        rut_.setId_rutina(datosRecuperados.getInt("Rutina"));
                        RutinasBD rut = new RutinasBD(getContext(), rut_, "Eliminar",user);
                        rut.execute();

                    }
                } else
                    Toast.makeText(getContext(), mensaje_devuelto, Toast.LENGTH_LONG).show();
            }
        });


        editarRutinaViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
}

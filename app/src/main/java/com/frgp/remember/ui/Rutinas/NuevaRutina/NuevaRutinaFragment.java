package com.frgp.remember.ui.Rutinas.NuevaRutina;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.frgp.remember.Base.Rutinas.RutinasBD;
import com.frgp.remember.Base.TipoRutinas.TiposRutinasBD;
import com.frgp.remember.Entidades.Rutinas;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.Principal.MainActivity;
import com.frgp.remember.R;
import com.frgp.remember.Session.Session;

import java.sql.Time;
import java.util.ArrayList;

public class NuevaRutinaFragment extends Fragment {

    private NuevaRutinaViewModel nuevaRutinaViewModel;
    private Spinner tipos_rutina;
    private EditText titulo;
    private TimePicker hora;
    private CheckBox lunes;
    private CheckBox martes;
    private CheckBox miercoles;
    private CheckBox jueves;
    private CheckBox viernes;
    private CheckBox sabado;
    private CheckBox domingo;
    private Button btn_guardar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        nuevaRutinaViewModel =
                ViewModelProviders.of(this).get(NuevaRutinaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_nueva_rutina, container, false);
        ((MainActivity) getActivity()).hideFlotanteContacto();
        ((MainActivity) getActivity()).hideMenu();
        ((MainActivity) getActivity()) .hideVinculaciones();

        tipos_rutina = (Spinner) root.findViewById(R.id.tipos_rutina);
        btn_guardar = (Button) root.findViewById(R.id.btn_guardar_rutina);
        titulo = (EditText) root.findViewById(R.id.txt_titulo_rutina);
        hora = (TimePicker) root.findViewById(R.id.hora);
        lunes = (CheckBox) root.findViewById(R.id.chkLunes);
        martes = (CheckBox) root.findViewById(R.id.chkMartes);
        miercoles = (CheckBox) root.findViewById(R.id.chkMiercoles);
        jueves = (CheckBox) root.findViewById(R.id.chkJueves);
        viernes = (CheckBox) root.findViewById(R.id.chkViernes);
        sabado = (CheckBox) root.findViewById(R.id.chkSabado);
        domingo = (CheckBox) root.findViewById(R.id.chkDomingo);

        TiposRutinasBD tipo = new TiposRutinasBD(getContext(),tipos_rutina,"TraerTipos");
        tipo.execute();

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean paso = true;
                Session ses = new Session();
                ses.setCt(getContext());
                ses.cargar_session();

                if(!lunes.isChecked() && !martes.isChecked() && !miercoles.isChecked() && !jueves.isChecked() && !viernes.isChecked()
                && !sabado.isChecked() && !domingo.isChecked()){
                    Toast.makeText(getContext(), "Selecciona alguna dia!", Toast.LENGTH_SHORT).show();
                    paso = false;
                }

                if(titulo.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Ingresa una descripcion!", Toast.LENGTH_SHORT).show();
                    paso = false;
                }

                if(ses.getTipo_rol().equals("Paciente") || ses.getTipo_rol().equals("Familiar")){

                    if(tipos_rutina.getSelectedItem().toString().equals("Profesional")) {
                        paso = false;
                        Toast.makeText(getContext(), "No puedes seleccionar este tipo de rutina!", Toast.LENGTH_SHORT).show();
                    }

                }

                if(paso){

                    ArrayList<String> dias = new ArrayList<String>();
                    Rutinas rut = new Rutinas();


                    rut.setDescripcion(titulo.getText().toString());



                    if(lunes.isChecked())
                        dias.add(lunes.getText().toString());

                    if(martes.isChecked())
                        dias.add(martes.getText().toString());

                    if(miercoles.isChecked())
                        dias.add(miercoles.getText().toString());

                    if(jueves.isChecked())
                        dias.add(jueves.getText().toString());

                    if(viernes.isChecked())
                        dias.add(viernes.getText().toString());

                    if(sabado.isChecked())
                        dias.add(sabado.getText().toString());

                    if(domingo.isChecked())
                        dias.add(domingo.getText().toString());

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        String str = "" + hora.getHour() + ":" + hora.getMinute() + ":00";
                        Log.d("HORA",str);
                        Time tiempo = null;
                        Time tiempook = tiempo.valueOf(str);
                        rut.setHora(tiempook);
                    }


                    if(ses.getTipo_rol().equals("Paciente")) {

                        Usuarios usr = new Usuarios();
                        usr.setId_usuario(ses.getId_usuario());
                        RutinasBD ruti = new RutinasBD(rut, dias, tipos_rutina.getSelectedItem().toString(), getContext(), "RutinaPaciente",usr);
                        ruti.execute();
                    }
                    else{
                        final Bundle datosRecuperados = getArguments();

                        if (datosRecuperados != null) {

                            Usuarios usr = new Usuarios();
                            usr.setId_usuario(datosRecuperados.getInt("PacienteRutinas"));
                            RutinasBD ruti = new RutinasBD(rut, dias, tipos_rutina.getSelectedItem().toString(), getContext(), "RutinaPaciente",usr);
                            ruti.execute();
                        }


                    }



                }


            }
        });


        //((MainActivity) getActivity()).hideFlotanteRedireccionContacto();
        //final TextView textView = root.findViewById(R.id.text_send);
        nuevaRutinaViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
}
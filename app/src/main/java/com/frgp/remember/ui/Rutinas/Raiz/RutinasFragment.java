package com.frgp.remember.ui.Rutinas.Raiz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.frgp.remember.Base.Rutinas.RutinasBD;
import com.frgp.remember.Dialogos.DialogoRutina;
import com.frgp.remember.Entidades.Rutinas;
import com.frgp.remember.Entidades.TipoRutinas;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.Principal.MainActivity;
import com.frgp.remember.R;
import com.frgp.remember.Session.Session;
import com.frgp.remember.ui.Rutinas.NuevaRutina.NuevaRutinaFragment;

public class RutinasFragment extends Fragment {

    private RutinasViewModel rutinasViewModel;
    private ImageButton btn_nueva_rutina;
    private ListView l_misrutinas;
    private ListView l_familiares;
    private ListView l_profesionales;
    private TextView no_misrutinas;
    private TextView no_profesionales;
    private TextView no_familiares;
    private Session ses;
    private Usuarios usu;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rutinasViewModel =
                ViewModelProviders.of(this).get(RutinasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rutinas, container, false);
        //final TextView textView = root.findViewById(R.id.text_gallery);
        ((MainActivity) getActivity()).hideFlotanteContacto();
        ((MainActivity) getActivity()).hideMenu();
        ((MainActivity) getActivity()) .hideVinculaciones();

        btn_nueva_rutina = root.findViewById(R.id.btn_nueva_rutina);
        l_misrutinas = root.findViewById(R.id.list_misrutinas);
        l_familiares = root.findViewById(R.id.list_rutinas_familiares);
        l_profesionales = root.findViewById(R.id.list_rutinas_profesionales);
        no_misrutinas = root.findViewById(R.id.no_hay_misrutinas);
        no_familiares = root.findViewById(R.id.no_hay_rutinasfamiliares);
        no_profesionales = root.findViewById(R.id.no_hay_rutinasprofesionales);

        ses = new Session();
        ses.setCt(getContext());
        ses.cargar_session();

        if(ses.getTipo_rol().equals("Paciente")) {
            usu = new Usuarios();
            usu.setId_usuario(ses.getId_usuario());
            RutinasBD rut = new RutinasBD(getContext(), "CargarRutinas", l_misrutinas, l_profesionales, l_familiares,
                    no_misrutinas, no_profesionales, no_familiares,usu);
            rut.execute();
        }
        else{

            final Bundle datosRecuperados = getArguments();

            if (datosRecuperados != null) {
                usu = new Usuarios();
                usu.setId_usuario(datosRecuperados.getInt("PacienteRutinas"));
            }

            RutinasBD rut = new RutinasBD(getContext(), "CargarRutinas", l_misrutinas, l_profesionales, l_familiares,
                    no_misrutinas, no_profesionales, no_familiares,usu);
            rut.execute();
        }

        l_familiares.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                Rutinas rut = new Rutinas();
                Usuarios usu = new Usuarios();
                Usuarios usu_ = new Usuarios();
                TipoRutinas tipo = new TipoRutinas();
                TextView id_paciente = (TextView) view.findViewById(R.id.txt_idusuario_paciente);
                TextView id_creador = (TextView) view.findViewById(R.id.txt_idusuario_creador);
                TextView id_rutina = (TextView) view.findViewById(R.id.txt_id_rutina);
                TextView nombre_creador = (TextView) view.findViewById(R.id.txt_nombrecreador);
                TextView apellido_creador = (TextView) view.findViewById(R.id.txt_apellidocreador);
                TextView tipo_rutina = (TextView) view.findViewById(R.id.txt_tipo_rutina);

                Log.d("PACIENTE SELECCIONADO:" , id_paciente.getText().toString());

                usu.setNombre(nombre_creador.getText().toString());
                usu.setApellido(apellido_creador.getText().toString());
                rut.setId_rutina(Integer.parseInt(id_rutina.getText().toString()));
                usu.setId_usuario(Integer.parseInt(id_creador.getText().toString()));
                rut.setId_creador(usu);
                usu_.setId_usuario(Integer.parseInt(id_paciente.getText().toString()));
                rut.setId_paciente(usu_);
                tipo.setDescripcion(tipo_rutina.getText().toString());
                rut.setTipo_rutina(tipo);

                DialogoRutina dia = new DialogoRutina(rut);
                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                dia.show(fragmentManager, "");

            }
        });


        l_misrutinas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Rutinas rut = new Rutinas();
                Usuarios usu = new Usuarios();
                Usuarios usu_ = new Usuarios();
                TipoRutinas tipo = new TipoRutinas();
                TextView id_paciente = (TextView) view.findViewById(R.id.txt_idusuario_paciente);
                TextView id_creador = (TextView) view.findViewById(R.id.txt_idusuario_creador);
                TextView id_rutina = (TextView) view.findViewById(R.id.txt_id_rutina);
                TextView nombre_creador = (TextView) view.findViewById(R.id.txt_nombrecreador);
                TextView apellido_creador = (TextView) view.findViewById(R.id.txt_apellidocreador);
                TextView tipo_rutina = (TextView) view.findViewById(R.id.txt_tipo_rutina);


                usu.setNombre(nombre_creador.getText().toString());
                usu.setApellido(apellido_creador.getText().toString());
                rut.setId_rutina(Integer.parseInt(id_rutina.getText().toString()));
                usu.setId_usuario(Integer.parseInt(id_creador.getText().toString()));
                rut.setId_creador(usu);
                usu_.setId_usuario(Integer.parseInt(id_paciente.getText().toString()));
                rut.setId_paciente(usu_);
                tipo.setDescripcion(tipo_rutina.getText().toString());
                rut.setTipo_rutina(tipo);

                Log.d("PACIENTE SELECCIONADO:" , id_paciente.getText().toString());

                DialogoRutina dia = new DialogoRutina(rut);
                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                dia.show(fragmentManager, "");

            }
        });

        l_profesionales.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Rutinas rut = new Rutinas();
                Usuarios usu = new Usuarios();
                Usuarios usu_ = new Usuarios();
                TipoRutinas tipo = new TipoRutinas();
                TextView id_paciente = (TextView) view.findViewById(R.id.txt_idusuario_paciente);
                TextView id_creador = (TextView) view.findViewById(R.id.txt_idusuario_creador);
                TextView id_rutina = (TextView) view.findViewById(R.id.txt_id_rutina);
                TextView nombre_creador = (TextView) view.findViewById(R.id.txt_nombrecreador);
                TextView apellido_creador = (TextView) view.findViewById(R.id.txt_apellidocreador);
                TextView tipo_rutina = (TextView) view.findViewById(R.id.txt_tipo_rutina);

                usu.setNombre(nombre_creador.getText().toString());
                usu.setApellido(apellido_creador.getText().toString());
                rut.setId_rutina(Integer.parseInt(id_rutina.getText().toString()));
                usu.setId_usuario(Integer.parseInt(id_creador.getText().toString()));
                rut.setId_creador(usu);
                usu_.setId_usuario(Integer.parseInt(id_paciente.getText().toString()));
                rut.setId_paciente(usu_);
                tipo.setDescripcion(tipo_rutina.getText().toString());
                rut.setTipo_rutina(tipo);

                DialogoRutina dia = new DialogoRutina(rut);
                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                dia.show(fragmentManager, "");

            }
        });


        //((MainActivity) getActivity()).hideFlotanteRedireccionContacto();
        rutinasViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });


        btn_nueva_rutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ses.getTipo_rol().equals("Supervisor") || ses.getTipo_rol().equals("Familiar")){

                final Bundle datosRecuperados = getArguments();

                if (datosRecuperados != null) {
                    Bundle datosAEnviar = new Bundle();
                    datosAEnviar.putInt("PacienteRutinas", datosRecuperados.getInt("PacienteRutinas"));

                    Fragment fragmento = new NuevaRutinaFragment();
                    fragmento.setArguments(datosAEnviar);
                    FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_main, fragmento).commit();

                }
                }
                else{

                    FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_main, new NuevaRutinaFragment()).commit();


                }



            }
        });

        return root;
    }
}
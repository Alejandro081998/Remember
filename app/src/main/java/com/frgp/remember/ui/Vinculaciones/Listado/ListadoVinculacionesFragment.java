package com.frgp.remember.ui.Vinculaciones.Listado;

import android.os.Bundle;
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

import com.frgp.remember.Base.Vinculaciones.VinculacionesBD;
import com.frgp.remember.Dialogos.DialogoGestionVinculaciones;
import com.frgp.remember.Dialogos.DialogoGestionVinculacionesPaciente;
import com.frgp.remember.Entidades.TipoRol;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.Entidades.Vinculaciones;
import com.frgp.remember.Principal.MainActivity;
import com.frgp.remember.R;
import com.frgp.remember.ui.ListadoContactos.ListadoContactosViewModel;
import com.frgp.remember.ui.Vinculaciones.NuevaVinculacion.VinculacionesFragment;

public class ListadoVinculacionesFragment extends Fragment {

    private ListadoVinculacionesViewModel listadoVinculacionesViewModel;

    private ListView lista_familiares;
    private ListView lista_medicos;
    private TextView no_hay_medicos;
    private TextView no_hay_familiares;
    private ImageButton btn_agregar_familiar;
    private ImageButton btn_agregar_medico;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listadoVinculacionesViewModel =
                ViewModelProviders.of(this).get(ListadoVinculacionesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_listado_vinculaciones, container, false);
        //final TextView textView = root.findViewById(R.id.text_seguimiento);
        ((MainActivity) getActivity()).hideFlotanteContacto();
        ((MainActivity) getActivity()).hideMenu();
        ((MainActivity) getActivity()) .showVinculaciones();

        lista_familiares = root.findViewById(R.id.list_vincu_familiares);
        lista_medicos = root.findViewById(R.id.list_vincu_medicos);
        no_hay_familiares = root.findViewById(R.id.no_hay_familiares_vincu);
        no_hay_medicos = root.findViewById(R.id.no_hay_profesionales_vincu);
        btn_agregar_medico = root.findViewById(R.id.btn_agregar_medico_vincu);
        btn_agregar_familiar = root.findViewById(R.id.btn_agregar_familiar_vincu);

        VinculacionesBD vin = new VinculacionesBD(getContext(),"ListadoIndividual",lista_familiares,lista_medicos,lista_familiares,
                no_hay_familiares,no_hay_medicos,no_hay_familiares,null);
        vin.execute();

        btn_agregar_familiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle datosAEnviar = new Bundle();
                datosAEnviar.putString("mostrar", "Familiares");

                Fragment fragmento = new VinculacionesFragment();
                fragmento.setArguments(datosAEnviar);
                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main, fragmento).commit();

            }
        });

        btn_agregar_medico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle datosAEnviar = new Bundle();
                datosAEnviar.putString("mostrar", "Medicos");

                Fragment fragmento = new VinculacionesFragment();
                fragmento.setArguments(datosAEnviar);
                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main, fragmento).commit();

            }
        });

        lista_medicos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Vinculaciones vin = new Vinculaciones();
                Usuarios user = new Usuarios();
                TextView id_vinculacion = (TextView) view.findViewById(R.id.txt_id_vinculacion);
                TextView id_usuario = (TextView) view.findViewById(R.id.txt_idusuario_vinculacion);
                user.setId_usuario(Integer.parseInt(id_usuario.getText().toString()));
                vin.setId_supervisor(user);
                vin.setId_vinculacion(Integer.parseInt(id_vinculacion.getText().toString()));

                DialogoGestionVinculacionesPaciente dialog = new DialogoGestionVinculacionesPaciente(vin);
                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                dialog.show(fragmentManager, "");
            }
        });

        lista_familiares.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Vinculaciones vin = new Vinculaciones();
                Usuarios user = new Usuarios();
                TextView id_vinculacion = (TextView) view.findViewById(R.id.txt_id_vinculacion);
                TextView id_usuario = (TextView) view.findViewById(R.id.txt_idusuario_vinculacion);
                user.setId_usuario(Integer.parseInt(id_usuario.getText().toString()));
                vin.setId_supervisor(user);
                vin.setId_vinculacion(Integer.parseInt(id_vinculacion.getText().toString()));

                DialogoGestionVinculacionesPaciente dialog = new DialogoGestionVinculacionesPaciente(vin);
                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                dialog.show(fragmentManager, "");
            }
        });

        //((MainActivity) getActivity()).hideFlotanteRedireccionContacto();
        listadoVinculacionesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
}
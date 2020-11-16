package com.frgp.remember.ui.Vinculaciones.ListadoProfesional;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
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
import com.frgp.remember.Dialogos.DialogoVinculacion;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.Entidades.Vinculaciones;
import com.frgp.remember.Principal.MainActivity;
import com.frgp.remember.R;
import com.frgp.remember.ui.ListadoContactos.ListadoContactosViewModel;
import com.frgp.remember.ui.Vinculaciones.Listado.ListadoVinculacionesViewModel;
import com.frgp.remember.ui.Vinculaciones.NuevaVinculacion.VinculacionesFragment;
import com.frgp.remember.ui.Vinculaciones.Pendientes.VinculacionesPendientesViewModel;

public class VinculacionesProfesionalFragment extends Fragment {

    private VinculacionesPendientesViewModel vinculacionesProfesionalFragment;

    private ListView lista_pacientes;
    private TextView no_hay_pacientes;
    private SearchView busqueda;
    private ImageButton btn_agregar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vinculacionesProfesionalFragment =
                ViewModelProviders.of(this).get(VinculacionesPendientesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_listado_vinculaciones_profesional, container, false);
        //final TextView textView = root.findViewById(R.id.text_seguimiento);
        ((MainActivity) getActivity()).hideFlotanteContacto();
        ((MainActivity) getActivity()).hideMenu();
        ((MainActivity) getActivity()) .showVinculaciones();

        lista_pacientes = root.findViewById(R.id.list_vincu_pacientes);
        no_hay_pacientes = root.findViewById(R.id.no_hay_pacientes_vincu);
        busqueda = root.findViewById(R.id.busqueda_pacientes_vinculaciones);
        btn_agregar = root.findViewById(R.id.btn_agregar_paciente);

        VinculacionesBD vin = new VinculacionesBD(getContext(),"ListadoIndividual",lista_pacientes,null,null,no_hay_pacientes,null,null,busqueda);
        vin.execute();

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main, new VinculacionesFragment()).commit();
            }
        });

        lista_pacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Vinculaciones vin = new Vinculaciones();
                Usuarios usu = new Usuarios();
                TextView id_vinculacion = (TextView) view.findViewById(R.id.txt_id_vinculacion);
                TextView id_usuario = (TextView) view.findViewById(R.id.txt_idusuario_vinculacion);
                usu.setId_usuario(Integer.parseInt(id_usuario.getText().toString()));
                vin.setId_paciente(usu);
                vin.setId_vinculacion(Integer.parseInt(id_vinculacion.getText().toString()));

                DialogoGestionVinculaciones dialog = new DialogoGestionVinculaciones(vin);
                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                dialog.show(fragmentManager, "");
            }
        });

        //((MainActivity) getActivity()).hideFlotanteRedireccionContacto();
        vinculacionesProfesionalFragment.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
}
package com.frgp.remember.ui.Vinculaciones.Pendientes;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
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

import com.frgp.remember.Base.Usuarios.UsuariosBD;
import com.frgp.remember.Base.Vinculaciones.VinculacionesBD;
import com.frgp.remember.Dialogos.DialogoVinculacion;
import com.frgp.remember.Dialogos.DialogoVinculado;
import com.frgp.remember.Dialogos.NuevoContacto;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.Entidades.Vinculaciones;
import com.frgp.remember.Principal.MainActivity;
import com.frgp.remember.R;
import com.frgp.remember.Session.Session;
import com.frgp.remember.ui.ListadoContactos.ListadoContactosViewModel;

public class VinculacionesPendientesFragment extends Fragment {

    private VinculacionesPendientesViewModel vinculacionesPendientesViewModel;
    private ListView listaPendientes;
    private TextView no_hay;
    private Session ses;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vinculacionesPendientesViewModel =
                ViewModelProviders.of(this).get(VinculacionesPendientesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_listado_vinculaciones_pendientes, container, false);
        //final TextView textView = root.findViewById(R.id.text_seguimiento);
        ((MainActivity) getActivity()).hideFlotanteContacto();
        ((MainActivity) getActivity()).hideMenu();
        ((MainActivity) getActivity()) .hideVinculaciones();

        listaPendientes = root.findViewById(R.id.list_usuarios_vinculaciones_pendientes);
        no_hay = root.findViewById(R.id.no_hay_vinculaciones_pendientes);

        VinculacionesBD vin = new VinculacionesBD(getContext(),"Pendientes",listaPendientes,no_hay);
        vin.execute();

        listaPendientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Vinculaciones vin = new Vinculaciones();
                Usuarios usu = new Usuarios();
                TextView id_usuario = (TextView) view.findViewById(R.id.txt_idusuario_vinculacion);
                TextView id_vinculacion = (TextView) view.findViewById(R.id.txt_id_vinculacion);
                usu.setId_usuario(Integer.parseInt(id_usuario.getText().toString()));
                vin.setId_supervisor(usu);
                vin.setId_paciente(usu);
                vin.setId_vinculacion(Integer.parseInt(id_vinculacion.getText().toString()));

                DialogoVinculacion dialog = new DialogoVinculacion(vin);
                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                dialog.show(fragmentManager, "");
            }
        });


        //((MainActivity) getActivity()).hideFlotanteRedireccionContacto();
        vinculacionesPendientesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
}
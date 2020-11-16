package com.frgp.remember.ui.Seguimiento.Raiz;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ImageView;
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

import com.frgp.remember.Adaptadores.AdaptadorSeguimiento;
import com.frgp.remember.Base.Usuarios.UsuariosBD;
import com.frgp.remember.Entidades.SeguimientoDatos;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.Principal.MainActivity;
import com.frgp.remember.R;
import com.frgp.remember.Session.Session;
import com.frgp.remember.ui.Seguimiento.Detalle.DetallePerfilFragment;
import com.frgp.remember.ui.Vinculaciones.NuevaVinculacion.VinculacionesFragment;

import java.util.ArrayList;
import java.util.List;

public class SeguimientoFragment extends Fragment {

    private SeguimientoViewModel seguimientoViewModel;
    private ListView lista_seguimiento;
    private TextView txt_no_hay_seguimiento;
    private SearchView buscar_segui;
    private ArrayList<Usuarios> arrayentidad;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        seguimientoViewModel =
                ViewModelProviders.of(this).get(SeguimientoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_seguimiento, container, false);


        //final TextView textView = root.findViewById(R.id.text_seguimiento);
        ((MainActivity) getActivity()).hideFlotanteContacto();
        ((MainActivity) getActivity()).hideMenu();
        ((MainActivity) getActivity()) .hideVinculaciones();

        lista_seguimiento = (ListView) root.findViewById(R.id.list_pacientes_seguimiento) ;
        txt_no_hay_seguimiento = (TextView) root.findViewById(R.id.no_hay_seguimiento);
        buscar_segui = (SearchView) root.findViewById(R.id.buscar_usuario_seguimiento);


        UsuariosBD user = new UsuariosBD(getContext(),"ListarSeguimiento",lista_seguimiento,txt_no_hay_seguimiento,buscar_segui);
        user.execute();


        //((MainActivity) getActivity()).hideFlotanteRedireccionContacto();
        seguimientoViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

      /* lista_seguimiento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),"PROBANDO",Toast.LENGTH_SHORT).show();
            }
        });*/

        lista_seguimiento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                TextView tvidUsuario = (TextView) view.findViewById(R.id.txt_idusuario_seguimiento) ;
                TextView tvNombre = (TextView) view.findViewById(R.id.txt_nombre_seguimiento);
                ImageView imageView = (ImageView) view.findViewById(R.id.idImagen);
                TextView tvapellido = (TextView) view.findViewById(R.id.txt_apellido_seguimiento) ;
                TextView tvusuario = (TextView) view.findViewById(R.id.txt_usu_seguimiento);


                Bundle datosAEnviar = new Bundle();

                datosAEnviar.putString("NombreSeguimiento", tvNombre.getText().toString());
                datosAEnviar.putInt("IdUsuarioseguimiento",Integer.parseInt(tvidUsuario.getText().toString()));
                datosAEnviar.putString("ApellidoSeguimiento", tvapellido.getText().toString());
                datosAEnviar.putString("usuSeguimiento", tvusuario.getText().toString());




                Fragment fragmento = new DetallePerfilFragment();
                fragmento.setArguments(datosAEnviar);
                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main, fragmento).commit();


            }
        });


        return root;
    }
}
package com.frgp.remember.ui.Seguimiento.Detalle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.frgp.remember.Base.Usuarios.UsuariosBD;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.Principal.MainActivity;
import com.frgp.remember.R;


public class DetallePerfilFragment extends Fragment {

    private DetallePerfilViewModel detallePerfilViewModel;
    private TextView idusuario,nombreusu,apellidousu,ususeg;
    private ImageView imgusudet;
    private ListView listalogs;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        detallePerfilViewModel =
                ViewModelProviders.of(this).get(DetallePerfilViewModel.class);
        View root = inflater.inflate(R.layout.fragment_perfil_seguimiento, container, false);
        //final TextView textView = root.findViewById(R.id.text_seguimiento);
        ((MainActivity) getActivity()).hideFlotanteContacto();
        ((MainActivity) getActivity()).hideMenu();
        ((MainActivity) getActivity()) .hideVinculaciones();
        //((MainActivity) getActivity()).hideFlotanteRedireccionContacto();

        idusuario = (TextView) root.findViewById(R.id.tvIdUsu);
        nombreusu = (TextView) root.findViewById(R.id.tvNombre);
        imgusudet = (ImageView) root.findViewById(R.id.imgFotoDet_seguimiento);
        ususeg = (TextView) root.findViewById(R.id.tvUsuario);
        listalogs = (ListView)  root.findViewById(R.id.lvlogs);

        final Bundle datosRecuperados = getArguments();

        if (datosRecuperados != null) {

            Usuarios usu = new Usuarios();

            usu.setId_usuario(datosRecuperados.getInt("IdUsuarioseguimiento"));
            idusuario.setText(""+usu.getId_usuario());
            nombreusu.setText(datosRecuperados.getString("NombreSeguimiento") + " " +
                    datosRecuperados.getString("ApellidoSeguimiento" ));
            ususeg.setText(datosRecuperados.getString("usuSeguimiento"));

            UsuariosBD usuimg = new UsuariosBD(imgusudet,usu,getContext(),"ObtenerFotoDetSeguimiento");


            UsuariosBD userlog = new UsuariosBD(getContext(),"Listar_logs",listalogs,usu);

            usuimg.execute();
            userlog.execute();

        }


        detallePerfilViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
}
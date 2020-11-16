package com.frgp.remember.ui.Perfil.PerfilDetalle;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.frgp.remember.Base.ListaNegra.ListaNegraBD;
import com.frgp.remember.Base.Usuarios.UsuariosBD;
import com.frgp.remember.Base.Vinculaciones.VinculacionesBD;
import com.frgp.remember.Entidades.ListaNegra;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.Entidades.Vinculaciones;
import com.frgp.remember.Principal.MainActivity;
import com.frgp.remember.R;

public class PerfilDetalleFragment extends Fragment {

    private PerfilDetalleViewModel perfilDetalleViewModel;
    private TextView nombre_apellido;
    private TextView usuario;
    private ImageView img;
    private TextView rol;
    private TextView desde;
    private Switch vinculacion;
    private Switch bloqueado;
    private Usuarios user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        perfilDetalleViewModel =
                ViewModelProviders.of(this).get(PerfilDetalleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_detalle_perfil, container, false);

        ((MainActivity) getActivity()).hideFlotanteContacto();
        ((MainActivity) getActivity()).hideMenu();
        ((MainActivity) getActivity()) .hideVinculaciones();

        nombre_apellido = (TextView) root.findViewById(R.id.txt_nombre);
        usuario = (TextView) root.findViewById(R.id.txt_usuario);
        img = (ImageView)  root.findViewById(R.id.imgFotoDet);
        rol = (TextView) root.findViewById(R.id.txt_rol);
        desde = (TextView) root.findViewById(R.id.txt_desde);
        vinculacion = (Switch) root.findViewById(R.id.switch_vinculacion);
        bloqueado = (Switch) root.findViewById(R.id.switch_bloqueado);


        final Bundle datosRecuperados = getArguments();

        if (datosRecuperados != null) {

            this.user = new Usuarios();
            user.setId_usuario(datosRecuperados.getInt("usuario_perfil"));
            UsuariosBD us = new UsuariosBD(getContext(),"Perfil",user,nombre_apellido,usuario,img,rol,desde,vinculacion,bloqueado);
            us.execute();
        }

        vinculacion.setOnClickListener(new Switch.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vinculacion.isChecked()){

                    if (datosRecuperados != null) {

                        vinculacion.setChecked(false);
                        VinculacionesBD vin = new VinculacionesBD(user,getContext(),"Insertar");
                        vin.execute();
                    }

                }else{

                    if (datosRecuperados != null) {

                        VinculacionesBD vin = new VinculacionesBD(user,getContext(),"DesvincularseId");
                        vin.execute();
                    }


                }
            }
        });

        bloqueado.setOnClickListener(new Switch.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bloqueado.isChecked()){

                    ListaNegra lista = new ListaNegra();

                    ListaNegraBD list = new ListaNegraBD(lista,user,getContext(),"Bloquear");
                    list.execute();

                }else{

                    ListaNegra lista = new ListaNegra();

                    ListaNegraBD list = new ListaNegraBD(lista,user,getContext(),"DesbloquearId");
                    list.execute();


                }
            }
        });

        //final TextView textView = root.findViewById(R.id.text_send);
        perfilDetalleViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });


        return root;
    }

}

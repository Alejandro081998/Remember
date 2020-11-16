package com.frgp.remember.ui.Vinculaciones.NuevaVinculacion;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
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

import com.frgp.remember.Base.Usuarios.UsuariosBD;
import com.frgp.remember.Dialogos.DialogoEditarFoto;
import com.frgp.remember.Dialogos.DialogoVinculado;
import com.frgp.remember.Dialogos.NuevoContacto;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.Principal.MainActivity;
import com.frgp.remember.R;
import com.frgp.remember.Session.Session;
import com.frgp.remember.ui.ListadoContactos.ListadoContactosViewModel;

public class VinculacionesFragment extends Fragment {

    private VinculacionesViewModel vinculacionesViewModel;
    private SearchView buscar;
    private ListView listaUsuarios;
    private TextView no_hay;
    private CheckBox chk_medicos;
    private CheckBox chk_familiares;
    private Session ses;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vinculacionesViewModel =
                ViewModelProviders.of(this).get(VinculacionesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_nueva_vinculacion, container, false);
        //final TextView textView = root.findViewById(R.id.text_seguimiento);
        ((MainActivity) getActivity()).hideFlotanteContacto();
        ((MainActivity) getActivity()).hideMenu();
        ((MainActivity) getActivity()) .showVinculaciones();

        listaUsuarios = root.findViewById(R.id.list_usuarios_vinculaciones);
        no_hay = root.findViewById(R.id.no_hay_usuarios);
        buscar = root.findViewById(R.id.buscar_usuario_vinculaciones);
        chk_medicos = root.findViewById(R.id.chk_medicos);
        chk_familiares = root.findViewById(R.id.chk_familiares);


        Bundle datosRecuperados = getArguments();

        if (datosRecuperados == null) {
            UsuariosBD user = new UsuariosBD(getContext(),"ListarUsuariosVinculaciones",
                    listaUsuarios,buscar,no_hay,true,true,chk_familiares,chk_medicos);
            user.execute();
        }else{

            if(datosRecuperados.getString("mostrar").equals("Medicos")){

                chk_medicos.setChecked(true);
                chk_familiares.setChecked(false);
                UsuariosBD user = new UsuariosBD(getContext(),"ListarUsuariosVinculaciones",
                        listaUsuarios,buscar,no_hay,false,true,chk_familiares,chk_medicos);
                user.execute();

            }

            if(datosRecuperados.getString("mostrar").equals("Familiares")){

                chk_medicos.setChecked(false);
                chk_familiares.setChecked(true);
                UsuariosBD user = new UsuariosBD(getContext(),"ListarUsuariosVinculaciones",
                        listaUsuarios,buscar,no_hay,true,false,chk_familiares,chk_medicos);
                user.execute();

            }


        }


        listaUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Usuarios usu = new Usuarios();
                TextView id_contacto = (TextView) view.findViewById(R.id.txt_id_usuario_vinculado);
                int id_contacto_ = Integer.parseInt(id_contacto.getText().toString());

                usu.setId_usuario(id_contacto_);

                DialogoVinculado dia = new DialogoVinculado(usu);
                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                dia.show(fragmentManager, "");
            }
        });

        chk_medicos.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!chk_medicos.isChecked() && chk_familiares.isChecked())
                {
                    UsuariosBD usu = new UsuariosBD(getContext(),"ListarUsuariosVinculaciones",
                            listaUsuarios,buscar,no_hay,true,false,chk_familiares,chk_medicos);
                    usu.execute();
                    Log.d("OPCION - MEDICOS", "A");
                }


                if(chk_medicos.isChecked() && !chk_familiares.isChecked()){
                    UsuariosBD usu = new UsuariosBD(getContext(),"ListarUsuariosVinculaciones",
                            listaUsuarios,buscar,no_hay,false,true,chk_familiares,chk_medicos);
                    usu.execute();
                    Log.d("OPCION - MEDICOS", "B");
                }

                if(chk_familiares.isChecked() && chk_medicos.isChecked()){
                    UsuariosBD usu = new UsuariosBD(getContext(),"ListarUsuariosVinculaciones",
                            listaUsuarios,buscar,no_hay,true,true,chk_familiares,chk_medicos);
                    usu.execute();
                    Log.d("OPCION - MEDICOS", "C");
                }

                if(!chk_familiares.isChecked() && !chk_medicos.isChecked()) {
                    chk_medicos.setChecked(true);
                    Log.d("OPCION - MEDICOS", "D");
                }

            }
        });


        chk_familiares.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!chk_familiares.isChecked() && chk_medicos.isChecked())
                {
                    UsuariosBD usu = new UsuariosBD(getContext(),"ListarUsuariosVinculaciones",
                            listaUsuarios,buscar,no_hay,false,true,chk_familiares,chk_medicos);
                    usu.execute();
                    Log.d("OPCION - FAMILIARES", "A");
                }

                if(chk_familiares.isChecked() && !chk_medicos.isChecked()){
                    UsuariosBD usu = new UsuariosBD(getContext(),"ListarUsuariosVinculaciones",
                            listaUsuarios,buscar,no_hay,true,false,chk_familiares,chk_medicos);
                    usu.execute();
                    Log.d("OPCION - FAMILIARES", "B");
                }

                if(chk_familiares.isChecked() && chk_medicos.isChecked()){
                    UsuariosBD usu = new UsuariosBD(getContext(),"ListarUsuariosVinculaciones",
                            listaUsuarios,buscar,no_hay,true,true,chk_familiares,chk_medicos);
                    usu.execute();
                    Log.d("OPCION - FAMILIARES", "C");
                }

                if(!chk_medicos.isChecked() && !chk_familiares.isChecked()) {
                    chk_familiares.setChecked(true);
                    Log.d("OPCION - FAMILIARES", "D");
                }

            }
        });



        //((MainActivity) getActivity()).hideFlotanteRedireccionContacto();
        vinculacionesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
}
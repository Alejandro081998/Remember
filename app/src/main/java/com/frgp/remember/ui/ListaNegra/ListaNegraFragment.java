package com.frgp.remember.ui.ListaNegra;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.frgp.remember.Base.ListaNegra.ListaNegraBD;
import com.frgp.remember.Dialogos.DialogoDesbloqueo;
import com.frgp.remember.Entidades.ListaNegra;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.Principal.MainActivity;
import com.frgp.remember.R;

public class ListaNegraFragment extends Fragment {

    private ListaNegraViewModel listaNegra;
    private ListView lista_negra;
    private SearchView buscar;
    private TextView no_hay_bloqueados;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listaNegra =
                ViewModelProviders.of(this).get(ListaNegraViewModel.class);
        View root = inflater.inflate(R.layout.fragment_lista_negra, container, false);
        ((MainActivity) getActivity()).hideFlotanteContacto();
        ((MainActivity) getActivity()).hideMenu();
        ((MainActivity) getActivity()) .hideVinculaciones();

        lista_negra = (ListView) root.findViewById(R.id.list_lista_negra);
        buscar = (SearchView) root.findViewById(R.id.buscar_bloqueado);
        no_hay_bloqueados = (TextView) root.findViewById(R.id.no_hay_lista_negra);


        ListaNegraBD lista = new ListaNegraBD(getContext(),"Listar",lista_negra,no_hay_bloqueados,buscar);
        lista.execute();

        lista_negra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ListaNegra list = new ListaNegra();
                Usuarios user = new Usuarios();

                TextView id_lista_negra = (TextView) view.findViewById(R.id.txt_id_lista_negra);
                TextView id_user_bloqueado = (TextView) view.findViewById(R.id.txt_id_user_bloqueado);

                Log.d("USUARIO:" , id_user_bloqueado.getText().toString());

                user.setId_usuario(Integer.parseInt(id_user_bloqueado.getText().toString()));
                list.setId_bloqueo(Integer.parseInt(id_lista_negra.getText().toString()));
                list.setId_user_bloqueado(user);

                DialogoDesbloqueo dialog = new DialogoDesbloqueo(list);
                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                dialog.show(fragmentManager, "");

            }
        });


        //final TextView textView = root.findViewById(R.id.text_send);
        listaNegra.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });


        return root;
    }

}

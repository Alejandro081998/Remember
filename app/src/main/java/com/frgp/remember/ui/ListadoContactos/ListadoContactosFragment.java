package com.frgp.remember.ui.ListadoContactos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.frgp.remember.Adaptadores.AdaptadorContactos;
import com.frgp.remember.Base.Contactos.ContactosBD;
import com.frgp.remember.Entidades.Contactos;
import com.frgp.remember.Principal.MainActivity;
import com.frgp.remember.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListadoContactosFragment extends Fragment {

    private ListadoContactosViewModel listadoContactosViewModel;
    private ListView lcontactos;
    private TextView no_hay;
    private SearchView buscar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listadoContactosViewModel =
                ViewModelProviders.of(this).get(ListadoContactosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_listado_contactos, container, false);
        ((MainActivity) getActivity()).showFlotanteContacto();
        ((MainActivity) getActivity()).hideMenu();
        ((MainActivity) getActivity()) .hideVinculaciones();

        lcontactos = root.findViewById(R.id.list_contactos);
        no_hay = root.findViewById(R.id.no_hay_contactos);
        buscar = root.findViewById(R.id.buscar_contacto);



        //adapter = new AdaptadorContactos(getContext(),listaContactos);



        ContactosBD cont = new ContactosBD(getContext(),"Listar",lcontactos,no_hay,buscar);
        cont.execute();

        //final TextView textView = root.findViewById(R.id.text_send);
        listadoContactosViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        lcontactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView id_contacto = (TextView) view.findViewById(R.id.alerta_invisible);
                TextView nombre = (TextView) view.findViewById(R.id.txt_nombre_contacto);
                TextView numero = (TextView) view.findViewById(R.id.txt_numero_contacto);
                int id_contacto_ = Integer.parseInt(id_contacto.getText().toString());
                String nombre_ = nombre.getText().toString();
                String numero_ = numero.getText().toString();

                Contactos cont = new Contactos();
                cont.setId_contacto(id_contacto_);
                cont.setNombre_contacto(nombre_);
                cont.setNumero(numero_);

                ((MainActivity) getActivity()).abrirDialogoContacto(cont);

            }
        });


        return root;
    }

}
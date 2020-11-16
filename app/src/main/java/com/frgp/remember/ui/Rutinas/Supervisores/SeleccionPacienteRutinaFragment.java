package com.frgp.remember.ui.Rutinas.Supervisores;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.frgp.remember.Principal.MainActivity;
import com.frgp.remember.R;

public class SeleccionPacienteRutinaFragment extends Fragment {

    private SeleccionPacienteRutinaViewModel seleccionPaciente ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        seleccionPaciente =
                ViewModelProviders.of(this).get(SeleccionPacienteRutinaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_detalle_perfil, container, false);
        //final TextView textView = root.findViewById(R.id.text_seguimiento);
        ((MainActivity) getActivity()).hideFlotanteContacto();
        ((MainActivity) getActivity()).hideMenu();
        ((MainActivity) getActivity()) .hideVinculaciones();
        //((MainActivity) getActivity()).hideFlotanteRedireccionContacto();
        seleccionPaciente.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
}
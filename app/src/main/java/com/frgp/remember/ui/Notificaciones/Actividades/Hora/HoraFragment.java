package com.frgp.remember.ui.Notificaciones.Actividades.Hora;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.frgp.remember.Principal.MainActivity;
import com.frgp.remember.R;

public class HoraFragment extends Fragment {

    private HoraViewModel horaViewModel;
    private Button btn_hora1;
    private Button btn_hora2;
    private Button btn_hora3;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        horaViewModel =
                ViewModelProviders.of(this).get(HoraViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ent_eshora, container, false);
        ((MainActivity) getActivity()).hideFlotanteContacto();
        ((MainActivity) getActivity()).hideMenu();
        ((MainActivity) getActivity()) .hideVinculaciones();
        //((MainActivity) getActivity()).hideFlotanteRedireccionContacto();
        //final TextView textView = root.findViewById(R.id.text_send);
        horaViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
}
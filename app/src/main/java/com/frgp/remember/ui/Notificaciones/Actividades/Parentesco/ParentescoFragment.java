package com.frgp.remember.ui.Notificaciones.Actividades.Parentesco;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.frgp.remember.Principal.MainActivity;
import com.frgp.remember.R;

public class ParentescoFragment extends Fragment {

    private ParentescoViewModel parentescoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        parentescoViewModel =
                ViewModelProviders.of(this).get(ParentescoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ent_parentesco, container, false);
        ((MainActivity) getActivity()).hideFlotanteContacto();
        ((MainActivity) getActivity()).hideMenu();
        ((MainActivity) getActivity()) .hideVinculaciones();
        //((MainActivity) getActivity()).hideFlotanteRedireccionContacto();
        //final TextView textView = root.findViewById(R.id.text_send);
        parentescoViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
}
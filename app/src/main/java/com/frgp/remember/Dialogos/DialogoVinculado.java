package com.frgp.remember.Dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.frgp.remember.Base.Vinculaciones.VinculacionesBD;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.Entidades.Vinculaciones;
import com.frgp.remember.R;
import com.frgp.remember.ui.Perfil.PerfilDetalle.PerfilDetalleFragment;
import com.frgp.remember.ui.Seguimiento.Detalle.DetallePerfilFragment;

public class DialogoVinculado extends AppCompatDialogFragment {

    private Usuarios usu;

    public DialogoVinculado(Usuarios u){

        this.usu = u;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("")
                .setItems(R.array.vinculacion, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if(which == 0) {
                            Bundle datosAEnviar = new Bundle();
                            datosAEnviar.putInt("usuario_perfil", usu.getId_usuario());

                            Fragment fragmento = new PerfilDetalleFragment();
                            fragmento.setArguments(datosAEnviar);
                            FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.content_main, fragmento).commit();
                        }
                        else {
                            Log.d("DIALOGO", "Vincularse");
                            VinculacionesBD vin = new VinculacionesBD(usu,getContext(),"Insertar");
                            vin.execute();
                        }
                        // The 'which' argument contains the index position
                        // of the selected item
                    }
                });
        return builder.create();
    }


}

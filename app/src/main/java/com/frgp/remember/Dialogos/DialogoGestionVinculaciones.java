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
import com.frgp.remember.ui.Rutinas.Raiz.RutinasFragment;

public class DialogoGestionVinculaciones extends AppCompatDialogFragment {

    private Vinculaciones vin;

    public DialogoGestionVinculaciones(Vinculaciones v){

        this.vin = v;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("");
                builder.setItems(R.array.gestion_vinculacion, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        VinculacionesBD vini;
                        Bundle datosAEnviar = new Bundle();

                        switch (which){

                            case 0:

                                datosAEnviar.putInt("usuario_perfil", vin.getId_paciente().getId_usuario());

                                Fragment fragmento = new PerfilDetalleFragment();
                                fragmento.setArguments(datosAEnviar);
                                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.content_main, fragmento).commit();
                                break;

                            case 1:
                                //desvincularse
                                vini = new VinculacionesBD(vin,getContext(),"Desvincularse");
                                vini.execute();
                                break;
                            case 2:
                                datosAEnviar.putInt("PacienteRutinas", vin.getId_paciente().getId_usuario());

                                Fragment fragmento_rut = new RutinasFragment();
                                fragmento_rut.setArguments(datosAEnviar);
                                FragmentManager fragmentManager_rut = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                                fragmentManager_rut.beginTransaction().replace(R.id.content_main, fragmento_rut).commit();
                                break;
                        }
                    }
                });
        return builder.create();
    }


}

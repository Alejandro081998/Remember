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
import com.frgp.remember.Session.Session;
import com.frgp.remember.ui.Perfil.PerfilDetalle.PerfilDetalleFragment;

public class DialogoVinculacion extends AppCompatDialogFragment {

    private Vinculaciones vin;

    public DialogoVinculacion(Vinculaciones v){

        this.vin = v;

    }

    public DialogoVinculacion(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("")
                .setItems(R.array.solicitud_vinculacion, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        VinculacionesBD vini;
                        Session ses = new Session();
                        ses.setCt(getContext());
                        ses.cargar_session();

                        switch (which){

                            case 0:
                                Bundle datosAEnviar = new Bundle();

                                if(ses.getTipo_rol().equals("Paciente"))
                                datosAEnviar.putInt("usuario_perfil", vin.getId_supervisor().getId_usuario());
                                else
                                datosAEnviar.putInt("usuario_perfil", vin.getId_paciente().getId_usuario());

                                Fragment fragmento = new PerfilDetalleFragment();
                                fragmento.setArguments(datosAEnviar);
                                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.content_main, fragmento).commit();
                                break;

                            case 1:
                                //rechazar
                                 vini = new VinculacionesBD(vin,getContext(),"Rechazar");
                                vini.execute();
                                break;

                            case 2:
                                vini = new VinculacionesBD(vin,getContext(),"Aceptar");
                                vini.execute();
                                //aceptar
                                break;


                        }
                    }
                });
        return builder.create();
    }


}

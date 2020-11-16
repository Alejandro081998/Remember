package com.frgp.remember.Dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.frgp.remember.Base.ListaNegra.ListaNegraBD;
import com.frgp.remember.Base.Vinculaciones.VinculacionesBD;
import com.frgp.remember.Entidades.ListaNegra;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.Entidades.Vinculaciones;
import com.frgp.remember.R;
import com.frgp.remember.Session.Session;
import com.frgp.remember.ui.Perfil.PerfilDetalle.PerfilDetalleFragment;

public class DialogoDesbloqueo extends AppCompatDialogFragment {

    private ListaNegra lista;

    public DialogoDesbloqueo(ListaNegra l){

        this.lista = l;

    }

    public DialogoDesbloqueo(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("")
                .setItems(R.array.lista_debloqueo, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Session ses = new Session();
                        ses.setCt(getContext());
                        ses.cargar_session();

                        switch (which){

                            case 0:
                                Bundle datosAEnviar = new Bundle();


                                datosAEnviar.putInt("usuario_perfil", lista.getId_user_bloqueado().getId_usuario());

                                Fragment fragmento = new PerfilDetalleFragment();
                                fragmento.setArguments(datosAEnviar);
                                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.content_main, fragmento).commit();
                                break;

                            case 1:
                                Usuarios user = new Usuarios();
                                user.setId_usuario(lista.getId_user_bloqueado().getId_usuario());
                                ListaNegraBD list = new ListaNegraBD(lista,user,getContext(),"Desbloquear");
                                list.execute();
                                break;


                        }
                    }
                });
        return builder.create();
    }


}

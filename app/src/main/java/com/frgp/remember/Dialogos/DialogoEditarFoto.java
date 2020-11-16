package com.frgp.remember.Dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.frgp.remember.Base.Usuarios.UsuariosBD;
import com.frgp.remember.Base.Vinculaciones.VinculacionesBD;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.Entidades.Vinculaciones;
import com.frgp.remember.GestionImagen.GestionarImagen;
import com.frgp.remember.R;
import com.frgp.remember.Registrarse.registrarse;

import java.io.IOException;

public class DialogoEditarFoto extends AppCompatDialogFragment  {

    //private Vinculaciones vin;
    private int PICK_IMAGE_REQUEST = 1;
    public static final int RESULT_OK = -1;

    public DialogoEditarFoto(){

        //this.vin = v;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("")
                .setItems(R.array.gestion_foto, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        VinculacionesBD vini;

                        switch (which){

                            case 0:
                                UsuariosBD usu = new UsuariosBD(getContext(),"EliminarFoto");
                                usu.execute();
                                break;

                            case 1:
                                Intent intent = new Intent(getContext(), GestionarImagen.class);
                                startActivity(intent);
                                break;
                        }
                    }
                });
        return builder.create();
    }

}
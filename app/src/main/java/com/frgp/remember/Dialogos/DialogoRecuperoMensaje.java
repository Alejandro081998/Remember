package com.frgp.remember.Dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.frgp.remember.Base.Recuperaciones.RecuperacionesBD;
import com.frgp.remember.Entidades.Recuperaciones;
import com.frgp.remember.IniciarSesion.iniciar_sesion;
import com.frgp.remember.Principal.MainActivity;
import com.frgp.remember.R;

public class DialogoRecuperoMensaje extends AppCompatDialogFragment {


    private EditText numero;

    private String numero_;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogo_recuperacion, null);

        builder.setView(view);
        builder.setTitle(R.string.ingrese_tel_recupero);


        numero = view.findViewById(R.id.telefono_recuperacion);

        builder.setPositiveButton(R.string.guardar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                numero_ = numero.getText().toString();
                boolean validacion = true;

                if (!numero_.isEmpty()) {

                    if(numero_.length()<7){
                        Toast.makeText(getContext(), "El telefono debe contar con al menos 7 caracteres!", Toast.LENGTH_SHORT).show();
                        validacion = false;
                    }

                    if(numero_.length()>15){
                        Toast.makeText(getContext(), "El telefono no debe exceder los 15 digitos!", Toast.LENGTH_SHORT).show();
                        validacion = false;
                    }

                    if(validacion){

                        String usuario = null;
                        String contrasena = null;
                        RecuperacionesBD rec = new RecuperacionesBD(getContext(),"EnviarMensaje",numero_,usuario,contrasena);
                        rec.execute();

                    }

                } else {
                    Toast.makeText(getContext(), "Complete todos los campos!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return builder.create();


    }


}



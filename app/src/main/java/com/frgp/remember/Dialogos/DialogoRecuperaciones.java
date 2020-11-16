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

import com.frgp.remember.Base.Contactos.ContactosBD;
import com.frgp.remember.Base.Recuperaciones.RecuperacionesBD;
import com.frgp.remember.Entidades.Recuperaciones;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.R;

public class DialogoRecuperaciones extends AppCompatDialogFragment {

    private EditText numero;
    private TextView id_recuperacion;

    private String numero_;
    private String recupe;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogo_recuperacion, null);

        builder.setView(view);
        builder.setTitle(R.string.tel_recu);


        numero = view.findViewById(R.id.telefono_recuperacion);
        id_recuperacion = view.findViewById(R.id.txt_id_recuperacion);


        RecuperacionesBD recu = new RecuperacionesBD(getContext(),"TraerTelefono",numero,id_recuperacion);
        recu.execute();


        builder.setNegativeButton(R.string.Eliminar_contacto, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Recuperaciones rec = new Recuperaciones();

                if(!id_recuperacion.getText().toString().isEmpty()) {
                    rec.setId_recuperacion(Integer.parseInt(id_recuperacion.getText().toString()));
                    rec.setTelefono(numero.getText().toString());
                    RecuperacionesBD rr = new RecuperacionesBD(rec, getContext(), "Eliminar");
                    rr.execute();
                }
                else
                    Toast.makeText(getContext(), "No tienes un telefono registrado!", Toast.LENGTH_SHORT).show();


            }
        });


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


                        Recuperaciones rec = new Recuperaciones();


                        if(id_recuperacion.getText().toString().isEmpty()){

                            rec.setTelefono(numero.getText().toString());
                            RecuperacionesBD rr = new RecuperacionesBD(rec,getContext(),"Guardar");
                            rr.execute();
                        }
                        else{

                            rec.setId_recuperacion(Integer.parseInt(id_recuperacion.getText().toString()));
                            rec.setTelefono(numero.getText().toString());

                            RecuperacionesBD rr = new RecuperacionesBD(rec,getContext(),"Guardar");
                            rr.execute();
                        }


                    }

                } else {
                    Toast.makeText(getContext(), "Complete todos los campos!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return builder.create();


    }


}


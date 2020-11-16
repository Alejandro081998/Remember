package com.frgp.remember.Dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.frgp.remember.Base.Contactos.ContactosBD;
import com.frgp.remember.Entidades.Contactos;
import com.frgp.remember.R;

public class NuevoContacto extends AppCompatDialogFragment {

    private EditText nombre_contacto;
    private EditText numero_contacto;
    private String nombre;
    private String numero;
    private Contactos cont;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogo_contacto, null);

        builder.setView(view);
        builder.setTitle(R.string.Nuevo_contacto);

        cont = new Contactos();
        nombre_contacto = view.findViewById(R.id.nombre_contacto);
        numero_contacto = view.findViewById(R.id.numero_contacto);


        builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {



            }
        });
        builder.setPositiveButton(R.string.agregar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                nombre = nombre_contacto.getText().toString();
                numero = numero_contacto.getText().toString();
                boolean validacion = true;

                if (!nombre.isEmpty() && !numero.isEmpty()) {

                    if(nombre.length()<3){
                        Toast.makeText(getContext(), "El nombre debe contar con al menos 3 caracteres!", Toast.LENGTH_SHORT).show();
                        validacion = false;
                    }

                    if(numero.length()<7){
                        Toast.makeText(getContext(), "El telefono debe contar con al menos 7 caracteres!", Toast.LENGTH_SHORT).show();
                        validacion = false;
                    }

                    if(numero.length()>15){
                        Toast.makeText(getContext(), "El telefono no debe exceder los 15 digitos!", Toast.LENGTH_SHORT).show();
                        validacion = false;
                    }

                    if(validacion){

                        cont.setNombre_contacto(nombre);
                        cont.setNumero(numero);
                        cont.setEstado(true);

                        ContactosBD con = new ContactosBD(cont,getContext(),"Insertar");
                        con.execute();

                    }

                } else {
                    Toast.makeText(getContext(), "Complete todos los campos!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return builder.create();


    }

}

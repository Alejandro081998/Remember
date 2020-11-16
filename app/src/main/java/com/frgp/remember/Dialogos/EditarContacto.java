package com.frgp.remember.Dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

import com.frgp.remember.Base.Contactos.ContactosBD;
import com.frgp.remember.Entidades.Contactos;
import com.frgp.remember.R;
import com.frgp.remember.ui.ListadoContactos.ListadoContactosFragment;

import java.util.List;

public class EditarContacto extends AppCompatDialogFragment {

    private EditText nombre_contacto;
    private EditText numero_contacto;
    private String nombre;
    private String numero;
    private Contactos cont;

    public EditarContacto(Contactos c){
        cont = new Contactos();
        this.cont = c;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogo_contacto, null);

        builder.setView(view);
        builder.setTitle(R.string.Editar_contacto);


        nombre_contacto = view.findViewById(R.id.nombre_contacto);
        numero_contacto = view.findViewById(R.id.numero_contacto);

        nombre_contacto.setText(cont.getNombre_contacto());
        numero_contacto.setText(cont.getNumero());

        builder.setNeutralButton(R.string.Eliminar_contacto, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                ContactosBD con = new ContactosBD(cont,getContext(),"Eliminar");
                con.execute();

            }
        });


        builder.setNegativeButton(R.string.Modificar_contacto, new DialogInterface.OnClickListener() {
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

                        ContactosBD con = new ContactosBD(cont,getContext(),"Modificar");
                        con.execute();


                    }

                } else {
                    Toast.makeText(getContext(), "Complete todos los campos!", Toast.LENGTH_SHORT).show();
                }


            }
        });
        builder.setPositiveButton(R.string.llamar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Uri number = Uri.parse("tel:"+cont.getNumero());
                /*Intent callIntent = new Intent(Intent.ACTION_DIAL, number);

                PackageManager packageManager = getActivity().getPackageManager();
                List<ResolveInfo> activities = packageManager.queryIntentActivities(callIntent,
                        PackageManager.MATCH_DEFAULT_ONLY);
                boolean isIntentSafe = activities.size() > 0;

                if (isIntentSafe) {
                    startActivity(callIntent);
                }*/


                Intent intent = new Intent(Intent.ACTION_DIAL, number);

               //String title = getResources().getString(R.string.chooser_title);
                // Create intent to show chooser
                Intent chooser = Intent.createChooser(intent, "");

                // Verify the intent will resolve to at least one activity
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(chooser);
                }



            }
        });

        return builder.create();


    }


}

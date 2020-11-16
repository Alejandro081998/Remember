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

import com.frgp.remember.Base.Rutinas.RutinasBD;
import com.frgp.remember.Base.Vinculaciones.VinculacionesBD;
import com.frgp.remember.Entidades.Rutinas;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.Entidades.Vinculaciones;
import com.frgp.remember.R;
import com.frgp.remember.Session.Session;
import com.frgp.remember.ui.Rutinas.Editar.EditarRutinaFragment;

public class DialogoRutina extends AppCompatDialogFragment {

    private Rutinas rut;

    public DialogoRutina(Rutinas r){

        this.rut = r;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Creado por: " + rut.getId_creador().getNombre() + " " + rut.getId_creador().getApellido())
                .setItems(R.array.dialogo_rutina, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        VinculacionesBD vini;
                        Usuarios user = new Usuarios();
                        user.setId_usuario(rut.getId_paciente().getId_usuario());
                        Session ses = new Session();
                        ses.setCt(getContext());
                        ses.cargar_session();

                        switch (which){

                            case 0:
                                if(ses.getTipo_rol().equals("Paciente")) {
                                    RutinasBD ruti = new RutinasBD(getContext(), rut, "Ignorar",user);
                                    ruti.execute();
                                }
                                else{
                                    RutinasBD ruti = new RutinasBD(getContext(), rut, "Ignorar",user);
                                    ruti.execute();
                                }
                                break;

                            case 1:
                                if(ses.getTipo_rol().equals("Paciente")) {
                                    RutinasBD rutii = new RutinasBD(getContext(), rut, "Activar",user);
                                    rutii.execute();
                                }
                                else{
                                    RutinasBD ruti = new RutinasBD(getContext(), rut, "Activar",user);
                                    ruti.execute();
                                }
                                break;

                            case 2:
                                Bundle datosAEnviar = new Bundle();
                                datosAEnviar.putInt("Rutina", rut.getId_rutina());
                                datosAEnviar.putInt("Creador", rut.getId_creador().getId_usuario());
                                datosAEnviar.putInt("Paciente", rut.getId_paciente().getId_usuario());
                                Log.d("PACIENTE:" , "" + rut.getId_paciente().getId_usuario());
                                datosAEnviar.putString("TipoRutina", rut.getTipo_rutina().getDescripcion());

                                Fragment fragmento = new EditarRutinaFragment();
                                fragmento.setArguments(datosAEnviar);
                                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.content_main, fragmento).commit();
                                break;


                        }
                    }
                });
        return builder.create();
    }


}
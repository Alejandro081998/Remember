package com.frgp.remember.Adaptadores;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorLong;
import androidx.annotation.NonNull;

import com.frgp.remember.Entidades.DiasXRutinas;
import com.frgp.remember.Entidades.Rutinas;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.Entidades.Vinculaciones;
import com.frgp.remember.R;
import com.frgp.remember.Session.Session;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorRutinasFamiliares extends ArrayAdapter<Rutinas>  {

    private Session ses;

    public AdaptadorRutinasFamiliares(Context context, List<Rutinas> objetos) {
        super(context, R.layout.fragment_rutinas, objetos);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.item_listado_rutinas_familiares, null);


        TextView titulo_rutina = (TextView) item.findViewById(R.id.txt_titulo);
        TextView hora = (TextView) item.findViewById(R.id.txt_hora);
        TextView id_rutina = (TextView) item.findViewById(R.id.txt_id_rutina);
        TextView id_paciente = (TextView) item.findViewById(R.id.txt_idusuario_paciente);
        TextView id_creador = (TextView) item.findViewById(R.id.txt_idusuario_creador);
        TextView tipo_rutina = (TextView) item.findViewById(R.id.txt_tipo_rutina);
        LinearLayout marco = (LinearLayout) item.findViewById(R.id.marco_familiar);
        TextView nombre_creador = (TextView) item.findViewById(R.id.txt_nombrecreador);
        TextView apellido_creador = (TextView) item.findViewById(R.id.txt_apellidocreador);

        ses = new Session();
        ses.setCt(getContext());
        ses.cargar_session();

        titulo_rutina.setText(getItem(position).getDescripcion());
        hora.setText("Hora: " + getItem(position).getHora().getHours() + ":" + getItem(position).getHora().getMinutes());
        id_rutina.setText(""+getItem(position).getId_rutina());
        nombre_creador.setText(getItem(position).getId_creador().getNombre());
        apellido_creador.setText(getItem(position).getId_creador().getApellido());
        id_paciente.setText("" + getItem(position).getId_paciente().getId_usuario());
        id_creador.setText("" + getItem(position).getId_creador().getId_usuario());
        tipo_rutina.setText(getItem(position).getTipo_rutina().getDescripcion());

        if(getItem(position).getEstados().getId_estado() == 7){

            marco.setBackgroundColor(getContext().getResources().getColor(R.color.verde_clarito));

        }else if(getItem(position).getEstados().getId_estado() == 8){

            marco.setBackgroundColor(getContext().getResources().getColor(R.color.rojo_clarito));

        }

        return item;
    }

}
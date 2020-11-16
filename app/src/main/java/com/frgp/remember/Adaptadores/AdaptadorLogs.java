package com.frgp.remember.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.frgp.remember.Entidades.LogsUsuarios;
import com.frgp.remember.R;

import java.util.List;

public class AdaptadorLogs extends ArrayAdapter <LogsUsuarios> {

    Context contexto;
    List<LogsUsuarios> ListaLogs;

    public AdaptadorLogs(Context contexto, List<LogsUsuarios> Listobjects) {
        super(contexto, R.layout.fragment_perfil_seguimiento,Listobjects);
        this.ListaLogs = Listobjects;
    }
    @Override
    public int getCount() {
        return ListaLogs.size();
    }

    @Nullable
    @Override
    public LogsUsuarios getItem(int position) {
        return ListaLogs.get(getCount() - position - 1);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.item_logs, null);


        TextView usulog = (TextView) item.findViewById(R.id.txtvLog);
        TextView feclog = (TextView) item.findViewById(R.id.fechahora) ;

        usulog.setText(getItem(position).getLogdesc());
        feclog.setText("" + +getItem(position).getFecha_hora_log().getDate() + "/" + (getItem(position).getFecha_hora_log().getMonth() + 1) + " - "  + getItem(position).getFecha_hora_log().getHours() + ":" + getItem(position).getFecha_hora_log().getMinutes() );




        return item;
    }

}
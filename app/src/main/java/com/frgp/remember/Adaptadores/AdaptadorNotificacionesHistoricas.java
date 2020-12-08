package com.frgp.remember.Adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.frgp.remember.Entidades.Apartados;
import com.frgp.remember.Entidades.Notificaciones;
import com.frgp.remember.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorNotificacionesHistoricas extends ArrayAdapter<Notificaciones> implements Filterable {

    protected List<Notificaciones> objetos;
    CustomFilter filter;
    List<Notificaciones> filterlist;

    public AdaptadorNotificacionesHistoricas(Context context, List<Notificaciones> objetos) {
        super(context, R.layout.fragment_historico_notificaciones, objetos);
        this.objetos = objetos;
        this.filterlist = objetos;
    }

    @Override
    public int getCount() {
        return objetos.size();
    }

    @Nullable
    @Override
    public Notificaciones getItem(int position) {
        return objetos.get(getCount() - position - 1);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.item_listado_notificaciones, null);


        LinearLayout marco = (LinearLayout) item.findViewById(R.id.marco_notificaciones);
        TextView descripcion = (TextView) item.findViewById(R.id.txt_descripcion_notificacion);
        TextView hora = (TextView) item.findViewById(R.id.txt_hora);
        TextView id_notificacion = (TextView) item.findViewById(R.id.txt_id_notificacion);
        TextView idapartado = (TextView) item.findViewById(R.id.txt_idapartado);
        TextView remitente = (TextView) item.findViewById(R.id.txt_id_remitente);
        TextView destinatario = (TextView) item.findViewById(R.id.txt_id_receptor);
        TextView apart_descripcion = (TextView) item.findViewById(R.id.txt_apart_descrip);

        descripcion.setText(getItem(position).getDescripcion());
        hora.setText("" + +getItem(position).getHora().getDate() + "/" + (getItem(position).getHora().getMonth() + 1) + " - "  + getItem(position).getHora().getHours() + ":" + getItem(position).getHora().getMinutes() );
        id_notificacion.setText("" + getItem(position).getIdNotificacion());
        idapartado.setText("" + getItem(position).getIdApartado().getIdApartado());
        remitente.setText("" + getItem(position).getIdRemitente());
        destinatario.setText("" + getItem(position).getIdDestinatario());
        apart_descripcion.setText(getItem(position).getIdApartado().getDescripcion());






        return item;
    }


    @NonNull
    @Override
    public Filter getFilter() {
        if(filter==null){
            filter = new CustomFilter();
        }
        return filter;
    }

    class CustomFilter extends Filter{


        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            if(charSequence!=null && charSequence.length()>0){

                charSequence = charSequence.toString().toUpperCase();
                ArrayList<Notificaciones> filters = new ArrayList<>();
                for(int i = 0;i<filterlist.size();i++){
                    if(filterlist.get(i).getDescripcion().toUpperCase().contains(charSequence)){

                        Notificaciones not = new Notificaciones();
                        Apartados apart = new Apartados();

                        not.setDescripcion(filterlist.get(i).getDescripcion());
                        not.setHora(filterlist.get(i).getHora());
                        not.setIdNotificacion(filterlist.get(i).getIdNotificacion());
                        apart.setIdApartado(filterlist.get(i).getIdApartado().getIdApartado());
                        not.setIdApartado(apart);
                        not.setIdDestinatario(filterlist.get(i).getIdDestinatario());
                        not.setIdRemitente(filterlist.get(i).getIdRemitente());

                        filters.add(not);

                    }

                }
                results.count = filters.size();
                results.values = filters;

            }else{

                results.count = filterlist.size();
                results.values = filterlist;

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            objetos = (ArrayList<Notificaciones>)filterResults.values;
            notifyDataSetChanged();
        }
    }

}

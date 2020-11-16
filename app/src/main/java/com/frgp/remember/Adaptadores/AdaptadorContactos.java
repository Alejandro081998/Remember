package com.frgp.remember.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.frgp.remember.R;
import com.frgp.remember.Entidades.Contactos;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorContactos extends ArrayAdapter<Contactos> implements Filterable {

    protected List<Contactos> objetos;
    CustomFilter filter;
    List<Contactos> filterlist;

    public AdaptadorContactos(Context context, List<Contactos> objetos) {
        super(context, R.layout.fragment_listado_contactos, objetos);
        this.objetos = objetos;
        this.filterlist = objetos;
    }

    @Override
    public int getCount() {
        return objetos.size();
    }

    @Nullable
    @Override
    public Contactos getItem(int position) {
        return objetos.get(getCount() - position - 1);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.item_listado_contactos, null);


        TextView nombre = (TextView) item.findViewById(R.id.txt_nombre_contacto);
        TextView numero = (TextView) item.findViewById(R.id.txt_numero_contacto);
        //ImageButton btn_llamar = (ImageButton) item.findViewById(R.id.btn_llamar);
        TextView invisible = (TextView) item.findViewById(R.id.alerta_invisible);
        //TableRow fila_pintar = (TableRow) item.findViewById(R.id.fila_pintar);


        if(getItem(position).getEstado() == true){


            nombre.setText(getItem(position).getNombre_contacto());
            //nombre.setBackgroundColor(Color.GREEN);
            numero.setText("" + getItem(position).getNumero());
            //numero.setBackgroundColor(Color.GREEN);
            invisible.setText(getItem(position).getId_contacto() +"");
            //btn_llamar.setBackgroundColor(Color.GREEN);
            //fila_pintar.setBackgroundColor(Color.GREEN);

        }

        if(getItem(position).getEstado() == false){


            nombre.setText(getItem(position).getNombre_contacto());
            //nombre.setBackgroundColor(Color.YELLOW);
            numero.setText("" + getItem(position).getNumero());
            //numero.setBackgroundColor(Color.YELLOW);
            invisible.setText(getItem(position).getId_contacto() +"");
            //btn_llamar.setBackgroundColor(Color.YELLOW);
            //fila_pintar.setBackgroundColor(Color.YELLOW);
        }

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
                ArrayList<Contactos> filters = new ArrayList<>();
                for(int i = 0;i<filterlist.size();i++){
                    if(filterlist.get(i).getNombre_contacto().toUpperCase().contains(charSequence)){
                        Contactos c = new Contactos();
                        c.setNombre_contacto(filterlist.get(i).getNombre_contacto());
                        c.setId_contacto(filterlist.get(i).getId_contacto());
                        c.setNumero(filterlist.get(i).getNumero());
                        filters.add(c);

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
            objetos = (ArrayList<Contactos>)filterResults.values;
            notifyDataSetChanged();
        }
    }

}


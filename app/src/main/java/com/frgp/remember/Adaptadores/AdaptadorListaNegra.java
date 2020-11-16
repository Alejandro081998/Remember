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

import com.frgp.remember.Entidades.ListaNegra;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorListaNegra extends ArrayAdapter<ListaNegra> implements Filterable {

    protected List<ListaNegra> objetos;
    CustomFilter filter;
    List<ListaNegra> filterlist;

    public AdaptadorListaNegra(Context context, List<ListaNegra> objetos) {
        super(context, R.layout.fragment_lista_negra, objetos);
        this.objetos = objetos;
        this.filterlist = objetos;
    }

    @Override
    public int getCount() {
        return objetos.size();
    }

    @Nullable
    @Override
    public ListaNegra getItem(int position) {
        return objetos.get(getCount() - position - 1);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.item_lista_negra, null);


        TextView nombre = (TextView) item.findViewById(R.id.txt_nombre_usuario_negra);
        TextView id_lista_negra = (TextView) item.findViewById(R.id.txt_id_lista_negra);
        TextView id_user_bloqueado = (TextView) item.findViewById(R.id.txt_id_user_bloqueado);


        nombre.setText(getItem(position).getId_user_bloqueado().getNombre() + " " + getItem(position).getId_user_bloqueado().getApellido());
        id_lista_negra.setText("" + getItem(position).getId_bloqueo());
        id_user_bloqueado.setText("" + getItem(position).getId_user_bloqueado().getId_usuario());



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
                ArrayList<ListaNegra> filters = new ArrayList<>();
                for(int i = 0;i<filterlist.size();i++){
                    if(filterlist.get(i).getId_user_bloqueado().getNombre().toUpperCase().contains(charSequence) ||
                            filterlist.get(i).getId_user_bloqueado().getApellido().toUpperCase().contains(charSequence)){

                        ListaNegra list = new ListaNegra();
                        Usuarios user = new Usuarios();
                        Usuarios user2 = new Usuarios();


                        user2.setId_usuario(filterlist.get(i).getId_user_bloqueado().getId_usuario());
                        user2.setNombre(filterlist.get(i).getId_user_bloqueado().getNombre());
                        user2.setApellido(filterlist.get(i).getId_user_bloqueado().getApellido());
                        user.setId_usuario(filterlist.get(i).getId_usuario().getId_usuario());
                        list.setId_user_bloqueado(filterlist.get(i).getId_user_bloqueado());
                        list.setId_user_bloqueado(user2);
                        list.setId_usuario(user);

                        filters.add(list);

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
            objetos = (ArrayList<ListaNegra>)filterResults.values;
            notifyDataSetChanged();
        }
    }

}
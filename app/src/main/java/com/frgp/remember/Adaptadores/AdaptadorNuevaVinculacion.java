package com.frgp.remember.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.frgp.remember.Entidades.TipoRol;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.R;
import com.frgp.remember.Entidades.Contactos;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorNuevaVinculacion extends ArrayAdapter<Usuarios> implements Filterable {

    protected List<Usuarios> objetos;
    CustomFilter filter;
    List<Usuarios> filterlist;

    public AdaptadorNuevaVinculacion(Context context, List<Usuarios> objetos) {
        super(context, R.layout.fragment_nueva_vinculacion, objetos);
        this.objetos = objetos;
        this.filterlist = objetos;
    }

    @Override
    public int getCount() {
        return objetos.size();
    }

    @Nullable
    @Override
    public Usuarios getItem(int position) {
        return objetos.get(getCount() - position - 1);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.item_listado_usuarios_vinculaciones, null);


        TextView nombre = (TextView) item.findViewById(R.id.txt_nombre_nuevo_vinculado);
        TextView usuario = (TextView) item.findViewById(R.id.txt_usuario_vinculado);
        TextView invisible = (TextView) item.findViewById(R.id.txt_id_usuario_vinculado);
        ImageView profesional = (ImageView) item.findViewById(R.id.es_medico);
        ImageView paciente = (ImageView) item.findViewById(R.id.es_paciente);
        ImageView familiar = (ImageView) item.findViewById(R.id.es_familiar);


        nombre.setText(getItem(position).getNombre() + " " + getItem(position).getApellido());
        usuario.setText("Usuario: " + getItem(position).getUsuario());
        invisible.setText(getItem(position).getId_usuario() +"");

        if(getItem(position).getTipo().getRol().equals("Supervisor")) {
            paciente.setVisibility(View.GONE);
            familiar.setVisibility(View.GONE);
            profesional.setVisibility(View.VISIBLE);
        }
        else if(getItem(position).getTipo().getRol().equals("Familiar")){
            paciente.setVisibility(View.GONE);
            profesional.setVisibility(View.GONE);
            familiar.setVisibility(View.VISIBLE);
        }
        else{
            paciente.setVisibility(View.VISIBLE);
            profesional.setVisibility(View.GONE);
            familiar.setVisibility(View.GONE);
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
                ArrayList<Usuarios> filters = new ArrayList<>();
                for(int i = 0;i<filterlist.size();i++){
                    if(filterlist.get(i).getNombre().toUpperCase().contains(charSequence) ||
                            filterlist.get(i).getApellido().toUpperCase().contains(charSequence) ||
                            filterlist.get(i).getUsuario().toUpperCase().contains(charSequence)
                    || filterlist.get(i).getTipo().getRol().toUpperCase().contains(charSequence)){
                        Usuarios u = new Usuarios();
                        TipoRol t = new TipoRol();
                        u.setNombre(filterlist.get(i).getNombre());
                        u.setApellido(filterlist.get(i).getApellido());
                        u.setId_usuario(filterlist.get(i).getId_usuario());
                        u.setUsuario(filterlist.get(i).getUsuario());
                        t.setId_rol(filterlist.get(i).getTipo().getId_rol());
                        t.setRol(filterlist.get(i).getTipo().getRol());
                        u.setTipo(t);
                        filters.add(u);

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
            objetos = (ArrayList<Usuarios>)filterResults.values;
            notifyDataSetChanged();
        }
    }

}
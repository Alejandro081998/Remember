package com.frgp.remember.Adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.frgp.remember.Entidades.Contactos;
import com.frgp.remember.Entidades.Seguimiento;
import com.frgp.remember.Entidades.SeguimientoDatos;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdaptadorSeguimiento extends ArrayAdapter<Seguimiento> {

    protected List<Seguimiento> objetos;
    AdaptadorSeguimiento.CustomFilter filter;
    List<Seguimiento> filterlist;

    public AdaptadorSeguimiento(Context contexto, List<Seguimiento> listaObjetos) {
        super(contexto, R.layout.fragment_seguimiento, listaObjetos);
        this.objetos = listaObjetos;
        this.filterlist = objetos;
    }

    @Override
    public int getCount() {
        return objetos.size();
    }

    @Nullable
    @Override
    public Seguimiento getItem(int position) {
        return objetos.get(getCount() - position - 1);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista = convertView;

        LayoutInflater inflate = LayoutInflater.from(getContext());
        vista = inflate.inflate(R.layout.item_listado_seguimiento,null);

        ImageView imagen = (ImageView) vista.findViewById(R.id.idImagen);
        TextView id_usuario = (TextView) vista.findViewById(R.id.txt_idusuario_seguimiento);
        TextView nombre = (TextView) vista.findViewById(R.id.txt_nombre_seguimiento);
        TextView usuario = (TextView) vista.findViewById(R.id.txt_usu_seguimiento);


        id_usuario.setText("" + getItem(position).getUsuarios().getId_usuario());
        nombre.setText(getItem(position).getUsuarios().getNombre() + " " + getItem(position).getUsuarios().getApellido());
        usuario.setText("Usuario: " + getItem(position).getUsuarios().getUsuario());
        imagen.setImageBitmap(getItem(position).getImagen());

        return vista;
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
                ArrayList<Seguimiento> filters = new ArrayList<>();
                for(int i = 0;i<filterlist.size();i++){
                    if(filterlist.get(i).getUsuarios().getNombre().toUpperCase().contains(charSequence) ||
                            filterlist.get(i).getUsuarios().getApellido().toUpperCase().contains(charSequence)){
                        Seguimiento s = new Seguimiento();
                        Usuarios u = new Usuarios();

                        u.setNombre(filterlist.get(i).getUsuarios().getNombre());
                        u.setApellido(filterlist.get(i).getUsuarios().getApellido());
                        u.setId_usuario(filterlist.get(i).getUsuarios().getId_usuario());
                        u.setUsuario(filterlist.get(i).getUsuarios().getUsuario());
                        s.setImagen(filterlist.get(i).getImagen());
                        s.setUsuarios(u);
                        filters.add(s);
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
            objetos = (ArrayList<Seguimiento>)filterResults.values;
            notifyDataSetChanged();
        }
    }



}
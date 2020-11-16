package com.frgp.remember.Adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.R;

import java.util.ArrayList;
import java.util.List;

public class Seguimiento_prueba extends ArrayAdapter<Usuarios> implements Filterable {

        private Bitmap image = null;
        private List<Bitmap> imagenes;
        protected List<Usuarios> objetos;
        CustomFilter filter;
        List<Usuarios> filterlist;

        public Seguimiento_prueba(Context context, List<Usuarios> objetos, List<Bitmap> img) {
            super(context, R.layout.fragment_seguimiento, objetos);
            this.objetos = objetos;
            this.filterlist = objetos;
            this.imagenes = img;
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

            View vista = convertView;

            LayoutInflater inflate = LayoutInflater.from(getContext());
            vista = inflate.inflate(R.layout.item_listado_seguimiento,null);

            ImageView imagen = (ImageView) vista.findViewById(R.id.idImagen);
            TextView id_usuario = (TextView) vista.findViewById(R.id.txt_idusuario_seguimiento);
            TextView nombre = (TextView) vista.findViewById(R.id.txt_nombre_seguimiento);
            TextView apellido = (TextView) vista.findViewById(R.id.txt_apellido_seguimiento);
            TextView usuario = (TextView) vista.findViewById(R.id.txt_usu_seguimiento);


            id_usuario.setText("" + getItem(position).getId_usuario());
            nombre.setText(getItem(position).getNombre());
            apellido.setText(getItem(position).getApellido());
            usuario.setText(getItem(position).getUsuario());

            imagen.setImageBitmap(imagenes.get(position));

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
                    ArrayList<Usuarios> filters = new ArrayList<>();
                    for(int i = 0;i<filterlist.size();i++){
                        if(filterlist.get(i).getNombre().toUpperCase().contains(charSequence) ||
                                filterlist.get(i).getApellido().toUpperCase().contains(charSequence)
                         || filterlist.get(i).getUsuario().toUpperCase().contains(charSequence)){

                            Usuarios user = new Usuarios();
                            user.setNombre(filterlist.get(i).getNombre());
                            user.setApellido(filterlist.get(i).getApellido());
                            user.setUsuario(filterlist.get(i).getUsuario());
                            user.setId_usuario(filterlist.get(i).getId_usuario());


                            filters.add(user);

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

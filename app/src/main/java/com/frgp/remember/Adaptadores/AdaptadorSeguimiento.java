package com.frgp.remember.Adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.frgp.remember.Entidades.Contactos;
import com.frgp.remember.Entidades.SeguimientoDatos;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdaptadorSeguimiento extends ArrayAdapter<Usuarios> {

    private Bitmap image = null;
    private List<Bitmap> imagenes;

    public AdaptadorSeguimiento(Context contexto, List<Usuarios> listaObjetos, List<Bitmap> img) {
        super(contexto, R.layout.fragment_seguimiento, listaObjetos);
        this.imagenes = img;
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


        id_usuario.setText("" + getItem(position).getId_usuario());
        nombre.setText(getItem(position).getNombre() + " " + getItem(position).getApellido());
        usuario.setText("Usuario: " + getItem(position).getUsuario());

        imagen.setImageBitmap(imagenes.get(position));

        return vista;
    }
}
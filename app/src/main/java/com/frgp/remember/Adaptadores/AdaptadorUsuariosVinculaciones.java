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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.frgp.remember.Entidades.Contactos;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.Entidades.Vinculaciones;
import com.frgp.remember.R;
import com.frgp.remember.Session.Session;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorUsuariosVinculaciones extends ArrayAdapter<Vinculaciones> implements Filterable {

    private Session ses;
    protected List<Vinculaciones> objetos;
    CustomFilter filter;
    List<Vinculaciones> filterlist;

    public AdaptadorUsuariosVinculaciones(Context context, List<Vinculaciones> objetos) {
        super(context, R.layout.fragment_perfil, objetos);
        this.objetos = objetos;
        this.filterlist = objetos;
    }

    @Override
    public int getCount() {
        return objetos.size();
    }

    @Nullable
    @Override
    public Vinculaciones getItem(int position) {
        return objetos.get(getCount() - position - 1);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.item_listado_vinculaciones, null);


        TextView nombre_apellido = (TextView) item.findViewById(R.id.txt_nombre_apellido);
        TextView id_vinculacion = (TextView) item.findViewById(R.id.txt_id_vinculacion);
        TextView id_usuario = (TextView) item.findViewById(R.id.txt_idusuario_vinculacion);
        ImageView img_medico = (ImageView) item.findViewById(R.id.es_medico_animacion);
        ImageView img_familia = (ImageView) item.findViewById(R.id.es_familiar_animacion);
        ImageView img_paciente = (ImageView) item.findViewById(R.id.es_paciente_animacion);
        LinearLayout marco = (LinearLayout) item.findViewById(R.id.marco_vinculacion);

        ses = new Session();
        ses.setCt(getContext());
        ses.cargar_session();


        if (ses.getTipo_rol().equals("Paciente")){

            nombre_apellido.setText(getItem(position).getId_supervisor().getNombre() + " " + getItem(position).getId_supervisor().getApellido());
            //nombre_apellido.setText("Alejandro");
            id_vinculacion.setText("" + getItem(position).getId_vinculacion());
            id_usuario.setText("" + getItem(position).getId_supervisor().getId_usuario());

            if(getItem(position).getId_supervisor().getTipo().getRol().equals("Supervisor")) {
                img_medico.setVisibility(View.VISIBLE);
                img_familia.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    marco.setBackground(getContext().getResources().getDrawable(R.drawable.marco_rojo));
                }
            }
            else {
                img_medico.setVisibility(View.GONE);
                img_familia.setVisibility(View.VISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    marco.setBackground(getContext().getResources().getDrawable(R.drawable.marco_amarillo));
                }
            }

            img_paciente.setVisibility(View.GONE);

        }else{
            nombre_apellido.setText(getItem(position).getId_paciente().getNombre() + " " + getItem(position).getId_paciente().getApellido());
            id_vinculacion.setText("" + getItem(position).getId_vinculacion());
            id_usuario.setText("" + getItem(position).getId_paciente().getId_usuario());
            img_medico.setVisibility(View.GONE);
            img_familia.setVisibility(View.GONE);
            img_paciente.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                marco.setBackground(getContext().getResources().getDrawable(R.drawable.marco_violeta));
            }
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
                ArrayList<Vinculaciones> filters = new ArrayList<>();
                for(int i = 0;i<filterlist.size();i++){
                    if(filterlist.get(i).getId_paciente().getNombre().toUpperCase().contains(charSequence) ||
                            filterlist.get(i).getId_paciente().getApellido().toUpperCase().contains(charSequence)){
                        Vinculaciones v = new Vinculaciones();
                        Usuarios u = new Usuarios();
                        u.setNombre(filterlist.get(i).getId_paciente().getNombre());
                        u.setApellido(filterlist.get(i).getId_paciente().getApellido());
                        v.setId_vinculacion(filterlist.get(i).getId_vinculacion());
                        v.setId_paciente(u);
                        filters.add(v);

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
            objetos = (ArrayList<Vinculaciones>)filterResults.values;
            notifyDataSetChanged();
        }
    }

}

package com.frgp.remember.ui.Notificaciones.Historico;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.frgp.remember.Base.ListaNegra.ListaNegraBD;
import com.frgp.remember.Base.Notificaciones.NotificacionesBD;
import com.frgp.remember.Dialogos.DialogoDesbloqueo;
import com.frgp.remember.Entidades.ListaNegra;
import com.frgp.remember.Entidades.Notificaciones;
import com.frgp.remember.Entidades.Usuarios;
import com.frgp.remember.Principal.MainActivity;
import com.frgp.remember.R;
import com.frgp.remember.Session.Session;
import com.frgp.remember.ui.ListaNegra.ListaNegraViewModel;
import com.frgp.remember.ui.Rutinas.Raiz.RutinasFragment;
import com.frgp.remember.ui.Vinculaciones.Listado.ListadoVinculacionesFragment;
import com.frgp.remember.ui.Vinculaciones.ListadoProfesional.VinculacionesProfesionalFragment;
import com.frgp.remember.ui.Vinculaciones.Pendientes.VinculacionesPendientesFragment;

public class NotificacionesHistoricoFragment extends Fragment {

    private NotificacionesHistoricoViewModel notificacionesHistoricoViewModel;
    private ListView list_notificaciones;
    private SearchView buscar_notificacion;
    private TextView no_hay_notificaciones;
    private Session ses;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificacionesHistoricoViewModel =
                ViewModelProviders.of(this).get(NotificacionesHistoricoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_historico_notificaciones, container, false);
        ((MainActivity) getActivity()).hideFlotanteContacto();
        ((MainActivity) getActivity()).hideMenu();
        ((MainActivity) getActivity()) .hideVinculaciones();

        list_notificaciones = (ListView) root.findViewById(R.id.list_notificaciones_historico);
        buscar_notificacion = (SearchView) root.findViewById(R.id.buscar_notificacion_historico);
        no_hay_notificaciones = (TextView) root.findViewById(R.id.no_hay_notificaciones_historicas_historico);

        list_notificaciones.addFooterView(new View(getContext()), null, true);
        list_notificaciones.addHeaderView(new View(getContext()), null, true);

        NotificacionesBD not = new NotificacionesBD(getContext(),"CargarNotificacionesHistoricas"
                ,no_hay_notificaciones,list_notificaciones,buscar_notificacion);
        not.execute();

        ses = new Session();
        ses.setCt(getContext());
        ses.cargar_session();

        list_notificaciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView apartado = (TextView) view.findViewById(R.id.txt_apart_descrip);
                TextView noti_id = (TextView) view.findViewById(R.id.txt_id_notificacion);

                String apart = apartado.getText().toString();

                Notificaciones not = new Notificaciones();
                not.setIdNotificacion(Integer.parseInt(noti_id.getText().toString()));

                NotificacionesBD notbd = new NotificacionesBD(getContext(),not,"Leida");
                notbd.execute();


                Log.d("NOTIF", "TOCO EN NOTIFICACION");
                Log.d("APARTADO:", apart);

                if(apart.equals("Vinculaciones Pendientes")){

                    FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_main, new VinculacionesPendientesFragment()).commit();

                }

                if(apart.equals("Vinculaciones Existentes")){

                    if(ses.getTipo_rol().equals("Paciente")) {
                        FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_main, new ListadoVinculacionesFragment()).commit();
                    }
                    else{
                        FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_main, new VinculacionesProfesionalFragment()).commit();
                    }
                }

                if(apart.equals("Rutinas Existentes")){

                    FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_main, new RutinasFragment()).commit();

                }


            }
        });


        return root;
    }

}

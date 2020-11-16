package com.frgp.remember.Registrarse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.frgp.remember.R;

public class registrarse extends AppCompatActivity {

    private Button btn_profesional;
    private Button btn_familiar;
    private Button btn_paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrarse);

        btn_familiar = (Button) findViewById(R.id.btnFamiliar);
        btn_paciente = (Button) findViewById(R.id.btnPaciente);
        btn_profesional = (Button) findViewById(R.id.btnProfesional);

        final Intent intent = new Intent(this, registrar_datos.class);

        btn_familiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent.putExtra("Matricula","no");
                intent.putExtra("Rol","Familiar");
                startActivity(intent);
            }
        });

        btn_paciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent.putExtra("Matricula","no");
                intent.putExtra("Rol","Paciente");
                startActivity(intent);
            }
        });

        btn_profesional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent.putExtra("Matricula","si");
                intent.putExtra("Rol","Supervisor");
                startActivity(intent);
            }
        });



    }
}

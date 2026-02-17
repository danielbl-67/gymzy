package com.example.gymzy.musculosGeneral;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gymzy.general.PantallasPrincipales.ListaRutina;
import com.example.gymzy.R;

public class pierna extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.mg_pierna);


        Button buttonSalir;
        buttonSalir = findViewById(R.id.buttonSalir);
        buttonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pierna.this, ListaRutina.class);
                startActivity(intent);
            }
        });

    }


}

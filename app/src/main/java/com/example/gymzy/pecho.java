package com.example.gymzy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class pecho extends AppCompatActivity {
    private SessionManager sessionManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.pecho);

        sessionManager = new SessionManager(this);

        Button buttonSalir;
        buttonSalir = findViewById(R.id.buttonSalir);
        sessionManager.playClickSound(); // 🔊 Sonido al hacer click
        buttonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(pecho.this, ListaRutina.class);
                startActivity(intent);
            }
        });

        // En tu activity principal (pecho.java)
        ImageButton imageButton1 = findViewById(R.id.imageButton1);
        imageButton1.setOnClickListener(v -> {
            sessionManager.playClickSound(); // 🔊 Sonido al hacer click
            Intent intent = new Intent(this, PressBancaDetalleActivity.class);
            startActivity(intent);
        });

    }


}

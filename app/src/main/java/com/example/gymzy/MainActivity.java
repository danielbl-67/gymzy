package com.example.gymzy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView tvGreeting;
    Button btnEmpezar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        tvGreeting = findViewById(R.id.tvGreeting);
        btnEmpezar = findViewById(R.id.btnEmpezar);

        btnEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Mostrar Toast
                Toast.makeText(MainActivity.this, "¡Comenzando tu entrenamiento diario!", Toast.LENGTH_SHORT).show();
                // Ir a ListaRutina
                Intent intent = new Intent(MainActivity.this, ListaRutina.class);
                startActivity(intent);
            }
        });



    }
}
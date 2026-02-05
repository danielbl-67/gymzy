package com.example.gymzy.general;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.gymzy.R;
import com.example.gymzy.general.sesion.SessionManager;

public class HomeActivity extends DrawerBaseActivity {

    private SessionManager sessionManager;
    private float litrosActuales = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Inflamos el layout corregido que te pasé
        View view = getLayoutInflater().inflate(R.layout.activity_home, null);
        // 2. IMPORTANTE: Usamos el metodo de DrawerBaseActivity
        setContentView(view);
        allocateActivityTitle("");

        sessionManager = new SessionManager(this);

        // Configurar saludo
        TextView tvWelcome = findViewById(R.id.tvWelcomeUser);
        String username = sessionManager.getUsername();
        tvWelcome.setText("¡Hola, " + (username != null ? username : "Atleta") + "!");

        // Lógica del botón de Agua
        Button btnAddWater = findViewById(R.id.btnAddWater);
        TextView tvWaterCount = findViewById(R.id.tvWaterCount);

        btnAddWater.setOnClickListener(v -> {
            litrosActuales += 0.25f; // Suma un vaso (250ml)
            tvWaterCount.setText(String.format("%.1fL / 2.5L", litrosActuales));
            Toast.makeText(this, "¡Hidratación registrada!", Toast.LENGTH_SHORT).show();
        });
    }
}
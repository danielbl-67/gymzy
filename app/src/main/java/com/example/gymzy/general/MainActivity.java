package com.example.gymzy.general;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.gymzy.R;
import com.example.gymzy.general.sesion.SessionManager;
import com.example.gymzy.musculosGeneral.hombro;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

// 1. Cambiamos la herencia a DrawerBaseActivity
public class MainActivity extends DrawerBaseActivity {

    TextView tvGreeting;
    Button btnIniciarSesion, btnVerPrecios;
    TextInputEditText editTextUsuario, editTextContrasena;
    TextInputLayout textInputLayoutUsuario, textInputLayoutContrasena;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = getLayoutInflater().inflate(R.layout.activity_main, null);
        setContentView(view);
        sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {
            redirigirAHome();
            return;
        }

        // Inicializar vistas
        tvGreeting = findViewById(R.id.tvGreeting);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnVerPrecios = findViewById(R.id.btnVerPrecios);
        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextContrasena = findViewById(R.id.editTextContrasena);
        textInputLayoutUsuario = findViewById(R.id.textInputLayoutUsuario);
        textInputLayoutContrasena = findViewById(R.id.textInputLayoutContrasena);

        btnIniciarSesion.setOnClickListener(v -> iniciarSesion());

        btnVerPrecios.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
        });

    }

    private void iniciarSesion() {
        String usuario = editTextUsuario.getText().toString().trim();
        String contrasena = editTextContrasena.getText().toString().trim();

        if (usuario.isEmpty()) {
            textInputLayoutUsuario.setError("Ingresa tu usuario");
            return;
        }

        if (contrasena.isEmpty()) {
            textInputLayoutContrasena.setError("Ingresa tu contraseña");
            return;
        }

        sessionManager.createLoginSession(usuario);
        Toast.makeText(this, "¡Comenzando tu entrenamiento diario!", Toast.LENGTH_SHORT).show();
        redirigirAHome();
    }

    private void redirigirAHome() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sessionManager != null) {
            sessionManager.releaseSoundPlayer();
        }
    }
}
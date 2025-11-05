package com.example.gymzy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    TextView tvGreeting;
    Button btnIniciarSesion, btnVerPrecios;
    TextInputEditText editTextUsuario, editTextContrasena;
    TextInputLayout textInputLayoutUsuario, textInputLayoutContrasena;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Inicializar SessionManager
        sessionManager = new SessionManager(this);

        // Verificar si ya hay una sesión activa
        if (sessionManager.isLoggedIn()) {
            redirigirAListaRutina();
            return;
        }

        // Inicializar vistas
        tvGreeting = findViewById(R.id.tvGreeting);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnVerPrecios = findViewById(R.id.btnVerPrecios);

        // Inicializar campos de texto
        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextContrasena = findViewById(R.id.editTextContrasena);
        textInputLayoutUsuario = findViewById(R.id.textInputLayoutUsuario);
        textInputLayoutContrasena = findViewById(R.id.textInputLayoutContrasena);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });

        btnVerPrecios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "¡Precios de la app!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Precios.class);
                startActivity(intent);
            }
        });
    }

    private void iniciarSesion() {
        String usuario = editTextUsuario.getText().toString().trim();
        String contrasena = editTextContrasena.getText().toString().trim();

        // Validaciones básicas
        if (usuario.isEmpty()) {
            textInputLayoutUsuario.setError("Ingresa tu usuario");
            return;
        } else {
            textInputLayoutUsuario.setError(null);
        }

        if (contrasena.isEmpty()) {
            textInputLayoutContrasena.setError("Ingresa tu contraseña");
            return;
        } else {
            textInputLayoutContrasena.setError(null);
        }

        // Validación simple
        if (usuario.equals("admin") && contrasena.equals("1234")) {
            // Crear sesión
            sessionManager.createLoginSession(usuario);
            Toast.makeText(MainActivity.this, "¡Comenzando tu entrenamiento diario!", Toast.LENGTH_SHORT).show();
            redirigirAListaRutina();
        } else {
            // Para probar, acepta cualquier usuario/contraseña no vacíos
            sessionManager.createLoginSession(usuario);
            Toast.makeText(MainActivity.this, "¡Comenzando tu entrenamiento diario!", Toast.LENGTH_SHORT).show();
            redirigirAListaRutina();
        }
    }

    private void redirigirAListaRutina() {
        Intent intent = new Intent(MainActivity.this, ListaRutina.class);
        intent.putExtra("USUARIO", sessionManager.getUsername());
        startActivity(intent);
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberar recursos del reproductor de sonido
        sessionManager.releaseSoundPlayer();
    }
}
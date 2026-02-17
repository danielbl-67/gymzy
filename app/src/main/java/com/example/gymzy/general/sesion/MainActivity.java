package com.example.gymzy.general.sesion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gymzy.R;
import com.example.gymzy.general.PantallasPrincipales.DrawerBaseActivity;
import com.example.gymzy.general.PantallasPrincipales.HomeActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends DrawerBaseActivity {

    TextView tvGreeting;
    Button btnIniciarSesion, btnVerPrecios;
    TextInputEditText editTextUsuario, editTextContrasena;
    TextInputLayout textInputLayoutUsuario, textInputLayoutContrasena;

    private SessionManager sessionManager;
    private FirebaseAuth mAuth; // Instancia de Firebase Auth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = getLayoutInflater().inflate(R.layout.activity_main, null);
        setContentView(view);

        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        sessionManager = new SessionManager(this);

        // Si ya hay una sesión activa en Firebase, entramos directo
        if (mAuth.getCurrentUser() != null) {
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
            // Esto podrías cambiarlo a una pantalla informativa,
            // ya que ir a HomeActivity sin login podría dar errores.
            Toast.makeText(this, "Debes iniciar sesión para ver planes", Toast.LENGTH_SHORT).show();
        });
    }

    private void iniciarSesion() {
        String email = editTextUsuario.getText().toString().trim();
        String contrasena = editTextContrasena.getText().toString().trim();

        // Validaciones visuales
        if (email.isEmpty()) {
            textInputLayoutUsuario.setError("Ingresa tu correo");
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

        // --- LÓGICA DE FIREBASE ---
        // Esto verifica si el usuario existe en Firebase Authentication
        mAuth.signInWithEmailAndPassword(email, contrasena)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // El usuario existe y la contraseña es correcta
                        FirebaseUser user = mAuth.getCurrentUser();

                        // Guardamos el nombre o email en tu sessionManager local
                        sessionManager.createLoginSession(email);

                        Toast.makeText(MainActivity.this, "¡Bienvenido de nuevo!", Toast.LENGTH_SHORT).show();
                        redirigirAHome();
                    } else {
                        // El usuario NO está en la base de datos o los datos son erróneos
                        Toast.makeText(MainActivity.this, "Error: Usuario no registrado o datos incorrectos",
                                Toast.LENGTH_LONG).show();

                        textInputLayoutUsuario.setError("Verifica tu email");
                        textInputLayoutContrasena.setError("Verifica tu contraseña");
                    }
                });
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
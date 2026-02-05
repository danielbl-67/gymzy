package com.example.gymzy.general;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gymzy.R;
import com.example.gymzy.general.sesion.SessionManager;
import com.example.gymzy.musculosGeneral.abs;
import com.example.gymzy.musculosGeneral.biceps;
import com.example.gymzy.musculosGeneral.cardio;
import com.example.gymzy.musculosGeneral.espalda;
import com.example.gymzy.musculosGeneral.gluteo;
import com.example.gymzy.musculosGeneral.hombro;
import com.example.gymzy.musculosGeneral.pecho;
import com.example.gymzy.musculosGeneral.pierna;
import com.example.gymzy.musculosGeneral.triceps;

public class ListaRutina extends DrawerBaseActivity {

    private SessionManager sessionManager;
    private Button bpecho, bespalda, bhombro, bbiceps, btricpes, bpierna, bgluteo, babs, bcardio, btnCerrarSesion;
    private TextView tvBienvenida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rutinas);
        allocateActivityTitle("Mis Rutinas");

        // ✅ Inicializar SessionManager
        sessionManager = new SessionManager(this);

        // ✅ Verificar si hay sesión activa
        if (!sessionManager.isLoggedIn()) {
            redirigirALogin();
            return;
        }

        // ✅ Inicializa los botones
        babs = findViewById(R.id.babs);
        bpecho = findViewById(R.id.bpecho);
        bespalda = findViewById(R.id.bespalda);
        bhombro = findViewById(R.id.bhombro);
        bbiceps = findViewById(R.id.bbiceps);
        btricpes = findViewById(R.id.btriceps);
        bpierna = findViewById(R.id.bpierna);
        bgluteo = findViewById(R.id.bgluteo);
        bcardio = findViewById(R.id.bcardio);
        tvBienvenida = findViewById(R.id.tvBienvenida);

        // ✅ Botón para cerrar sesión (añádelo en tu layout)
       btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        // ✅ Mostrar el mensaje de bienvenida con el usuario de la sesión
        String usuario = sessionManager.getUsername();
        tvBienvenida.setText("¡Bienvenido, " + usuario + "!");

        // ✅ Configurar botón de cerrar sesión
        if (btnCerrarSesion != null) {
            sessionManager.playClickSound(); // 🔊 Sonido al hacer click
            btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cerrarSesion();
                }
            });
        }

        // ✅ Listeners para los botones de ejercicios
        babs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.playClickSound(); // 🔊 Sonido al hacer click
                Toast.makeText(ListaRutina.this, "¡Preparando ejercicios de abdominales!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListaRutina.this, abs.class);
                startActivity(intent);
            }
        });

        bpecho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.playClickSound(); // 🔊 Sonido al hacer click
                Toast.makeText(ListaRutina.this, "¡Preparando ejercicios de pecho!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListaRutina.this, pecho.class);
                startActivity(intent);
            }
        });

        bespalda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.playClickSound(); // 🔊 Sonido al hacer click
                Toast.makeText(ListaRutina.this, "¡Preparando ejercicios de espalda!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListaRutina.this, espalda.class);
                startActivity(intent);
            }
        });

        bhombro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.playClickSound(); // 🔊 Sonido al hacer click
                Toast.makeText(ListaRutina.this, "¡Preparando ejercicios de hombro!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListaRutina.this, hombro.class);
                startActivity(intent);
            }
        });

        bbiceps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.playClickSound(); // 🔊 Sonido al hacer click
                Toast.makeText(ListaRutina.this, "¡Preparando ejercicios de bíceps!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListaRutina.this, biceps.class);
                startActivity(intent);
            }
        });

        btricpes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.playClickSound(); // 🔊 Sonido al hacer click
                Toast.makeText(ListaRutina.this, "¡Preparando ejercicios de tríceps!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListaRutina.this, triceps.class);
                startActivity(intent);
            }
        });

        bpierna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.playClickSound(); // 🔊 Sonido al hacer click
                Toast.makeText(ListaRutina.this, "¡Preparando ejercicios de pierna!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListaRutina.this, pierna.class);
                startActivity(intent);
            }
        });

        bgluteo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.playClickSound(); // 🔊 Sonido al hacer click
                Toast.makeText(ListaRutina.this, "¡Preparando ejercicios de glúteo!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListaRutina.this, gluteo.class);
                startActivity(intent);
            }
        });

        bcardio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.playClickSound(); // 🔊 Sonido al hacer click
                Toast.makeText(ListaRutina.this, "¡Preparando ejercicios de cardio!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListaRutina.this, cardio.class);
                startActivity(intent);
            }
        });
    }

    private void cerrarSesion() {
        sessionManager.logout();
        sessionManager.playClickSound(); // 🔊 Sonido al hacer click
        Toast.makeText(this, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show();
        redirigirALogin();
    }

    private void redirigirALogin() {
        sessionManager.playClickSound(); // 🔊 Sonido al hacer click
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Verificar sesión cuando la actividad se reanude
        if (!sessionManager.isLoggedIn()) {
            redirigirALogin();
        }
    }
}
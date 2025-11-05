package com.example.gymzy;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class PressBancaDetalleActivity extends AppCompatActivity {

    private VideoView videoView;
    private Button buttonReproducir, buttonPausar, buttonVolver;
    private MediaController mediaController;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pressbanca);
        // ✅ Inicializar SessionManager
        sessionManager = new SessionManager(this);


        // Inicializar vistas
        videoView = findViewById(R.id.videoView);
        buttonReproducir = findViewById(R.id.buttonReproducir);
        buttonPausar = findViewById(R.id.buttonPausar);
        buttonVolver = findViewById(R.id.buttonVolver);

        // Configurar video (reemplaza con tu video real)
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.pressvideo;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        // Configurar botones
        buttonReproducir.setOnClickListener(v -> {
            sessionManager.playClickSound(); // 🔊 Sonido al hacer click
            if (!videoView.isPlaying()) {
                videoView.start();
            }
        });

        buttonPausar.setOnClickListener(v -> {
            sessionManager.playClickSound(); // 🔊 Sonido al hacer click
            if (videoView.isPlaying()) {
                videoView.pause();
            }
        });

        buttonVolver.setOnClickListener(v -> {
            sessionManager.playClickSound(); // 🔊 Sonido al hacer click
            finish(); // Cierra esta activity y vuelve a la anterior
        });

        // Opcional: MediaController para controles nativos
        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
    }

    @Override
    protected void onPause() {
        sessionManager.playClickSound(); // 🔊 Sonido al hacer click
        super.onPause();
        if (videoView.isPlaying()) {
            videoView.pause();
        }
    }
}

package com.example.gymzy.musculoAbs;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gymzy.R;

public class ab_mountainclimber extends AppCompatActivity {

    private VideoView videoView;
    private EditText editSeries, editReps, editPeso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Asegúrate de que el nombre del layout XML sea activity_ab_mountainclimber
        setContentView(R.layout.ab_mountainclimber);

        // 1. Inicializar vistas
        videoView = findViewById(R.id.videoView);
        Button btnReproducir = findViewById(R.id.buttonReproducir);
        Button btnPausar = findViewById(R.id.buttonPausar);
        Button btnVolver = findViewById(R.id.buttonVolver);

        editSeries = findViewById(R.id.editSeries);
        editReps = findViewById(R.id.editReps);
        editPeso = findViewById(R.id.editPeso);

        // 2. Configurar el Video (Archivo en res/raw/ab_plancha o el que uses para climbers)
        // Nota: Asegúrate de que el nombre del archivo en res/raw sea correcto
        try {
          //  String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.ab_plancha;
          //  Uri uri = Uri.parse(videoPath);
          //  videoView.setVideoURI(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3. Loop del video
        videoView.setOnCompletionListener(mp -> videoView.start());

        // 4. Listeners de control
        btnReproducir.setOnClickListener(v -> videoView.start());
        btnPausar.setOnClickListener(v -> videoView.pause());
        btnVolver.setOnClickListener(v -> finish());
    }

    // Pausar video si se sale de la app para no gastar recursos
    @Override
    protected void onPause() {
        super.onPause();
        if (videoView != null && videoView.isPlaying()) {
            videoView.pause();
        }
    }
}
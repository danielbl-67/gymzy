package com.example.gymzy.musculoAbs;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gymzy.R;

public class ab_elevaciones extends AppCompatActivity {

    private VideoView videoView;
    private EditText editSeries, editReps, editPeso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Asegúrate de que este nombre sea el de tu archivo XML de diseño
        setContentView(R.layout.ab_elevaciones);

        // 1. Inicializar los componentes del XML
        videoView = findViewById(R.id.videoView);
        Button btnReproducir = findViewById(R.id.buttonReproducir);
        Button btnPausar = findViewById(R.id.buttonPausar);
        Button btnVolver = findViewById(R.id.buttonVolver);

        editSeries = findViewById(R.id.editSeries);
        editReps = findViewById(R.id.editReps);
        editPeso = findViewById(R.id.editPeso);

        // 2. Configurar el Video (Archivo en res/raw/p_levantaminetopierna)
        try {
          //  String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.p_levantaminetopierna;
          //  Uri uri = Uri.parse(videoPath);
          //  videoView.setVideoURI(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3. Hacer que el video se repita automáticamente al terminar (Loop)
        videoView.setOnCompletionListener(mp -> videoView.start());

        // 4. Controles de Reproducción
        btnReproducir.setOnClickListener(v -> videoView.start());
        btnPausar.setOnClickListener(v -> videoView.pause());

        // 5. Botón Volver
        btnVolver.setOnClickListener(v -> finish());
    }

    // Opcional: Si el usuario sale de la app, pausamos el video para ahorrar batería
    @Override
    protected void onStop() {
        super.onStop();
        if (videoView.isPlaying()) {
            videoView.pause();
        }
    }
}
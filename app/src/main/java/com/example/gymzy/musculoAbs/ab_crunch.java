package com.example.gymzy.musculoAbs;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.VideoView;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gymzy.R;

public class ab_crunch extends AppCompatActivity {

    private VideoView videoView;
    private EditText editSeries, editReps, editPeso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Asegúrate de que el nombre del layout sea el correcto (ej. activity_ab_crunch)
        setContentView(R.layout.ab_crunches);

        // 1. Inicializar vistas
        videoView = findViewById(R.id.videoView);
        Button btnReproducir = findViewById(R.id.buttonReproducir);
        Button btnPausar = findViewById(R.id.buttonPausar);
        Button btnVolver = findViewById(R.id.buttonVolver);

        editSeries = findViewById(R.id.editSeries);
        editReps = findViewById(R.id.editReps);
        editPeso = findViewById(R.id.editPeso);

        // 2. Configurar el Video (Debe estar en la carpeta res/raw)
        // Si tu video se llama "crunch_video.mp4", pon R.raw.crunch_video
        //String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.ab_crunchenmaquina;
       // Uri uri = Uri.parse(videoPath);
       // videoView.setVideoURI(uri);

        // 3. Lógica de los botones de video
        btnReproducir.setOnClickListener(v -> videoView.start());
        btnPausar.setOnClickListener(v -> videoView.pause());

        // 4. Botón Volver
        btnVolver.setOnClickListener(v -> finish());

        // 5. Opcional: Cargar datos guardados previamente (si usas SharedPreferences)
        cargarProgreso();
    }

    // Metodo para guardar datos cuando el usuario sale de la pantalla
    @Override
    protected void onPause() {
        super.onPause();
        guardarProgreso();
    }

    private void guardarProgreso() {
        // Aquí podrías guardar los datos en SharedPreferences o Base de Datos
        String series = editSeries.getText().toString();
        String reps = editReps.getText().toString();
        String peso = editPeso.getText().toString();
        // Lógica para guardar...
    }

    private void cargarProgreso() {
        // Lógica para recuperar datos...
    }
}
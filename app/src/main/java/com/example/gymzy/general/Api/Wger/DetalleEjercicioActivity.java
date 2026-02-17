package com.example.gymzy.general.Api.Wger;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.gymzy.R;

import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
// ... (tus imports anteriores)

public class DetalleEjercicioActivity extends AppCompatActivity {

    // --- Vistas del XML ---
    private TextView tvTitulo, tvDescripcion;
    private ImageView imgEjercicio;
    private EditText editSeries, editReps, editPeso;
    private Button btnVolver, btnGuardar, btnLimpiar; // Añadido btnLimpiar

    // --- Datos y API ---
    private int exerciseId;
    private final String API_KEY = "19a89b71a50ec72b8af060f82fd23c04fd249e50";
    private WgerInternalApi api;

    // --- INTERFAZ DE API ---
    interface WgerInternalApi {
        @GET("exercise/{id}/")
        Call<ExerciseModel> getDetails(@Path("id") int id);

        @GET("exerciseimage/")
        Call<ImageResponse> getImages(@Query("exercise") int id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_ejercicio);

        btnGuardar.setOnClickListener(v -> {
            // 1. Recoger datos de los EditText
           // int series = Integer.parseInt(etSeries.getText().toString());
          //  int reps = Integer.parseInt(etReps.getText().toString());
          //  float peso = Float.parseFloat(etPeso.getText().toString());

            // 2. Crear el objeto de registro
         //   RegistroSesion nuevoRegistro = new RegistroSesion(nombreEjercicio, series, reps, peso);

            // 3. Lanzar el nuevo Activity con la tabla
            Intent intent = new Intent(this, TablaRegistrosActivity.class);
        //   intent.putExtra("REGISTRO", nuevoRegistro);
            startActivity(intent);
        });

        vincularVistas();
        configurarRetrofit();

        exerciseId = getIntent().getIntExtra("EXERCISE_ID", -1);
        String nombreInicial = getIntent().getStringExtra("EXERCISE_NAME");
        tvTitulo.setText(nombreInicial);

        if (exerciseId != -1) {
            consultarDatosEjercicio();
            consultarImagenEjercicio();
        }

        // --- CONFIGURACIÓN DE EVENTOS ---

        btnVolver.setOnClickListener(v -> finish());

        // Botón Reproducir (ahora actúa como GUARDAR según tu lógica)
        btnGuardar.setOnClickListener(v -> guardarRegistro());

        // Botón Pausar (ahora actúa como LIMPIAR)
        btnLimpiar.setOnClickListener(v -> limpiarCampos());
    }

    private void vincularVistas() {
        tvTitulo = findViewById(R.id.tituloEjercicio);
        tvDescripcion = findViewById(R.id.descripcionEjercicio);
        imgEjercicio = findViewById(R.id.imgEjercicioDetalle);
        editSeries = findViewById(R.id.editSeries);
        editReps = findViewById(R.id.editReps);
        editPeso = findViewById(R.id.editPeso);
        btnVolver = findViewById(R.id.buttonVolver);

        // Mapeamos los botones del XML a nuestras variables de lógica
        btnGuardar = findViewById(R.id.buttonReproducir);
        btnLimpiar = findViewById(R.id.buttonPausar);
    }

    private void configurarRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .addHeader("Authorization", "Token " + API_KEY)
                            .build();
                    return chain.proceed(request);
                }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wger.de/api/v2/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(WgerInternalApi.class);
    }

    private void consultarDatosEjercicio() {
        api.getDetails(exerciseId).enqueue(new Callback<ExerciseModel>() {
            @Override
            public void onResponse(Call<ExerciseModel> call, Response<ExerciseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String descRaw = response.body().description;
                    // Manejo de versiones para Html.fromHtml
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        tvDescripcion.setText(Html.fromHtml(descRaw, Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        tvDescripcion.setText(Html.fromHtml(descRaw));
                    }
                }
            }
            @Override
            public void onFailure(Call<ExerciseModel> call, Throwable t) {
                Toast.makeText(DetalleEjercicioActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void consultarImagenEjercicio() {
        api.getImages(exerciseId).enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().results.isEmpty()) {
                    String url = response.body().results.get(0).image;
                    Glide.with(DetalleEjercicioActivity.this)
                            .load(url)
                            .into(imgEjercicio);
                }
            }
            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {}
        });
    }

    private void guardarRegistro() {
        String s = editSeries.getText().toString();
        String r = editReps.getText().toString();
        String p = editPeso.getText().toString();

        if (s.isEmpty() || r.isEmpty() || p.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            // Aquí simulas el guardado. En una app real, guardarías en SQLite o Firebase
            String resumen = "Guardado: " + s + " series de " + r + " reps con " + p + "kg";
            Toast.makeText(this, resumen, Toast.LENGTH_LONG).show();

            // Opcional: Limpiar después de guardar
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        editSeries.setText("");
        editReps.setText("");
        editPeso.setText("");
        editSeries.requestFocus(); // Pone el foco de nuevo en el primer campo
        Toast.makeText(this, "Campos limpios", Toast.LENGTH_SHORT).show();
    }

    // --- CLASES DE APOYO ---
    class ExerciseModel {
        String description;
    }

    class ImageResponse {
        List<ImageData> results;
    }

    class ImageData {
        String image;
    }
}
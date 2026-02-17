package com.example.gymzy.general.Api.Wger;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gymzy.R;
import com.example.gymzy.general.PantallasPrincipales.DrawerBaseActivity;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EjerciciosListaActivity extends DrawerBaseActivity {

    private RecyclerView recyclerView;
    private ExerciseAdapter adapter;
    private List<Ejercicios.Exercise> exerciseList = new ArrayList<>();
    private int musculoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflamos el layout dentro del marco del Drawer
        View view = getLayoutInflater().inflate(R.layout.activity_ejercicios_lista, null);
        setContentView(view);

        // Inicializar RecyclerView
        recyclerView = findViewById(R.id.rvEjerciciosGenericos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Si musculoId es 0 o menor, la API de Wger lanzará Error 400
        if (musculoId <= 0) {
            musculoId = 10; // Valor por defecto (Pecho) para evitar el error 400
        }
        // Obtener datos del Intent (enviados desde la pantalla de selección de músculos)
        musculoId = getIntent().getIntExtra("MUSCULO_ID", 0);
        String titulo = getIntent().getStringExtra("TITULO_MUSCULO");

        // Método de tu DrawerBaseActivity para poner el título en el Toolbar
        allocateActivityTitle(titulo != null ? titulo : "Ejercicios");

        // Configurar adaptador vacío inicialmente
        adapter = new ExerciseAdapter(exerciseList, this);
        recyclerView.setAdapter(adapter);

        // Cargar datos
        cargarEjerciciosDesdeApi();
    }

    private void cargarEjerciciosDesdeApi() {
        // IMPORTANTE: Asegúrate de que RetrofitClient.getClient() use "https://wger.de/"
        WgerApi api = RetrofitClient.getClient().create(WgerApi.class);

        api.getExercisesByMusculo(musculoId).enqueue(new Callback<WgerResponse<Ejercicios.Exercise>>() {
            @Override
            public void onResponse(Call<WgerResponse<Ejercicios.Exercise>> call, Response<WgerResponse<Ejercicios.Exercise>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    exerciseList.clear();
                    exerciseList.addAll(response.body().getResults());

                    // ESTA LÍNEA ES VITAL: Sin esto, la pantalla sigue negra aunque haya datos
                    adapter.notifyDataSetChanged();
                } else {
                    // Esto es lo que sale en tu Imagen 4
                    Toast.makeText(EjerciciosListaActivity.this, "Error: Código " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WgerResponse<Ejercicios.Exercise>> call, Throwable t) {
                Toast.makeText(EjerciciosListaActivity.this, "Fallo de red: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
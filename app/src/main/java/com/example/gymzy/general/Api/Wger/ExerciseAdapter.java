package com.example.gymzy.general.Api.Wger;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.gymzy.R;
import java.util.List;
public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {
    private List<Ejercicios.Exercise> exercises;
    private Context context;

    public ExerciseAdapter(List<Ejercicios.Exercise> exercises, Context context) {
        this.exercises = exercises;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos el item_exercise.xml que me pasaste
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ejercicios.Exercise ex = exercises.get(position);

        // 1. Nombre del ejercicio (Asegúrate que en Ejercicios.java sea public String nombre)
        holder.name.setText(ex.nombre != null ? ex.nombre : "Sin nombre");

        // 2. Descripción (Limpiando HTML de la API de Wger)
        if (ex.descripcion != null && !ex.descripcion.isEmpty()) {
            holder.description.setText(android.text.Html.fromHtml(ex.descripcion, android.text.Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.description.setText("Sin descripción disponible");
        }

        // 3. Cargar Imagen Real de Wger (Evita el cuadro rojo vacío)
        String imageUrl = "https://wger.de/static/images/exercises/main/" + ex.id + ".png";

        com.bumptech.glide.Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_logoapp) // Tu logo mientras carga
                .error(R.drawable.ic_logoapp)        // Tu logo si no hay imagen en la API
                .into(holder.image);

        // 4. HACER QUE LA TARJETA SE PORTE COMO UN BOTÓN
        holder.itemView.setOnClickListener(v -> {
            // Aquí defines qué pasa al tocar el ejercicio
            android.widget.Toast.makeText(context, "Seleccionado: " + ex.nombre, android.widget.Toast.LENGTH_SHORT).show();

            // Ejemplo: Abrir una nueva actividad con detalles
            Intent intent = new Intent(context, DetalleEjercicioActivity.class);
            intent.putExtra("ID", ex.id);
             context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return exercises != null ? exercises.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgExercise);
            name = itemView.findViewById(R.id.tvExerciseName);
            description = itemView.findViewById(R.id.tvExerciseDescription);
        }
    }
}
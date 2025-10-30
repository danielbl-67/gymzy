package com.example.gymzy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ListaRutina extends AppCompatActivity {

    Button bpecho,bespalda,bhombro,bbiceps,btricpes,bpierna,bgluteo,babs,bcardio;


 protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rutinas);

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

     babs.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             // Mostrar Toast
             Toast.makeText(ListaRutina.this, "¡Preparando ejercicios de abdominales", Toast.LENGTH_SHORT).show();
             // Ir a Abs
             Intent intent = new Intent(ListaRutina.this, abs.class);
             startActivity(intent);
         }
     });


     bpecho.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             // Mostrar Toast
             Toast.makeText(ListaRutina.this, "¡Preparando ejercicios de pecho", Toast.LENGTH_SHORT).show();
             // Ir a ListaRutina
             Intent intent = new Intent(ListaRutina.this, pecho.class);
             startActivity(intent);
         }
     });

     bespalda.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             // Mostrar Toast
             Toast.makeText(ListaRutina.this, "¡Preparando ejercicios de espalda", Toast.LENGTH_SHORT).show();
             // Ir a ListaRutina
             Intent intent = new Intent(ListaRutina.this, espalda.class);
             startActivity(intent);
         }
     });
     bhombro.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             // Mostrar Toast
             Toast.makeText(ListaRutina.this, "¡Preparando ejercicios de hombro", Toast.LENGTH_SHORT).show();
             // Ir a ListaRutina
             Intent intent = new Intent(ListaRutina.this, hombro.class);
             startActivity(intent);
         }
     });
     bbiceps.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             // Mostrar Toast
             Toast.makeText(ListaRutina.this, "¡Preparando ejercicios de biceps", Toast.LENGTH_SHORT).show();
             // Ir a ListaRutina
             Intent intent = new Intent(ListaRutina.this, biceps.class);
             startActivity(intent);
         }
     });
     btricpes.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             // Mostrar Toast
             Toast.makeText(ListaRutina.this, "¡Preparando ejercicios de triceps", Toast.LENGTH_SHORT).show();
             // Ir a ListaRutina
             Intent intent = new Intent(ListaRutina.this, triceps.class);
             startActivity(intent);
         }
     });
     bpierna.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             // Mostrar Toast
             Toast.makeText(ListaRutina.this, "¡Preparando ejercicios de pierna", Toast.LENGTH_SHORT).show();
             // Ir a ListaRutina
             Intent intent = new Intent(ListaRutina.this, pierna.class);
             startActivity(intent);
         }
     });
     bgluteo.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             // Mostrar Toast
             Toast.makeText(ListaRutina.this, "¡Preparando ejercicios de gluteo", Toast.LENGTH_SHORT).show();
             // Ir a ListaRutina
             Intent intent = new Intent(ListaRutina.this, gluteo.class);
             startActivity(intent);
         }
     });


     bcardio.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             // Mostrar Toast
             Toast.makeText(ListaRutina.this, "¡Preparando ejercicios de cardio", Toast.LENGTH_SHORT).show();
             // Ir a ListaRutina
             Intent intent = new Intent(ListaRutina.this, cardio.class);
             startActivity(intent);
         }
     });

    }


}

package com.example.gymzy.general.sesion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gymzy.R;
import com.example.gymzy.general.PantallasPrincipales.HomeActivity;
import com.example.gymzy.general.Usuarios.Usuario;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistroActivity extends AppCompatActivity {

    private TextInputEditText etNombre, etEdad, etPeso, etAltura;
    private AutoCompleteTextView spGenero, spActividad, spObjetivo;
    private MaterialButton btnFinalizar;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db; // Instancia para Firestore

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // 1. Inicializar Firebase Auth y Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // 2. Vincular vistas con los IDs de tu XML
        etNombre = findViewById(R.id.etNombreCompleto);
        etEdad = findViewById(R.id.etEdad);
        etPeso = findViewById(R.id.etPeso);
        etAltura = findViewById(R.id.etAltura);
        spGenero = findViewById(R.id.spinnerGenero);
        spObjetivo = findViewById(R.id.spinnerObjetivo);
        spActividad = findViewById(R.id.spinnerActividad);
        btnFinalizar = findViewById(R.id.btnFinalizarRegistro);

        setupDropdowns();

        btnFinalizar.setOnClickListener(v -> guardarDatosEnFirestore());
    }

    private void setupDropdowns() {
        String[] opcionesGenero = {"Hombre", "Mujer", "Otro"};
        String[] opcionesObjetivo = {"Perder peso", "Mantener peso", "Ganar masa muscular"};
        String[] opcionesActividad = {"Sedentario", "Ligero", "Moderado", "Intenso"};

        spGenero.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, opcionesGenero));
        spObjetivo.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, opcionesObjetivo));
        spActividad.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, opcionesActividad));
    }

    private void guardarDatosEnFirestore() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Verificar si el usuario está autenticado (evita el error de "No hay sesión")
        if (currentUser == null) {
            Toast.makeText(this, "Sesión no válida. Regresa al inicio.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Capturar datos
            String nombre = etNombre.getText().toString().trim();
            String edadStr = etEdad.getText().toString().trim();
            String pesoStr = etPeso.getText().toString().trim();
            String alturaStr = etAltura.getText().toString().trim();
            String genero = spGenero.getText().toString();
            String objetivo = spObjetivo.getText().toString();
            String actividad = spActividad.getText().toString();

            // Validar campos vacíos
            if (nombre.isEmpty() || edadStr.isEmpty() || pesoStr.isEmpty() || alturaStr.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            int edad = Integer.parseInt(edadStr);
            double peso = Double.parseDouble(pesoStr);
            double altura = Double.parseDouble(alturaStr);

            // 3. Crear el objeto Usuario (Constructor de 7 parámetros)
            Usuario user = new Usuario(nombre, edad, peso, altura, genero, objetivo, actividad);

            btnFinalizar.setEnabled(false); // Deshabilitar botón para evitar duplicados

            // 4. Guardar en la colección "Usuarios" usando el UID como ID del documento
            db.collection("Usuarios").document(currentUser.getUid())
                    .set(user)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "¡Perfil guardado correctamente!", Toast.LENGTH_SHORT).show();
                        // Ir al Home
                        Intent intent = new Intent(RegistroActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        btnFinalizar.setEnabled(true);
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Revisa los campos numéricos", Toast.LENGTH_SHORT).show();
        }
    }
}
package com.example.gymzy.general.PantallasPrincipales;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.gymzy.R;
import com.example.gymzy.general.firebase.FirebaseHelper;
import com.example.gymzy.general.sesion.SessionManager;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ConfiguracionActivity extends DrawerBaseActivity {

    private EditText etNombre, etEdad, etPeso, etAltura;
    private TextView tvValorIMC, tvEstadoIMC;
    private Spinner spinnerSexo;
    private ImageView ivPerfil;
    private Button btnGuardar;
    private SessionManager sessionManager;

    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    if (imageUri != null) {
                        ivPerfil.setImageURI(imageUri);
                        getSharedPreferences("UserPrefs", MODE_PRIVATE).edit()
                                .putString("profile_image", imageUri.toString()).apply();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_configuracion, null);
        setContentView(view);
        allocateActivityTitle("Configuración de Perfil");

        sessionManager = new SessionManager(this);

        initUI();
        setupSpinner();
        cargarDatos();

        ivPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryLauncher.launch(intent);
        });

        // Listener para cálculo de IMC en tiempo real
        TextWatcher imcWatcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { calcularIMC(); }
            @Override public void afterTextChanged(Editable s) {}
        };
        etPeso.addTextChangedListener(imcWatcher);
        etAltura.addTextChangedListener(imcWatcher);

        btnGuardar.setOnClickListener(v -> guardarPerfilCompleto());
    }

    private void initUI() {
        ivPerfil = findViewById(R.id.ivPerfilUsuario);
        etNombre = findViewById(R.id.etNombrePerfil);
        etEdad = findViewById(R.id.etEdadPerfil);
        etPeso = findViewById(R.id.etPesoPerfil);
        etAltura = findViewById(R.id.etAlturaPerfil);
        spinnerSexo = findViewById(R.id.spinnerSexo);
        tvValorIMC = findViewById(R.id.tvValorIMC);
        tvEstadoIMC = findViewById(R.id.tvEstadoIMC);
        btnGuardar = findViewById(R.id.btnGuardarPerfil);
    }

    private void setupSpinner() {
        String[] opciones = {"Hombre", "Mujer", "Otro", "Sin especificar"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opciones);
        spinnerSexo.setAdapter(adapter);
    }

    private void calcularIMC() {
        String sPeso = etPeso.getText().toString();
        String sAltura = etAltura.getText().toString();

        if (!sPeso.isEmpty() && !sAltura.isEmpty()) {
            try {
                float peso = Float.parseFloat(sPeso);
                float altura = Float.parseFloat(sAltura) / 100;
                if (altura > 0) {
                    float imc = peso / (altura * altura);
                    tvValorIMC.setText(String.format(Locale.US, "%.1f", imc));
                    if (imc < 18.5) {
                        tvEstadoIMC.setText("Bajo peso");
                        tvEstadoIMC.setTextColor(Color.CYAN);
                    } else if (imc < 25) {
                        tvEstadoIMC.setText("Peso saludable");
                        tvEstadoIMC.setTextColor(Color.GREEN);
                    } else {
                        tvEstadoIMC.setText("Sobrepeso");
                        tvEstadoIMC.setTextColor(Color.RED);
                    }
                }
            } catch (Exception e) { tvValorIMC.setText("--"); }
        }
    }

    private void cargarDatos() {
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        // Prioridad al nombre de sesión
        String nombreSesion = sessionManager.getUsername();
        etNombre.setText(nombreSesion != null && !nombreSesion.isEmpty() ? nombreSesion : prefs.getString("nombre", ""));

        etEdad.setText(prefs.getString("edad", ""));
        etPeso.setText(prefs.getString("peso", ""));
        etAltura.setText(prefs.getString("altura", ""));

        String sexo = prefs.getString("sexo", "Hombre");
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinnerSexo.getAdapter();
        if (adapter != null) spinnerSexo.setSelection(adapter.getPosition(sexo));

        String imgPath = prefs.getString("profile_image", null);
        if (imgPath != null) {
            try { ivPerfil.setImageURI(Uri.parse(imgPath)); }
            catch (Exception e) { ivPerfil.setImageResource(android.R.drawable.ic_menu_camera); }
        }
        calcularIMC();
    }

    private void guardarPerfilCompleto() {
        String nombre = etNombre.getText().toString().trim();
        String edad = etEdad.getText().toString().trim();
        String peso = etPeso.getText().toString().trim();
        String altura = etAltura.getText().toString().trim();
        String sexo = spinnerSexo.getSelectedItem().toString();
        String imc = tvValorIMC.getText().toString();

        if (nombre.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa un nombre", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = sessionManager.getUsername();
        if (userId == null || userId.isEmpty()) userId = "usuario_desconocido";

        // 1. Crear mapa para Firebase
        Map<String, Object> perfilData = new HashMap<>();
        perfilData.put("nombre", nombre);
        perfilData.put("edad", edad);
        perfilData.put("peso", peso);
        perfilData.put("altura", altura);
        perfilData.put("sexo", sexo);
        perfilData.put("imc", imc);
        perfilData.put("ultima_actualizacion", System.currentTimeMillis());

        // 2. Guardar en Firebase usando tu Helper
        FirebaseHelper.getPerfilRef(userId).setValue(perfilData)
                .addOnSuccessListener(aVoid -> {
                    // 3. Si Firebase va bien, guardamos en Local
                    guardarEnLocal(nombre, edad, peso, altura, sexo);
                    Toast.makeText(ConfiguracionActivity.this, "¡Perfil sincronizado con éxito!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ConfiguracionActivity.this, "Error al guardar en la nube", Toast.LENGTH_SHORT).show();
                });
    }

    private void guardarEnLocal(String n, String e, String p, String a, String s) {
        SharedPreferences.Editor editor = getSharedPreferences("UserPrefs", MODE_PRIVATE).edit();
        editor.putString("nombre", n);
        editor.putString("edad", e);
        editor.putString("peso", p);
        editor.putString("altura", a);
        editor.putString("sexo", s);
        editor.apply();
    }
}
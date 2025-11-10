package com.example.gymzy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class Precios  extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.preciosactivity);


        // Obtener la configuración local del dispositivo (idioma y país)
        Locale locale = Locale.getDefault();

        // Formateador de moneda según la localización
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);

        // Precios base en euros (puedes cambiarlos si prefieres otra moneda base)
        double precioBasico = 19.99;
        double precioPremium = 34.99;
        double precioAnual = 239.99;

        // Si quieres forzar una moneda base (por ejemplo, siempre euro), descomenta esta línea:
        // currencyFormat.setCurrency(Currency.getInstance("EUR"));

        // Formatear precios según el idioma/país
        String precioBasicoFormateado = currencyFormat.format(precioBasico);
        String precioPremiumFormateado = currencyFormat.format(precioPremium);
        String precioAnualFormateado = currencyFormat.format(precioAnual);

        // TextViews de tu layout precios.xml
        TextView txtBasico = findViewById(R.id.precioBasico);
        TextView txtPremium = findViewById(R.id.precioPremium);
        TextView txtAnual = findViewById(R.id.precioAnual);

        // Textos con el formato correcto (mes / año según idioma)
        String perMonth = locale.getLanguage().equals("es") ? "/mes" : "/month";
        String perYear = locale.getLanguage().equals("es") ? "/año" : "/year";

        // Asignar los precios formateados
        txtBasico.setText(precioBasicoFormateado + " " + perMonth);
        txtPremium.setText(precioPremiumFormateado + " " + perMonth);
        txtAnual.setText(precioAnualFormateado + " " + perYear);



        Button btnSalir = findViewById(R.id.btnSalir);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ir a ListaRutina
                Intent intent = new Intent(Precios.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}

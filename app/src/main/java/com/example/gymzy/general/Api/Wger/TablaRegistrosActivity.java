package com.example.gymzy.general.Api.Wger;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.gymzy.R;
import com.example.gymzy.general.PantallasPrincipales.DrawerBaseActivity;
import com.example.gymzy.general.sesion.SessionManager;

public class TablaRegistrosActivity extends DrawerBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflamos el layout (puedes usar el que diseñamos antes)
        View view = getLayoutInflater().inflate(R.layout.activity_tabla_registros, null);
        setContentView(view);
        allocateActivityTitle("Historial de Entrenamiento");

        TableLayout table = findViewById(R.id.tableRegistros);

        // Recibimos el objeto enviado
        SessionManager.RegistroSesion registro = (SessionManager.RegistroSesion) getIntent().getSerializableExtra("REGISTRO");

        if (registro != null) {
            agregarFila(table, registro);
        }
    }

    private void agregarFila(TableLayout table, SessionManager.RegistroSesion reg) {
        TableRow row = new TableRow(this);
        row.setPadding(10, 20, 10, 20);

        TextView tvEjer = crearTextView(reg.getEjercicio());
        TextView tvSer = crearTextView(String.valueOf(reg.getSeries()));
        TextView tvRep = crearTextView(String.valueOf(reg.getReps()));
        TextView tvPes = crearTextView(reg.getPeso() + " kg");
        tvPes.setTextColor(Color.parseColor("#FFD700")); // Color dorado para destacar el peso

        row.addView(tvEjer);
        row.addView(tvSer);
        row.addView(tvRep);
        row.addView(tvPes);
        table.addView(row);
    }

    private TextView crearTextView(String texto) {
        TextView tv = new TextView(this);
        tv.setText(texto);
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }
}
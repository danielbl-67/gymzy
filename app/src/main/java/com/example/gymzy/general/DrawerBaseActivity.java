package com.example.gymzy.general;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.gymzy.R;
import com.google.android.material.navigation.NavigationView;
import org.jspecify.annotations.NonNull;

public abstract class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout drawerLayout;

    @Override
    public void setContentView(View view) {
        // Inflamos la base que contiene el Drawer y el FrameLayout
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);

        // Añadimos el contenido de la actividad hija al contenedor
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.menu_open, R.string.menu_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        int id = item.getItemId();

        // Evita recargar la misma actividad si ya estás en ella
        if (id == R.id.nav_inicio && !(this instanceof MainActivity)) {
            startActivity(new Intent(this, MainActivity.class));
        } else if (id == R.id.nav_configuracion && !(this instanceof ListaRutina)) {
            startActivity(new Intent(this, ListaRutina.class));
        } else if (id == R.id.nav_planes && !(this instanceof Precios)) {
            startActivity(new Intent(this, Precios.class));
        }

        overridePendingTransition(0, 0);
        return false;
    }

    protected void allocateActivityTitle(String titleString) {
        if (getSupportActionBar() != null) {
            // Esto elimina el espacio del título por completo
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            // Por seguridad, dejamos el string vacío
            getSupportActionBar().setTitle("");
        }
    }
}
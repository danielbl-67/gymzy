package com.example.gymzy.general.sesion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

import com.example.gymzy.R;

public class SessionManager {
    // Shared Preferences references
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private MediaPlayer soundPlayer;

    // Shared Preferences mode
    private static final int PRIVATE_MODE = 0;

    // Shared Preferences file name
    private static final String PREF_NAME = "GymzyAppPref";

    // All Shared Preferences Keys
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_LOGIN_TIME = "login_time";
    private static final String KEY_SOUND_ENABLED = "sound_enabled";

    // Constructor
    @SuppressLint("WrongConstant")
    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();

        // Inicializar el reproductor de sonido
        initializeSoundPlayer();
    }

    /**
     * Inicializar el reproductor de sonido
     */
    private void initializeSoundPlayer() {
        soundPlayer = MediaPlayer.create(context, R.raw.main_sonidobotones);
        if (soundPlayer != null) {
            soundPlayer.setVolume(0.5f, 0.5f); // Volumen al 50%
        }
    }

    /**
     * Reproducir sonido de click
     */
    public void playClickSound() {
        if (soundPlayer != null && isSoundEnabled()) {
            try {
                if (soundPlayer.isPlaying()) {
                    soundPlayer.seekTo(0);
                } else {
                    soundPlayer.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Verificar si el sonido está habilitado
     */
    public boolean isSoundEnabled() {
        return sharedPreferences.getBoolean(KEY_SOUND_ENABLED, true); // Por defecto activado
    }

    /**
     * Habilitar/deshabilitar sonido
     */
    public void setSoundEnabled(boolean enabled) {
        editor.putBoolean(KEY_SOUND_ENABLED, enabled);
        editor.commit();
    }

    /**
     * Create login session with username
     */
    public void createLoginSession(String username) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_USERNAME, username);
        editor.putLong(KEY_LOGIN_TIME, System.currentTimeMillis());
        editor.commit();
    }

    /**
     * Check login status
     */
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    /**
     * Get stored username
     */
    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "");
    }

    /**
     * Get login time
     */
    public long getLoginTime() {
        return sharedPreferences.getLong(KEY_LOGIN_TIME, 0);
    }

    /**
     * Clear session details (logout)
     */
    public void logout() {
        editor.clear();
        editor.commit();
    }

    /**
     * Liberar recursos del reproductor de sonido
     */
    public void releaseSoundPlayer() {
        if (soundPlayer != null) {
            soundPlayer.release();
            soundPlayer = null;
        }
    }
}
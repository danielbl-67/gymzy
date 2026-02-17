package com.example.gymzy.general.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseHelper {

    // Keys de la base de datos (Centralizadas para evitar errores de escritura)
    public static final String KEY_USUARIOS = "Usuarios";
    public static final String KEY_PERFIL = "perfil";
    public static final String KEY_FOTOS = "perfiles_fotos";

    private static DatabaseReference mDatabase;
    private static StorageReference mStorage;

    // Obtener la referencia a la base de datos Realtime
    public static DatabaseReference getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }
        return mDatabase;
    }

    // Obtener la referencia a Storage (para fotos)
    public static StorageReference getStorage() {
        if (mStorage == null) {
            mStorage = FirebaseStorage.getInstance().getReference();
        }
        return mStorage;
    }

    // Ruta específica para el perfil de un usuario
    public static DatabaseReference getPerfilRef(String userId) {
        return getDatabase().child(KEY_USUARIOS).child(userId).child(KEY_PERFIL);
    }
}
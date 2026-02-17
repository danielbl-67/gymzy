package com.example.gymzy.general.Api.Wger;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Ejercicios {
    public static class Response {
        @SerializedName("results")
        public List<Exercise> results;
    }

    public static class Exercise {
        @SerializedName("id")
        public int id;

        @SerializedName("name") // IMPORTANTE: Wger envía "name"
        public String nombre;

        @SerializedName("description") // IMPORTANTE: Wger envía "description"
        public String descripcion;
    }
}
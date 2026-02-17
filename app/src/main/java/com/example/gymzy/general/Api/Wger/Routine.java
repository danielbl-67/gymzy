package com.example.gymzy.general.Api.Wger;

public class Routine {
    private int id; // Generado por la API al crearla
    private String name;
    private String description;

    // Constructor para creación
    public Routine(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getId() { return id; }
}

package com.example.gymzy.general.Usuarios;

public class Usuario {
    public String nombre, genero, objetivo, nivelActividad;
    public int edad;
    public double peso, altura;

    public Usuario() {} // Obligatorio para Firebase

    // Constructor de 7 parámetros (ajustado a tu diseño actual)
    public Usuario(String nombre, int edad, double peso, double altura, String genero, String objetivo, String nivelActividad) {
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.altura = altura;
        this.genero = genero;
        this.objetivo = objetivo;
        this.nivelActividad = nivelActividad;
    }
}
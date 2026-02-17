package com.example.gymzy.general.Api.Wger;

import java.util.List;

public class WorkoutDay {
    private int id;
    private int routine; // ID de la rutina a la que pertenece
    private List<Integer> day; // 1=Lunes, 2=Martes...

    public WorkoutDay(int routine, List<Integer> day) {
        this.routine = routine;
        this.day = day;
    }
}
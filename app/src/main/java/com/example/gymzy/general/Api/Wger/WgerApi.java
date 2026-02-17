package com.example.gymzy.general.Api.Wger;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WgerApi {
    // Al haber puesto api/v2/ en la base, aquí solo pones el endpoint
    @GET("exercise/?language=7&status=2")
    Call<WgerResponse<Ejercicios.Exercise>> getExercisesByMusculo(
            @Query("category") int musculoId
    );
}
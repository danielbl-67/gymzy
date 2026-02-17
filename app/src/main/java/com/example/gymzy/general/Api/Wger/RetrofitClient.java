package com.example.gymzy.general.Api.Wger;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static final String WGER_URL = "https://wger.de/";
    private static final String API_KEY = "19a89b71a50ec72b8af060f82fd23c04fd249e50";

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request request = chain.request().newBuilder()
                                // Importante: El formato debe ser "Token TU_CLAVE"
                                .header("Authorization", "Token 19a89b71a50ec72b8af060f82fd23c04fd249e50")
                                .header("Accept", "application/json")
                                .build();
                        return chain.proceed(request);
                    }).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://wger.de/api/v2/") // URL simplificada
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
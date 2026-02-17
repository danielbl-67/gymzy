package com.example.gymzy.general.Api.Wger;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WgerResponse<T> {
    @SerializedName("results")
    private List<T> results;

    public List<T> getResults() {
        return results;
    }
}
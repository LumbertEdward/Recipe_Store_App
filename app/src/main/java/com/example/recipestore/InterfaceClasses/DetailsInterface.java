package com.example.recipestore.InterfaceClasses;

import com.example.recipestore.AllClasses.DetailsAll;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DetailsInterface {
    @GET("lookup.php")
    Call<DetailsAll> getAllDetails(
            @Query("i") String id
    );
}

package com.example.recipestore.InterfaceClasses;

import com.example.recipestore.AllClasses.AreasAll;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AreasInterface {
    @GET("list.php")
    Call<AreasAll> getAllAreas(
            @Query("a") String list
    );
}

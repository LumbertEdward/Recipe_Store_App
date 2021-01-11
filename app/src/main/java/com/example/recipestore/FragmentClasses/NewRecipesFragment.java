package com.example.recipestore.FragmentClasses;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.recipestore.AdapterClasses.MealsAdapter;
import com.example.recipestore.AdapterClasses.NewAdapter;
import com.example.recipestore.AllClasses.Categories;
import com.example.recipestore.AllClasses.CategoriesAll;
import com.example.recipestore.AllClasses.Meals;
import com.example.recipestore.AllClasses.MealsAll;
import com.example.recipestore.InterfaceClasses.LatestInterface;
import com.example.recipestore.R;
import com.example.recipestore.RetrofitClasses.LatestRetrofit;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewRecipesFragment extends Fragment {
    private LatestInterface latestInterface;
    private RecyclerView recyclerView;
    private NewAdapter newAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_recipes, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.latestRecycler);
        recyclerView.setVisibility(View.GONE);
        linearLayoutManager = new LinearLayoutManager(getContext());
        shimmerFrameLayout = (ShimmerFrameLayout) v.findViewById(R.id.shimmerLatest);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(linearLayoutManager);
        getData();
        return v;
    }

    private void getData() {
        latestInterface = LatestRetrofit.getRetrofit().create(LatestInterface.class);
        Call<CategoriesAll> mealsAllCall = latestInterface.getAllCategories();
        mealsAllCall.enqueue(new Callback<CategoriesAll>() {
            @Override
            public void onResponse(Call<CategoriesAll> call, Response<CategoriesAll> response) {
                recyclerView.setVisibility(View.VISIBLE);
                shimmerFrameLayout.setVisibility(View.GONE);
                getAllData(response.body().getCategoriesList());
            }

            @Override
            public void onFailure(Call<CategoriesAll> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                recyclerView.setVisibility(View.GONE);
                shimmerFrameLayout.setVisibility(View.VISIBLE);

            }
        });

    }

    private void getAllData(List<Categories> categoriesList) {
        newAdapter = new NewAdapter(getContext(), categoriesList);
        recyclerView.setAdapter(newAdapter);
    }

}

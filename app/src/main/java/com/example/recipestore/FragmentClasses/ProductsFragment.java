package com.example.recipestore.FragmentClasses;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.recipestore.AdapterClasses.MealsAdapter;
import com.example.recipestore.AllClasses.Categories;
import com.example.recipestore.AllClasses.Meals;
import com.example.recipestore.AllClasses.MealsAll;
import com.example.recipestore.InterfaceClasses.ProductsInterface;
import com.example.recipestore.R;
import com.example.recipestore.RetrofitClasses.ProductRetrofit;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductsFragment extends Fragment {
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private MealsAdapter mealsAdapter;
    private ProgressBar progressBar;
    private ProductsInterface productsInterface;
    private Categories categories;
    private ShimmerFrameLayout shimmerFrameLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null){
            categories = bundle.getParcelable("Data");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_products, container, false);
        progressBar = (ProgressBar) v.findViewById(R.id.progressProduct);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerProducts);
        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        shimmerFrameLayout = (ShimmerFrameLayout) v.findViewById(R.id.shimmerProd);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
        progressBar.setVisibility(View.GONE);
        getData();
        return v;
    }

    private void getData() {
        String name = categories.getStrCategory();
        productsInterface = ProductRetrofit.getRetrofit().create(ProductsInterface.class);
        Call<MealsAll> mealsAllCall = productsInterface.getSearchedMeal(name);
        mealsAllCall.enqueue(new Callback<MealsAll>() {
            @Override
            public void onResponse(Call<MealsAll> call, Response<MealsAll> response) {
                //progressBar.setVisibility(View.GONE);
                getProductData(response.body().getMealsList());
            }

            @Override
            public void onFailure(Call<MealsAll> call, Throwable t) {
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                shimmerFrameLayout.startShimmer();
                //progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getProductData(List<Meals> mealsList) {
        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();
        mealsAdapter = new MealsAdapter(mealsList, getContext());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(mealsAdapter);
    }
}

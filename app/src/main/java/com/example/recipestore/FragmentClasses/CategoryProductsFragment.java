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
import com.example.recipestore.AllClasses.Meals;
import com.example.recipestore.AllClasses.MealsAll;
import com.example.recipestore.InterfaceClasses.CATInterface;
import com.example.recipestore.InterfaceClasses.ProductsInterface;
import com.example.recipestore.R;
import com.example.recipestore.RetrofitClasses.CATRetrofit;
import com.example.recipestore.RetrofitClasses.ProductRetrofit;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryProductsFragment extends Fragment {
   private String string = "";
   private CATInterface catInterface;
   private RecyclerView recyclerView;
   private MealsAdapter mealsAdapter;
   private GridLayoutManager gridLayoutManager;
   private ProgressBar progressBar;
   private ShimmerFrameLayout shimmerFrameLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null){
            string = bundle.getString("DAT");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_category_products, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerItemProd);
        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        progressBar = (ProgressBar) v.findViewById(R.id.progressCat);
        progressBar.setVisibility(View.GONE);
        shimmerFrameLayout = (ShimmerFrameLayout) v.findViewById(R.id.shimmerCat);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
        getData();
        return v;
    }

    private void getData() {
        catInterface = CATRetrofit.getRetrofit().create(CATInterface.class);
        Call<MealsAll> mealsAllCall = catInterface.getSearchedMeal(string);
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
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        mealsAdapter = new MealsAdapter(mealsList, getContext());
        recyclerView.setAdapter(mealsAdapter);
    }
}

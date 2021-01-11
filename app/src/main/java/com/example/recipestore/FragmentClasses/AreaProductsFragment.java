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
import com.example.recipestore.AllClasses.Areas;
import com.example.recipestore.AllClasses.Meals;
import com.example.recipestore.AllClasses.MealsAll;
import com.example.recipestore.InterfaceClasses.AreaProductsInterface;
import com.example.recipestore.R;
import com.example.recipestore.RetrofitClasses.AreaProductsRetrofit;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AreaProductsFragment extends Fragment {
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private MealsAdapter mealsAdapter;
    private ProgressBar progressBar;
    private AreaProductsInterface areaProductsInterface;
    private Areas areas;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null){
            areas = bundle.getParcelable("Area");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_area_products, container, false);
        progressBar = (ProgressBar) v.findViewById(R.id.progressArea);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerAreaProducts);
        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        progressBar.setVisibility(View.GONE);
        shimmerFrameLayout = (ShimmerFrameLayout) v.findViewById(R.id.shimmerAre);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        getAreaData();
        return v;
    }

    private void getAreaData() {
        areaProductsInterface = AreaProductsRetrofit.getRetrofit().create(AreaProductsInterface.class);
        Call<MealsAll> mealsCall = areaProductsInterface.getAllAreas(areas.getStrArea());
        mealsCall.enqueue(new Callback<MealsAll>() {
            @Override
            public void onResponse(Call<MealsAll> call, Response<MealsAll> response) {
                //progressBar.setVisibility(View.GONE);
                getData(response.body().getMealsList());
            }

            @Override
            public void onFailure(Call<MealsAll> call, Throwable t) {
                shimmerFrameLayout.startShimmer();
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData(List<Meals> mealsList) {
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        mealsAdapter = new MealsAdapter(mealsList, getContext());
        recyclerView.setAdapter(mealsAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);
    }
}

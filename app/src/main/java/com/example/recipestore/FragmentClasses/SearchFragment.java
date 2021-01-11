package com.example.recipestore.FragmentClasses;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.recipestore.AdapterClasses.SearchAdapter;
import com.example.recipestore.AllClasses.Meals;
import com.example.recipestore.AllClasses.MealsAll;
import com.example.recipestore.InterfaceClasses.SearchInterface;
import com.example.recipestore.InterfaceClasses.SelectedProductsInterface;
import com.example.recipestore.R;
import com.example.recipestore.RetrofitClasses.SearchRetrofit;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SearchAdapter searchAdapter;
    private ImageView back;
    private ImageView cancel;
    private EditText search;
    private SelectedProductsInterface selectedProductsInterface;
    private SearchInterface searchInterface;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerSearch);
        linearLayoutManager = new LinearLayoutManager(getContext());
        back = (ImageView) v.findViewById(R.id.backSearch);
        cancel = (ImageView) v.findViewById(R.id.cancel);
        search = (EditText) v.findViewById(R.id.searchEdit);
        shimmerFrameLayout = (ShimmerFrameLayout) v.findViewById(R.id.shimmerSearch);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
        getData();
        actions();
        return v;
    }

    private void getData() {
        searchInterface = SearchRetrofit.getRetrofit().create(SearchInterface.class);
        Call<MealsAll> mealsAllCall = searchInterface.getSearchedMeal("Chicken");
        mealsAllCall.enqueue(new Callback<MealsAll>() {
            @Override
            public void onResponse(Call<MealsAll> call, Response<MealsAll> response) {
                showData(response.body().getMealsList());
            }

            @Override
            public void onFailure(Call<MealsAll> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                shimmerFrameLayout.setVisibility(View.VISIBLE);

            }
        });
    }

    private void showData(List<Meals> mealsList) {
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        searchAdapter = new SearchAdapter(mealsList, getContext());
        recyclerView.setAdapter(searchAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void actions() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedProductsInterface.onBackPressed();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setText("");
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        selectedProductsInterface = (SelectedProductsInterface) context;
    }
}

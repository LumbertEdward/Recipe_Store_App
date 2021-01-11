package com.example.recipestore.FragmentClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipestore.AdapterClasses.FavouritesAdapter;
import com.example.recipestore.AdapterClasses.MealsAdapter;
import com.example.recipestore.AllClasses.Meals;
import com.example.recipestore.AllClasses.MyDatabaseSQLite;
import com.example.recipestore.AllClasses.TinyDB;
import com.example.recipestore.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import io.paperdb.Paper;

public class FavouritesFragment extends Fragment {
    private FavouritesAdapter favouritesAdapter;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView recyclerView;
    private List<Meals> mealsList;
    private MyDatabaseSQLite myDatabaseSQLite;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView warn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favourites, container, false);
        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerFav);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe);
        warn = (TextView) v.findViewById(R.id.warnFav);
        warn.setVisibility(View.GONE);
        myDatabaseSQLite = new MyDatabaseSQLite(getContext());
        mealsList = myDatabaseSQLite.getMeals();
        if (mealsList.isEmpty()){
            warn.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
        }
        else {
            warn.setVisibility(View.GONE);
            favouritesAdapter = new FavouritesAdapter(mealsList, getContext());
            favouritesAdapter.notifyDataSetChanged();
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(favouritesAdapter);
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Collections.sort(mealsList, new Comparator<Meals>() {
                    @Override
                    public int compare(Meals o1, Meals o2) {
                        return o1.getStrMeal().compareTo(o2.getStrMeal());
                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        mealsList = myDatabaseSQLite.getMeals();
        super.onResume();
    }
}

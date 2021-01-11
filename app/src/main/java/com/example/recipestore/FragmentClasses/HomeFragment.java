package com.example.recipestore.FragmentClasses;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipestore.AdapterClasses.AreasAdapter;
import com.example.recipestore.AdapterClasses.CategoriesAdapter;
import com.example.recipestore.AdapterClasses.MealsAdapter;
import com.example.recipestore.AllClasses.Areas;
import com.example.recipestore.AllClasses.AreasAll;
import com.example.recipestore.AllClasses.Categories;
import com.example.recipestore.AllClasses.CategoriesAll;
import com.example.recipestore.AllClasses.Meals;
import com.example.recipestore.AllClasses.MealsAll;
import com.example.recipestore.InterfaceClasses.AreasInterface;
import com.example.recipestore.InterfaceClasses.BeefInterface;
import com.example.recipestore.InterfaceClasses.CategoriesInterface;
import com.example.recipestore.InterfaceClasses.MealsInterface;
import com.example.recipestore.InterfaceClasses.POrkInterface;
import com.example.recipestore.InterfaceClasses.SeafoodInterface;
import com.example.recipestore.InterfaceClasses.SelectedProductsInterface;
import com.example.recipestore.R;
import com.example.recipestore.RetrofitClasses.AreasRetrofit;
import com.example.recipestore.RetrofitClasses.BeefRetrofit;
import com.example.recipestore.RetrofitClasses.CategoriesRetrofit;
import com.example.recipestore.RetrofitClasses.PorkRetrofit;
import com.example.recipestore.RetrofitClasses.SeafoodRetrofit;
import com.example.recipestore.RetrofitClasses.SearchCategoryRetrofit;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private static final String list = "list";
    private RecyclerView category;
    private RecyclerView.LayoutManager layoutManagerCategory;
    private RecyclerView.LayoutManager layoutManagerArea;
    private RecyclerView.LayoutManager layoutManagerMeal;
    private RecyclerView.LayoutManager layoutManagerBeef;
    private RecyclerView.LayoutManager layoutManagerPork;
    private RecyclerView.LayoutManager layoutManagerSeafood;
    private CategoriesAdapter categoriesAdapter;
    private ShimmerFrameLayout shimmerFrameLayout;
    private RecyclerView area;
    private AreasAdapter areasAdapter;
    private RecyclerView chicken;
    private MealsAdapter mealsAdapter;
    private RecyclerView recyclerViewBeef;
    private MealsAdapter mealsAdapterBeef;
    private RecyclerView recyclerViewPork;
    private MealsAdapter mealsAdapterPork;
    private RecyclerView recyclerViewSeafood;
    private MealsAdapter mealsAdapterSeafood;
    private TextView dishHead;
    private TextView chickHead;
    private TextView chickenAll;
    private TextView beefHead;
    private TextView beefAll;
    private TextView porkHead;
    private TextView porkAll;
    private TextView seaHead;
    private TextView seaAll;
    private ScrollView scrollView;


    private CategoriesInterface categoriesInterface;
    private AreasInterface areasInterface;
    private MealsInterface mealsInterface;

    private BeefInterface beefInterface;
    private POrkInterface pOrkInterface;
    private SeafoodInterface seafoodInterface;

    private SelectedProductsInterface selectedProductsInterface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        category = (RecyclerView) v.findViewById(R.id.categoriesRecycler);
        area = (RecyclerView) v.findViewById(R.id.areaRecycler);
        chicken = (RecyclerView) v.findViewById(R.id.chickenRecycler);
        recyclerViewBeef = (RecyclerView) v.findViewById(R.id.beefRecycler);
        recyclerViewPork = (RecyclerView) v.findViewById(R.id.PorkRecycler);
        recyclerViewSeafood = (RecyclerView) v.findViewById(R.id.seaRecycler);
        shimmerFrameLayout = (ShimmerFrameLayout) v.findViewById(R.id.shimmerHome);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        scrollView = (ScrollView) v.findViewById(R.id.scrollRec);
        scrollView.setVisibility(View.GONE);
        chickHead = (TextView) v.findViewById(R.id.headChicken);
        dishHead = (TextView) v.findViewById(R.id.headDish);
        chickHead.setVisibility(View.GONE);
        dishHead.setVisibility(View.GONE);
        chickenAll = (TextView) v.findViewById(R.id.all);
        chickenAll.setVisibility(View.GONE);
        beefHead = (TextView) v.findViewById(R.id.headBeef);
        porkHead = (TextView) v.findViewById(R.id.headPork);
        seaHead = (TextView) v.findViewById(R.id.headSea);
        beefAll = (TextView) v.findViewById(R.id.allBeef);
        porkAll = (TextView) v.findViewById(R.id.allPork);
        seaAll = (TextView) v.findViewById(R.id.allSea);
        beefHead.setVisibility(View.GONE);
        porkHead.setVisibility(View.GONE);
        seaHead.setVisibility(View.GONE);
        beefAll.setVisibility(View.GONE);
        porkAll.setVisibility(View.GONE);
        seaAll.setVisibility(View.GONE);

        layoutManagerCategory = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        layoutManagerArea = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        layoutManagerMeal = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        layoutManagerBeef = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        layoutManagerPork = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        layoutManagerSeafood = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        getCategories();
        getAreas();
        getMeals();
        getPork();
        getSeaFoods();
        getBeef();
        seeAllItems();
        return v;
    }

    private void seeAllItems() {
        chickenAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedProductsInterface.setCatName("Chicken");
            }
        });
        beefAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedProductsInterface.setCatName("Beef");
            }
        });
        porkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedProductsInterface.setCatName("Pork");
            }
        });
        seaAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedProductsInterface.setCatName("SeaFood");
            }
        });
    }

    private void getCategories() {
        categoriesInterface = CategoriesRetrofit.getRetrofit().create(CategoriesInterface.class);
        Call<CategoriesAll> categoriesAllCall = categoriesInterface.getAllCategories();
        categoriesAllCall.enqueue(new Callback<CategoriesAll>() {
            @Override
            public void onResponse(Call<CategoriesAll> call, Response<CategoriesAll> response) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.GONE);
                getCategory(response.body().getCategoriesList());
            }

            @Override
            public void onFailure(Call<CategoriesAll> call, Throwable t) {
                shimmerFrameLayout.startShimmer();
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
                //progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getCategory(List<Categories> categoriesList) {
        categoriesAdapter = new CategoriesAdapter(categoriesList, getContext());
        category.setAdapter(categoriesAdapter);
        category.setLayoutManager(layoutManagerCategory);
    }

    private void getAreas() {
        areasInterface = AreasRetrofit.getRetrofit().create(AreasInterface.class);
        Call<AreasAll> areasAllCall = areasInterface.getAllAreas(list);
        areasAllCall.enqueue(new Callback<AreasAll>() {
            @Override
            public void onResponse(Call<AreasAll> call, Response<AreasAll> response) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.GONE);
                dishHead.setVisibility(View.VISIBLE);
                getAllAreasData(response.body().getAreasList());
            }

            @Override
            public void onFailure(Call<AreasAll> call, Throwable t) {
                shimmerFrameLayout.startShimmer();
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
                //progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getAllAreasData(List<Areas> areasList) {
        areasAdapter = new AreasAdapter(areasList, getContext());
        area.setLayoutManager(layoutManagerArea);
        area.setAdapter(areasAdapter);
    }

    private void getMeals() {
        mealsInterface = SearchCategoryRetrofit.getRetrofit().create(MealsInterface.class);
        Call<MealsAll> mealsAllCall = mealsInterface.getSearchedMeal("Chicken");
        mealsAllCall.enqueue(new Callback<MealsAll>() {
            @Override
            public void onResponse(Call<MealsAll> call, Response<MealsAll> response) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.GONE);
                chickHead.setVisibility(View.VISIBLE);
                chickenAll.setVisibility(View.VISIBLE);
                getMealsData(response.body().getMealsList());
            }

            @Override
            public void onFailure(Call<MealsAll> call, Throwable t) {
                shimmerFrameLayout.startShimmer();
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
                //progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMealsData(List<Meals> mealsList) {
        mealsAdapter = new MealsAdapter(mealsList, getContext());
        chicken.setAdapter(mealsAdapter);
        chicken.setLayoutManager(layoutManagerMeal);
    }

    private void getPork() {
        pOrkInterface = PorkRetrofit.getRetrofit().create(POrkInterface.class);
        Call<MealsAll> mealsAllCall = pOrkInterface.getSearchedMeal("Pork");
        mealsAllCall.enqueue(new Callback<MealsAll>() {
            @Override
            public void onResponse(Call<MealsAll> call, Response<MealsAll> response) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.GONE);
                porkHead.setVisibility(View.VISIBLE);
                porkAll.setVisibility(View.VISIBLE);
                getPorkData(response.body().getMealsList());
            }

            @Override
            public void onFailure(Call<MealsAll> call, Throwable t) {
                shimmerFrameLayout.startShimmer();
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
                //progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPorkData(List<Meals> mealsList) {
        mealsAdapterPork = new MealsAdapter(mealsList, getContext());
        recyclerViewPork.setAdapter(mealsAdapterPork);
        recyclerViewPork.setLayoutManager(layoutManagerPork);
    }

    private void getSeaFoods() {
        seafoodInterface = SeafoodRetrofit.getRetrofit().create(SeafoodInterface.class);
        Call<MealsAll> mealsAllCall = seafoodInterface.getSearchedMeal("SeaFood");
        mealsAllCall.enqueue(new Callback<MealsAll>() {
            @Override
            public void onResponse(Call<MealsAll> call, Response<MealsAll> response) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.GONE);
                seaHead.setVisibility(View.VISIBLE);
                seaAll.setVisibility(View.VISIBLE);
                getSeaData(response.body().getMealsList());
            }

            @Override
            public void onFailure(Call<MealsAll> call, Throwable t) {
                shimmerFrameLayout.startShimmer();
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
                //progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getSeaData(List<Meals> mealsList) {
        mealsAdapterSeafood = new MealsAdapter(mealsList, getContext());
        recyclerViewSeafood.setLayoutManager(layoutManagerSeafood);
        recyclerViewSeafood.setAdapter(mealsAdapterSeafood);
    }

    private void getBeef() {
        beefInterface = BeefRetrofit.getRetrofit().create(BeefInterface.class);
        Call<MealsAll> mealsAllCall = beefInterface.getSearchedMeal("Beef");
        mealsAllCall.enqueue(new Callback<MealsAll>() {
            @Override
            public void onResponse(Call<MealsAll> call, Response<MealsAll> response) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.GONE);
                beefHead.setVisibility(View.VISIBLE);
                beefAll.setVisibility(View.VISIBLE);
                getBeefData(response.body().getMealsList());
            }

            @Override
            public void onFailure(Call<MealsAll> call, Throwable t) {
                shimmerFrameLayout.startShimmer();
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
                //progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getBeefData(List<Meals> mealsList) {
        mealsAdapterBeef = new MealsAdapter(mealsList, getContext());
        recyclerViewBeef.setAdapter(mealsAdapterBeef);
        recyclerViewBeef.setLayoutManager(layoutManagerBeef);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        selectedProductsInterface = (SelectedProductsInterface) context;
    }
}

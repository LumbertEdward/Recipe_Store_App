package com.example.recipestore.AllActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipestore.AllClasses.Areas;
import com.example.recipestore.AllClasses.Categories;
import com.example.recipestore.AllClasses.FragmentClassesAll;
import com.example.recipestore.AllClasses.Meals;
import com.example.recipestore.FragmentClasses.AreaProductsFragment;
import com.example.recipestore.FragmentClasses.CategoryProductsFragment;
import com.example.recipestore.FragmentClasses.DetailsFragment;
import com.example.recipestore.FragmentClasses.FavouritesFragment;
import com.example.recipestore.FragmentClasses.HomeFragment;
import com.example.recipestore.FragmentClasses.NewRecipesFragment;
import com.example.recipestore.FragmentClasses.ProductsFragment;
import com.example.recipestore.FragmentClasses.SearchFragment;
import com.example.recipestore.FragmentClasses.VideoFragment;
import com.example.recipestore.InterfaceClasses.SelectedProductsInterface;
import com.example.recipestore.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements SelectedProductsInterface {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private TextView toolT;
    private CoordinatorLayout coordinatorLayout;

    //fragments
    private HomeFragment homeFragment;
    private FavouritesFragment favouritesFragment;
    private NewRecipesFragment newRecipesFragment;
    private ProductsFragment productsFragment;
    private AreaProductsFragment areaProductsFragment;
    private DetailsFragment detailsFragment;
    private VideoFragment videoFragment;
    private CategoryProductsFragment categoryProductsFragment;
    private SearchFragment searchFragment;

    private ArrayList<String> tags = new ArrayList<>();
    private ArrayList<FragmentClassesAll> fragmentClassesAlls = new ArrayList<>();
    private int EditCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            getWindow().setNavigationBarColor(getResources().getColor(R.color.back));
        }

        init();
        homeFrag();
    }

    private void homeFrag() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (homeFragment == null){
            homeFragment = new HomeFragment();
            fragmentTransaction.add(R.id.frame, homeFragment, "Home");
            tags.add("Home");
            fragmentClassesAlls.add(new FragmentClassesAll("Home", homeFragment));
            fragmentTransaction.commit();
        }
        else {
            tags.remove("Home");
            tags.add("Home");
        }
        setVisibility("Home");
    }

    private void init() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNav);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        toolT = (TextView) findViewById(R.id.titleTool);
        toolT.setText("Recipes");

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                HomeActivity.this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()){
                    case R.id.meals:
                        if (homeFragment == null){
                            homeFragment = new HomeFragment();
                            fragmentTransaction.add(R.id.frame, homeFragment, "Home");
                            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                            tags.add("Home");
                            fragmentClassesAlls.add(new FragmentClassesAll("Home", homeFragment));
                            fragmentTransaction.commit();
                        }
                        else {
                            tags.remove("Home");
                            tags.add("Home");
                        }
                        setVisibility("Home");
                        break;
                    case R.id.newMeals:
                        if (newRecipesFragment == null){
                            newRecipesFragment = new NewRecipesFragment();
                            fragmentTransaction.add(R.id.frame, newRecipesFragment, "New");
                            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                            tags.add("New");
                            fragmentClassesAlls.add(new FragmentClassesAll("New", newRecipesFragment));
                            fragmentTransaction.commit();
                        }
                        else {
                            tags.remove("New");
                            tags.add("New");
                        }
                        setVisibility("New");
                        break;
                    case R.id.fav:
                        if (favouritesFragment == null){
                            favouritesFragment = new FavouritesFragment();
                            fragmentTransaction.add(R.id.frame, favouritesFragment, "Favourites");
                            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                            tags.add("Favourites");
                            fragmentClassesAlls.add(new FragmentClassesAll("Favourites", favouritesFragment));
                            fragmentTransaction.commit();
                        }
                        else {
                            tags.remove("Favourites");
                            tags.add("Favourites");
                        }
                        setVisibility("Favourites");
                        break;
                    case R.id.settingsNav:
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()){
                    case R.id.mealsBot:
                        if (homeFragment == null){
                            homeFragment = new HomeFragment();
                            fragmentTransaction.add(R.id.frame, homeFragment, "Home");
                            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                            tags.add("Home");
                            fragmentClassesAlls.add(new FragmentClassesAll("Home", homeFragment));
                            fragmentTransaction.commit();
                        }
                        else {
                            tags.remove("Home");
                            tags.add("Home");
                        }
                        setVisibility("Home");
                        break;
                    case R.id.favBot:
                        if (favouritesFragment == null){
                            favouritesFragment = new FavouritesFragment();
                            fragmentTransaction.add(R.id.frame, favouritesFragment, "Favourites");
                            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                            tags.add("Favourites");
                            fragmentClassesAlls.add(new FragmentClassesAll("Favourites", favouritesFragment));
                            fragmentTransaction.commit();
                        }
                        else {
                            tags.remove("Favourites");
                            tags.add("Favourites");
                        }
                        setVisibility("Favourites");
                        break;
                    case R.id.newBot:
                        if (newRecipesFragment == null){
                            newRecipesFragment = new NewRecipesFragment();
                            fragmentTransaction.add(R.id.frame, newRecipesFragment, "New");
                            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                            tags.add("New");
                            fragmentClassesAlls.add(new FragmentClassesAll("New", newRecipesFragment));
                            fragmentTransaction.commit();
                        }
                        else {
                            tags.remove("New");
                            tags.add("New");
                        }
                        setVisibility("New");
                        break;
                }
                return true;
            }
        });
    }
    public void checkNavigation(String tag){
        Menu menu = navigationView.getMenu();
        MenuItem menuItem = null;
        if (tag.equals("Home")){
            menuItem = menu.getItem(0);
            menuItem.setChecked(true);
        }
        else if (tag.equals("Favourites")){
            menuItem = menu.getItem(1);
            menuItem.setChecked(true);
        }
        else if (tag.equals("New")){
            menuItem = menu.getItem(2);
            menuItem.setChecked(true);
        }

    }

    public void checkBottom(String tag){
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = null;
        if (tag.equals("Home")){
            menuItem = menu.getItem(0);
            menuItem.setChecked(true);
        }
        else if (tag.equals("Favourites")){
            menuItem = menu.getItem(1);
            menuItem.setChecked(true);
        }
        else if (tag.equals("New")){
            menuItem = menu.getItem(2);
            menuItem.setChecked(true);
        }
    }

    public void setVisibility(String tag){
        if (tag.equals("Home")){
            showBottom();
            toolT.setText("Recipes");
            coordinatorLayout.setVisibility(View.VISIBLE);
        }
        else if (tag.equals("Favourites")){
            showBottom();
            toolT.setText("Favourites");
            coordinatorLayout.setVisibility(View.VISIBLE);
        }
        else if (tag.equals("New")){
            showBottom();
            toolT.setText("Latest Recipes");
            coordinatorLayout.setVisibility(View.VISIBLE);
        }
        else if (tag.equals("Products")){
            showBottom();
            coordinatorLayout.setVisibility(View.VISIBLE);
        }
        else if (tag.equals("Areas")){
            showBottom();
            coordinatorLayout.setVisibility(View.VISIBLE);
        }
        else if (tag.equals("Details")){
            hideBottom();
            coordinatorLayout.setVisibility(View.GONE);
        }
        else if (tag.equals("Video")){
            hideBottom();
            coordinatorLayout.setVisibility(View.GONE);
        }
        else if (tag.equals("CAT")){
            showBottom();
            coordinatorLayout.setVisibility(View.VISIBLE);
        }
        else if (tag.equals("Search")){
            hideBottom();
            coordinatorLayout.setVisibility(View.GONE);
        }
        for (int i = 0; i < fragmentClassesAlls.size(); i++){
            if (tag.equals(fragmentClassesAlls.get(i).getTag())){
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.show(fragmentClassesAlls.get(i).getFragment());
                fragmentTransaction.commit();
            }
            else {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.hide(fragmentClassesAlls.get(i).getFragment());
                fragmentTransaction.commit();
            }
        }
        checkBottom(tag);
        checkNavigation(tag);

    }

    public void showBottom(){
        if (bottomNavigationView != null){
            bottomNavigationView.setVisibility(View.VISIBLE);
        }

    }
    public void hideBottom(){
        if (bottomNavigationView != null){
            bottomNavigationView.setVisibility(View.GONE);
        }

    }

    @Override
    public void onBackPressed() {
        int TotalCount = tags.size();
        if (TotalCount > 1){
            String top = tags.get(TotalCount - 1);
            String bot = tags.get(TotalCount - 2);
            setVisibility(bot);
            tags.remove(top);
            EditCount = 0;
        }
        else if (TotalCount == 1){
            String tp = tags.get(TotalCount - 1);
            if (tp.equals("Home")){
                Toast.makeText(this, "End", Toast.LENGTH_SHORT).show();
                EditCount++;
            }
            else {
                EditCount++;
            }
        }
        if (EditCount >= 2) {
            super.onBackPressed();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                if (searchFragment == null){
                    searchFragment = new SearchFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction.add(R.id.frame, searchFragment, "Search");
                    fragmentTransaction.commit();
                    tags.add("Search");
                    fragmentClassesAlls.add(new FragmentClassesAll("Search", searchFragment));
                }
                else {
                    tags.remove("Search");
                    tags.add("Search");
                }
                setVisibility("Search");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getSelectedProduct(Meals meals) {
        toolT.setText(meals.getStrMeal());
        if (detailsFragment != null){
            getSupportFragmentManager().beginTransaction().remove(detailsFragment).commitAllowingStateLoss();
        }
        detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("Detail", meals);
        detailsFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame, detailsFragment, "Details");
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.commit();
        tags.add("Details");
        fragmentClassesAlls.add(new FragmentClassesAll("Details", detailsFragment));
        setVisibility("Details");
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void getSelectedCategoryProducts(Categories categories) {
        toolT.setText(categories.getStrCategory());
        if (productsFragment != null){
            getSupportFragmentManager().beginTransaction().remove(productsFragment).commitAllowingStateLoss();
        }
        productsFragment = new ProductsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("Data", categories);
        productsFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame, productsFragment, "Products");
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.commit();
        tags.add("Products");
        fragmentClassesAlls.add(new FragmentClassesAll("Products", productsFragment));
        setVisibility("Products");
    }

    @Override
    public void getAreaProducts(Areas areas) {
        toolT.setText(areas.getStrArea());
        if (areaProductsFragment != null){
            getSupportFragmentManager().beginTransaction().remove(areaProductsFragment).commitAllowingStateLoss();
        }
        areaProductsFragment = new AreaProductsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("Area", areas);
        areaProductsFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame, areaProductsFragment, "Areas");
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.commit();
        tags.add("Areas");
        fragmentClassesAlls.add(new FragmentClassesAll("Areas", areaProductsFragment));
        setVisibility("Areas");
    }

    @Override
    public void sendVideoID(String vID) {
        if (videoFragment != null){
            getSupportFragmentManager().beginTransaction().remove(videoFragment).commitAllowingStateLoss();
        }
        videoFragment = new VideoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("ID", vID);
        videoFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame, videoFragment, "Video");
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.commit();
        tags.add("Video");
        fragmentClassesAlls.add(new FragmentClassesAll("Video", videoFragment));
        setVisibility("Video");

    }
    @Override
    public void setCatName(String name) {
        toolT.setText(name);
        if (categoryProductsFragment != null){
            getSupportFragmentManager().beginTransaction().remove(categoryProductsFragment).commitAllowingStateLoss();
        }
        categoryProductsFragment = new CategoryProductsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("DAT", name);
        categoryProductsFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame, categoryProductsFragment, "CAT");
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.commit();
        tags.add("CAT");
        fragmentClassesAlls.add(new FragmentClassesAll("CAT", categoryProductsFragment));
        setVisibility("CAT");

    }
}

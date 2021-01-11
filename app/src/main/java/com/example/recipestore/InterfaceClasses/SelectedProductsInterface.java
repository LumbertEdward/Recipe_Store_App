package com.example.recipestore.InterfaceClasses;

import com.example.recipestore.AllClasses.Areas;
import com.example.recipestore.AllClasses.Categories;
import com.example.recipestore.AllClasses.Meals;

import java.util.ArrayList;
import java.util.List;

public interface SelectedProductsInterface {
    void getSelectedProduct(Meals meals);
    void getSelectedCategoryProducts(Categories categories);
    void getAreaProducts(Areas areas);
    void sendVideoID(String vID);
    void onBackPressed();
    void setCatName(String name);
    //void saveFavourites(Meals meals);
}

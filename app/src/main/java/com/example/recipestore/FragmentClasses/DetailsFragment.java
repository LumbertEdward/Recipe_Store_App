package com.example.recipestore.FragmentClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.sax.TextElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipestore.AllClasses.Details;
import com.example.recipestore.AllClasses.DetailsAll;
import com.example.recipestore.AllClasses.Meals;
import com.example.recipestore.AllClasses.MyDatabaseSQLite;
import com.example.recipestore.AllClasses.TinyDB;
import com.example.recipestore.InterfaceClasses.DetailsInterface;
import com.example.recipestore.InterfaceClasses.SelectedProductsInterface;
import com.example.recipestore.R;
import com.example.recipestore.RetrofitClasses.DetailsRetrofit;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.gson.Gson;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsFragment extends Fragment {
    private DetailsInterface detailsInterface;
    private SelectedProductsInterface selectedProductsInterface;
    private Meals meals;

    private ImageView thumb;
    private ImageView play;
    private TextView title;
    private TextView area;
    private TextView category;
    private TextView instructions;
    private TextView ingredient1;
    private TextView measure1;
    private TextView ingredient2;
    private TextView measure2;
    private TextView ingredient3;
    private TextView measure3;
    private TextView ingredient4;
    private TextView measure4;
    private TextView ingredient5;
    private TextView measure5;
    private TextView ingredient6;
    private TextView measure6;
    private ImageView back;
    private LikeButton mLike;
    private ScrollView scrollView;
    private ProgressBar progressBar;
    private List<Meals> meals1;
    private MyDatabaseSQLite myDatabaseSQLite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null){
            meals = bundle.getParcelable("Detail");
        }
        Paper.init(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_details, container, false);
        thumb = (ImageView) v.findViewById(R.id.imageDetail);
        play = (ImageView) v.findViewById(R.id.imgPlay);
        title = (TextView) v.findViewById(R.id.titleDetail);
        area = (TextView) v.findViewById(R.id.areaDetail);
        category = (TextView) v.findViewById(R.id.categoryDetail);
        instructions = (TextView) v.findViewById(R.id.instructionDetail);
        progressBar = (ProgressBar) v.findViewById(R.id.progressDet);
        scrollView = (ScrollView) v.findViewById(R.id.scroll);
        scrollView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        ingredient1 = (TextView) v.findViewById(R.id.ingedient1);
        ingredient2 = (TextView) v.findViewById(R.id.ingedient2);
        ingredient3 = (TextView) v.findViewById(R.id.ingedient3);
        ingredient4 = (TextView) v.findViewById(R.id.ingedient4);
        ingredient5 = (TextView) v.findViewById(R.id.ingedient5);
        ingredient6 = (TextView) v.findViewById(R.id.ingedient6);
        measure1 = (TextView) v.findViewById(R.id.measure1);
        measure2 = (TextView) v.findViewById(R.id.measure2);
        measure3 = (TextView) v.findViewById(R.id.measure3);
        measure4 = (TextView) v.findViewById(R.id.measure4);
        measure5 = (TextView) v.findViewById(R.id.measure5);
        measure6 = (TextView) v.findViewById(R.id.measure6);
        back = (ImageView) v.findViewById(R.id.backDetail);
        mLike = (LikeButton) v.findViewById(R.id.likeB);
        myDatabaseSQLite = new MyDatabaseSQLite(getContext());
        mLike.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                boolean isInserted = myDatabaseSQLite.insertData(meals);
                if (isInserted == true){
                    Toast.makeText(getContext(), "Inserted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Not inserted", Toast.LENGTH_SHORT).show();
                }
                /**boolean confirmItem = myDatabaseSQLite.confirmItem(meals.getIdMeal());
                if (confirmItem == true){
                    Toast.makeText(getContext(), "Already added", Toast.LENGTH_SHORT).show();
                }
                else {

                    if (isInserted == true){
                        Toast.makeText(getContext(), "Inserted", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "Not inserted", Toast.LENGTH_SHORT).show();
                    }
                }**/

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                boolean isDeleted = myDatabaseSQLite.deleteItem(meals.getIdMeal());
                if (isDeleted == true){
                    Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Not deleted", Toast.LENGTH_SHORT).show();
                }
                /**boolean confirmItem = myDatabaseSQLite.confirmItem(meals.getIdMeal());
                if (confirmItem == true){
                    boolean isDeleted = myDatabaseSQLite.deleteItem(meals.getIdMeal());
                    if (isDeleted == true){
                        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "Not deleted", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "No such item", Toast.LENGTH_SHORT).show();
                }**/

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedProductsInterface.onBackPressed();
            }
        });
        getData();
        return v;
    }



    private void getData() {
        detailsInterface = DetailsRetrofit.getRetrofit().create(DetailsInterface.class);
        Call<DetailsAll> detailsAllCall = detailsInterface.getAllDetails(meals.getIdMeal());
        detailsAllCall.enqueue(new Callback<DetailsAll>() {
            @Override
            public void onResponse(Call<DetailsAll> call, Response<DetailsAll> response) {
                scrollView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                getAllDetailsData(response.body().getDetailsList());
            }

            @Override
            public void onFailure(Call<DetailsAll> call, Throwable t) {
                progressBar.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getAllDetailsData(List<Details> detailsList) {
        for (Details details : detailsList){
            title.setText(details.getStrMeal());
            area.setText(details.getStrArea());
            category.setText(details.getStrCategory());
            instructions.setText(details.getStrInstructions());
            ingredient1.setText(details.getStrIngredient1());
            ingredient2.setText(details.getStrIngredient2());
            ingredient3.setText(details.getStrIngredient3());
            ingredient4.setText(details.getStrIngredient4());
            ingredient5.setText(details.getStrIngredient5());
            ingredient6.setText(details.getStrIngredient6());
            measure1.setText(details.getStrMeasure1());
            measure2.setText(details.getStrMeasure2());
            measure3.setText(details.getStrMeasure3());
            measure4.setText(details.getStrMeasure4());
            measure5.setText(details.getStrMeasure5());
            measure6.setText(details.getStrMeasure6());
            Picasso.Builder builder = new Picasso.Builder(getContext());
            builder.downloader(new OkHttp3Downloader(getContext()));
            builder.build().load(details.getStrMealThumb()).into(thumb);
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedProductsInterface.sendVideoID(details.getStrYoutube());
                }
            });

        }
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        selectedProductsInterface = (SelectedProductsInterface) context;
    }
}

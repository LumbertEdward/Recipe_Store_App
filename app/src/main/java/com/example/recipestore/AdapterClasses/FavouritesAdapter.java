package com.example.recipestore.AdapterClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipestore.AllClasses.Meals;
import com.example.recipestore.AllClasses.MyDatabaseSQLite;
import com.example.recipestore.InterfaceClasses.SelectedProductsInterface;
import com.example.recipestore.R;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.MyViewHolder> {
    private List<Meals> mealsList;
    private Context context;
    private SelectedProductsInterface selectedProductsInterface;
    private MyDatabaseSQLite myDatabaseSQLite;

    public FavouritesAdapter(List<Meals> mealsList, Context context) {
        this.mealsList = mealsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.favourites_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(mealsList.get(position).getStrMeal());
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(mealsList.get(position).getStrMealThumb()).into(holder.imageView);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedProductsInterface.getSelectedProduct(mealsList.get(position));
            }
        });
        myDatabaseSQLite = new MyDatabaseSQLite(context);
        holder.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.options);
                popupMenu.inflate(R.menu.options_fav);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.fav_option:
                                boolean confirmItem = myDatabaseSQLite.confirmItem(mealsList.get(position).getIdMeal());
                                if (confirmItem == true){
                                    boolean isDeleted = myDatabaseSQLite.deleteItem(mealsList.get(position).getIdMeal());
                                    if (isDeleted == true){
                                        remove(mealsList.get(position));
                                        notifyItemRemoved(position);
                                        Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(context, "Not removed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

    }

    public void addAll(List<Meals> meals1){
        for (Meals meals : meals1){
            mealsList.add(meals);
        }
    }
    public void remove(Meals meals){
        int position = mealsList.indexOf(meals);
        if (position > -1){
            mealsList.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        selectedProductsInterface = (SelectedProductsInterface) context;
    }

    @Override
    public int getItemCount() {
        return mealsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private LinearLayout linearLayout;
        private ImageView imageView;
        private ImageView options;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.titleMealFav);
            imageView = (ImageView) itemView.findViewById(R.id.mealImgFav);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearFav);
            options = (ImageView) itemView.findViewById(R.id.optionsItemsFav);
        }
    }
}

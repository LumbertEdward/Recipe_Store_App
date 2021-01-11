package com.example.recipestore.AdapterClasses;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MyViewHolder> implements Filterable {
    private List<Meals> mealsList;
    private Context context;
    private List<Meals> filteredList;
    private SelectedProductsInterface selectedProductsInterface;
    private MyDatabaseSQLite myDatabaseSQLite;

    public MealsAdapter(List<Meals> mealsList, Context context) {
        this.mealsList = mealsList;
        this.context = context;
        filteredList = new ArrayList<>(mealsList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.all_meals_item, parent, false);
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
                popupMenu.inflate(R.menu.options_items);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.favItem:
                                boolean confirmItem = myDatabaseSQLite.confirmItem(mealsList.get(position).getIdMeal());
                                if (confirmItem == false){
                                    Toast.makeText(context, "Already added", Toast.LENGTH_SHORT).show();
                                }
                                else if (confirmItem == true){
                                    boolean isInserted = myDatabaseSQLite.insertData(mealsList.get(position));
                                    if (isInserted == true){
                                        notifyDataSetChanged();
                                        Toast.makeText(context, "Inserted", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(context, "Item Exists", Toast.LENGTH_SHORT).show();
                                    }
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

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        selectedProductsInterface = (SelectedProductsInterface) context;
    }

    @Override
    public int getItemCount() {
        return mealsList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Meals> meals = new ArrayList<>();
                if (constraint == null || constraint.length() == 0){
                    meals.addAll(filteredList);
                }
                else {
                    String vl = constraint.toString().toLowerCase().trim();
                    for (Meals meals1: filteredList){
                        if (meals1.getStrMeal().toLowerCase().equals(vl)){
                            meals.add(meals1);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = meals;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mealsList.clear();
                mealsList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private LinearLayout linearLayout;
        private ImageView imageView;
        private ImageView options;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.titleMealAll);
            imageView = (ImageView) itemView.findViewById(R.id.mealImgAll);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearAll);
            options = (ImageView) itemView.findViewById(R.id.optionsItems);
        }
    }
}

package com.example.recipestore.AdapterClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipestore.AllClasses.Meals;
import com.example.recipestore.InterfaceClasses.SelectedProductsInterface;
import com.example.recipestore.R;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> implements Filterable {
    private List<Meals> mealsList;
    private Context context;
    private List<Meals> filteredList;
    private SelectedProductsInterface selectedProductsInterface;
    private static final int FADE_DURATION = 1000;

    public SearchAdapter(List<Meals> mealsList, Context context) {
        this.mealsList = mealsList;
        this.context = context;
        filteredList = new ArrayList<>(mealsList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.search_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(mealsList.get(position).getStrMeal());
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(mealsList.get(position).getStrMealThumb()).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedProductsInterface.getSelectedProduct(mealsList.get(position));
            }
        });
        scaleAnim(holder.itemView);
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
                List<Meals> meals1 = new ArrayList<>();
                if (constraint.length() == 0 || constraint == null){
                    meals1.addAll(filteredList);
                }
                String txt = constraint.toString().toLowerCase().trim();
                for (Meals meals : filteredList){
                    if (meals.getStrMeal().toLowerCase().contains(txt)){
                        meals1.add(meals);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = meals1;
                return filterResults;
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
        private TextView textView;
        private ImageView imageView;
        private CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.txtSearch);
            imageView = (ImageView) itemView.findViewById(R.id.imgSearch);
            cardView = (CardView) itemView.findViewById(R.id.cardSearch);
        }
    }
    private void scaleAnim(View view){
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(FADE_DURATION);
        view.startAnimation(scaleAnimation);
    }
}

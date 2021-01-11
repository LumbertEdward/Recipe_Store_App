package com.example.recipestore.AdapterClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipestore.AllClasses.Categories;
import com.example.recipestore.InterfaceClasses.SelectedProductsInterface;
import com.example.recipestore.R;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewAdapter extends RecyclerView.Adapter<NewAdapter.MyViewHolder> {
    private Context context;
    private List<Categories> categories;
    private SelectedProductsInterface selectedProductsInterface;

    public NewAdapter(Context context, List<Categories> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.new_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(categories.get(position).getStrCategory());
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(categories.get(position).getStrCategoryThumb()).into(holder.img);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedProductsInterface.getSelectedCategoryProducts(categories.get(position));
            }
        });
        holder.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedProductsInterface.getSelectedCategoryProducts(categories.get(position));
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (context instanceof SelectedProductsInterface){
            selectedProductsInterface = (SelectedProductsInterface) context;
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private ImageView next;
        private TextView name;
        private CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imgNew);
            next = (ImageView) itemView.findViewById(R.id.imgNextNew);
            name = (TextView) itemView.findViewById(R.id.titleNew);
            cardView = (CardView) itemView.findViewById(R.id.cardNew);
        }
    }
}

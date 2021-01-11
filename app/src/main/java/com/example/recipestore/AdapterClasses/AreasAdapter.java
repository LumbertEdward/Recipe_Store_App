package com.example.recipestore.AdapterClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipestore.AllClasses.Areas;
import com.example.recipestore.InterfaceClasses.SelectedProductsInterface;
import com.example.recipestore.R;

import java.util.List;

public class AreasAdapter extends RecyclerView.Adapter<AreasAdapter.MyViewHolder> {
    private List<Areas> areasList;
    private Context context;
    private SelectedProductsInterface selectedProductsInterface;

    public AreasAdapter(List<Areas> areasList, Context context) {
        this.areasList = areasList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.area_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(areasList.get(position).getStrArea());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedProductsInterface.getAreaProducts(areasList.get(position));
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
        return areasList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private RelativeLayout relativeLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textArea);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.areaRel);
        }
    }
}

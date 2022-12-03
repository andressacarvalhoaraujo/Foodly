package com.uniritter.cdm.cookingrecipeapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uniritter.cdm.cookingrecipeapplication.R;
import com.uniritter.cdm.cookingrecipeapplication.model.ICulinaryRecipeModel;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ICulinaryRecipeModel> data;

    public IngredientsAdapter(List<ICulinaryRecipeModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_details_1, parent,false);
        return new IngredientsViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ICulinaryRecipeModel obj = this.data.get(position);

        ((TextView)((IngredientsViewHolder) holder).itemView.findViewById(R.id.item))
                .setText(obj.getIngredients());
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }
}

class IngredientsViewHolder extends RecyclerView.ViewHolder {
    public View view;

    public IngredientsViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
    }
}

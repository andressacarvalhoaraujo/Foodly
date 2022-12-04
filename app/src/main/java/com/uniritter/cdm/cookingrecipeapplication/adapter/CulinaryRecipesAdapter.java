package com.uniritter.cdm.cookingrecipeapplication.adapter;

import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uniritter.cdm.cookingrecipeapplication.R;
import com.uniritter.cdm.cookingrecipeapplication.model.ICulinaryRecipeModel;
import com.uniritter.cdm.cookingrecipeapplication.view.CulinaryRecipeDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class CulinaryRecipesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ICulinaryRecipeModel> data;
    private List<ICulinaryRecipeModel> dataFilter;

    public CulinaryRecipesAdapter(List<ICulinaryRecipeModel> data) {
        this.data = data;
        resetFilter();
    }

    public void resetFilter() {
        this.dataFilter = new ArrayList<>();
        this.dataFilter.addAll(this.data);
    }

    public void filterData(String filter) {
        String title = "";
        filter = filter.toLowerCase();
        this.dataFilter = new ArrayList<>();

        for (ICulinaryRecipeModel r : this.data) {
            title = r.getTitle().toLowerCase();
            if(title.contains(filter)) {
                this.dataFilter.add(r);
            }
        }

        this.notifyDataSetChanged();
    }

    public void getDataByUserId(int userId) {
        this.dataFilter = new ArrayList<>();

        for (ICulinaryRecipeModel r : this.data) {
            if(r.getUserId() == userId) {
                this.dataFilter.add(r);
            }
        }

        if (this.dataFilter.size() == 0)
            this.dataFilter = this.data;


        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_1, parent,false);
        return new CulinaryRecipesViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ICulinaryRecipeModel obj = this.dataFilter.get(position);

        ((TextView)((CulinaryRecipesViewHolder) holder).itemView.findViewById(R.id.item)).setText(obj.getTitle());

        holder.itemView.setOnClickListener((view)->{
            Intent intent = new Intent(view.getContext(), CulinaryRecipeDetailsActivity.class);
            intent.putExtra("culinaryRecipeObject", (Parcelable) obj);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return this.dataFilter.size();
    }
}

class CulinaryRecipesViewHolder extends RecyclerView.ViewHolder {
    public View view;

    public CulinaryRecipesViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
    }
}

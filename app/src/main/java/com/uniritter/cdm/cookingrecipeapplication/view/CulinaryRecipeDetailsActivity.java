package com.uniritter.cdm.cookingrecipeapplication.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.uniritter.cdm.cookingrecipeapplication.R;
import com.uniritter.cdm.cookingrecipeapplication.adapter.DescriptionAdapter;
import com.uniritter.cdm.cookingrecipeapplication.adapter.IngredientsAdapter;
import com.uniritter.cdm.cookingrecipeapplication.adapter.PreparationModeAdapter;
import com.uniritter.cdm.cookingrecipeapplication.helper.RequestHelper;
import com.uniritter.cdm.cookingrecipeapplication.model.ICulinaryRecipeModel;
import com.uniritter.cdm.cookingrecipeapplication.model.IDifficultyLevelModel;
import com.uniritter.cdm.cookingrecipeapplication.model.IUserModel;
import com.uniritter.cdm.cookingrecipeapplication.presenter.CulinaryRecipePresenter;
import com.uniritter.cdm.cookingrecipeapplication.presenter.CulinaryRecipePresenterContract;
import com.uniritter.cdm.cookingrecipeapplication.presenter.UserPresenter;
import com.uniritter.cdm.cookingrecipeapplication.presenter.UserPresenterContract;

import java.util.ArrayList;
import java.util.List;

public class CulinaryRecipeDetailsActivity extends AppCompatActivity implements CulinaryRecipePresenterContract.View, UserPresenterContract.View {
    private UserPresenterContract.Presenter userPresenter;
    private CulinaryRecipePresenterContract.Presenter culinaryRecipePresenter;
    private ICulinaryRecipeModel culinaryRecipe;
    private List<ICulinaryRecipeModel> culinaryRecipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culinary_recipe_details);

        this.culinaryRecipePresenter = new CulinaryRecipePresenter(this);
        this.userPresenter = new UserPresenter(this);

        this.culinaryRecipe = (ICulinaryRecipeModel) getIntent().getParcelableExtra("culinaryRecipeObject");
        this.culinaryRecipes.add(this.culinaryRecipe);

        onCall();

        TextView titleText = findViewById(R.id.titleText);
        titleText.setText(this.culinaryRecipe.getTitle());

        LinearLayoutManager llmDescription = new LinearLayoutManager(this);
        llmDescription.setOrientation(LinearLayoutManager.VERTICAL);
        DescriptionAdapter adapterDescription = new DescriptionAdapter(this.culinaryRecipes);
        RecyclerView rvDescription = (RecyclerView) findViewById(R.id.recyclerViewDescription);
        rvDescription.setHasFixedSize(true);
        rvDescription.setAdapter(adapterDescription);
        rvDescription.setLayoutManager(llmDescription);

        TextView preparationTimeText = findViewById(R.id.preparationTimeText);
        preparationTimeText.setText(this.culinaryRecipe.getPreparationTime() + " min");

        TextView yieldText = findViewById(R.id.yieldText);
        yieldText.setText(culinaryRecipe.getYield() + " porções");

        TextView totalCaloriesText = findViewById(R.id.totalCaloriesText);
        totalCaloriesText.setText(culinaryRecipe.getTotalCalories() + " calorias");

        LinearLayoutManager llmIngredients = new LinearLayoutManager(this);
        llmIngredients.setOrientation(LinearLayoutManager.VERTICAL);
        IngredientsAdapter adapterIngredients = new IngredientsAdapter(culinaryRecipes);
        RecyclerView rvIngredients = (RecyclerView) findViewById(R.id.recyclerViewIngredients);
        rvIngredients.setHasFixedSize(true);
        rvIngredients.setAdapter(adapterIngredients);
        rvIngredients.setLayoutManager(llmIngredients);

        LinearLayoutManager llmPreparationMode = new LinearLayoutManager(this);
        llmPreparationMode.setOrientation(LinearLayoutManager.VERTICAL);
        PreparationModeAdapter adapterPreparationMode = new PreparationModeAdapter(culinaryRecipes);
        RecyclerView rvPreparationMode = (RecyclerView) findViewById(R.id.recyclerViewPreparationMode);
        rvPreparationMode.setHasFixedSize(true);
        rvPreparationMode.setAdapter(adapterPreparationMode);
        rvPreparationMode.setLayoutManager(llmPreparationMode);


        findViewById(R.id.back).setOnClickListener(view -> {
            startActivity(new Intent(CulinaryRecipeDetailsActivity.this, HomeActivity.class));
            finish();
        });
    }

    @Override
    public void onCall() {
        IUserModel u = userPresenter.getUserById(this.culinaryRecipe.getUserId());

        TextView byUserText = findViewById(R.id.byUserText);
        byUserText.setText("Por " + u.getUserName());


        IDifficultyLevelModel dl = culinaryRecipePresenter.getDifficultyLevelById(this.culinaryRecipe.getDifficultyLevelId());

        char fl[] = dl.getDifficultyLevelName().toCharArray();
        fl[0] = Character.toLowerCase(fl[0]);
        String difficultyLevelName = new String(fl);
        TextView difficultyLevelText = findViewById(R.id.difficultyLevelText);
        difficultyLevelText.setText("Dificuldade " + difficultyLevelName);
    }

    @Override
    public void onResult(RequestHelper result) {

    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
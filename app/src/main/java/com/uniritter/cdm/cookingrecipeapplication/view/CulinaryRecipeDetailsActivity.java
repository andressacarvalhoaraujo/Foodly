package com.uniritter.cdm.cookingrecipeapplication.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.uniritter.cdm.cookingrecipeapplication.R;
import com.uniritter.cdm.cookingrecipeapplication.adapter.DescriptionAdapter;
import com.uniritter.cdm.cookingrecipeapplication.adapter.IngredientsAdapter;
import com.uniritter.cdm.cookingrecipeapplication.adapter.PreparationModeAdapter;
import com.uniritter.cdm.cookingrecipeapplication.helper.RequestHelper;
import com.uniritter.cdm.cookingrecipeapplication.helper.RequestType;
import com.uniritter.cdm.cookingrecipeapplication.model.ICulinaryRecipeModel;
import com.uniritter.cdm.cookingrecipeapplication.model.IDifficultyLevelModel;
import com.uniritter.cdm.cookingrecipeapplication.model.IFavoriteCulinaryRecipeModel;
import com.uniritter.cdm.cookingrecipeapplication.model.INextCulinaryRecipeModel;
import com.uniritter.cdm.cookingrecipeapplication.model.IUserModel;
import com.uniritter.cdm.cookingrecipeapplication.presenter.CulinaryRecipePresenter;
import com.uniritter.cdm.cookingrecipeapplication.presenter.CulinaryRecipePresenterContract;
import com.uniritter.cdm.cookingrecipeapplication.presenter.UserPresenter;
import com.uniritter.cdm.cookingrecipeapplication.presenter.UserPresenterContract;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class CulinaryRecipeDetailsActivity extends AppCompatActivity implements CulinaryRecipePresenterContract.View, UserPresenterContract.View {
    private static String TAG = "CulinaryRecipeDetailsAct";
    private UserPresenterContract.Presenter userPresenter;
    private CulinaryRecipePresenterContract.Presenter culinaryRecipePresenter;
    private ICulinaryRecipeModel culinaryRecipe;
    private List<ICulinaryRecipeModel> culinaryRecipes = new ArrayList<>();
    private int userId, favoriteCulinaryRecipeId, nextCulinaryRecipeId;
    private boolean isFavoriteCulinaryRecipe, isNextCulinaryRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culinary_recipe_details);

        SharedPreferences sharedPreferences = getSharedPreferences("userCredentials", MODE_PRIVATE);
        this.userId = sharedPreferences.getInt("userId", 0);

        this.culinaryRecipePresenter = new CulinaryRecipePresenter(this);
        this.userPresenter = new UserPresenter(this);

        this.culinaryRecipe = (ICulinaryRecipeModel) getIntent().getParcelableExtra("culinaryRecipeObject");
        this.culinaryRecipes.add(this.culinaryRecipe);

        this.checkFavoriteCulinaryRecipe();
        this.checkNextCulinaryRecipe();

        this.getData();

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
        yieldText.setText(this.culinaryRecipe.getYield() + " por????es");

        TextView totalCaloriesText = findViewById(R.id.totalCaloriesText);
        totalCaloriesText.setText(this.culinaryRecipe.getTotalCalories() + " calorias");

        LinearLayoutManager llmIngredients = new LinearLayoutManager(this);
        llmIngredients.setOrientation(LinearLayoutManager.VERTICAL);
        IngredientsAdapter adapterIngredients = new IngredientsAdapter(this.culinaryRecipes);
        RecyclerView rvIngredients = (RecyclerView) findViewById(R.id.recyclerViewIngredients);
        rvIngredients.setHasFixedSize(true);
        rvIngredients.setAdapter(adapterIngredients);
        rvIngredients.setLayoutManager(llmIngredients);

        LinearLayoutManager llmPreparationMode = new LinearLayoutManager(this);
        llmPreparationMode.setOrientation(LinearLayoutManager.VERTICAL);
        PreparationModeAdapter adapterPreparationMode = new PreparationModeAdapter(this.culinaryRecipes);
        RecyclerView rvPreparationMode = (RecyclerView) findViewById(R.id.recyclerViewPreparationMode);
        rvPreparationMode.setHasFixedSize(true);
        rvPreparationMode.setAdapter(adapterPreparationMode);
        rvPreparationMode.setLayoutManager(llmPreparationMode);


        findViewById(R.id.back).setOnClickListener(view -> {
            startActivity(new Intent(CulinaryRecipeDetailsActivity.this, HomeActivity.class));
            finish();
        });

        findViewById(R.id.favorite).setOnClickListener(view -> {
            try {
                onCallFavoriteCulinaryRecipe();
            } catch (JSONException e) {
                Toast.makeText(CulinaryRecipeDetailsActivity.this, "Ocorreu um erro inesperado! Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error on add culinary recipe to favorites! Returned message: " + e.getMessage());
                e.printStackTrace();
            }
        });

        findViewById(R.id.next).setOnClickListener(view -> {
            try {
                onCallNextCulinaryRecipe();
            } catch (JSONException e) {
                Toast.makeText(CulinaryRecipeDetailsActivity.this, "Ocorreu um erro inesperado! Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error on add culinary recipe to next! Returned message: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void checkFavoriteCulinaryRecipe() {
        IFavoriteCulinaryRecipeModel favoriteCulinaryRecipe = this.culinaryRecipePresenter.getFavoriteCulinaryRecipeByUserIdAndCulinaryRecipeId(this.userId, this.culinaryRecipe.getCulinaryRecipeId());
        ImageButton btnImg = (ImageButton) findViewById(R.id.favorite);

        if (favoriteCulinaryRecipe != null) {
            btnImg.setImageResource(R.drawable.ic_baseline_favorite_24);

            this.favoriteCulinaryRecipeId = favoriteCulinaryRecipe.getFavoriteCulinaryRecipeId();
            this.isFavoriteCulinaryRecipe = true;
        } else {
            btnImg.setImageResource(R.drawable.ic_baseline_favorite_border_24);

            this.isFavoriteCulinaryRecipe = false;
        }
    }

    private void checkNextCulinaryRecipe() {
        INextCulinaryRecipeModel nextCulinaryRecipe = this.culinaryRecipePresenter.getNextCulinaryRecipeByUserIdAndCulinaryRecipeId(this.userId, this.culinaryRecipe.getCulinaryRecipeId());
        ImageButton btnImg = (ImageButton) findViewById(R.id.next);

        if (nextCulinaryRecipe != null) {
            btnImg.setImageResource(R.drawable.ic_baseline_fast_rewind_24);

            this.nextCulinaryRecipeId = nextCulinaryRecipe.getNextCulinaryRecipeId();
            this.isNextCulinaryRecipe = true;
        } else {
            btnImg.setImageResource(R.drawable.ic_baseline_fast_forward_24);

            this.isNextCulinaryRecipe = false;
        }
    }

    private void getData() {
        IUserModel u = this.userPresenter.getUserById(this.culinaryRecipe.getUserId());

        TextView byUserText = findViewById(R.id.byUserText);
        byUserText.setText("Por " + u.getUserName());


        IDifficultyLevelModel dl = this.culinaryRecipePresenter.getDifficultyLevelById(this.culinaryRecipe.getDifficultyLevelId());

        char fl[] = dl.getDifficultyLevelName().toCharArray();
        fl[0] = Character.toLowerCase(fl[0]);
        String difficultyLevelName = new String(fl);
        TextView difficultyLevelText = findViewById(R.id.difficultyLevelText);
        difficultyLevelText.setText("Dificuldade " + difficultyLevelName);
    }

    @Override
    public void onCall() {

    }

    @Override
    public void onResult(RequestHelper result) {

    }

    @Override
    public void onCallCulinaryRecipe() throws JSONException {

    }

    @Override
    public void onResultCulinaryRecipe(RequestHelper result) {

    }

    @Override
    public void onCallFavoriteCulinaryRecipe() throws JSONException {
        if (this.isFavoriteCulinaryRecipe) {
            this.culinaryRecipePresenter.deleteFavoriteCulinaryRecipe(this.favoriteCulinaryRecipeId);
        } else {
            this.culinaryRecipePresenter.addFavoriteCulinaryRecipe(this.userId, this.culinaryRecipe.getCulinaryRecipeId());
        }
    }

    @Override
    public void onResultFavoriteCulinaryRecipe(RequestHelper result) {
        if (result.type == RequestType.OK) {
            if (this.isFavoriteCulinaryRecipe) {
                Log.d(TAG, "Remove culinary recipe of favorite success.");
                Toast.makeText(CulinaryRecipeDetailsActivity.this, "Receita removida dos favoritos com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, "Add culinary recipe to favorites success.");
                Toast.makeText(CulinaryRecipeDetailsActivity.this, "Receita adicionada aos favoritos com sucesso!", Toast.LENGTH_SHORT).show();
            }

            this.checkFavoriteCulinaryRecipe();
        } else
        {
            if (this.isFavoriteCulinaryRecipe) {
                Log.e(TAG, "Error on remove culinary recipe of favorites! Returned message: " + result.errorMessage);
                Toast.makeText(CulinaryRecipeDetailsActivity.this, "Ocorreu um erro ao remover a receita dos favoritos! " + result.errorMessage, Toast.LENGTH_SHORT).show();
            } else {
                Log.e(TAG, "Error on add culinary recipe to favorites success.");
                Toast.makeText(CulinaryRecipeDetailsActivity.this, "Ocorreu um erro ao adicionar a receita aos favoritos! " + result.errorMessage, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCallNextCulinaryRecipe() throws JSONException {
        if (this.isNextCulinaryRecipe) {
            this.culinaryRecipePresenter.deleteNextCulinaryRecipe(this.nextCulinaryRecipeId);
        } else {
            this.culinaryRecipePresenter.addNextCulinaryRecipe(this.userId, this.culinaryRecipe.getCulinaryRecipeId());
        }
    }

    @Override
    public void onResultNextCulinaryRecipe(RequestHelper result) {
        if (result.type == RequestType.OK) {
            if (this.isNextCulinaryRecipe) {
                Log.d(TAG, "Remove culinary recipe of next success.");
                Toast.makeText(CulinaryRecipeDetailsActivity.this, "Receita removida ??s pr??ximas com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, "Add culinary recipe to next success.");
                Toast.makeText(CulinaryRecipeDetailsActivity.this, "Receita adicionada ??s pr??ximas com sucesso!", Toast.LENGTH_SHORT).show();
            }

            this.checkNextCulinaryRecipe();
        } else
        {
            if (this.isNextCulinaryRecipe) {
                Log.e(TAG, "Error on remove culinary recipe of next! Returned message: " + result.errorMessage);
                Toast.makeText(CulinaryRecipeDetailsActivity.this, "Ocorreu um erro ao remover a receita das pr??ximas! " + result.errorMessage, Toast.LENGTH_SHORT).show();
            } else {
                Log.e(TAG, "Error on add culinary recipe to next success.");
                Toast.makeText(CulinaryRecipeDetailsActivity.this, "Ocorreu um erro ao adicionar a receita ??s pr??ximas! " + result.errorMessage, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
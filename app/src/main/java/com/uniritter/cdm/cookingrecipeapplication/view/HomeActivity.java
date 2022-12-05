package com.uniritter.cdm.cookingrecipeapplication.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.uniritter.cdm.cookingrecipeapplication.R;
import com.uniritter.cdm.cookingrecipeapplication.adapter.CulinaryRecipesAdapter;
import com.uniritter.cdm.cookingrecipeapplication.helper.RequestHelper;
import com.uniritter.cdm.cookingrecipeapplication.model.ICulinaryRecipeModel;
import com.uniritter.cdm.cookingrecipeapplication.model.IFavoriteCulinaryRecipeModel;
import com.uniritter.cdm.cookingrecipeapplication.model.INextCulinaryRecipeModel;
import com.uniritter.cdm.cookingrecipeapplication.presenter.CulinaryRecipePresenter;
import com.uniritter.cdm.cookingrecipeapplication.presenter.CulinaryRecipePresenterContract;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements CulinaryRecipePresenterContract.View {
    private CulinaryRecipePresenterContract.Presenter presenter;
    private CulinaryRecipesAdapter adapter;
    private List<ICulinaryRecipeModel> culinaryRecipes;
    private int userId;
    private ArrayList<Integer> favoriteCulinaryRecipesOfUser, nextCulinaryRecipesOfUser;
    private boolean isUserCulinaryRecipes = false;
    private boolean isUserFavoriteCulinaryRecipes = false;
    private boolean isUserNextCulinaryRecipes = false;
    private boolean userHasAnyCulinaryRecipe = false;
    private boolean thereIsAnyCulinaryRecipe = false;
    private boolean searchInfo = false;
    private MaterialButton userCulinaryRecipesBtn, favoriteCulinaryRecipesBtn, nextCulinaryRecipesBtn;
    private ImageButton searchBtnImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPreferences = getSharedPreferences("userCredentials", MODE_PRIVATE);
        this.userId = sharedPreferences.getInt("userId", 0);
        String userName = sharedPreferences.getString("userName", "");

        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText("Olá, " + userName + "!");

        this.presenter = new CulinaryRecipePresenter(this);

        this.culinaryRecipes = this.presenter.getAllCulinaryRecipes();

        this.adapter = new CulinaryRecipesAdapter(this.culinaryRecipes);
        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerViewCulinaryRecipes);
        rv.setHasFixedSize(true);

        rv.setAdapter(this.adapter);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        this.searchBtnImg = (ImageButton) findViewById(R.id.searchBtn);
        this.searchBtnImg.setImageResource(R.drawable.ic_baseline_search_24);

        this.userCulinaryRecipesBtn = findViewById(R.id.userCulinaryRecipes);
        this.favoriteCulinaryRecipesBtn = findViewById(R.id.favoriteCulinaryRecipes);
        this.nextCulinaryRecipesBtn = findViewById(R.id.nextCulinaryRecipes);

        findViewById(R.id.searchBtn).setOnClickListener(view -> {
            this.searchInfo = !this.searchInfo;

            EditText searchField = findViewById(R.id.search);
            String filter = ((EditText) findViewById(R.id.search)).getText().toString();

            if (this.searchInfo) {
                if (this.isUserCulinaryRecipes || this.isUserFavoriteCulinaryRecipes || this.isUserNextCulinaryRecipes)
                    this.thereIsAnyCulinaryRecipe = this.adapter.filterData(filter, true);
                else
                    this.thereIsAnyCulinaryRecipe = this.adapter.filterData(filter, false);

                if (!this.thereIsAnyCulinaryRecipe) {
                    Toast.makeText(HomeActivity.this, "Nenhuma receita encontrada.", Toast.LENGTH_SHORT).show();
                    searchField.setText("");
                    this.searchInfo = !this.searchInfo;
                } else {
                    this.searchBtnImg.setImageResource(R.drawable.ic_baseline_clear_24);
                }
            } else {
                if (!this.thereIsAnyCulinaryRecipe) {
                    Toast.makeText(HomeActivity.this, "Nenhuma receita encontrada.", Toast.LENGTH_SHORT).show();
                    this.searchInfo = !this.searchInfo;
                }

                searchField.setText("");

                if (this.isUserCulinaryRecipes || this.isUserFavoriteCulinaryRecipes || this.isUserNextCulinaryRecipes)
                    this.thereIsAnyCulinaryRecipe = this.adapter.filterData("", true);
                else
                    this.adapter.removeFilter();

                this.searchBtnImg.setImageResource(R.drawable.ic_baseline_search_24);
            }
        });

        findViewById(R.id.signOut).setOnClickListener(view -> {
            sharedPreferences.edit().clear().apply();

            startActivity(new Intent(HomeActivity.this, SignInActivity.class));
            finish();
        });

        findViewById(R.id.userCulinaryRecipes).setOnClickListener(view -> {
            this.adapter.resetFilter();

            this.isUserCulinaryRecipes = !this.isUserCulinaryRecipes;

            onCallCulinaryRecipe();
        });

        findViewById(R.id.favoriteCulinaryRecipes).setOnClickListener(view -> {
            this.adapter.resetFilter();

            this.isUserFavoriteCulinaryRecipes = !this.isUserFavoriteCulinaryRecipes;

            try {
                onCallFavoriteCulinaryRecipe();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        findViewById(R.id.nextCulinaryRecipes).setOnClickListener(view -> {
            this.adapter.resetFilter();

            this.isUserNextCulinaryRecipes = !this.isUserNextCulinaryRecipes;

            try {
                onCallNextCulinaryRecipe();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        findViewById(R.id.addCulinaryRecipe).setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, CulinaryRecipeAddActivity.class));
        });
    }

    @Override
    public void onCallCulinaryRecipe() {
        if (this.isUserCulinaryRecipes) {
            this.userHasAnyCulinaryRecipe = this.adapter.getDataByUserId(userId);

            if (!this.userHasAnyCulinaryRecipe) {
                this.favoriteCulinaryRecipesBtn.setEnabled(true);
                this.favoriteCulinaryRecipesBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue_200));
                this.nextCulinaryRecipesBtn.setEnabled(true);
                this.nextCulinaryRecipesBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue_200));

                Toast.makeText(HomeActivity.this, "Nenhuma receita encontrada.", Toast.LENGTH_SHORT).show();
            } else {
                this.favoriteCulinaryRecipesBtn.setEnabled(false);
                this.favoriteCulinaryRecipesBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                this.nextCulinaryRecipesBtn.setEnabled(false);
                this.nextCulinaryRecipesBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
            }
        } else {
            if (!this.userHasAnyCulinaryRecipe) {
                Toast.makeText(HomeActivity.this, "Nenhuma receita encontrada.", Toast.LENGTH_SHORT).show();
            }

            this.adapter.getDataByUserId(0);

            this.favoriteCulinaryRecipesBtn.setEnabled(true);
            this.favoriteCulinaryRecipesBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue_200));
            this.nextCulinaryRecipesBtn.setEnabled(true);
            this.nextCulinaryRecipesBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue_200));
        }
    }

    @Override
    public void onResultCulinaryRecipe(RequestHelper result) {

    }

    @Override
    public void onCallFavoriteCulinaryRecipe() throws JSONException {
        if (this.isUserFavoriteCulinaryRecipes) {
            this.favoriteCulinaryRecipesOfUser = new ArrayList<Integer>();

            IFavoriteCulinaryRecipeModel fc = null;

            for (ICulinaryRecipeModel r : this.culinaryRecipes) {
                fc = this.presenter.getFavoriteCulinaryRecipeByUserIdAndCulinaryRecipeId(this.userId, r.getCulinaryRecipeId());

                if (fc != null)
                    this.favoriteCulinaryRecipesOfUser.add(fc.getCulinaryRecipeId());
            }

            if (this.favoriteCulinaryRecipesOfUser.size() > 0) {
                this.adapter.getDataByCulinaryRecipeId(this.favoriteCulinaryRecipesOfUser);

                this.userCulinaryRecipesBtn.setEnabled(false);
                this.userCulinaryRecipesBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                this.nextCulinaryRecipesBtn.setEnabled(false);
                this.nextCulinaryRecipesBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
            } else {
                this.adapter.getDataByCulinaryRecipeId(new ArrayList<Integer>());

                this.userCulinaryRecipesBtn.setEnabled(true);
                this.userCulinaryRecipesBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue_200));
                this.nextCulinaryRecipesBtn.setEnabled(true);
                this.nextCulinaryRecipesBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue_200));

                Toast.makeText(HomeActivity.this, "Nenhuma receita encontrada.", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (this.favoriteCulinaryRecipesOfUser.size() == 0) {
                Toast.makeText(HomeActivity.this, "Nenhuma receita encontrada.", Toast.LENGTH_SHORT).show();
            }

            this.adapter.getDataByCulinaryRecipeId(new ArrayList<Integer>());

            this.userCulinaryRecipesBtn.setEnabled(true);
            this.userCulinaryRecipesBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue_200));
            this.nextCulinaryRecipesBtn.setEnabled(true);
            this.nextCulinaryRecipesBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue_200));
        }
    }

    @Override
    public void onResultFavoriteCulinaryRecipe(RequestHelper result) {

    }

    @Override
    public void onCallNextCulinaryRecipe() throws JSONException {
        if (this.isUserNextCulinaryRecipes) {
            this.nextCulinaryRecipesOfUser = new ArrayList<Integer>();

            INextCulinaryRecipeModel nc = null;

            for (ICulinaryRecipeModel r : this.culinaryRecipes) {
                nc = this.presenter.getNextCulinaryRecipeByUserIdAndCulinaryRecipeId(this.userId, r.getCulinaryRecipeId());

                if (nc != null)
                    this.nextCulinaryRecipesOfUser.add(nc.getCulinaryRecipeId());
            }

            if (this.nextCulinaryRecipesOfUser.size() > 0) {
                this.adapter.getDataByCulinaryRecipeId(this.nextCulinaryRecipesOfUser);

                this.userCulinaryRecipesBtn.setEnabled(false);
                this.userCulinaryRecipesBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
                this.favoriteCulinaryRecipesBtn.setEnabled(false);
                this.favoriteCulinaryRecipesBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.gray));
            } else {
                this.adapter.getDataByCulinaryRecipeId(new ArrayList<Integer>());

                this.userCulinaryRecipesBtn.setEnabled(true);
                this.userCulinaryRecipesBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue_200));
                this.favoriteCulinaryRecipesBtn.setEnabled(true);
                this.favoriteCulinaryRecipesBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue_200));

                Toast.makeText(HomeActivity.this, "Nenhuma receita encontrada.", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (this.nextCulinaryRecipesOfUser.size() == 0) {
                Toast.makeText(HomeActivity.this, "Nenhuma receita encontrada.", Toast.LENGTH_SHORT).show();
            }

            this.adapter.getDataByCulinaryRecipeId(new ArrayList<Integer>());

            this.userCulinaryRecipesBtn.setEnabled(true);
            this.userCulinaryRecipesBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue_200));
            this.favoriteCulinaryRecipesBtn.setEnabled(true);
            this.favoriteCulinaryRecipesBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue_200));
        }
    }

    @Override
    public void onResultNextCulinaryRecipe(RequestHelper result) {

    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
package com.uniritter.cdm.cookingrecipeapplication.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.uniritter.cdm.cookingrecipeapplication.R;
import com.uniritter.cdm.cookingrecipeapplication.adapter.CulinaryRecipesAdapter;
import com.uniritter.cdm.cookingrecipeapplication.helper.RequestHelper;
import com.uniritter.cdm.cookingrecipeapplication.model.ICulinaryRecipeModel;
import com.uniritter.cdm.cookingrecipeapplication.presenter.CulinaryRecipePresenter;
import com.uniritter.cdm.cookingrecipeapplication.presenter.CulinaryRecipePresenterContract;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements CulinaryRecipePresenterContract.View {
    private CulinaryRecipePresenterContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPreferences = getSharedPreferences("userCredentials", MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "");

        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText("Olá, " + userName + "!");

        this.presenter = new CulinaryRecipePresenter(this);

        List<ICulinaryRecipeModel> culinaryRecipes = this.presenter.getAllCulinaryRecipes();

        CulinaryRecipesAdapter adapter = new CulinaryRecipesAdapter(culinaryRecipes);
        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerViewCulinaryRecipes);
        rv.setHasFixedSize(true);

        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        findViewById(R.id.signOut).setOnClickListener(view -> {
            sharedPreferences.edit().clear().apply();

            startActivity(new Intent(HomeActivity.this, SignInActivity.class));
            finish();
        });

        findViewById(R.id.addCulinaryRecipe).setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, CulinaryRecipeAddActivity.class));
        });
    }

    @Override
    public void onCall() {

    }

    @Override
    public void onResult(RequestHelper result) {

    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
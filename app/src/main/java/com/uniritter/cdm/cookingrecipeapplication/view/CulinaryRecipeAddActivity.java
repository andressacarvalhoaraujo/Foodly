package com.uniritter.cdm.cookingrecipeapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.uniritter.cdm.cookingrecipeapplication.R;
import com.uniritter.cdm.cookingrecipeapplication.helper.RequestHelper;
import com.uniritter.cdm.cookingrecipeapplication.helper.RequestType;
import com.uniritter.cdm.cookingrecipeapplication.model.IDifficultyLevelModel;
import com.uniritter.cdm.cookingrecipeapplication.presenter.CulinaryRecipePresenter;
import com.uniritter.cdm.cookingrecipeapplication.presenter.CulinaryRecipePresenterContract;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class CulinaryRecipeAddActivity extends AppCompatActivity implements CulinaryRecipePresenterContract.View {
    private static String TAG = "CulinaryRecipeAddAct";
    private CulinaryRecipePresenterContract.Presenter presenter;
    private MaterialButton btnCreate;
    private int userId, difficultyLevelId, preparationTime, yield, totalCalories;
    private String title, description, ingredients, preparationMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culinary_recipe_add);

        SharedPreferences sharedPreferences = getSharedPreferences("userCredentials", MODE_PRIVATE);
        this.userId = sharedPreferences.getInt("userId", 0);

        this.presenter = new CulinaryRecipePresenter(this);

        ArrayList<String> dln = this.getAllDifficultyLevels();
        Spinner spinnerDifficultyLevel = (Spinner) findViewById (R.id.difficultyLevel);
        ArrayAdapter<String> adapterDifficultyLevel = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dln);
        spinnerDifficultyLevel.setAdapter(adapterDifficultyLevel);
        spinnerDifficultyLevel.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        difficultyLevelId = 0;
                        break;
                    case 1:
                        difficultyLevelId = 1;
                        break;
                    case 2:
                        difficultyLevelId = 2;
                        break;
                    case 3:
                        difficultyLevelId = 3;
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        findViewById(R.id.back).setOnClickListener(view -> {
            startActivity(new Intent(CulinaryRecipeAddActivity.this, HomeActivity.class));
            finish();
        });


        this.btnCreate = findViewById(R.id.create);
        this.btnCreate.setOnClickListener(view -> {
            this.title = ((EditText) findViewById(R.id.title)).getText().toString();
            this.description = ((EditText) findViewById(R.id.description)).getText().toString();
            this.ingredients = ((EditText) findViewById(R.id.ingredients)).getText().toString();
            this.preparationMode = ((EditText) findViewById(R.id.preparationMode)).getText().toString();
            String pt = ((EditText) findViewById(R.id.preparationTime)).getText().toString();
            this.preparationTime = pt != null && !TextUtils.isEmpty(pt) ? Integer.parseInt(pt) : 0;
            String y = ((EditText) findViewById(R.id.yield)).getText().toString();
            this.yield = y != null && !TextUtils.isEmpty(y) ? Integer.parseInt(y) : 0;
            String tc = ((EditText) findViewById(R.id.totalCalories)).getText().toString();
            this.totalCalories = tc != null && !TextUtils.isEmpty(tc) ? Integer.parseInt(tc) : 0;

            this.btnCreate.setText("Aguarde...");
            this.btnCreate.setEnabled(false);

            try {
                onCallCulinaryRecipe();
            } catch (JSONException e) {
                Toast.makeText(CulinaryRecipeAddActivity.this, "Ocorreu um erro inesperado! Tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error on add culinary recipe! Returned message: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onCallCulinaryRecipe() throws JSONException {
        this.presenter.addCulinaryRecipe(this.userId, this.title, this.description,
                this.ingredients, this.preparationMode, this.difficultyLevelId,
                this.preparationTime, this.yield, this.totalCalories);
    }

    @Override
    public void onResultCulinaryRecipe(RequestHelper result) {
        if (result.type == RequestType.OK) {
            Log.d(TAG, "Add culinary recipe success.");
            Toast.makeText(CulinaryRecipeAddActivity.this, "Receita cadastrada com sucesso!", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(() -> {
                startActivity(new Intent(CulinaryRecipeAddActivity.this, SignInActivity.class));
                finish();
            }, 5000);
        } else
        {
            this.btnCreate.setText("Cadastrar");
            this.btnCreate.setEnabled(true);

            Log.e(TAG, "Error on add culinary recipe! Returned message: " + result.errorMessage);
            Toast.makeText(CulinaryRecipeAddActivity.this, "Ocorreu um erro ao cadastrar a receita! " + result.errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCallFavoriteCulinaryRecipe() throws JSONException {

    }

    @Override
    public void onResultFavoriteCulinaryRecipe(RequestHelper result) {

    }

    @Override
    public void onCallNextCulinaryRecipe() throws JSONException {

    }

    @Override
    public void onResultNextCulinaryRecipe(RequestHelper result) {

    }

    private ArrayList<String> getAllDifficultyLevels() {
        ArrayList<String> dln = new ArrayList<>();
        dln.add("Selecione a dificuldade");

        List<IDifficultyLevelModel> d = this.presenter.getAllDifficultyLevels();

        for (IDifficultyLevelModel dl : d) {
            dln.add(dl.getDifficultyLevelName());
        }

        return dln;
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
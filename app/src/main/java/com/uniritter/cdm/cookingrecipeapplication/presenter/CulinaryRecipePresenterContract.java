package com.uniritter.cdm.cookingrecipeapplication.presenter;

import android.app.Activity;

import com.uniritter.cdm.cookingrecipeapplication.helper.RequestHelper;
import com.uniritter.cdm.cookingrecipeapplication.model.ICulinaryRecipeModel;
import com.uniritter.cdm.cookingrecipeapplication.model.IDifficultyLevelModel;

import org.json.JSONException;

import java.util.List;

public class CulinaryRecipePresenterContract {
    public interface View {
        void onCall() throws JSONException;

        void onResult(RequestHelper result);

        Activity getActivity();
    }

    public interface Presenter {
        List<ICulinaryRecipeModel> getAllCulinaryRecipes();

        void addCulinaryRecipe(int userId, String title, String description, String ingredients, String preparationMode, int difficultyLevelId, int preparationTime, int yield, int totalCalories) throws JSONException;

        IDifficultyLevelModel getDifficultyLevelById(int difficultyLevelId);

        List<IDifficultyLevelModel> getAllDifficultyLevels();
    }
}

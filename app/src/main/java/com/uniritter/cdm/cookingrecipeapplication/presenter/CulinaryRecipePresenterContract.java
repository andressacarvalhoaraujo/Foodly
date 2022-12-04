package com.uniritter.cdm.cookingrecipeapplication.presenter;

import android.app.Activity;

import com.uniritter.cdm.cookingrecipeapplication.helper.RequestHelper;
import com.uniritter.cdm.cookingrecipeapplication.model.ICulinaryRecipeModel;
import com.uniritter.cdm.cookingrecipeapplication.model.IDifficultyLevelModel;
import com.uniritter.cdm.cookingrecipeapplication.model.IFavoriteCulinaryRecipeModel;
import com.uniritter.cdm.cookingrecipeapplication.model.INextCulinaryRecipeModel;

import org.json.JSONException;

import java.util.List;

public class CulinaryRecipePresenterContract {
    public interface View {
        void onCallCulinaryRecipe() throws JSONException;

        void onResultCulinaryRecipe(RequestHelper result);

        void onCallFavoriteCulinaryRecipe() throws JSONException;

        void onResultFavoriteCulinaryRecipe(RequestHelper result);

        void onCallNextCulinaryRecipe() throws JSONException;

        void onResultNextCulinaryRecipe(RequestHelper result);

        Activity getActivity();
    }

    public interface Presenter {
        List<ICulinaryRecipeModel> getAllCulinaryRecipes();

        void addCulinaryRecipe(int userId, String title, String description, String ingredients, String preparationMode, int difficultyLevelId, int preparationTime, int yield, int totalCalories) throws JSONException;

        IDifficultyLevelModel getDifficultyLevelById(int difficultyLevelId);

        List<IDifficultyLevelModel> getAllDifficultyLevels();

        IFavoriteCulinaryRecipeModel getFavoriteCulinaryRecipeByUserIdAndCulinaryRecipeId(int userId, int culinaryRecipeId);

        List<IFavoriteCulinaryRecipeModel> getFavoriteCulinaryRecipesByUserId(int userId);

        INextCulinaryRecipeModel getNextCulinaryRecipeByUserIdAndCulinaryRecipeId(int userId, int culinaryRecipeId);

        List<INextCulinaryRecipeModel> getNextCulinaryRecipesByUserId(int userId);

        void addFavoriteCulinaryRecipe(int userId, int culinaryRecipeId) throws JSONException;

        void deleteFavoriteCulinaryRecipe(int favoriteCulinaryRecipeId);

        void addNextCulinaryRecipe(int userId, int culinaryRecipeId) throws JSONException;

        void deleteNextCulinaryRecipe(int nextCulinaryRecipeId);
    }
}

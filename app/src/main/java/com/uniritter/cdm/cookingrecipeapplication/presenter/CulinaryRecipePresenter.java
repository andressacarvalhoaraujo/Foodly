package com.uniritter.cdm.cookingrecipeapplication.presenter;

import com.uniritter.cdm.cookingrecipeapplication.helper.RequestHelper;
import com.uniritter.cdm.cookingrecipeapplication.model.ICulinaryRecipeModel;
import com.uniritter.cdm.cookingrecipeapplication.model.IDifficultyLevelModel;
import com.uniritter.cdm.cookingrecipeapplication.model.IFavoriteCulinaryRecipeModel;
import com.uniritter.cdm.cookingrecipeapplication.model.INextCulinaryRecipeModel;
import com.uniritter.cdm.cookingrecipeapplication.repository.CulinaryRecipeRepository;
import com.uniritter.cdm.cookingrecipeapplication.repository.DifficultyLevelRepository;
import com.uniritter.cdm.cookingrecipeapplication.repository.FavoriteCulinaryRecipeRepository;
import com.uniritter.cdm.cookingrecipeapplication.repository.NextCulinaryRecipeRepository;

import org.json.JSONException;

import java.util.List;

public class CulinaryRecipePresenter implements CulinaryRecipePresenterContract.Presenter, ICulinaryRecipePresenter {
    private CulinaryRecipePresenterContract.View view;
    private CulinaryRecipeRepository culinaryRecipeRepository;
    private DifficultyLevelRepository difficultyLevelRepository;
    private FavoriteCulinaryRecipeRepository favoriteCulinaryRecipeRepository;
    private NextCulinaryRecipeRepository nextCulinaryRecipeRepository;

    public CulinaryRecipePresenter(CulinaryRecipePresenterContract.View view) {
        this.view = view;
        this.culinaryRecipeRepository = CulinaryRecipeRepository.getInstance(view.getActivity());
        this.difficultyLevelRepository = DifficultyLevelRepository.getInstance(view.getActivity());
        this.favoriteCulinaryRecipeRepository = FavoriteCulinaryRecipeRepository.getInstance(view.getActivity());
        this.nextCulinaryRecipeRepository = NextCulinaryRecipeRepository.getInstance(view.getActivity());
    }

    @Override
    public List<ICulinaryRecipeModel> getAllCulinaryRecipes() {
        return this.culinaryRecipeRepository.getAllCulinaryRecipes();
    }

    @Override
    public IDifficultyLevelModel getDifficultyLevelById(int difficultyLevelId) {
        return this.difficultyLevelRepository.getDifficultyLevelById(difficultyLevelId);
    }

    @Override
    public List<IDifficultyLevelModel> getAllDifficultyLevels() {
        return this.difficultyLevelRepository.getAllDifficultyLevels();
    }

    @Override
    public IFavoriteCulinaryRecipeModel getFavoriteCulinaryRecipeByUserIdAndCulinaryRecipeId(int userId, int culinaryRecipeId) {
        return this.favoriteCulinaryRecipeRepository.getFavoriteCulinaryRecipeByUserIdAndCulinaryRecipeId(userId, culinaryRecipeId);
    }

    @Override
    public List<IFavoriteCulinaryRecipeModel> getFavoriteCulinaryRecipesByUserId(int userId) {
        return this.favoriteCulinaryRecipeRepository.getFavoriteCulinaryRecipesByUserId(userId);
    }

    @Override
    public INextCulinaryRecipeModel getNextCulinaryRecipeByUserIdAndCulinaryRecipeId(int userId, int culinaryRecipeId) {
        return this.nextCulinaryRecipeRepository.getNextCulinaryRecipeByUserIdAndCulinaryRecipeId(userId, culinaryRecipeId);
    }

    @Override
    public List<INextCulinaryRecipeModel> getNextCulinaryRecipesByUserId(int userId) {
        return this.nextCulinaryRecipeRepository.getNextCulinaryRecipesByUserId(userId);
    }

    @Override
    public void addCulinaryRecipe(int userId, String title, String description, String ingredients, String preparationMode, int difficultyLevelId, int preparationTime, int yield, int totalCalories) throws JSONException {
        this.culinaryRecipeRepository.addCulinaryRecipe(userId, title, description, ingredients, preparationMode, difficultyLevelId, preparationTime, yield, totalCalories, this);
    }

    @Override
    public void addFavoriteCulinaryRecipe(int userId, int culinaryRecipeId) throws JSONException {
        this.favoriteCulinaryRecipeRepository.addFavoriteCulinaryRecipe(userId, culinaryRecipeId, this);
    }

    @Override
    public void addNextCulinaryRecipe(int userId, int culinaryRecipeId) throws JSONException {
        this.nextCulinaryRecipeRepository.addNextCulinaryRecipe(userId, culinaryRecipeId, this);
    }

    @Override
    public void deleteFavoriteCulinaryRecipe(int favoriteCulinaryRecipeId) {
        this.favoriteCulinaryRecipeRepository.deleteFavoriteCulinaryRecipe(favoriteCulinaryRecipeId, this);
    }

    @Override
    public void deleteNextCulinaryRecipe(int nextCulinaryRecipeId) {
        this.nextCulinaryRecipeRepository.deleteNextCulinaryRecipe(nextCulinaryRecipeId, this);
    }

    @Override
    public void onResultCulinaryRecipe(RequestHelper requestHelper) {
        this.view.onResultCulinaryRecipe(requestHelper);
    }

    @Override
    public void onResultFavoriteCulinaryRecipe(RequestHelper requestHelper) {
        this.view.onResultFavoriteCulinaryRecipe(requestHelper);
    }

    @Override
    public void onResultNextCulinaryRecipe(RequestHelper requestHelper) {
        this.view.onResultNextCulinaryRecipe(requestHelper);
    }
}

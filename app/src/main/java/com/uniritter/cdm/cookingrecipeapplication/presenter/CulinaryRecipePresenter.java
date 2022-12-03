package com.uniritter.cdm.cookingrecipeapplication.presenter;

import com.uniritter.cdm.cookingrecipeapplication.helper.RequestHelper;
import com.uniritter.cdm.cookingrecipeapplication.model.ICulinaryRecipeModel;
import com.uniritter.cdm.cookingrecipeapplication.model.IDifficultyLevelModel;
import com.uniritter.cdm.cookingrecipeapplication.repository.CulinaryRecipeRepository;
import com.uniritter.cdm.cookingrecipeapplication.repository.DifficultyLevelRepository;

import org.json.JSONException;

import java.util.List;

public class CulinaryRecipePresenter implements CulinaryRecipePresenterContract.Presenter, ICulinaryRecipePresenter {
    private CulinaryRecipePresenterContract.View view;
    private CulinaryRecipeRepository culinaryRecipeRepository;
    private DifficultyLevelRepository difficultyLevelRepository;

    public CulinaryRecipePresenter(CulinaryRecipePresenterContract.View view) {
        this.view = view;
        this.culinaryRecipeRepository = CulinaryRecipeRepository.getInstance(view.getActivity());
        this.difficultyLevelRepository = DifficultyLevelRepository.getInstance(view.getActivity());
    }

    @Override
    public List<ICulinaryRecipeModel> getAllCulinaryRecipes() {
        return this.culinaryRecipeRepository.getAllCulinaryRecipes();
    }

    @Override
    public void addCulinaryRecipe(int userId, String title, String description, String ingredients, String preparationMode, int difficultyLevelId, int preparationTime, int yield, int totalCalories) throws JSONException {
        this.culinaryRecipeRepository.addCulinaryRecipe(userId, title, description, ingredients, preparationMode, difficultyLevelId, preparationTime, yield, totalCalories, this);
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
    public void onResult(RequestHelper requestHelper) {
        this.view.onResult(requestHelper);
    }
}

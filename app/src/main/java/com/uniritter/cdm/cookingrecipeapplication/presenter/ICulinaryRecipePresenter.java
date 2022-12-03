package com.uniritter.cdm.cookingrecipeapplication.presenter;

import com.uniritter.cdm.cookingrecipeapplication.helper.RequestHelper;

public interface ICulinaryRecipePresenter {
    void onResultCulinaryRecipe(RequestHelper requestHelper);

    void onResultFavoriteCulinaryRecipe(RequestHelper requestHelper);
}

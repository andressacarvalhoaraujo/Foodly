package com.uniritter.cdm.cookingrecipeapplication.model;

public interface IFavoriteCulinaryRecipeModel {
    int getFavoriteCulinaryRecipeId();

    int getUserId();

    int getCulinaryRecipeId();

    void setFavoriteCulinaryRecipeId(int favoriteCulinaryRecipeId);

    void setUserId(int userId);

    void setCulinaryRecipeId(int culinaryRecipeId);
}

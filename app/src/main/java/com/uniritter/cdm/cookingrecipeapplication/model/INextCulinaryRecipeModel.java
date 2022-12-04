package com.uniritter.cdm.cookingrecipeapplication.model;

public interface INextCulinaryRecipeModel {
    int getNextCulinaryRecipeId();

    int getUserId();

    int getCulinaryRecipeId();

    void setNextCulinaryRecipeId(int nextCulinaryRecipeId);

    void setUserId(int userId);

    void setCulinaryRecipeId(int culinaryRecipeId);
}

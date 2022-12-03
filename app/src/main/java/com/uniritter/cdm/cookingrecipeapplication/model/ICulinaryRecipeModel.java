package com.uniritter.cdm.cookingrecipeapplication.model;

public interface ICulinaryRecipeModel {
    int getCulinaryRecipeId();

    void setCulinaryRecipeId(int culinaryRecipeId);

    int getUserId();

    void setUserId(int userId);

    String getTitle();

    void setTitle(String title);

    String getDescription();

    void setDescription(String description);

    String getIngredients();

    void setIngredients(String ingredients);

    String getPreparationMode();

    void setPreparationMode(String preparationMode);

    int getDifficultyLevelId();

    void setDifficultyLevelId(int difficultyLevelId);

    int getPreparationTime();

    void setPreparationTime(int preparationTime);

    int getYield();

    void setYield(int yield);

    int getTotalCalories();

    void setTotalCalories(int totalCalories);
}

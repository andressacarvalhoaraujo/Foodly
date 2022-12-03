package com.uniritter.cdm.cookingrecipeapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CulinaryRecipeModel implements ICulinaryRecipeModel, Parcelable {
    private int culinaryRecipeId;
    private int userId;
    private String title;
    private String description;
    private String ingredients;
    private String preparationMode;
    private int difficultyLevelId;
    private int preparationTime;
    private int yield;
    private int totalCalories;

    public CulinaryRecipeModel(int culinaryRecipeId, int userId, String title, String description, String ingredients, String preparationMode, int difficultyLevelId, int preparationTime, int yield, int totalCalories) {
        this.culinaryRecipeId = culinaryRecipeId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.preparationMode = preparationMode;
        this.difficultyLevelId = difficultyLevelId;
        this.preparationTime = preparationTime;
        this.yield = yield;
        this.totalCalories = totalCalories;
    }


    protected CulinaryRecipeModel(Parcel in) {
        culinaryRecipeId = in.readInt();
        userId = in.readInt();
        title = in.readString();
        description = in.readString();
        ingredients = in.readString();
        preparationMode = in.readString();
        difficultyLevelId = in.readInt();
        preparationTime = in.readInt();
        yield = in.readInt();
        totalCalories = in.readInt();
    }

    public static final Creator<CulinaryRecipeModel> CREATOR = new Creator<CulinaryRecipeModel>() {
        @Override
        public CulinaryRecipeModel createFromParcel(Parcel in) {
            return new CulinaryRecipeModel(in);
        }

        @Override
        public CulinaryRecipeModel[] newArray(int size) {
            return new CulinaryRecipeModel[size];
        }
    };

    @Override
    public int getCulinaryRecipeId() {
        return this.culinaryRecipeId;
    }

    @Override
    public void setCulinaryRecipeId(int culinaryRecipeId) {
        this.culinaryRecipeId = culinaryRecipeId;
    }

    @Override
    public int getUserId() {
        return this.userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getIngredients() {
        return this.ingredients;
    }

    @Override
    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String getPreparationMode() {
        return this.preparationMode;
    }

    @Override
    public void setPreparationMode(String preparationMode) {
        this.preparationMode = preparationMode;
    }

    @Override
    public int getDifficultyLevelId() {
        return this.difficultyLevelId;
    }

    @Override
    public void setDifficultyLevelId(int difficultyLevelId) {
        this.difficultyLevelId = difficultyLevelId;
    }

    @Override
    public int getPreparationTime() {
        return this.preparationTime;
    }

    @Override
    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    @Override
    public int getYield() {
        return this.yield;
    }

    @Override
    public void setYield(int yield) {
        this.yield = yield;
    }

    @Override
    public int getTotalCalories() {
        return this.totalCalories;
    }

    @Override
    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(culinaryRecipeId);
        parcel.writeInt(userId);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(ingredients);
        parcel.writeString(preparationMode);
        parcel.writeInt(difficultyLevelId);
        parcel.writeInt(preparationTime);
        parcel.writeInt(yield);
        parcel.writeInt(totalCalories);
    }
}


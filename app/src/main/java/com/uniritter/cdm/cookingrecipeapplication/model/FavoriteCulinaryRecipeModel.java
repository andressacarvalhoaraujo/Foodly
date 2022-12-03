package com.uniritter.cdm.cookingrecipeapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FavoriteCulinaryRecipeModel implements IFavoriteCulinaryRecipeModel, Parcelable {
    private int favoriteCulinaryRecipeId;
    private int userId;
    private int culinaryRecipeId;

    public FavoriteCulinaryRecipeModel(int favoriteCulinaryRecipeId, int userId, int culinaryRecipeId) {
        this.favoriteCulinaryRecipeId = favoriteCulinaryRecipeId;
        this.userId = userId;
        this.culinaryRecipeId = culinaryRecipeId;
    }

    protected FavoriteCulinaryRecipeModel(Parcel in) {
        favoriteCulinaryRecipeId = in.readInt();
        userId = in.readInt();
        culinaryRecipeId = in.readInt();
    }

    public static final Creator<FavoriteCulinaryRecipeModel> CREATOR = new Creator<FavoriteCulinaryRecipeModel>() {
        @Override
        public FavoriteCulinaryRecipeModel createFromParcel(Parcel in) {
            return new FavoriteCulinaryRecipeModel(in);
        }

        @Override
        public FavoriteCulinaryRecipeModel[] newArray(int size) {
            return new FavoriteCulinaryRecipeModel[size];
        }
    };

    @Override
    public int getFavoriteCulinaryRecipeId() {
        return this.favoriteCulinaryRecipeId;
    }

    @Override
    public int getUserId() {
        return this.userId;
    }

    @Override
    public int getCulinaryRecipeId() {
        return this.culinaryRecipeId;
    }

    @Override
    public void setFavoriteCulinaryRecipeId(int favoriteCulinaryRecipeId) {
        this.favoriteCulinaryRecipeId = favoriteCulinaryRecipeId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public void setCulinaryRecipeId(int culinaryRecipeId) {
        this.culinaryRecipeId = culinaryRecipeId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(favoriteCulinaryRecipeId);
        parcel.writeInt(userId);
        parcel.writeInt(culinaryRecipeId);
    }
}


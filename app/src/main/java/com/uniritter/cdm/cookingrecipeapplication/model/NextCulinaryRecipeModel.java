package com.uniritter.cdm.cookingrecipeapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class NextCulinaryRecipeModel implements INextCulinaryRecipeModel, Parcelable {
    private int nextCulinaryRecipeId;
    private int userId;
    private int culinaryRecipeId;

    public NextCulinaryRecipeModel(int nextCulinaryRecipeId, int userId, int culinaryRecipeId) {
        this.nextCulinaryRecipeId = nextCulinaryRecipeId;
        this.userId = userId;
        this.culinaryRecipeId = culinaryRecipeId;
    }

    protected NextCulinaryRecipeModel(Parcel in) {
        nextCulinaryRecipeId = in.readInt();
        userId = in.readInt();
        culinaryRecipeId = in.readInt();
    }

    public static final Creator<NextCulinaryRecipeModel> CREATOR = new Creator<NextCulinaryRecipeModel>() {
        @Override
        public NextCulinaryRecipeModel createFromParcel(Parcel in) {
            return new NextCulinaryRecipeModel(in);
        }

        @Override
        public NextCulinaryRecipeModel[] newArray(int size) {
            return new NextCulinaryRecipeModel[size];
        }
    };

    @Override
    public int getNextCulinaryRecipeId() {
        return this.nextCulinaryRecipeId;
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
    public void setNextCulinaryRecipeId(int nextCulinaryRecipeId) {
        this.nextCulinaryRecipeId = nextCulinaryRecipeId;
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
        parcel.writeInt(nextCulinaryRecipeId);
        parcel.writeInt(userId);
        parcel.writeInt(culinaryRecipeId);
    }
}


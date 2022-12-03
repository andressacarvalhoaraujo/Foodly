package com.uniritter.cdm.cookingrecipeapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DifficultyLevelModel implements IDifficultyLevelModel, Parcelable {
    private int difficultyLevelId;
    private String difficultyLevelName;

    public DifficultyLevelModel(int difficultyLevelId, String difficultyLevelName) {
        this.difficultyLevelId = difficultyLevelId;
        this.difficultyLevelName = difficultyLevelName;
    }

    protected DifficultyLevelModel(Parcel in) {
        difficultyLevelId = in.readInt();
        difficultyLevelName = in.readString();
    }

    public static final Creator<DifficultyLevelModel> CREATOR = new Creator<DifficultyLevelModel>() {
        @Override
        public DifficultyLevelModel createFromParcel(Parcel in) {
            return new DifficultyLevelModel(in);
        }

        @Override
        public DifficultyLevelModel[] newArray(int size) {
            return new DifficultyLevelModel[size];
        }
    };

    @Override
    public int getDifficultyLevelId() {
        return this.difficultyLevelId;
    }

    @Override
    public String getDifficultyLevelName() {
        return this.difficultyLevelName;
    }

    @Override
    public void setDifficultyLevelId(int difficultyLevelId) {
        this.difficultyLevelId = difficultyLevelId;
    }

    @Override
    public void setDifficultyLevelName(String difficultyLevelName) {
        this.difficultyLevelName = difficultyLevelName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(difficultyLevelId);
        parcel.writeString(difficultyLevelName);
    }
}


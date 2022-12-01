package com.uniritter.cdm.cookingrecipeapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserModel implements IUserModel, Parcelable {
    private int userId;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userPerfilImage;
    private String userBiography;

    public UserModel(int userId, String userName, String userEmail, String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public UserModel(int userId, String userName, String userEmail, String userPassword, String userPerfilImage, String userBiography) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userPerfilImage = userPerfilImage;
        this.userBiography = userBiography;
    }

    protected UserModel(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
        userEmail = in.readString();
        userPassword = in.readString();
        userPerfilImage = in.readString();
        userBiography = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    @Override
    public int getUserId() {
        return this.userId;
    }

    @Override
    public String getUserName() {
        return this.userName;
    }

    @Override
    public String getUserEmail() {
        return this.userEmail;
    }

    @Override
    public String getUserPassword() {
        return this.userPassword;
    }

    @Override
    public String getUserPerfilImage() { return this.userPerfilImage; }

    @Override
    public String getUserBiography() { return this.userBiography; }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public void setUserPerfilImage(String userPerfilImage) {
        this.userPerfilImage = userPerfilImage;
    }

    @Override
    public void setUserBiography(String userBiography) {
        this.userBiography = userBiography;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(userId);
        parcel.writeString(userName);
        parcel.writeString(userEmail);
        parcel.writeString(userPassword);
        parcel.writeString(userPerfilImage);
        parcel.writeString(userBiography);
    }
}


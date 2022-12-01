package com.uniritter.cdm.cookingrecipeapplication.model;

public interface IUserModel {
    int getUserId();

    String getUserName();

    String getUserEmail();

    String getUserPassword();

    String getUserPerfilImage();

    String getUserBiography();

    void setUserId(int userId);

    void setUserName(String userName);

    void setUserEmail(String userEmail);

    void setUserPassword(String userPassword);

    void setUserPerfilImage(String userPerfilImage);

    void setUserBiography(String userBiography);
}

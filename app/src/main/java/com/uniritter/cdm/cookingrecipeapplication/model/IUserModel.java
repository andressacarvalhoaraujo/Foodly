package com.uniritter.cdm.cookingrecipeapplication.model;

public interface IUserModel {
    int getUserId();

    String getUserName();

    String getUserEmail();

    String getUserPassword();

    void setUserId(int userId);

    void setUserName(String userName);

    void setUserEmail(String userEmail);

    void setUserPassword(String userPassword);
}

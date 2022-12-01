package com.uniritter.cdm.cookingrecipeapplication.presenter;

import android.app.Activity;

import com.uniritter.cdm.cookingrecipeapplication.helper.RequestHelper;
import com.uniritter.cdm.cookingrecipeapplication.model.IUserModel;

public class UserPresenterContract {
    public interface View {
        void onCall();

        void onResult(RequestHelper result);

        void saveUserCredentials(int userId, String userName, String userEmail, String userPassword);

        Activity getActivity();
    }

    public interface Presenter {
        IUserModel getUserByEmail(String userEmail);

        void doLogin(String userEmail, String userPassword);

        void addUser(String userName, String userEmail, String userPassword, String userConfirmPassword);
    }
}

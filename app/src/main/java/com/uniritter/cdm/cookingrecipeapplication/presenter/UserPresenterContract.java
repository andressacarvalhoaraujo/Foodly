package com.uniritter.cdm.cookingrecipeapplication.presenter;

import android.app.Activity;

import com.uniritter.cdm.cookingrecipeapplication.helper.RequestHelper;
import com.uniritter.cdm.cookingrecipeapplication.model.IUserModel;

public class UserPresenterContract {
    public interface View {
        void onCall();

        void onResult(RequestHelper result);

        Activity getActivity();
    }

    public interface Presenter {
        IUserModel getUserById(int userId);

        IUserModel getUserByEmail(String userEmail);

        void doLogin(String userEmail, String userPassword);

        void addUser(String userName, String userEmail, String userPassword, String userConfirmPassword);
    }
}

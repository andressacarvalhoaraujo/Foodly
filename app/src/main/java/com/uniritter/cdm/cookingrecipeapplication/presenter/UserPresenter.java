package com.uniritter.cdm.cookingrecipeapplication.presenter;

import com.uniritter.cdm.cookingrecipeapplication.helper.RequestHelper;
import com.uniritter.cdm.cookingrecipeapplication.model.IUserModel;
import com.uniritter.cdm.cookingrecipeapplication.repository.UserRepository;

public class UserPresenter implements UserPresenterContract.Presenter, IUserPresenter {
    private UserPresenterContract.View view;
    private UserRepository repository;

    public UserPresenter(UserPresenterContract.View view) {
        this.view = view;
        this.repository = UserRepository.getInstance(view.getActivity());
    }

    @Override
    public IUserModel getUserByEmail(String userEmail) {
        return this.repository.getUserByEmail(userEmail);
    }

    @Override
    public void doLogin(String userEmail, String userPassword) {
        this.view.onResult(this.repository.validateUserCredentials(userEmail, userPassword));
    }

    @Override
    public void addUser(String userName, String userEmail, String userPassword, String userConfirmPassword) {
        this.repository.addUser(userName, userEmail, userPassword, userConfirmPassword, this);
    }

    @Override
    public void onResult(RequestHelper requestHelper) {
        this.view.onResult(requestHelper);
    }
}

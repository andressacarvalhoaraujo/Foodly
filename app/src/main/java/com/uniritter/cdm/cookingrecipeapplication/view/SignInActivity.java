package com.uniritter.cdm.cookingrecipeapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.uniritter.cdm.cookingrecipeapplication.R;
import com.uniritter.cdm.cookingrecipeapplication.helper.RequestHelper;
import com.uniritter.cdm.cookingrecipeapplication.helper.RequestType;
import com.uniritter.cdm.cookingrecipeapplication.model.IUserModel;
import com.uniritter.cdm.cookingrecipeapplication.presenter.UserPresenter;
import com.uniritter.cdm.cookingrecipeapplication.presenter.UserPresenterContract;

public class SignInActivity extends AppCompatActivity implements UserPresenterContract.View {
    private static String TAG = "SignInActivity";
    private String userEmail, userPassword;
    private UserPresenterContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        findViewById(R.id.signUpOption).setOnClickListener(view -> {
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        });

        this.presenter = new UserPresenter(this);

        findViewById(R.id.signIn).setOnClickListener(view -> {
            this.userEmail = ((EditText)findViewById(R.id.userEmail)).getText().toString();
            this.userPassword = ((EditText)findViewById(R.id.userPassword)).getText().toString();

            onCall();
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = getSharedPreferences("userCredentials", MODE_PRIVATE);
        if (sharedPreferences.contains("userId")) {
            startActivity(new Intent(SignInActivity.this, HomeActivity.class));
            finish();
        }
    }

    @Override
    public void onCall() {
        this.presenter.doLogin(this.userEmail, this.userPassword);
    }

    @Override
    public void onResult(RequestHelper result) {
        if (result.type == RequestType.OK) {
            IUserModel user = this.presenter.getUserByEmail(this.userEmail);

            this.saveUserCredentials(user.getUserId(), user.getUserName(), user.getUserEmail(), user.getUserPassword());

            Log.d(TAG, "Sign in success!");

            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
            intent.putExtra("userObject", (Parcelable) user);
            startActivity(intent);
            finish();
        } else
        {
            Log.e(TAG, "Error on sign in! Returned message: " + result.errorMessage);
            Toast.makeText(SignInActivity.this, "O login falhou! " + result.errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserCredentials(int userId, String userName, String userEmail, String userPassword) {
        SharedPreferences sharedPreferences = getSharedPreferences("userCredentials", MODE_PRIVATE);

        if (userId != 0
                && userName != null && !TextUtils.isEmpty(userName)
                && userEmail != null && !TextUtils.isEmpty(userEmail)
                && userPassword != null && !TextUtils.isEmpty(userPassword)) {
            SharedPreferences.Editor edit = sharedPreferences.edit();

            edit.putInt("userId", userId);
            edit.putString("userName", userName);
            edit.putString("userEmail", userEmail);
            edit.putString("userPassword", userPassword);

            edit.apply();
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
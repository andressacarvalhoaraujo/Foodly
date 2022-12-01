package com.uniritter.cdm.cookingrecipeapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.uniritter.cdm.cookingrecipeapplication.R;
import com.uniritter.cdm.cookingrecipeapplication.repository.UserRepository;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        UserRepository.getInstance(this);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        new Handler().postDelayed(() -> {
            startActivity(new Intent(getBaseContext(), SignInActivity.class));
            finish();
        }, 5000);
    }
}
package com.uniritter.cdm.cookingrecipeapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.uniritter.cdm.cookingrecipeapplication.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPreferences = getSharedPreferences("userCredentials", MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "");

        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText("Olá, " + userName + "!");

        findViewById(R.id.signOut).setOnClickListener(view -> {
            sharedPreferences.edit().clear().apply();

            startActivity(new Intent(HomeActivity.this, SignInActivity.class));
            finish();
        });
    }
}
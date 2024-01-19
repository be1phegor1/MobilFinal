package com.example.finales.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finales.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    public void Login(View v){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    public void Register(View v){
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        finish();
    }



}
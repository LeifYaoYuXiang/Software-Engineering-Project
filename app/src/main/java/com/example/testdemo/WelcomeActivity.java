package com.example.testdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.trncic.library.DottedProgressBar;

public class WelcomeActivity extends AppCompatActivity {

    private  final int SPLASH_DISPLAY_LENGHT = 6000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        DottedProgressBar dottedProgressBar = findViewById(R.id.welcomeDottedProgress);
        dottedProgressBar.startProgress();

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }
}

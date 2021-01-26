package com.example.fihhuda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.fihhuda.home.HomeActivity;

public class SplahActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplahActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        },2000)
        ;
    }

    }


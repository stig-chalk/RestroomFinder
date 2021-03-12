package com.example.cs125;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import jp.wasabeef.blurry.Blurry;

public class MainActivity extends AppCompatActivity {
    private Button JumpNo;
    private Button JumpYes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        setContentView(R.layout.activity_main);
        JumpNo = (Button)findViewById(R.id.JumpNo);
        JumpNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginPage();
            }
        });

        JumpYes = (Button)findViewById(R.id.JumpYes);
        JumpYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchPage();
            }
        });


        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        Blurry.with(this)
                .radius(10)
                .sampling(8)
                .color(Color.argb(66, 255, 255, 0))
                .async()
                .onto(rootView);
    }

    public void openLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    public void openSearchPage() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}

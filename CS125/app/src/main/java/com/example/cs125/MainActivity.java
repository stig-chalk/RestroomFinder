package com.example.cs125;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button JumpNo;
    private Button JumpYes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                if(checkLogin()){
                    openRecommendationPage();
                }
                else{
                    openLoginPage();
                }
            }
        });

    }
    public void openLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    public void openRecommendationPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}

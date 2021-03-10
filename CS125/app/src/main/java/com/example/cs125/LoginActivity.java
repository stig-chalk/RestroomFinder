package com.example.cs125;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    String username,password;
    private ProgressBar loadingProgressBar;

    private EditText usernameEditText;
    private EditText passwordEditText;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.Password);
        Button login = findViewById(R.id.login);
        TextView signup = findViewById(R.id.signUp);

//        if(keep_login.equals("true")){
//            loadingProgressBar.setVisibility(View.VISIBLE);
//            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
//            startActivity(intent);
//        }
//        else if(keep_login.equals("false")){
//            Toast.makeText(this,"Please Sign In.", Toast.LENGTH_SHORT).show();
//        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameEditText.getText().toString().trim();
                password = passwordEditText.getText().toString().trim();
                check_valid(username,password);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public void check_valid(String username, String password) {
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;

        String url = "http://ec2-100-24-72-207.compute-1.amazonaws.com:8080/user/login";

        Uri.Builder builder = Uri.parse(url).buildUpon();
        builder.appendQueryParameter("email", username);
        builder.appendQueryParameter("password", password);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, builder.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loadingProgressBar = findViewById(R.id.loading);
                        loadingProgressBar.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(LoginActivity.this, RefrenceActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("login.error", error.toString());
                passwordEditText.setText("");
                usernameEditText.setText("");
            }
        });

        queue.add(request);
    }
}

package com.example.cs125;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {
    private Button SignUp;
    String Email,Password, Repassword;
    private EditText EmailInput;
    private EditText PasswordInput;
    private EditText RepasswordInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        EmailInput = findViewById(R.id.Email);
        PasswordInput = findViewById(R.id.Password);
        RepasswordInput = findViewById(R.id.repassword);
        SignUp = (Button)findViewById(R.id.SignUp);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email = EmailInput.getText().toString().trim();
//                check email address and password correctness
                if (!isUserNameValid(Email)){
                    Toast.makeText(SignUpActivity.this,"Email address is invalid. Try again.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Password = PasswordInput.getText().toString().trim();
                if (!isPasswordValid(Password)){
                    Toast.makeText(SignUpActivity.this,"Password must be >= 6 characters. Try again.", Toast.LENGTH_SHORT).show();
                    PasswordInput.setText("");
                    RepasswordInput.setText("");
                    return;
                }
                Repassword = RepasswordInput.getText().toString().trim();
                if (!Repassword.equals(Password)){
                    Toast.makeText(SignUpActivity.this,"Not the same password. Try again.", Toast.LENGTH_SHORT).show();
                    PasswordInput.setText("");
                    RepasswordInput.setText("");
                    return;
                }
                check_valid(Email,Password);
            }
        });
    }

    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    public void check_valid(String email, String password) {
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;

        String url = "http://ec2-100-24-72-207.compute-1.amazonaws.com:8080/user/regist";

//      Create parameter for url
        Uri.Builder builder = Uri.parse(url).buildUpon();
        builder.appendQueryParameter("email", email);
        builder.appendQueryParameter("password", password);

        //      Using POST request to get Json, have the same email if tag success is not true
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, builder.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String success = response.getString("success");
                            if (success.equals("true")){
//                                if sign up success then auto login
                                Login();
                            }
                            else{
                                String msg = response.getString("msg");
                                Toast.makeText(SignUpActivity.this,msg, Toast.LENGTH_SHORT).show();
                                EmailInput.setText("");
                                PasswordInput.setText("");
                                RepasswordInput.setText("");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("Sign up.error", error.toString());
                EmailInput.setText("");
                PasswordInput.setText("");
                RepasswordInput.setText("");
            }
        });

        queue.add(request);
    }

    public void Login() {
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;

        String url = "http://ec2-100-24-72-207.compute-1.amazonaws.com:8080/user/login";

//      Create parameter for url
        Uri.Builder builder = Uri.parse(url).buildUpon();
        builder.appendQueryParameter("email", Email);
        builder.appendQueryParameter("password", Password);


//      Using POST request to get Json, don't have the correct email and password if tag success is not true
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, builder.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Intent intent = new Intent(SignUpActivity.this, PrefrenceActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("Sign up.error", error.toString());
            }
        });
        queue.add(request);
    }

}

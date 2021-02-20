package com.example.cs125;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {
    private Button Submit;
    String Email,Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        final EditText EmailInput = findViewById(R.id.Email);
        final EditText PasswordInput = findViewById(R.id.Password);
        Submit = (Button)findViewById(R.id.Submit);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email = EmailInput.getText().toString().trim();
                if (!isUserNameValid(Email)){
                    EmailInput.setError("Email is invalid.");
                    return;
                }
                Password = PasswordInput.getText().toString().trim();
                if (!isPasswordValid(Password)){
                    PasswordInput.setError("Password must be >= 6 characters.");
                    return;
                }
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
}

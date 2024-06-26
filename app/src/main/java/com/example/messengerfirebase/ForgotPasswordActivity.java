package com.example.messengerfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPasswordActivity extends AppCompatActivity {

    private static final String EXTRA_EMAIL = "email";
    private EditText editTextEmail;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextEmail = findViewById(R.id.editTextEmail);
        resetButton = findViewById(R.id.buttonReset);

        String email = getIntent().getStringExtra(EXTRA_EMAIL);

        if (email != null) {
            editTextEmail.setText(email);
        }

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                // todo reset password
            }
        });
    }

    public static Intent newIntent(Context context, String email) {
        Intent intent = new Intent(context, ForgotPasswordActivity.class);
        intent.putExtra(EXTRA_EMAIL, email);
        return intent;
    }
}
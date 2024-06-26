package com.example.messengerfirebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPasswordActivity extends AppCompatActivity {

    private static final String EXTRA_EMAIL = "email";
    private EditText editTextEmail;
    private Button resetButton;
    private ResetPasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextEmail = findViewById(R.id.editTextEmail);
        resetButton = findViewById(R.id.buttonReset);
        viewModel = new ViewModelProvider(this).get(ResetPasswordViewModel.class);
        observeViewModel();

        String email = getIntent().getStringExtra(EXTRA_EMAIL);

        if (email != null) {
            editTextEmail.setText(email);
        }

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                // todo reset password
                viewModel.resetPassword(email);
            }
        });
    }

    private void observeViewModel() {
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null) {
                    Toast.makeText(
                                    ForgotPasswordActivity.this,
                                    errorMessage,
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
        viewModel.isSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean success) {
                if (success) {
                    Toast.makeText(
                                    ForgotPasswordActivity.this,
                                    R.string.reset_password_link_send,
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    public static Intent newIntent(Context context, String email) {
        Intent intent = new Intent(context, ForgotPasswordActivity.class);
        intent.putExtra(EXTRA_EMAIL, email);
        return intent;
    }
}
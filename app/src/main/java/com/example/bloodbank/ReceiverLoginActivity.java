package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ReceiverLoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail,editTextPassword;
    Button buttonLogin;

    TextView goToRegister;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_login);

        editTextEmail = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        buttonLogin = findViewById(R.id.buttonLogin);
        goToRegister = findViewById(R.id.goToRegister);

        goToRegister.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.goToRegister:
                registerReceiver();
                break;

            case R.id.buttonLogin:
                loginReceiver();
        }
    }

    private void loginReceiver() {
        progressDialog.show();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            Toast.makeText(ReceiverLoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }else
                        {
                            Toast.makeText(ReceiverLoginActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });

    }

    private void registerReceiver() {

        Intent intent = new Intent(ReceiverLoginActivity.this,ReceiverRegisterActivity.class);
        startActivity(intent);
        finish();
    }
}

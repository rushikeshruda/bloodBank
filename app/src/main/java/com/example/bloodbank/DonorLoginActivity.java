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

public class DonorLoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTextEmail,editTextPassword;
    Button buttonLogin;

    TextView goToRegister;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_login);

        editTextEmail = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        goToRegister = findViewById(R.id.goToRegister);

        buttonLogin = findViewById(R.id.buttonLogin);

        goToRegister.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser()!=null)
        {
            Intent intent = new Intent(DonorLoginActivity.this,DonorHome.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.goToRegister:
                registerDonor();
                break;

            case R.id.buttonLogin:
                loginDonor();
        }

    }

    private void loginDonor() {

        progressDialog.show();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            Toast.makeText(DonorLoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            Intent intent= new Intent(DonorLoginActivity.this,DonorHome.class);
                            startActivity(intent);
                        }else
                        {
                            Toast.makeText(DonorLoginActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });

    }

    private void registerDonor() {

        Intent i = new Intent(DonorLoginActivity.this,DonorRegisterActivity.class);
        startActivity(i);
        finish();

    }
}
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

public class ReceiverRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail,editTextPassword,editTextUsername;
    Button buttonRegister;

    TextView goToLogin;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_register);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        goToLogin = findViewById(R.id.goToLogin);

        buttonRegister = findViewById(R.id.buttonRegister);

        goToLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.goToLogin:
                Intent i = new Intent(ReceiverRegisterActivity.this,ReceiverLoginActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.buttonRegister:
                registerDonor();
        }

    }

    private void registerDonor() {
        progressDialog.show();

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            Toast.makeText(ReceiverRegisterActivity.this, "Receiver Registered Successfully", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                        else {
                            Toast.makeText(ReceiverRegisterActivity.this,""+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }
}

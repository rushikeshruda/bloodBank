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

import com.example.bloodbank.Modules.Donor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DonorRegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTextEmail,editTextPassword,editTextUsername,editTextBloodGroup,editTextMobileNo;
    Button buttonRegister;

    TextView goToLogin;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_register);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextBloodGroup = findViewById(R.id.editTextBloodGroup);
        editTextMobileNo = findViewById(R.id.editTextMobNo);

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
                Intent i = new Intent(DonorRegisterActivity.this,DonorLoginActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.buttonRegister:
                registerDonor();
        }
    }

    private void registerDonor() {
        progressDialog.show();

        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String username = editTextUsername.getText().toString().trim();
        final String bloodGroup = editTextBloodGroup.getText().toString().trim();
        final String mobileNo= editTextMobileNo.getText().toString().trim();



        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            databaseReference = FirebaseDatabase.getInstance().getReference("Donor");

                            String id = firebaseAuth.getUid();

                            String lastdonated="-";

                            Donor donor = new Donor(id,username,email,password,bloodGroup,mobileNo,lastdonated);

                            databaseReference.child(id).setValue(donor);

                            Toast.makeText(DonorRegisterActivity.this, "Donor Registered Successfully", Toast.LENGTH_LONG).show();
                            Intent intent= new Intent(DonorRegisterActivity.this,DonorHome.class);
                            startActivity(intent);
                            progressDialog.dismiss();
                        }
                        else {
                            Toast.makeText(DonorRegisterActivity.this,""+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }
}

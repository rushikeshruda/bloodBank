package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button buttonDonor,buttonReceiver;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonDonor = findViewById(R.id.buttonDonor);
        buttonReceiver = findViewById(R.id.buttonReceiver);
        buttonDonor.setOnClickListener(this);
        buttonReceiver.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.buttonDonor:

                Intent intent = new Intent(MainActivity.this,DonorLoginActivity.class);
                startActivity(intent);
                break;

            case R.id.buttonReceiver:

                Intent intent1 = new Intent(MainActivity.this,ReceiverLoginActivity.class);
                startActivity(intent1);
                break;
        }
    }
}

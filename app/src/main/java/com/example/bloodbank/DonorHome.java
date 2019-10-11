package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodbank.Modules.BloodBank;
import com.example.bloodbank.Modules.Donor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DonorHome extends AppCompatActivity implements View.OnClickListener {

    TextView textViewUsername,textViewEmail,textViewBloodGroup,textViewMobileNo,textViewLastDonated;
    Button buttonDonate;
    String bloodGroup,bloodBankId;
    int bloodAmount,days;

    Date endDate,startDate;

    DatabaseReference databaseReference,databaseReferenceBlood;
    Donor donor;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    Period period;
    static int flag =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_home);

        textViewUsername = findViewById(R.id.textViewUsername);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewBloodGroup = findViewById(R.id.textViewBloodGroup);
        textViewMobileNo = findViewById(R.id.textViewMobileNo);
        textViewLastDonated = findViewById(R.id.textViewLastDonated);

        buttonDonate = findViewById(R.id.buttonDonate);

        buttonDonate.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("Donor");
        databaseReferenceBlood = FirebaseDatabase.getInstance().getReference("BloodBank");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        final Query query = databaseReference;

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot donorSnapshot : dataSnapshot.getChildren())
                {
                    if (donorSnapshot.getKey().equals(firebaseAuth.getUid())) {
                        donor = donorSnapshot.getValue(Donor.class);


                        textViewUsername.append(donor.getUsername());
                        textViewEmail.append(donor.getEmail());
                        textViewBloodGroup.append(donor.getBloodGroup());
                        bloodGroup = donor.getBloodGroup();
                        textViewMobileNo.append(donor.getMobileNo());
                        textViewLastDonated.append(donor.getLastdonated());
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {



        switch(view.getId())
        {
            case R.id.buttonDonate:

                String lastDonatedDate = textViewLastDonated.getText().toString();
                String[] date = lastDonatedDate.split("\\- ");

                Date c = Calendar.getInstance().getTime();

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                String d = sdf.format(c);

                try {
                    endDate = sdf.parse(d);
                    startDate = sdf.parse("03/09/1998");

                } catch (ParseException e) {
                    e.printStackTrace();
                }




                if (date[1].equals("-")) {

                    days=31;

                }else {

                    long diff = startDate.getTime() - endDate.getTime();
                    days = (int) (diff / (1000*60*60*24));


                }
                if (days>30) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setTitle("Are you sure?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Query query = databaseReferenceBlood;

                            query.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot bloodSnapshot : dataSnapshot.getChildren()) {
                                        BloodBank bloodBank = bloodSnapshot.getValue(BloodBank.class);
                                        if (bloodGroup.equals(bloodBank.getBloodGroup())) {
                                            bloodBankId = bloodBank.getId();
                                            bloodAmount = bloodBank.getAmount();
                                            flag = 1;

                                            donor.setLastdonated(endDate.toString());
                                            databaseReference.setValue(donor);

                                            break;
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            if (flag == 0) {

                                String id = databaseReference.push().getKey();

                                BloodBank bloodBank = new BloodBank(id, bloodGroup, 1);

                                databaseReferenceBlood.child(id).setValue(bloodBank).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            Toast.makeText(DonorHome.this, "Donation Successful", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(DonorHome.this, "Error Occurred.Please try again!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                            } else {

                                bloodAmount = bloodAmount + 1;

                                BloodBank bloodBank = new BloodBank(bloodBankId, bloodGroup, bloodAmount);

                                databaseReferenceBlood.child(bloodBankId).setValue(bloodBank).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(DonorHome.this, "Donation Successful", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(DonorHome.this, "Error Occurred.Please try again!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                        }
                    });


                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();

                }else {
                    Toast.makeText(this, "You cannot donate more than once in a month", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }
}

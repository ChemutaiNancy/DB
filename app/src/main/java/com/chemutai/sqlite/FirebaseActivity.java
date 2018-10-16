package com.chemutai.sqlite;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import dmax.dialog.SpotsDialog;

public class FirebaseActivity extends AppCompatActivity {

    MaterialEditText inputNames, inputEmail, inputBalance;
    SpotsDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        inputNames = findViewById(R.id.inputNames);
        inputEmail = findViewById(R.id.inputEmail);
        inputBalance = findViewById(R.id.inputBalance);

        progress = new SpotsDialog(this);
//        progress.setMessage("Saving");

    }

    public void Save(View view) {
        String names = inputNames.getText().toString();
        String email = inputEmail.getText().toString();
        String bal = inputBalance.getText().toString();

        if (names.isEmpty() || email.isEmpty() || bal.isEmpty()){
            Toast.makeText(this, "Fill all the values", Toast.LENGTH_SHORT).show();
            return;
        }

        double balance = Double.parseDouble(bal);
        Customer c = new Customer(names, email, balance);
        progress.show();

        DatabaseReference fb= FirebaseDatabase.getInstance().getReference("customers/"+System.currentTimeMillis());//connect to db
        fb.setValue(c).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(FirebaseActivity.this, "Customer saved", Toast.LENGTH_SHORT).show();
                inputNames.setText("");
                inputEmail.setText("");
                inputBalance.setText("");
                progress.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FirebaseActivity.this, "Customer failed to save", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });//save

    }


    public void Fetch(View view) {

        Intent i = new Intent(this, FirebaseDisplayActivity.class);
        startActivity(i);
    }
}

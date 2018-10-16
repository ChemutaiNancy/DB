package com.chemutai.sqlite;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class FirebaseDisplayActivity extends AppCompatActivity {

    RecyclerView firebaseRecycler;
    ArrayList<Customer> customers;
    FirebaseCustomAdapter adapter;
    SpotsDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_display);

        firebaseRecycler = findViewById(R.id.firebaseRecycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        firebaseRecycler.setLayoutManager(manager);
        progress = new SpotsDialog(this);


        customers = new ArrayList<>();
        adapter = new FirebaseCustomAdapter(customers, this);
        firebaseRecycler.setAdapter(adapter);
        progress.show();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("customers");//connect to db
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                customers.clear();

                for (DataSnapshot s: dataSnapshot.getChildren())
                {
                   Customer c = s.getValue(Customer.class);//value to be converted to customer
                    c.setId(Long.parseLong(s.getKey()));
                   customers.add(c);
                   progress.dismiss();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FirebaseDisplayActivity.this, "Error while fetching", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });//fetching data

     /*   ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });does fetching once*/


    }

}

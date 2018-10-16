package com.chemutai.sqlite;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {

    RecyclerView customersRecyclerView;
    ArrayList<Customer> customers;
    Database db;
    CustomerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        customersRecyclerView = findViewById(R.id.customersList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        customersRecyclerView.setLayoutManager(manager);


        db = new Database(this);
        customers = db.show();
        adapter = new CustomerAdapter(customers, this);
        customersRecyclerView.setAdapter(adapter);

        /*update code - firebase.database().ref('users/' + userId).set({
                username: name,
                email: email,
                profile_picture : imageUrl*/
  };

}


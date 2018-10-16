package com.chemutai.sqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {

    MaterialEditText inputNames, inputEmail, inputBalance;
    Database db;//declaring db as global variable
    RecyclerView delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputNames = findViewById(R.id.inputNames);
        inputEmail = findViewById(R.id.inputEmail);
        inputBalance = findViewById(R.id.inputBalance);


        db = new Database(this);//instantiate database

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
        db.insert(c);

        inputNames.setText("");
        inputEmail.setText("");
        inputBalance.setText("");

    }

    public void Show(View view) {
        db.show();
        Toast.makeText(this, "Count is "+db.count(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, DisplayActivity.class);
        startActivity(intent);
    }

    public void firebase(View view) {
        Intent firebase = new Intent(this, FirebaseActivity.class);
        startActivity(firebase);
    }

    public void phpMysql(View view) {
        Intent phpMysql = new Intent(this, MySqlActivity.class);
        startActivity(phpMysql);
    }
}

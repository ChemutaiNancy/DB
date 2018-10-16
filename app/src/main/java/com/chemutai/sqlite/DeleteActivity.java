package com.chemutai.sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

public class DeleteActivity extends AppCompatActivity {

    TextView inputNames, inputEmail, inputBalance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        inputNames = findViewById(R.id.inputNames);
        inputEmail = findViewById(R.id.inputEmail);
        inputBalance = findViewById(R.id.inputBalance);


    }
}

package com.chemutai.sqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.rengwuxian.materialedittext.MaterialEditText;

import cz.msebera.android.httpclient.Header;
import dmax.dialog.SpotsDialog;

public class MySqlActivity extends AppCompatActivity {

    MaterialEditText inputNames, inputEmail, inputBalance;
    SpotsDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sql);

        inputNames = findViewById(R.id.inputNames);
        inputEmail = findViewById(R.id.inputEmail);
        inputBalance = findViewById(R.id.inputBalance);

        progress = new SpotsDialog(this);
    }

    public void save(View view) {
        String names = inputNames.getText().toString();
        String email = inputEmail.getText().toString();
        String bal = inputBalance.getText().toString();

        if (names.isEmpty() || email.isEmpty() || bal.isEmpty()){
            Toast.makeText(this, "Fill all the values", Toast.LENGTH_SHORT).show();
            return;
        }

        double balance = Double.parseDouble(bal);

        progress.show();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();//package data in to a form
        params.put("names", names);
        params.put("email", email);
        params.put("balance", balance);

        String url = "http://jistymarketer.com/nnc/save.php";

        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(MySqlActivity.this, "Failed to save, try again", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                progress.dismiss();
                Toast.makeText(MySqlActivity.this, "Result "+responseString, Toast.LENGTH_SHORT).show();

                if (responseString.contains("failed")){
                    Toast.makeText(MySqlActivity.this, "Failed to save, try again", Toast.LENGTH_SHORT).show();
                }
                else{
                    inputNames.setText("");
                    inputEmail.setText("");
                    inputBalance.setText("");

                    Toast.makeText(MySqlActivity.this, "Record inserted successfully", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public void fetch(View view) {
        Intent intent = new Intent(this, MySqlDisplayActivity.class);//navigate from this activity to MySqlDiaplayActivity
        startActivity(intent);
    }
}

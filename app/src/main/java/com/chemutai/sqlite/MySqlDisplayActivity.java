package com.chemutai.sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import dmax.dialog.SpotsDialog;

public class MySqlDisplayActivity extends AppCompatActivity {

    RecyclerView mySqlRecycler;
    ArrayList<Customer> customers;
    MySqlCustomAdapter  mySqlAdapter;
    SpotsDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sql_display);

        mySqlRecycler = findViewById(R.id.firebaseRecycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mySqlRecycler.setLayoutManager(manager);
        progress = new SpotsDialog(this);
        customers = new ArrayList<>();
        mySqlAdapter = new MySqlCustomAdapter(customers,this);
        mySqlRecycler.setAdapter(mySqlAdapter);
        fetch();
    }

    private void fetch() {
        String url = "http://jistymarketer.com/nnc/fetch.php";
        AsyncHttpClient client = new AsyncHttpClient();
        progress.show();
        client.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progress.dismiss();
                Toast.makeText(MySqlDisplayActivity.this, "failed to fetch", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("MSQL", "onSuccess: "+responseString);
                progress.dismiss();
                try {
                    JSONArray array = new JSONArray(responseString);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        long id = obj.getLong("customer_id");
                        String names = obj.getString("names");
                        String email = obj.getString("email");
                        Double balance = obj.getDouble("balance");

                        Customer c = new Customer(id, names, email, balance);

                        customers.add(c);
                    }

                    mySqlAdapter.notifyDataSetChanged();//refresh
                } catch (JSONException e) {
                    Toast.makeText(MySqlDisplayActivity.this, "Error while processing", Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
                }
            }
        });
    }
}

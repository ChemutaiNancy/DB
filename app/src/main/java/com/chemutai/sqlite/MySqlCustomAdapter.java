package com.chemutai.sqlite;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import dmax.dialog.SpotsDialog;

public class MySqlCustomAdapter extends RecyclerView.Adapter<MySqlCustomAdapter.MyViewHolder> {

    ArrayList<Customer> customerList;
    Context context;
    SpotsDialog progress;


    public MySqlCustomAdapter(ArrayList<Customer> customerList, Context context) {
        this.customerList = customerList;
        this.context = context;

    }

    @NonNull
    @Override
    public MySqlCustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_delete, parent, false);//display(inflate)
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MySqlCustomAdapter.MyViewHolder holder, final int position) {
        final Customer c = customerList.get(position);
        holder.inputNames.setText(c.getNames());
        holder.inputEmail.setText(c.getEmail());
        holder.inputBalance.setText(""+ c.getBalance());

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Delete customer "+c.getNames());
                dialog.setMessage("Are you sure you want to delete?");

                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(context, "Deleted user " + c.getNames(), Toast.LENGTH_SHORT).show();
                        customerList.remove(c);

                        //delete record in db
                        AsyncHttpClient client = new AsyncHttpClient();
                        RequestParams params = new RequestParams();
                        params.put("id", c.getId());

                        final SpotsDialog progress = new SpotsDialog(context);

                        String url = "http://jistymarketer.com/nnc/delete.php";

                        progress.show();
                        client.post(url, params, new TextHttpResponseHandler() {
                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                progress.dismiss();
                                Toast.makeText(context, "Failed to delete the record", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                Toast.makeText(context, "Record deleted", Toast.LENGTH_SHORT).show();
                                progress.dismiss();
                            }
                        });

                        notifyDataSetChanged();
                    }
                });

                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                dialog.show();
            }
        });

        holder.inputNames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Edit Customer "+c.getNames());
                View root= LayoutInflater.from(context).inflate(R.layout.update_layout, null, false);//display(inflate
                final MaterialEditText edtNames = root.findViewById(R.id.inputNames);
                final MaterialEditText edtEmail = root.findViewById(R.id.inputEmail);
                final MaterialEditText edtBal = root.findViewById(R.id.inputBalance);

                edtNames.setText(c.getNames());
                edtEmail.setText(c.getEmail());
                edtBal.setText(""+c.getBalance());

                dialog.setView(root);


                dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String names = edtNames.getText().toString();
                        String email = edtEmail.getText().toString();
                        String bal = edtBal.getText().toString();

                        if (names.isEmpty() || email.isEmpty() || bal.isEmpty()){
                            Toast.makeText(context, "Fill all the values", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        double balance = Double.parseDouble(bal);

                        c.setNames(names);
                        c.setEmail(email);
                        c.setBalance(balance);

                        notifyDataSetChanged();//refresh the list

                    }
                });

                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView inputNames, inputEmail, inputBalance;// declare items that you have in your layout
        ImageView imgDelete;

        public MyViewHolder(View customerView){

            super(customerView);

            inputNames = customerView.findViewById(R.id.inputNames);
            inputEmail = customerView.findViewById(R.id.inputEmail);
            inputBalance = customerView.findViewById(R.id.inputBalance);
            imgDelete = customerView.findViewById(R.id.imgDelete);
        }
    }
}

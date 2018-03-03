package com.example.root.smartcart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Bill extends AppCompatActivity {

    TextView quantity,price,total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        quantity = (TextView) findViewById(R.id.quantity);
        price = (TextView) findViewById(R.id.price);
        total = (TextView) findViewById(R.id.total);


        try{
            Bundle bundle = getIntent().getExtras();
            String qty= bundle.getString("quantity");
            String prr= bundle.getString("price");
            quantity.setText(qty);
            price.setText(prr);
            long qty2=Long.parseLong(qty.toString());
            long prr2=Long.parseLong(prr.toString());
            long tota= qty2*prr2;
            total.setText(prr);
           // price.setText(Long.toString(tota));
        }
        catch (Exception e){
            Log.e("Error is: ",e.getMessage());
        }

    }
}

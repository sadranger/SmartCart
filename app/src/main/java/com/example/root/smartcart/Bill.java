package com.example.root.smartcart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Bill extends AppCompatActivity {

    Button checkout;
    public int SQty,SQty2;


    TextView quantity,price,quantity2,price2,total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        quantity = (TextView) findViewById(R.id.quantity);
        price = (TextView) findViewById(R.id.price);
        total = (TextView) findViewById(R.id.total);
        quantity2 = (TextView) findViewById(R.id.quantity2);
        price2 = (TextView) findViewById(R.id.price2);


        try{
            Bundle bundle = getIntent().getExtras();
            int qty= bundle.getInt("quantity");
            int qty2= bundle.getInt("quantity2");
            String prr2= bundle.getString("price2");
            String prr= bundle.getString("price");
            quantity.setText(Integer.toString(Shopping.MuffQty));
            quantity2.setText(Integer.toString(Shopping.ChocoQty));
            price2.setText("20");
            /*this.SQty=qty;
            int SPrr= Integer.parseInt(prr.toString());
            this.SQty2=qty2;
            int SPrr2= Integer.parseInt(prr2);*/
            int Total= Shopping.ChocoQty*20+Shopping.MuffQty*45;
            price.setText("45");
            total.setText(Integer.toString(Total));
        }
        catch (Exception e){
            Log.e("Error is: ",e.getMessage());
        }

        checkout = (Button) findViewById(R.id.checkout);


        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checkOut = new Intent(Bill.this,CheckOut.class);
                Bundle checking = new Bundle();
                checking.putInt("measure1",SQty);
                checking.putInt("measure",SQty2);
                checkOut.putExtras(checking);
                startActivity(checkOut);
            }
        });

    }
}

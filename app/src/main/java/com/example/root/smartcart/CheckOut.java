package com.example.root.smartcart;

import android.bluetooth.BluetoothAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CheckOut extends AppCompatActivity {

    private BluetoothAdapter bluetooth;
    Button takeout,bDisable;
    TextView Qty1,Qty2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        bluetooth = BluetoothAdapter.getDefaultAdapter();


        takeout  = (Button) findViewById(R.id.takeout);
        bDisable = (Button) findViewById(R.id.bDisable);
        Qty1 = (TextView) findViewById(R.id.qty1);
        Qty2 = (TextView) findViewById(R.id.qty2);


        Bundle checking = getIntent().getExtras();

        int qty1 = checking.getInt("measure1");
        int qty2 = checking.getInt("measure2");


        Qty1.setText(Integer.toString(Shopping.MuffQty));
        Qty2.setText(Integer.toString(Shopping.ChocoQty));
        bDisable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bluetooth.isEnabled()){
                    bluetooth.disable();

                }
                else{
                    Toast.makeText(CheckOut.this, "Bluetooth is already turned off!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}

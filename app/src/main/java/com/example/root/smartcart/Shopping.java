package com.example.root.smartcart;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Shopping extends AppCompatActivity {

    Button throwin,throwout,bt,done;
    TextView nameT,priceT,offersT;
    int REQUEST_ENABLE_BT=1;
    BluetoothAdapter bAdapter;
    Set<BluetoothDevice> pairedDevices;
    String plist[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        throwin = (Button) findViewById(R.id.throwin);
        throwout = (Button) findViewById(R.id.throwout);
        done = (Button) findViewById(R.id.done);
        bt = (Button) findViewById(R.id.bluetooth);
        nameT = (TextView) findViewById(R.id.nameres);
        priceT = (TextView)  findViewById(R.id.priceres);
        offersT = (TextView) findViewById(R.id.offersres);

        try
        {
            Bundle bundle = getIntent().getExtras();
            String name = bundle.getString("name");
            String price = bundle.getString("price");
            String offers = bundle.getString("offers");
            nameT.setText(name);
            priceT.setText(price);
            offersT.setText(offers);
        }
        catch(Exception e ){
            Log.e("error is: ",e.getMessage());
        }
        try {
            Bundle bundle2 = getIntent().getExtras();
            String name = bundle2.getString("name");
            String price = bundle2.getString("price");
            String offers = bundle2.getString("offers");
            nameT.setText(name);
            priceT.setText(price);
            offersT.setText(offers);
        }
        catch (Exception e){
          //  Toast.makeText(this, "Failed to get details!!", Toast.LENGTH_SHORT).show();
            Log.e("Error is:",e.getMessage());
        }


        throwin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Shopping.this, BarcodeScanner.class);
                startActivity(intent);
            }
        });
        throwout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Shopping.this, BarcodeScanner.class);
                startActivity(intent);
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bAdapter = BluetoothAdapter.getDefaultAdapter();

                if(bAdapter==null){

                    Toast.makeText(Shopping.this, "Bluetooth is not available on this device!!", Toast.LENGTH_SHORT).show();


                }
                else{

                if(!bAdapter.isEnabled()){

                    Intent startBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(startBluetooth,REQUEST_ENABLE_BT);

                }

                }

            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Shopping.this);
                builder.setTitle("Are you sure to end shopping?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent (Shopping.this,Bill.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Shopping.this,Shopping.class);
                        startActivity(intent);
                    }
                });
                AlertDialog alert= builder.create();
                alert.show();
            }
        });

    }




    public void onActivityResult(int requestcode,int resultcode,Intent data){

        if(requestcode==REQUEST_ENABLE_BT){

            if(resultcode==RESULT_OK){

                Toast.makeText(this, "Bluetooth successfully turned ON!", Toast.LENGTH_SHORT).show();
                pairedDevices = bAdapter.getBondedDevices();
                int count = pairedDevices.size();
                plist = new String[count];
                int i=0;
                for(BluetoothDevice device : pairedDevices){

                    plist[i]=device.getName().toString();
                    i++;

                }
                Bundle bn = new Bundle();
                bn.putStringArray("paired",plist);
                Intent pass = new Intent("paired_dev");
                pass.putExtras(bn);
                startActivity(pass);

            }
        }

    }
}


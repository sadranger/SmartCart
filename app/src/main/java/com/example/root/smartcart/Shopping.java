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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Shopping extends AppCompatActivity {

    Button throwin,throwout,bt,done;
    TextView nameT,priceT,offersT,qtyT;
    BluetoothAdapter bAdapter;
    Set<BluetoothDevice> pairedDevices;
    String plist[];
    public static int measure1,measure2,qty,qty2;
    public final int REQ_CODE_FOR_THROW_IN=2,REQ_CODE_FOR_THROW_OUT=3,REQUEST_ENABLE_BT=1;


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
        qtyT = (TextView) findViewById(R.id.quantityres);

        try
        {
            Bundle bundle = getIntent().getExtras();
            String name = bundle.getString("name");
            String price = bundle.getString("price");
            String offers = bundle.getString("offers");
            qty=bundle.getInt("qty");
            qty2=bundle.getInt("qty2");
            int key = bundle.getInt("key");
            this.measure1 = qty;
            this.measure2 = qty2;
            if(key==1){
                qtyT.setText(Integer.toString(measure1));
                nameT.setText(name);
                priceT.setText(price);
                offersT.setText(offers);

            }
            else{

                qtyT.setText(Integer.toString(measure2));
                nameT.setText(name);
                priceT.setText(price);
                offersT.setText(offers);
            }
        }
        catch (Exception e){
            Log.e("Error is:",e.getMessage());
        }

        throwin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Shopping.this, BarcodeScanner.class);
                startActivityForResult(intent,REQ_CODE_FOR_THROW_IN);
            }
        });
        throwout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(Shopping.this, BarcodeScannerThrowOut.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("measure1",measure1);
                    bundle.putInt("measure2",measure2);
                    intent2.putExtras(bundle);
                    startActivityForResult(intent2,REQ_CODE_FOR_THROW_OUT);

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
                        Bundle bundle =new Bundle();
                        bundle.putInt("quantity",measure1);
                        bundle.putString("price","45");
                        bundle.putString("price2","20");
                        bundle.putInt("quantity",measure2);

                        Intent intent = new Intent (Shopping.this,Bill.class);
                        intent.putExtras(bundle);
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


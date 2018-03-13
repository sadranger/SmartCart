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
import java.util.concurrent.ConcurrentHashMap;

public class Shopping extends AppCompatActivity {

    Button throwin,throwout,bt,done;
    TextView nameT,priceT,offersT,qtyT;
    BluetoothAdapter bAdapter;
    Set<BluetoothDevice> pairedDevices;
    String plist[];
    public static int MuffQty,ChocoQty;
    public final int REQ_CODE_FOR_THROW_IN=2,REQ_CODE_FOR_THROW_OUT=3,REQUEST_ENABLE_BT=1,REQ_CODE_FOR_BILL=4;


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


        throwin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent GO_TO_SCANNER = new Intent(getApplicationContext(), BarcodeScanner.class);
                startActivityForResult(GO_TO_SCANNER,REQ_CODE_FOR_THROW_IN);
            }
        });
        throwout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent GO_TO_SCANNER = new Intent(getApplicationContext(), BarcodeScannerThrowOut.class);
                GO_TO_SCANNER.putExtra("MuffQty",MuffQty);
                GO_TO_SCANNER.putExtra("ChocoQty", ChocoQty);
                startActivityForResult(GO_TO_SCANNER,REQ_CODE_FOR_THROW_OUT);

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
                        Intent GO_TO_BILL= new Intent (getApplicationContext(),Bill.class);
                        GO_TO_BILL.putExtra("MuffQty",MuffQty);
                        GO_TO_BILL.putExtra("ChocoQty",ChocoQty);
                        startActivity(GO_TO_BILL);
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
        else if(requestcode==REQ_CODE_FOR_THROW_IN){

            if(resultcode==RESULT_OK){
                int PassedKeyForThrowIn =data.getIntExtra("key",0);
                if(PassedKeyForThrowIn==1){
                    if(MuffQty>0){
                        nameT.setText("Muffins");
                        priceT.setText("45");
                        qtyT.setText(Integer.toString(MuffQty));
                        offersT.setText(" ");
                    }

                }
                if(PassedKeyForThrowIn==2){
                    if(ChocoQty>0){
                        nameT.setText("Chocolates");
                        priceT.setText("20");
                        qtyT.setText(Integer.toString(ChocoQty));
                        offersT.setText(" ");
                    }

                }
                if(PassedKeyForThrowIn==0){
                    Toast.makeText(this, "Failed to fetch details!!!", Toast.LENGTH_SHORT).show();
                }


            }


        }
        else if(requestcode==REQ_CODE_FOR_THROW_OUT){
            if(resultcode==RESULT_OK){
                int PassedKeyForThrowOut=data.getIntExtra("key",0);
                if(PassedKeyForThrowOut==1){
                    if(MuffQty<=0){
                        nameT.setText("0");
                        priceT.setText("0");
                        offersT.setText(" " );
                        qtyT.setText("0");

                    }
                    else {
                        nameT.setText("Muffins");
                        offersT.setText(" ");
                        priceT.setText("45");
                        qtyT.setText(Integer.toString(MuffQty));
                    }

                }
                if(PassedKeyForThrowOut==2){
                    if(ChocoQty<=0){
                        nameT.setText("0");
                        priceT.setText("0");
                        offersT.setText(" ");
                        qtyT.setText("0");

                    }
                    else {
                        nameT.setText("Chocolates");
                        priceT.setText("20");
                        offersT.setText(" ");
                        qtyT.setText(Integer.toString(ChocoQty));
                    }

                }
                if(PassedKeyForThrowOut==0){
                    Toast.makeText(this, "Failed to fetch details!!!", Toast.LENGTH_SHORT).show();
                }


            }

        }

    }
}


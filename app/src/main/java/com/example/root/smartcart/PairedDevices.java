package com.example.root.smartcart;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PairedDevices extends AppCompatActivity {

   ListView devices;
   String [] paired;
   ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paired_devices);

        devices=(ListView) findViewById(R.id.devices);


        Bundle bn = getIntent().getExtras();
        paired = bn.getStringArray("paired");

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,paired);
        devices.setAdapter(adapter);

    }
}

package com.example.root.smartcart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ScanTrolley extends AppCompatActivity {

    Button scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_trolley);

        scan=(Button) findViewById(R.id.scant);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ScanTrolley.this,QrScanner.class);
                try{
                    startActivity(intent);
                }
                catch(Exception e){
                    Log.e("Error is ", e.getMessage());
                }
            }
        });
    }


}

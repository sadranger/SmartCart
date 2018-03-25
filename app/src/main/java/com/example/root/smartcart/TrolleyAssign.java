package com.example.root.smartcart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TrolleyAssign extends AppCompatActivity {

    Button shop,scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trolley_assign);

        shop = (Button) findViewById(R.id.shopping);
        scan = (Button) findViewById(R.id.scant);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scanTrolley=new Intent(TrolleyAssign.this,QrScanner.class);
                startActivity(scanTrolley);
            }
        });
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrolleyAssign.this,Shopping.class);
                startActivity(intent);
            }
        });
    }
}

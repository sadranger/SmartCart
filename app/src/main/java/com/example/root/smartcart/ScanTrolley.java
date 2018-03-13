package com.example.root.smartcart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ScanTrolley extends AppCompatActivity {

    Button scan;
    public final int REQ_FOR_SCANNER=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_trolley);

        scan=(Button) findViewById(R.id.scant);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GO_TO_SCANNER=new Intent(ScanTrolley.this,QrScanner.class);
                try{
                    startActivityForResult(GO_TO_SCANNER,REQ_FOR_SCANNER);
                }
                catch(Exception e){
                    Log.e("Error is ", e.getMessage());
                }
            }
        });
    }

    public void onActivityResult(int requestcode,int resultcode,Intent data){

        if(requestcode==1){
            if(resultcode==RESULT_OK){
                Intent GO_TO_ASSIGN = new Intent(ScanTrolley.this,TrolleyAssign.class);
                startActivity(GO_TO_ASSIGN);
            }
        }


    }


}

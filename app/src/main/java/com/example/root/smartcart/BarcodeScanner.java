package com.example.root.smartcart;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;
import static android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK;

/**
 * Created by root on 2/3/18.
 */

public class BarcodeScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private static int camId = CAMERA_FACING_BACK;
    public static int measure1=0,measure2=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        int currentApiVersion = Build.VERSION.SDK_INT;

        if(currentApiVersion >=  Build.VERSION_CODES.M)
        {
            if(checkPermission())
            {
                //Toast.makeText(getApplicationContext(), "Permission already granted!", Toast.LENGTH_LONG).show();
            }
            else
            {
                requestPermission();
            }
        }
    }

    private boolean checkPermission()
    {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    @Override
    public void onResume() {
        super.onResume();

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if(scannerView == null) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            } else {
                requestPermission();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(BarcodeScanner.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void handleResult(Result result) {
        String src1,src2,res;
        res=result.getText().toString();
        src1="123456";
        src2="654321";

        if(src1.equals(res)) {
            measure1+=1;

            Intent intent = new Intent(BarcodeScanner.this,Shopping.class);
            Bundle bundle = new Bundle();
            bundle.putString("name","Muffins");
            bundle.putString("price","45");
            bundle.putInt("qty",measure1);
            bundle.putString("offers"," ");
            bundle.putInt("key",1);
           // bundle.putInt("measure",measure1);
            intent.putExtras(bundle);
            startActivity(intent);


        }
        else if(src2.equals(res)){
            measure2+=1;

            Intent intent = new Intent(BarcodeScanner.this,Shopping.class);
            //Intent intent2 = new Intent(BarcodeScanner.this,BarcodeScannerThrowOut.class);
            Bundle bundle = new Bundle();
            bundle.putString("name","Chocolate");
            bundle.putString("price","20");
            bundle.putInt("qty2",measure2);
            bundle.putString("offers"," ");
            bundle.putInt("key",2);
            //bundle.putInt("measure",measure2);
            intent.putExtras(bundle);
           // intent2.putExtras(bundle2);
           // startActivity(intent2);
            startActivity(intent);

        }
        else{
            Intent intent = new Intent(BarcodeScanner.this,Shopping.class);
            startActivity(intent);
            Toast.makeText(this, "Item out of stock. Inconvenience caused is deeply regretted!!", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Failed to get details!!", Toast.LENGTH_SHORT).show();
        }


    }
}


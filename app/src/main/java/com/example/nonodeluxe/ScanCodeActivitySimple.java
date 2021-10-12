package com.example.nonodeluxe;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.nonodeluxe.model.PrdItem;
import com.google.zxing.Result;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivitySimple extends AppCompatActivity implements ZXingScannerView.ResultHandler, View.OnTouchListener {
    int MY_PERMISSION_REQUEST_CAMERA = 0;
    ZXingScannerView scannerView;

    ArrayList<PrdItem> prdItems;
    PrdItem prdItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        scannerView.setOnTouchListener(this);
    }

    @Override
    public void handleResult(Result result) {
        PrdNewActivity.edt_barcode.setText(result.getText());
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
        this.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) !=
        PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},MY_PERMISSION_REQUEST_CAMERA);

        }
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        scannerView.toggleFlash();
        return false;
    }
}
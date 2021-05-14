package com.example.nonodeluxe;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.nonodeluxe.model.PrdItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler, View.OnTouchListener {
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

        prdItems = (ArrayList<PrdItem>) getIntent().getSerializableExtra("ItemList");

        scannerView.setOnTouchListener(this);
    }

    @Override
    public void handleResult(Result result) {
        onCheckList(result);
    }

    private void onCheckList(Result result){
        String resultBarcode;
        boolean check = false;

        try {
            resultBarcode = result.getText();

            for (PrdItem currentItem : prdItems){
                if (currentItem.getBarcode().equals(resultBarcode)){
                    check = true;
                    Intent outIntent = new Intent(getApplicationContext(),PrdListActivity.class);
                    outIntent.putExtra("prd_selected",currentItem);
//                    outIntent.putExtra("prd_name",currentItem.getName());
                    setResult(RESULT_OK,outIntent);
                    finish();
                    break;
                }
            }
            if (!check){
                showErrorDialog("등록되지 않은 바코드입니다.");
            }

        } catch (NumberFormatException e) {
            showErrorDialog(e.getMessage());
        } catch (Exception e){
            showErrorDialog(e.getMessage());
        }
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

    private void showErrorDialog(String errorMessage){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Error");
        dialog.setMessage(errorMessage);
        dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onResume();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        scannerView.toggleFlash();
        return false;
    }
}
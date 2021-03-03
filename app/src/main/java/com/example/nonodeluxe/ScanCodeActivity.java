package com.example.nonodeluxe;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.nonodeluxe.model.PrdItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    int MY_PERMISSION_REQUEST_CAMERA = 0;
    ZXingScannerView scannerView;

    PrdItem prdItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

//        scannerView.setOnTouchListener(this);
    }

    @Override
    public void handleResult(Result result) {
//        MainActivity.txt_view.setText(result.getText());
//        PrdListActivity.currentPrdName = result.getText();
        onSearchResult(result);
//        if () {
//            Toast.makeText(getApplicationContext(),prdItem.getName(),Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(getApplicationContext(),"이건 틀렸오",Toast.LENGTH_SHORT).show();
//        }


        onBackPressed();
    }

    private void onSearchResult(Result result) {

        FirebaseDatabase.getInstance().getReference()
                .child("real").child("product").orderByChild("barcode").equalTo(Integer.parseInt(result.getText()))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            prdItem = dataSnapshot.getValue(PrdItem.class);

                            String currentPrdName = null;
                            if (prdItem != null) {
                                currentPrdName = prdItem.getName();
                                Intent intent = new Intent(getApplicationContext(),PrdInfoActivity.class);
                                intent.putExtra("prd_name",currentPrdName);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(),"잘못됨",Toast.LENGTH_SHORT).show();
                                break;
                            }
//                            Toast.makeText(getApplicationContext(),currentPrdName,Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();


        this.onBackPressed();
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

//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent) {
//        scannerView.toggleFlash();
//        return false;
//    }
}
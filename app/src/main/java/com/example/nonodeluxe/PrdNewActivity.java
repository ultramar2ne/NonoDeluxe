package com.example.nonodeluxe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nonodeluxe.model.PrdItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PrdNewActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    ArrayAdapter<String> arrayAdapter;

    private ArrayList<PrdItem> prdItems = new ArrayList<>();

    ImageButton btn_scanner;
    Button btn_save;
    Spinner btn_category;
    public static EditText edt_barcode;
    EditText edt_name, edt_standard;

    private String[] category = {"원재료","시럽/소스","파우더/라떼","일회용품"};
    private int currentCategory = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prd_new);

        setPrdData();

        btn_scanner = (ImageButton)findViewById(R.id.prdNew_btn_barcode);
        btn_save = (Button)findViewById(R.id.prdNew_btn_save);
        edt_name = (EditText)findViewById(R.id.prdNew_edt_name);
        edt_barcode = (EditText)findViewById(R.id.prdNew_edt_barcode);
        edt_standard = (EditText)findViewById(R.id.prdNew_edt_standard);

        btn_scanner.setOnClickListener(this);
        btn_save.setOnClickListener(this);

        btn_category = (Spinner)findViewById(R.id.prdNew_btn_category);
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.style_spinner,category);
        btn_category.setAdapter(arrayAdapter);
        btn_category.setOnItemSelectedListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btn_scanner) {
            Intent intent = new Intent(getApplicationContext(),ScanCodeActivitySimple.class);
            startActivity(intent);
//            startActivityForResult(intent,0);
//            intent.putExtra("ItemList",prdItems);
//            startActivityForResult(intent,0);
        }
        if (v == btn_save) {
            if (!chkNullPointer()){
                Toast.makeText(getApplicationContext(),"위의 내용을 모두 입력해주세요.",Toast.LENGTH_SHORT).show();
            }else if (!chkOverlap()){
                Toast.makeText(getApplicationContext(),"이미 있는 상품입니다.",Toast.LENGTH_SHORT).show();
            }else {
                addNewProduct();
                onBackPressed();
                Toast.makeText(getApplicationContext(),"저장되었습니다.",Toast.LENGTH_SHORT).show();
            }


//            try {
//                if (!chkNullPointer()){
//                    Toast.makeText(getApplicationContext(),"위의 내용을 모두 입력해주세요.",Toast.LENGTH_SHORT).show();
//                }else if (!chkOverlap()){
//                    Toast.makeText(getApplicationContext(),"이미 있는 상품입니다.",Toast.LENGTH_SHORT).show();
//                }else {
//                    addNewProduct();
//                    onBackPressed();
//                    Toast.makeText(getApplicationContext(),"저장되었습니다.",Toast.LENGTH_SHORT).show();
//                }
//            } catch (NumberFormatException e){
//                Toast.makeText(getApplicationContext(),"바코드는 숫자형식으로 입력해주세요.",Toast.LENGTH_SHORT).show();
//            }

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        currentCategory = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setPrdData() {
        prdItems.clear();
        FirebaseDatabase.getInstance().getReference()
                .child("real").child("product")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot currentSnapshot : snapshot.getChildren()){
                            PrdItem currentItem = currentSnapshot.getValue(PrdItem.class);

                            prdItems.add(currentItem);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private boolean chkOverlap(){
        for (int i = 0; i < prdItems.size() ; i++){
            if (!prdItems.get(i).getName().equals(edt_name.getText().toString())){
                if (prdItems.get(i).getBarcode().equals(edt_barcode.getText().toString())){
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean chkNullPointer(){
        return !edt_name.getText().toString().equals("") && !edt_barcode.getText().toString().equals("") && !edt_standard.getText().toString().equals("");
    }

    private void addNewProduct() {

        PrdItem prdItem = new PrdItem();
        prdItem.setName(edt_name.getText().toString());
        prdItem.setBarcode(edt_barcode.getText().toString());
        prdItem.setCategory(category[currentCategory]);
        prdItem.setStandard(edt_standard.getText().toString());

        FirebaseDatabase.getInstance().getReference()
                .child("real").child("product").child(String.valueOf(prdItems.size()))
                .setValue(prdItem);
    }

    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//    }
}
package com.example.nonodeluxe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText edt_id, edt_pw;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_id = (EditText) findViewById(R.id.login_edt_id);
        edt_pw = (EditText)findViewById(R.id.login_edt_pw);
        btn_login = (Button)findViewById(R.id.login_btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("info").child("employee").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String input_id = edt_id.getText().toString();
                        String input_pw = edt_pw.getText().toString();

                        if (snapshot.child(input_id).exists()){
                            if (snapshot.child(input_id).child("pw").getValue(String.class).equals(input_pw)){
                                    Preferences.setString(LoginActivity.this,"id",input_id);
                                    Preferences.setString(LoginActivity.this,"pw",input_pw);
                                    startActivity(new Intent(getApplicationContext(),EmpMainActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(),"비밀번호틀림",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),"없는아이디",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }


    protected void onStart() {
        super.onStart();
        edt_id.setText(Preferences.getString(LoginActivity.this,"id"));
        edt_pw.setText(Preferences.getString(LoginActivity.this,"pw"));

//            if (Preferences.getString(ExLoginActivity.this,"grade").equals("master")){
//                startActivity(new Intent(getApplicationContext(),ExEmpMainActivity.class));
//            }
//            if (Preferences.getString(ExLoginActivity.this, "grade").equals("emp")){
//                startActivity(new Intent(getApplicationContext(),ExEmpMainActivity.class));
//            }

    }
}
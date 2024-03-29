package com.example.nonodeluxe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nonodeluxe.model.UserItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edt_id, edt_pw;
    private Button btn_login;
    private UserItem userItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_id = (EditText) findViewById(R.id.login_edt_id);
        edt_pw = (EditText)findViewById(R.id.login_edt_pw);
        btn_login = (Button)findViewById(R.id.login_btn_login);

        btn_login.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        if(v == btn_login){
            FirebaseDatabase.getInstance().getReference()
                    .child("info").child("user")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String input_id = edt_id.getText().toString();
                            String input_pw = edt_pw.getText().toString();

                            //nullpointerException

                            if (snapshot.child(input_id).exists()){
                                if (snapshot.child(input_id).child("pw").getValue(String.class).equals(input_pw)){

                                    userItem = snapshot.child(input_id).getValue(UserItem.class);

                                    Preferences.setString(LoginActivity.this,"id",input_id);
                                    Preferences.setString(LoginActivity.this,"pw",input_pw);
                                    Preferences.setInt(LoginActivity.this,"unitCode", userItem.getUnit_code());
                                    Preferences.setString(LoginActivity.this,"unitName", userItem.getUnit_name());
                                    Preferences.setString(LoginActivity.this,"grade", userItem.getEmp_grade());

                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));

                                } else {
                                    Toast.makeText(getApplicationContext(),"비밀번호가 맞지 않습니다.",Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(),"아이디를 확인해 주세요.",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }
    }
}
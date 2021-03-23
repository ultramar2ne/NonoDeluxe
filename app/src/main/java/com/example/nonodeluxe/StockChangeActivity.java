package com.example.nonodeluxe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StockChangeActivity extends AppCompatActivity implements View.OnClickListener {

    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private Date mDate = new Date(System.currentTimeMillis());

    int mYear, mMonth, mDay;

    Button btn_search;
    ImageButton btn_scanner;
    Button btn_type;
    Button btn_changeStock;
    Button btn_date;
    Button btn_add;
    ImageButton btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_change);

        btn_search = (Button)findViewById(R.id.change_btn_search);
        btn_type = (Button)findViewById(R.id.change_btn_type);
        btn_changeStock = (Button)findViewById(R.id.change_btn_changeStock);
        btn_date = (Button)findViewById(R.id.change_btn_date);
        btn_add = (Button)findViewById(R.id.change_btn_add);
        btn_scanner = (ImageButton)findViewById(R.id.change_btn_barcode);
        btn_save = (ImageButton)findViewById(R.id.change_btn_save);

        btn_search.setOnClickListener(this);
        btn_type.setOnClickListener(this);
        btn_changeStock.setOnClickListener(this);
        btn_date.setOnClickListener(this);
        btn_date.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_scanner.setOnClickListener(this);
        btn_save.setOnClickListener(this);

        mYear = Integer.parseInt(new SimpleDateFormat("yyyy", Locale.KOREA).format(mDate));
        mMonth = Integer.parseInt(new SimpleDateFormat("MM",Locale.KOREA).format(mDate));
        mDay = Integer.parseInt(new SimpleDateFormat("dd",Locale.KOREA).format(mDate));

        btn_type.setText("입고");
        btn_date.setText(mYear + "/" + mMonth + "/" + mDay);

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                mYear = year;
                mMonth = month+1;
                mDay = day;
                btn_date.setText(mYear + "/" + mMonth + "/" + mDay);
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_btn_search:
                break;

            case R.id.change_btn_type:
                if (btn_type.getText().equals("입고"))
                    btn_type.setText("출고");
                else
                    btn_type.setText("입고");
                break;

            case R.id.change_btn_changeStock:
                break;

            case R.id.change_btn_date:
                DatePickerDialog dialog = new DatePickerDialog(this,onDateSetListener,mYear,(mMonth - 1),mDay);
                dialog.show();
                break;

            case R.id.change_btn_add:
                break;

            case R.id.change_btn_barcode:
                break;

            case R.id.change_btn_save:
                break;

            default:
                break;
        }
    }
}